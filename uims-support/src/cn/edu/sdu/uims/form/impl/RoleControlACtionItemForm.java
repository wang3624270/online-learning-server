package cn.edu.sdu.uims.form.impl;

import cn.edu.sdu.common.form.UForm;

public class RoleControlACtionItemForm extends UForm {
	private String componentName;
	private String menuName;
	private String status;
	public String getComponentName() {
		return componentName;
	}
	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
