package cn.edu.sdu.uims.handler.impl;

import java.awt.event.ActionEvent;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import javax.swing.event.ListSelectionEvent;

import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.component.dialog.UDialogPanel;
import cn.edu.sdu.uims.component.list.UList;
import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.form.UHtmlItemFormI;
import cn.edu.sdu.uims.form.impl.HtmlContentItemEditForm;

public class UimsHtmlContentItemEditDialogHandler extends UDialogHandler {
	private  List itemList = null;
	private HashMap<String, String> dataMap = new HashMap<String, String>();
	public void start() {
		HtmlContentItemEditForm form = (HtmlContentItemEditForm) dataForm;
		UHtmlItemFormI df = form.getFormData();
		if (df != null)
			itemList = df.getHtmlItemList();
		UComponentI com = (UComponentI) this.component
				.getSubComponent("itemList");
		FilterI f = com.getFilter();
		f.setAddedData(itemList);
		com.updateAddedDatas();
		setDataMap();
	}
	public void setDataMap(){
		HtmlContentItemEditForm form = (HtmlContentItemEditForm) dataForm;
		UHtmlItemFormI df = form.getFormData();
		if (df == null || itemList == null)
			return;
		ListOptionInfo info;
		String itemName;
		String ms;
		Method m;
		try {
			for(int i = 0; i < itemList.size();i++) {
				info = (ListOptionInfo)itemList.get(i);
				itemName = info.getValue();
				ms = "get" + itemName.substring(0, 1).toUpperCase()
						+ itemName.substring(1, itemName.length());
				m = df.getClass().getMethod(ms);
				dataMap.put(itemName, (String)m.invoke(df));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void setFormData(){
		HtmlContentItemEditForm form = (HtmlContentItemEditForm) dataForm;
		UHtmlItemFormI df = form.getFormData();
		if (df == null || itemList == null)
			return;
		ListOptionInfo info;
		String itemName;
		String ms;
		Method m;
		try {
			for(int i = 0; i < itemList.size();i++) {
				info = (ListOptionInfo)itemList.get(i);
				itemName = info.getValue();
				ms = "set" + itemName.substring(0, 1).toUpperCase()
						+ itemName.substring(1, itemName.length());
				m = df.getClass().getMethod(ms, String.class);
				m.invoke(df, dataMap.get(itemName));
			}
		}catch(Exception e) {
			e.printStackTrace();
		}		
	}
	public void processListSelectionEvent(Object o, String cmd) {
		ListSelectionEvent e = (ListSelectionEvent) o;
		UList uList = (UList) e.getSource();
		ListOptionInfo info = (ListOptionInfo) uList.getSelectedValue();
		if (info == null || info.getValue() == null)
			return;
		HtmlContentItemEditForm form = (HtmlContentItemEditForm) dataForm;
		String itemName = form.getItemName();
		if (itemName != null && itemName.equals(info.getValue()))
			return;
		this.componentToForm();
		if (itemName != null) {
			dataMap.put(itemName, form.getContent());
		}
		itemName = info.getValue();
		form.setItemName(itemName);
		form.setContent(dataMap.get(itemName));
		this.formToComponent();
	}
	public void processActionEvent(Object o, String cmd) {
		ActionEvent e = (ActionEvent) o;
		String command = e.getActionCommand();
		UDialogPanel dlg = (UDialogPanel) component;
		if (command.equals("okCommand")) {
			HtmlContentItemEditForm form = (HtmlContentItemEditForm) dataForm;
			String itemName = form.getItemName();
			this.componentToForm();
			if (itemName != null) {
				dataMap.put(itemName, form.getContent());
			}
			setFormData();
			dlg.doOk();
		} else if (command.equals("cancelCommand")) {
			dlg.doCancel();
		}
	}
}
