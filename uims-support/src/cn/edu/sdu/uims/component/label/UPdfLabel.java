package cn.edu.sdu.uims.component.label;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.uims.util.UimsUtils;

public class UPdfLabel extends ULabel implements MouseListener{
	private ListOptionInfo  info= null;
	public void initContents() {
		this.addMouseListener(this);
	}

	
	public Object getData() {
		// TODO Auto-generated method stub
		return info;
	}
	public void setData(Object obj) {
		// TODO Auto-generated method stub
			if(obj instanceof ListOptionInfo) {
				info = (ListOptionInfo) obj;
			}
			if(info == null )
				this.setText("");
			else
				this.setText(info.getLabel());
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(info == null || info.getValue() == null)
			return;
		Integer attachId = new Integer(info.getValue());
		UimsUtils.openDocment(UimsUtils.getDataOfAttachFile(attachId), info.getLabel());
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

}
