package cn.edu.sdu.uims.component.complex;

import java.util.List;

import javax.swing.JCheckBox;

import cn.edu.sdu.uims.component.complex.form.MultiLevelDataI;

public class MultiLevelCheckBox extends JCheckBox implements MultiLevelDataI{
	private String label;
	private String value;
	private MultiLevelDataI father;
	private List<MultiLevelDataI> subList;
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public MultiLevelDataI getFather() {
		return father;
	}
	public void setFather(MultiLevelDataI father) {
		this.father = father;
	}
	public List<MultiLevelDataI> getSubList() {
		return subList;
	}
	public void setSubList(List<MultiLevelDataI> subList) {
		this.subList = subList;
	}
}
