package cn.edu.sdu.uims.component.button;

import javax.swing.Icon;

import cn.edu.sdu.uims.util.UimsUtils;

public class UIconButton extends UButton {
	public void initContents() {
		// TODO Auto-generated method stub
		Icon icon = null;
		if(buttonTemplate != null){
			this.setOpaque(true);
			this.setActionCommand(buttonTemplate.cmd);
			if(buttonTemplate.content != null && !buttonTemplate.content.equals(""))
				this.setText(buttonTemplate.content);
			else
				this.setText(null);
			if(buttonTemplate.iconName != null){
				icon = UimsUtils.getCSClientIcon(buttonTemplate.iconName);
				setIcon(icon);
			}
			if(buttonTemplate.selectedIconName != null){
				icon = UimsUtils.getCSClientIcon(buttonTemplate.selectedIconName);
				setSelectedIcon(icon);
			}
			if(buttonTemplate.pressedIconName != null){
				icon = UimsUtils.getCSClientIcon(buttonTemplate.pressedIconName);
				this.setPressedIcon(icon);
			}
		}
		this.setRequestFocusEnabled(true);
		this.setEnabled(true);
	}

}
