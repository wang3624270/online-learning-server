package cn.edu.sdu.uims.component.groupcomponent;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JComponent;

import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.def.UElementTemplate;

public class UGroupComponentBoxH extends UGroupComponentBox{
	public UGroupComponentBoxH(){
		lMode = BoxLayout.X_AXIS;
	}
	public void setCommpontAttribute(UComponentI com, UElementTemplate elementTemplate){
		super.setCommpontAttribute(com, elementTemplate);
		JComponent jc = (JComponent)com.getAWTComponent();
		if(elementTemplate.verticalAlignment == UConstants.ALIGNMENT_TOP)
			jc.setAlignmentY(Component.TOP_ALIGNMENT);
		else if(elementTemplate.verticalAlignment == UConstants.ALIGNMENT_BOTTOM)
		 	jc.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		else
			jc.setAlignmentY(Component.CENTER_ALIGNMENT);
	}

}
