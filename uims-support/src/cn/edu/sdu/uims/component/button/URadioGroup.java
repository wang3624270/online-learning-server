package cn.edu.sdu.uims.component.button;

import java.awt.Color;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.common.reportdog.UFont;
import cn.edu.sdu.uims.service.UFactory;

public class URadioGroup extends UButtonGroup {
	
	protected ButtonGroup group;
	protected AbstractButton getComponentButton(){
		return new JRadioButton();
	}
	protected Border getGoupBorder(){
		String title = null;
		if(groupTemplate.text == null)
			title = "";
		else
			title = groupTemplate.text;
		Border border ;
		if(groupTemplate.borderName.equals("fnull")) 
			 return BorderFactory.createLineBorder(Color.BLACK,0);
		border= BorderFactory.createLineBorder(Color.BLACK,1);			
		UFont f = UFactory.getModelSession().getFontByName(elementTemplate.fontName);
		UColor c = UFactory.getModelSession().getColorByName(elementTemplate.colorName);
		return BorderFactory.createTitledBorder(border, title, TitledBorder.LEFT, TitledBorder.TOP, f.font, c.color);
	}
	public void initButtonGroup(){
		group =  new ButtonGroup();
	}
	public void setButtonAttribute(int i){
		super.setButtonAttribute(i);
		group.add(buttons[i]);
		
	}
	
	public void setData(Object obj) {
		// TODO Auto-generated method stub
		int index = 0;
		int i;
		if(obj != null) {
			if(obj instanceof Integer ){
				index = (Integer)obj; 
			}
			else {
				String cmd = obj.toString();
				for(i = 0; i < buttons.length;i++){
					if(buttons[i].getActionCommand().equals(cmd)) {
						index = i;
						break;
					}
				}
			}
		}
		for(i = 0 ; i < buttons.length;i++)
			if(i == index)
				buttons[index].setSelected(true);
			else 
				buttons[index].setSelected(false);
		
	}
	
	public Object getData() {
		int index = 0;
		for( int i = 0; i< buttons.length;i++){
			if(buttons[i].isSelected()){
				index= i;
			}
		}
		if(elementTemplate.dictionary == null && groupTemplate.buttons.length == 0)
			return index;
		else
			return buttons[index].getActionCommand();
	}


}
