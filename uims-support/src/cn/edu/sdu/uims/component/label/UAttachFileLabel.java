package cn.edu.sdu.uims.component.label;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import cn.edu.sdu.common.form.BaseAttachmentInfoForm;
import cn.edu.sdu.uims.UimsFactory;
import cn.edu.sdu.uims.util.UimsUtils;


public class UAttachFileLabel extends ULabel implements MouseListener{
	
	private BaseAttachmentInfoForm attachForm;
	public void initContents() {
		// TODO Auto-generated method stub
		this.addMouseListener(this);
	}
	public void setData(Object obj){
		if(obj instanceof BaseAttachmentInfoForm)
			attachForm = (BaseAttachmentInfoForm)obj;
		else
			attachForm = null;
		if(attachForm != null)
			this.setText(attachForm.getFileName());
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		displayAttachFile();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	void displayAttachFile(){
		if(attachForm == null)
			return;
		Integer attachId = attachForm.getAttachId();
		if(attachId == null) 
			return;
		RmiRequest request = new RmiRequest();
		request.add("attachId", attachId+"");
		request.add("isFileDataInDB", UimsFactory.getClientMainI().isFileDataInDB());
		request.setCmd("attachmentDownloadAttachmentFile");
		RmiResponse respond = UimsUtils.requestProcesser(request);
		if (respond.getErrorMsg() != null){
			UimsUtils.messageBoxError("系统错误，无法下载附件");
		}
		UimsUtils.openDocment((byte[]) respond.get(RmiKeyConstants.KEY_BYTES), attachForm.getFileName());
	}

}
