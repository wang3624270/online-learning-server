package cn.edu.sdu.uims.form.impl;

import java.util.List;

import cn.edu.sdu.common.form.UForm;


public class UTreeNodeForm extends UForm {
	private Integer id;
	private Object obj;
	private Object father;
	private List sonList = null;
	private String path;
	
	public UTreeNodeForm(){
		
	}
	public UTreeNodeForm(Object obj, List sonList){
		this.obj = obj;
		this.father = null;
		this.sonList = sonList;
	}
	
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Object getFather() {
		return father;
	}
	public void setFather(Object father) {
		this.father = father;
	}
	public List getSonList() {
		return sonList;
	}
	public void setSonList(List sonList) {
		this.sonList = sonList;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String toString(){
		if(obj != null)
			return obj.toString();
		else
			return null;
	}
	
}
