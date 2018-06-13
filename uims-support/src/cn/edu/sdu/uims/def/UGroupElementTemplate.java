package cn.edu.sdu.uims.def;

import java.util.ArrayList;

import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.def.cell.UCellStruct;



public class UGroupElementTemplate extends UElementTemplate {
	public String layoutMode = "shift";
	public int align = UConstants.ALIGNMENT_CENTER;
	public int divh = 5, divw = 5,divsw=UConstants.SPLIT_WIDTH;
	public int componentNum = 0;
	public String dispTitleMode = "top";
	public ArrayList<UElementTemplate> componentTemplates = null; //UElementTemplate []
	public UCellStruct cellStruct= null;
	public String getLayoutMode() {
		return layoutMode;
	}
	public void setLayoutMode(String layoutMode) {
		this.layoutMode = layoutMode;
	}
	public int getDivh() {
		return divh;
	}
	public void setDivh(int divh) {
		this.divh = divh;
	}
	public int getDivw() {
		return divw;
	}
	public void setDivw(int divw) {
		this.divw = divw;
	}
	public int getComponentNum() {
		return componentNum;
	}
	public void setComponentNum(int componentNum) {
		this.componentNum = componentNum;
	}
	public ArrayList getComponentTemplates() {
		return componentTemplates;
	}
	public void setComponentTemplates(ArrayList componentTemplates) {
		this.componentTemplates = componentTemplates;
	}
	public void getIntValueFromString() {
		int i;
		super.getIntValueFromString();
		if(componentTemplates == null)
			return;
		for( i = 0; i < componentTemplates.size();i++){
			if(componentTemplates.get(i) != null)
				((UElementTemplate)componentTemplates.get(i)).getIntValueFromString();
		}
	}
	public void getStringValueFromInt(){
		int i;
		super.getStringValueFromInt();
		for( i = 0; i < componentTemplates.size();i++){
			((UElementTemplate)componentTemplates.get(i)).getStringValueFromInt();
		}
		
	}
}
