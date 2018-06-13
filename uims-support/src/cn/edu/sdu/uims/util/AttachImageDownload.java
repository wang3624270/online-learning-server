package cn.edu.sdu.uims.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.List;

import javax.imageio.ImageIO;

import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import cn.edu.sdu.uims.UimsFactory;
import cn.edu.sdu.uims.component.complex.ImageCanvasProcessI;
import cn.edu.sdu.uims.pi.ImageDataDriverI;

public class AttachImageDownload extends Thread {

	private List<Integer> attachIdList;
	private ImageCanvasProcessI pi;
	private boolean isStop;
	private ImageDataDriverI driver;
	public void setStop(boolean isStop) {
		this.isStop = isStop;
	}

	public AttachImageDownload() {
		attachIdList = null;
		pi = null;
		
	}

	public AttachImageDownload(List<Integer> attachIdList,ImageDataDriverI driver,
			ImageCanvasProcessI pi) {
		this.attachIdList = attachIdList;
		this.pi = pi;
		this.driver = driver;
	}

	public void run() {
		if (attachIdList == null || attachIdList.size() == 0) {
			return;
		}
		isStop = false;
//		ImageDataDriverI driver = ClientMain.getInstance().getImgeDataDriver();
		if (driver == null)
			return;
		BufferedImage img;
		Integer attachId;
		RmiRequest request = new RmiRequest();
		RmiResponse respond;
		try {
			int i = 0;
			while(i < attachIdList.size() && !isStop) {
				attachId = attachIdList.get(i);
				if(!driver.isExistImageData(attachId)) {
					request = new RmiRequest();
					request.add("isCursor", false);
					request.add("attachId", attachId + "");
					request.add("isFileDataInDB", UimsFactory.getClientMainI().isFileDataInDB());
					request.setCmd("attachmentDownloadAttachmentFile");
					respond = UimsUtils.requestProcesser(request);
					if (null == respond.getErrorMsg()) {
						byte[] b = (byte[]) respond.get(RmiKeyConstants.KEY_BYTES);
						if (b != null) {
							img = ImageIO.read(new ByteArrayInputStream(b));
							driver.putImageDate(attachId, img);
							if (pi != null) {
								pi.repaintDrawImage();
							}
						}
					}
					sleep(100);
				}
				i++;
			}
		} catch (Exception e) {
//			e.printStackTrace();
		}
	}

}
