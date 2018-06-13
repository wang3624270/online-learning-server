package cn.edu.sdu.uims.component.button;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cn.edu.sdu.uims.component.panel.UFPanel;


public class UButtonFold extends UButton {
	private boolean groupOpened = false;
	public void initContents() {
		// TODO Auto-generated method stub
		super.initContents();
		addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				openorCloseGroupComponet();
			}	
		});
		if(buttonTemplate != null) {
			groupOpened = buttonTemplate.groupOpened;
		}
	}
	public void openorCloseGroupComponet(){
		if(groupOpened) {
			groupOpened = false;
		}else {
			groupOpened = true;
		}
		UFPanel p = (UFPanel)this.getUParent();
		p.reComputeComponentBound();
	}
	public boolean getGroupOpened(){
		return  groupOpened;
	}
	public int getGroupHeight(){
		return buttonTemplate.groupHeight;
	}
}
