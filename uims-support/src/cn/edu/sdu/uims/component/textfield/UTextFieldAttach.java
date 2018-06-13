package cn.edu.sdu.uims.component.textfield;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.uims.UimsFactory;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.component.table.UTablePanel;
import cn.edu.sdu.uims.util.UimsUtils;

public class UTextFieldAttach extends UTextField implements MouseListener{

	private ListOptionInfo attachInfo = null;
	private UComponentI screenOwner = null;

	public void initContents() {
		// TODO Auto-generated method stub
		this.addMouseListener(this);
		this.setEditable(false);
	}
	
	public void setData(Object obj){
		if(obj instanceof ListOptionInfo)
			attachInfo = (ListOptionInfo)obj;
		else
			attachInfo = null;
		if(attachInfo != null)
			this.setText(attachInfo.getLabel());
	}
	public Object getData() {
		return attachInfo;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		displayAttachFile();		
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
		ListOptionInfo info = null;
		if(screenOwner != null || screenOwner instanceof UTablePanel ) {
			UTablePanel tp = (UTablePanel)screenOwner;
			info = (ListOptionInfo )tp.getSelectRowColumnObject();
		}else 
			info = attachInfo;
		if(info == null) {
			UimsUtils.messageBoxInfo("没有附件附件不能打开！");
			return;
		}
		String attachId = info.getValue();
		if(attachId == null) 
			return;
		RmiRequest request = new RmiRequest();
		request.add("attachId", attachId);
		request.add("isFileDataInDB", UimsFactory.getClientMainI().isFileDataInDB());
		request.setCmd("attachmentDownloadAttachmentFile");
		RmiResponse respond = UimsUtils.requestProcesser(request);
		if (respond.getErrorMsg() != null){
			UimsUtils.messageBoxError("系统错误，无法下载附件");
		}
		UimsUtils.openDocment((byte[]) respond.get(RmiKeyConstants.KEY_BYTES), info.getLabel());
	}

	@Override
	public void setScreenOwner(UComponentI screenOwner) {
		// TODO Auto-generated method stub
		this.screenOwner = screenOwner;
	}

}
