package cn.edu.sdu.uims.component.groupcomponent;

import java.awt.Dimension;

import javax.swing.BoxLayout;

import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.def.UElementTemplate;

public class UGroupComponentBox extends UGroupComponent {
	protected int lMode;
	public  UGroupComponentBox(){
		lMode = BoxLayout.X_AXIS;
	}
	public void addComponent(UComponentI com, UElementTemplate elementTemplate) {
		if (comnum == 0) {
			container.setLayout(new BoxLayout(container,lMode));
		}
		container.add(com.getAWTComponent());
		setCommpontAttribute(com,elementTemplate);
		
		comnum++;
	}
	public void setCommpontAttribute(UComponentI com, UElementTemplate elementTemplate){
		com.getAWTComponent().setPreferredSize(new Dimension(elementTemplate.w,elementTemplate.h));
	}

}
