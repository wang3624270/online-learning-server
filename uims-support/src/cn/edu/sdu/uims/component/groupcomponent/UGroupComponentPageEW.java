package cn.edu.sdu.uims.component.groupcomponent;

import java.awt.GridLayout;

public class UGroupComponentPageEW extends UGroupComponentPageLabel {
	public GridLayout getGridLayout(int num) {
		return new GridLayout(num,1);
	}
	public String changeMenuDispTitle(String title){
		if(title == null || title.length()== 0)
			return title;
		String str = "<html>";

		for(int i = 0; i < title.length();i++) {
			str += title.charAt(i);
			if(i < title.length()-1) {
				str += "<br>";
			}
		}
		str += "</html>";
		return str;
	}
}
