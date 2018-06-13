package org.octopus.common_business.globalinfo.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "system_global_variable")
public class SystemGlobalVariable  {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer variableId;
	private String variableName;
	private String variableDes;
	private String variableType;
	private String variableValue;
	private Date modifyTime;
	private String modifyCmd;
	private Integer modifier;
	
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
	public String getModifyCmd() {
		return modifyCmd;
	}
	public void setModifyCmd(String modifyCmd) {
		this.modifyCmd = modifyCmd;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public Integer getModifier() {
		return modifier;
	}
	public void setModifier(Integer modifier) {
		this.modifier = modifier;
	}
	public String getVariableValue() {
		return variableValue;
	}
	public void setVariableValue(String variableValue) {
		this.variableValue = variableValue;
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
	
}
