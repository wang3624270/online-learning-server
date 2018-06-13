package cn.edu.sdu.uims.form.impl;

import java.util.List;

import cn.edu.sdu.common.form.UForm;
import cn.edu.sdu.uims.handler.ClientBusinessProcessorI;

public class UMutiInfoForm extends UForm {
	private ClientBusinessProcessorI pi;
	private boolean isEnd;
	private String info;
	private Object userObject;
	private String cmd;
	
	public UMutiInfoForm(){
	}
	public UMutiInfoForm(String info){
		this.info = info;
	}
	public UMutiInfoForm(List list){
		getInfoByList(list);
	}
	public String getInfo(){
		return info;
	}
	public void getInfoByList(List list) {
		if(list == null)
			return;
		Object []items = list.toArray();
		items = list.toArray();
		info = "";
		for(int i = 0; i < items.length;i++) {
			info += items[i].toString();
			if( i!= items.length-1)
				info += "\n";
		}
	}
	public void  setInfo(String info){
		this.info = info;
	}
	public void addInfo(String str) {
		if(info == null || info.length() == 0)
			info = str;
		else {
			info += "\n" + str;
		}
	}
	public boolean isEnd() {
		return isEnd;
	}
	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}
	public ClientBusinessProcessorI getPi() {
		return pi;
	}
	public void setPi(ClientBusinessProcessorI pi) {
		this.pi = pi;
	}
	public Object getUserObject() {
		return userObject;
	}
	public void setUserObject(Object userObject) {
		this.userObject = userObject;
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
}
