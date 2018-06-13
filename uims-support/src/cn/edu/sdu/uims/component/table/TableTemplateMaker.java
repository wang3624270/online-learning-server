package cn.edu.sdu.uims.component.table;

import java.util.List;

import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.base.TemplateMaker;
import cn.edu.sdu.uims.def.TableViewControlTemplate;
import cn.edu.sdu.uims.def.UColumnTemplate;
import cn.edu.sdu.uims.def.UTableTemplate;

public class TableTemplateMaker implements TemplateMaker {

	public UTemplate makeTemplate() {
		// TODO Auto-generated method stub
		int i = 0;
		List headList = getHeadItemList();
		UTableTemplate tableTemplate = new UTableTemplate();
		ColumSetAttribute ca[] = getColumSetAttributs(headList);
		tableTemplate.itemFormClassName = getFormClassName();
		tableTemplate.viewControl = new TableViewControlTemplate();
		tableTemplate.viewControl.type = TableViewControlTemplate.CONTROL_TYPE_COLUMN_CONTROL;
		tableTemplate.viewControl.width = this.getColumnControlWidth();
		tableTemplate.columnNum = ca.length;
		tableTemplate.topNum = ca.length;
		tableTemplate.topStrings = new String[ca.length];
		for(i = 0; i< ca.length;i++) {
			tableTemplate.topStrings[i] = ca[i].title;
		}
		tableTemplate.columnTemplates = new UColumnTemplate[tableTemplate.columnNum];
		UColumnTemplate ct;
		for(i = 0; i < tableTemplate.columnNum; i++) {
			ct = new UColumnTemplate();
			ct.name =  "colName" + i;
			ct.width = ca[i].width;
			ct.itemIndex = ca[i].index;
			ct.itemFormMember = ca[i].member;
			tableTemplate.columnTemplates[i] = ct;
		}
		return tableTemplate;
	}
	public List getHeadItemList(){
		return null;
	}
	public ColumSetAttribute[] getColumSetAttributs(){
		return null;
	}
	
	public ColumSetAttribute [] getColumSetAttributs(List list){
		if(list == null || list.size() == 0)
			return getColumSetAttributs();
		ColumSetAttribute[] attSet = new ColumSetAttribute[list.size()];		
		ListOptionInfo op;
		for(int i = 0; i < list.size();i++) {
			op = (ListOptionInfo)list.get(i);
			attSet[i] = new ColumSetAttribute();
			attSet[i].title = op.getLabel();
			attSet[i].index = i;
			attSet[i].member = "dataElement";
			attSet[i].width = 20*op.getLabel().length();			
		}
		return attSet;
	}
	public String getFormClassName(){
		return "cn.edu.sdu.uims.form.impl.UDataArrayForm";
	}
	public boolean getColumnControl(){
		return true;
	}
	public int getColumnControlWidth(){
		return 100;
	}
}
