package org.octopus.common_business.globalinfo.form;

import java.util.Date;

import cn.edu.sdu.common.form.UFormI;



public class SystemGlobalVariableForm implements UFormI{
	private Integer variableId;
	private String variableName;
	private String variableValue;
	private Date modifyTime;
	private String modifyCmd;
	private Integer modifier;
	private String variableDes;
	private String variableType;
	public Integer getVariableId() {
		return variableId;
	}
	public void setVariableId(Integer variableId) {
		this.variableId = variableId;
	}
	public String getVariableName() {
		return variableName;
	}
	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}
	public String getVariableValue() {
		return variableValue;
	}
	public void setVariableValue(String variableValue) {
		this.variableValue = variableValue;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getModifyCmd() {
		return modifyCmd;
	}
	public void setModifyCmd(String modifyCmd) {
		this.modifyCmd = modifyCmd;
	}
	public Integer getModifier() {
		return modifier;
	}
	public void setModifier(Integer modifier) {
		this.modifier = modifier;
	}
	public String getVariableDes() {
		return variableDes;
	}
	public void setVariableDes(String variableDes) {
		this.variableDes = variableDes;
	}
	public String getVariableType() {
		return variableType;
	}
	public void setVariableType(String variableType) {
		this.variableType = variableType;
	}
	public String toString(){
		return variableDes;
	}
	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void getDependFieldValues() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Object getAttributeObject(String attributeName) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setAttributeObject(String attributeName, Object obj) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Object getAttributeObject(String attributeName, Integer index) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setAttributeObject(String attributeName, Object obj, Integer index) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setAttributeByParse(String value) {
		// TODO Auto-generated method stub
		
	}
}
