package cn.edu.sdu.uims.component.groupcomponent;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JPanel;

import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.def.UElementTemplate;

public class UGroupComponentBorder extends UGroupComponent {
	public void addComponent(UComponentI com, UElementTemplate elementTemplate) {
		if (comnum == 0) {
			container.setLayout(new BorderLayout());
		}
		Component c = null;
		if(elementTemplate.layout != UConstants.ALIGNMENT_CENTER && !(com.getAWTComponent() instanceof JPanel)) {
			JPanel p = new JPanel();
			p.setLayout(new FlowLayout());
			p.add(com.getAWTComponent());
			c = p;
		}else
			c = com.getAWTComponent();
		switch (elementTemplate.layout) {
		case UConstants.ALIGNMENT_BOTTOM:
			container.add(c,BorderLayout.SOUTH);
			break;
		case UConstants.ALIGNMENT_CENTER:
			container.add(c,BorderLayout.CENTER);
			break;
		case UConstants.ALIGNMENT_LEFT:
			container.add(c,BorderLayout.WEST);
			break;
		case UConstants.ALIGNMENT_RIGHT:
			container.add(c,BorderLayout.EAST);
			break;
		case UConstants.ALIGNMENT_TOP:
			container.add(c,BorderLayout.NORTH);
			break;
		}
		if(com.getAWTComponent() instanceof JPanel) {
			com.getAWTComponent().setBounds(elementTemplate.x, elementTemplate.y, elementTemplate.w, elementTemplate.h);
			com.getAWTComponent().setPreferredSize(new Dimension(elementTemplate.w, elementTemplate.h));
		}
//		com.setShellBounds();
		comnum++;
	}

}
