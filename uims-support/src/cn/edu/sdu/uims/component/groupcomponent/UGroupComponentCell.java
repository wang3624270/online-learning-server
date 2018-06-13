package cn.edu.sdu.uims.component.groupcomponent;

import java.util.HashMap;

import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.def.UElementTemplate;
import pagelayout.Cell;
import pagelayout.PageLayout;

public class UGroupComponentCell extends UGroupComponent{
	private HashMap<String,UComponentI> comMap;
/*	
	public void addComponent(UComponentI com, UElementTemplate elementTemplate) {
		if (comnum == 0) {
			container.setLayout(new GridLayout(groupElementTemplate.rowNum,groupElementTemplate.colNum));
		}
		container.add(com.getAWTComponent());
		comnum++;
	}
*/
	public void addComponent(UComponentI com, UElementTemplate elementTemplate) {
		if(comnum == 0)
			comMap = new HashMap<String, UComponentI>();
		comMap.put(com.getComponentName(), com);
		comnum++;
	}	
	public void addComponentAfter() {
		if(comMap == null || groupElementTemplate.cellStruct == null)
			return ;
		Cell cell = groupElementTemplate.cellStruct.getCell(comMap);
		PageLayout lm = cell.createLayout(container);
	}


}
