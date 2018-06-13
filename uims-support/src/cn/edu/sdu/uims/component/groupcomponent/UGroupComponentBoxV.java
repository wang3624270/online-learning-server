package cn.edu.sdu.uims.component.groupcomponent;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JComponent;

import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.def.UElementTemplate;

public class UGroupComponentBoxV extends UGroupComponentBox {
	public UGroupComponentBoxV(){
		lMode = BoxLayout.Y_AXIS;
	}
	public void setCommpontAttribute(UComponentI com, UElementTemplate elementTemplate){
		super.setCommpontAttribute(com, elementTemplate);
		JComponent jc = (JComponent)com.getAWTComponent();
		if(elementTemplate.horizontalAlignment == UConstants.ALIGNMENT_LEFT)
			jc.setAlignmentX(Component.LEFT_ALIGNMENT);
		else if(elementTemplate.horizontalAlignment == UConstants.ALIGNMENT_RIGHT)
		 	jc.setAlignmentX(Component.RIGHT_ALIGNMENT);
		else
			jc.setAlignmentX(Component.CENTER_ALIGNMENT);
	}

}
