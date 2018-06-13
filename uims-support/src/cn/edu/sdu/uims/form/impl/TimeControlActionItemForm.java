package cn.edu.sdu.uims.form.impl;

import cn.edu.sdu.common.form.UForm;

public class TimeControlActionItemForm extends UForm {
	private String componentName;
	private String menuName;
	private boolean cando;
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
	public boolean isCando() {
		return cando;
	}
	public void setCando(boolean cando) {
		this.cando = cando;
	}
}
