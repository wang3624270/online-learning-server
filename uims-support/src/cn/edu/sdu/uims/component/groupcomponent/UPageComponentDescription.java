package cn.edu.sdu.uims.component.groupcomponent;

import javax.swing.JToggleButton;

import cn.edu.sdu.uims.UimsFactory;
import cn.edu.sdu.uims.base.UComponentI;

public class UPageComponentDescription {
	public UComponentI com;
	public JToggleButton bt;
	public String name;
	public boolean visible;
	public String title;
	public String enTitle;
	public String iconName;
	public String toString(){
		if(UimsFactory.getClientMainI().isEnglishVersion())
			return enTitle;
		else
			return title;
	}
}
