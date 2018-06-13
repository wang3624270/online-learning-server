package cn.edu.sdu.uims.handler.impl;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.component.event.EventConstants;
import cn.edu.sdu.uims.component.list.UList;
import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.form.impl.UListSelectForm;

public class TowListAdjustHandler extends UFormHandler {
	public void initAddedData() {
		initFormData();
		resetAddedData();
	}
	public void initAddedData(List list) {
		initFormData(list);
		resetAddedData();
	}

	public void resetAddedData() {
		initLeftList();
		initRightList();
		updateAddedData();
	}

	public void initLeftList() {
		UComponentI com = component.getSubComponent("leftList");
		FilterI filter = com.getFilter();
		filter.setAddedData(getStatusList(false));
	}

	public void initRightList() {
		UComponentI com = component.getSubComponent("rightList");
		FilterI filter = com.getFilter();
		filter.setAddedData(getStatusList(true));
	}

	public List getStatusList(boolean b) {
		List list = new ArrayList();
		Object[] items;
		UListSelectForm form = (UListSelectForm) dataForm;
		items = form.getItems();
		for (int i = 0; i < items.length; i++) {
			if (form.isSelected(i) == b)
				list.add(items[i]);
		}
		return list;
	}

	public void initFormData() {
		List list = getListData();
		initFormData(list);
	}
	public void initFormData(List list) {
		UListSelectForm form = (UListSelectForm) dataForm;
		int size = list.size();
		Boolean select[] = new Boolean[size];
		for (int i = 0; i < size; i++) {
			select[i] = false;
		}
		form.setItems(list.toArray());
		form.setSelected(select);
	}

	public List getListData() {
		List list = new ArrayList();
		list.add(new ListOptionInfo("001", "在读"));
		list.add(new ListOptionInfo("002", "肄业"));
		list.add(new ListOptionInfo("003", "毕业"));
		return list;
	}

	public void changeSelectItems(Object[] objs, boolean b) {
		if(objs== null || objs.length == 0)
			return;
		UListSelectForm form = (UListSelectForm) dataForm;
		ListOptionInfo info, obj;
		Object []items = form.getItems();
		for(int i = 0; i< objs.length; i++){
			obj = (ListOptionInfo)objs[i];
			for(int j = 0 ; j < items.length;j++){
				info = (ListOptionInfo)items[j];
				if(obj.getValue().equals(info.getValue())){
					form.setSelected(j, b);
					break;
				}
			}
		}
		resetAddedData();
	}

	public void processActionEvent(Object o, String cmd){
		ActionEvent e = (ActionEvent)o;
		UList uList;
		String command = e.getActionCommand();
		if(command.equals("rightShiftCmd")){
			uList = (UList)component.getSubComponent("rightList");
			Object [] ret = (Object [])uList.getSelectedValues();
			changeSelectItems(ret, false);
		}else if(command.equals("leftShiftCmd")){
			uList = (UList)component.getSubComponent("leftList");
			Object [] ret = (Object [])uList.getSelectedValues();
			changeSelectItems(ret, true);			
		}
	}
	public void processMouseEvent(Object o, String cmd) {
		MouseEvent e = (MouseEvent) o;
		if (cmd.equals(EventConstants.CMD_CLICKED)&& e.getClickCount() == 2) {
			UList uList = (UList) e.getSource();
			Object [] ret = (Object [])uList.getSelectedValues();
			if (ret == null || ret.length == 0)
				return;
			if (uList == component.getSubComponent("leftList")) {
				changeSelectItems(ret, true);
			} else {
				changeSelectItems(ret, false);
			}
		}
	}
	public List getSelectList(){
		List list = new ArrayList();
		UListSelectForm form = (UListSelectForm) dataForm;
		ListOptionInfo info, obj;
		Object []items = form.getItems();
		for(int i = 0; i < items.length;i++){
			if(form.isSelected(i)){
				list.add(items[i]);
			}
		}
		return list;
			
	}
}
