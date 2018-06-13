package org.octopus.reportdog.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DefaultDataForm {

	private HashMap itemMap;
	private String name;

	public DefaultDataForm() {
		itemMap = new HashMap();
	}

	public void set(Object itemName, Object itemValue) {

		if (itemMap == null) {
			itemMap = new HashMap();
		}

		itemMap.put(itemName, itemValue);

	}

	public void set(Object itemValue) {
		if (itemMap == null) {
			itemMap = new HashMap();
		}
		itemMap.put(name, itemValue);
	}

	public void addTopDataFormOrList(Object itemName, Object itemValue) {

		if (itemMap == null) {
			itemMap = new HashMap();
		}

		if (itemValue instanceof DefaultDataForm) {
			List<DefaultDataForm> list = new ArrayList();
			list.add((DefaultDataForm) itemValue);
			itemMap.put(itemName, list);
		} else
			itemMap.put(itemName, itemValue);

	}

	public void addTopDataFormOrList(Object itemValue) {

		if (itemMap == null) {
			itemMap = new HashMap();
		}

		if (itemValue instanceof DefaultDataForm) {
			List<DefaultDataForm> list = new ArrayList();
			list.add((DefaultDataForm) itemValue);
			itemMap.put(name, list);
		} else
			itemMap.put(name, itemValue);

	}

	public void addDataFormListForVar(Object itemName, Object itemValue) {

		if (itemMap == null) {
			itemMap = new HashMap();
		}

		DefaultDataForm form = new DefaultDataForm();
		form.set(itemName, itemValue);
		itemMap.put(itemName, form);

	}

	public void addDataFormListForVar(Object itemValue) {

		if (itemMap == null) {
			itemMap = new HashMap();
		}

		DefaultDataForm form = new DefaultDataForm();
		form.set(name, itemValue);
		itemMap.put(name, form);

	}

	public Object get(Object itemName) {

		if (itemMap != null) {
			return itemMap.get(itemName);
		}
		return null;
	}

	public HashMap getItemMap() {
		return itemMap;
	}

	public void setItemMap(HashMap itemMap) {
		this.itemMap = itemMap;
	}

	public int getItemCount() {
		return this.itemMap.size();
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
