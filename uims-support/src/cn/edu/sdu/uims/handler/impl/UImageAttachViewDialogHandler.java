package cn.edu.sdu.uims.handler.impl;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import javax.imageio.ImageIO;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import cn.edu.sdu.common.form.BaseAttachmentInfoForm;
import cn.edu.sdu.uims.UimsFactory;
import cn.edu.sdu.uims.component.label.UImageLabel;
import cn.edu.sdu.uims.component.method.GetFile;
import cn.edu.sdu.uims.form.impl.ImageAttachViewForm;
import cn.edu.sdu.uims.util.UimsUtils;

public class UImageAttachViewDialogHandler extends UDialogHandler{
	
	public ImageAttachViewForm getImageAttachViewForm() {
		return (ImageAttachViewForm) dataForm;
	}

	private void setImageData(BufferedImage img) {
		ImageAttachViewForm f = (ImageAttachViewForm) dataForm;
		f.setImageData(img);
		this.formToComponent();
	}

	public void processActionEvent(Object o, String cmd) {
		ActionEvent e = (ActionEvent) o;
		String amd = e.getActionCommand();
		if (amd.equals("doUpload")) {
			doUpload();
		} else if (amd.equals("leftScale")) {
			leftScale();
		} else if (amd.equals("rightScale")) {
			rightScale();
		} else if (amd.equals("doEnlarge")) {
			doEnlarge();
		} else if (amd.equals("doShrink")) {
			doShrink();
		}else if (amd.equals("doSave")) {
			doSave();
		} else if (amd.equals("doDelete")) {
			doDelete();
		} else if (amd.equals("doDownload")) {
			doDownload();
		} else {
			if (this.getImageAttachViewForm().isModify()) {
				int ret = UimsUtils.messageBoxChoice("图像已经修改需要保存吗！");
				if (ret == UimsUtils.YES_OPTION)
					doSave();
			}
			super.processActionEvent(o, cmd);
		}
	}

	public void doUpload() {
//		File file = GetFile.getOpenFile("png");
		File file = GetFile.getOpenFile();
		if (file == null)
			return;
		try {
			BufferedImage img = ImageIO.read(new FileInputStream(file));
			this.setImageData(img);
			this.getImageAttachViewForm().setModify(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void leftScale() {
		UImageLabel com = (UImageLabel) this.component
				.getSubComponent("imageDate");
		com.rotate(-90);
		BufferedImage img = com.getImg();
		((ImageAttachViewForm)dataForm).setImageData(img);
		this.getImageAttachViewForm().setModify(true);
	}

	public void rightScale() {
		UImageLabel com = (UImageLabel) this.component
				.getSubComponent("imageDate");
		com.rotate(90);
		BufferedImage img = com.getImg();
		((ImageAttachViewForm)dataForm).setImageData(img);
		this.getImageAttachViewForm().setModify(true);

	}

	public void doSave() {
		this.getImageAttachViewForm().setModify(false);
		ImageAttachViewForm f = this.getImageAttachViewForm();
		Integer attachId = f.getAttachId();
		BufferedImage img = f.getImageData();
		byte[] data = null;
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ImageIO.write(img, "png", out);
			out.flush();
			data = out.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (data == null)
			return;
		RmiRequest request = new RmiRequest();
		RmiResponse response;
		if (attachId == null) {
			if (f.getHandler() == null) {
				return;
			}
			BaseAttachmentInfoForm af = f.getHandler()
					.getBaseAttachmentInfoForm(f.getCmd());
			if (af == null) {
				return;
			}
			request.add("attachType", af.getAttachType());
			request.add("ownerId", af.getOwnerId());
			request.add("fileName", af.getFileName());
			request.add("folderName", af.getAttachFolder());
			request.add("fileData", data);
			request.add("isFileDataInDB", UimsFactory.getClientMainI().isFileDataInDB());
			request.setCmd("attachmentUpLoadAttachmentFile");
			response = UimsUtils.requestProcesser(request);
			if (response.getErrorMsg() != null) {
				UimsUtils.messageBoxInfo(response.getErrorMsg());
			} else {
				UimsUtils.messageBoxInfo("保存成功！");
				attachId = (Integer)response.get("attachId");
				f.setAttachId(attachId);
				f.getHandler().doAfterAttachSaved(f.getCmd(), attachId);
				if(UimsFactory.getImageDataDriverI() != null)
					UimsFactory.getImageDataDriverI().putImageDate(attachId, img);
			}
		} else {
			request.add("attachId", attachId);
			request.add("fileData", data);
			request.add("isFileDataInDB", UimsFactory.getClientMainI().isFileDataInDB());
			request.setCmd("attachmentReplaceAttachmentFileOfAttachId");
			response = UimsUtils.requestProcesser(request);
			if (response.getErrorMsg() != null) {
				UimsUtils.messageBoxInfo(response.getErrorMsg());
			} else {
				UimsUtils.messageBoxInfo("保存成功！");
				if(UimsFactory.getImageDataDriverI() != null)
					UimsFactory.getImageDataDriverI().putImageDate(attachId, img);
			}
		}
	}

	public void doDownload() {
		String fname = ((ImageAttachViewForm) dataForm).getFileName();
		File file;
		if (fname == null || fname.length() == 0) {
			file = GetFile.getSaveFile("png");
		} else {
			file = GetFile.getDirName();
			if (file == null)
				return;
			fname = file.getPath() + "//" + fname;
			file = new File(fname);
		}
		if (file == null)
			return;
		try {
			BufferedImage img = this.getImageAttachViewForm().getImageData();
			if (img == null)
				return;
			ImageIO.write(img, "png", new FileOutputStream(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void doDelete() {
		int ret = UimsUtils.messageBoxChoice("你确定要删除该照片么？");
		if(ret != 0)
			return;
		ImageAttachViewForm f = this.getImageAttachViewForm();
		Integer attachId = f.getAttachId();
		RmiRequest request = new RmiRequest();
		request.add("attachId", attachId);
		request.setCmd("attachmentDeleteAttachmentFile");
		RmiResponse respond = UimsUtils.requestProcesser(request);
		if(respond.getErrorMsg() != null) {
			UimsUtils.messageBoxInfo("照片删除成功！");
			return;
		}else {
			UimsUtils.messageBoxInfo(respond.getErrorMsg());
			return;
		}
	}
	
	public void doEnlarge() {
		
		
	}
	
	public void doShrink() {
		
	}
}
