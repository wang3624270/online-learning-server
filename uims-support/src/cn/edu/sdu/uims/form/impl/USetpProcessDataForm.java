package cn.edu.sdu.uims.form.impl;

import java.util.HashMap;
import java.util.List;

import cn.edu.sdu.common.form.UForm;

public class USetpProcessDataForm extends UForm {
	private List<USetpProcessDataItemForm> itemList;
	private long delayTime;
	private String cmd;
	private HashMap paras;	
	
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public HashMap getParas() {
		return paras;
	}
	public void setParas(HashMap paras) {
		this.paras = paras;
	}
	public List<USetpProcessDataItemForm> getItemList() {
		return itemList;
	}
	public void setItemList(List<USetpProcessDataItemForm> itemList) {
		this.itemList = itemList;
	}
	public long getDelayTime() {
		return delayTime;
	}
	public void setDelayTime(long delayTime) {
		this.delayTime = delayTime;
	}
 
}
