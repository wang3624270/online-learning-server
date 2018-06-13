package cn.edu.sdu.uims.component.list;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;

import cn.edu.sdu.uims.base.CheckObject;
import cn.edu.sdu.uims.component.combobox.CheckListCellRenderer;

public class UListCheck extends UList {
	Object [] dataArray = null; 
	public void initContents() {
		// TODO Auto-generated method stub
		jList.setCellRenderer(new CheckListCellRenderer());
		jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	public void setData(Object o){
		if(o == null) {
			dataArray =new ArrayList().toArray();
		}
		else {
			if(o instanceof List) {
				List l = (List)o;
				dataArray= l.toArray();
			}else {
				dataArray =(Object [])o;				
			}
		}
		jList.setListData(dataArray);
	}
	protected void processValueChanged(ListSelectionEvent arg0){
		int index =  arg0.getFirstIndex();
		Object o = jList.getSelectedValue();
		if(!(o instanceof CheckObject))
			return;
		CheckObject co = (CheckObject)o;
		co.bolValue = !co.bolValue;
		jList.repaint();
		CheckObject c1;
		for(int i = 0; i < dataArray.length;i++) {
			c1 = (CheckObject)dataArray[i];
			if(co.value == c1.value) {
				c1.bolValue = co.bolValue;
			}
		}
		
	}
	protected void clearSelectedItem(ListSelectionEvent arg0){
		int index =  arg0.getFirstIndex();
		jList.removeSelectionInterval(index, index);
	}
	public Object getData(){
		return dataArray;
	}
	public void doAll(boolean b){
		if(dataArray == null || dataArray.length == 0 )
			return;
		CheckObject c1;
		for(int i = 0; i < dataArray.length;i++) {
			c1 = (CheckObject)dataArray[i];
			c1.bolValue = b;
		}
		jList.repaint();
	}
}
