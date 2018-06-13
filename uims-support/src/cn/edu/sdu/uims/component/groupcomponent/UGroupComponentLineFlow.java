package cn.edu.sdu.uims.component.groupcomponent;

import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JPanel;

import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.component.complex.UComplexPanel;
import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.def.UElementTemplate;

public class UGroupComponentLineFlow extends UGroupComponent {
	private JPanel linePanel[] = null;
	public void addComponent(UComponentI com, UElementTemplate elementTemplate) {
		if (comnum == 0) {
			container.setLayout(new GridLayout(groupElementTemplate.rowNum,1));
			linePanel = new JPanel[groupElementTemplate.rowNum];
			for(int i = 0; i < groupElementTemplate.rowNum;i++) {
				linePanel[i] =new JPanel();
				container.add(linePanel[i]);
				linePanel[i].setLayout(getFlowLayout());
			}
		}
		if(com  instanceof UComplexPanel) {
			UComplexPanel up =(UComplexPanel)com;
			up.AddCommpntToContainer(linePanel[elementTemplate.lineNo]);
		}else {
			linePanel[elementTemplate.lineNo].add(com.getAWTComponent());			
		}
		comnum++;
	}
	private FlowLayout getFlowLayout(){
		int align;
		if(groupElementTemplate.align == UConstants.ALIGNMENT_CENTER){
			align = FlowLayout.CENTER;
		}else if(groupElementTemplate.align == UConstants.ALIGNMENT_LEFT){
			align = FlowLayout.LEFT;
		}else if(groupElementTemplate.align == UConstants.ALIGNMENT_RIGHT){
			align = FlowLayout.RIGHT;
		}else if(groupElementTemplate.align == UConstants.ALIGNMENT_LEADING){
			align = FlowLayout.LEADING;
		}else if(groupElementTemplate.align == UConstants.ALIGNMENT_TRAILING){
			align = FlowLayout.TRAILING;
		}else  {
			align = FlowLayout.CENTER;			
		}			
		return new FlowLayout(align, (int)groupElementTemplate.divw,(int)groupElementTemplate.divh);
	}

}
