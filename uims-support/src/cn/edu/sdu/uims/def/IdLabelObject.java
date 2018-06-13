package cn.edu.sdu.uims.def;

import java.io.Serializable;

public class IdLabelObject implements  Serializable{
	private Integer id;
	private String label;
	private Object dataObject;
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Object getDataObject() {
		return dataObject;
	}
	public void setDataObject(Object dataObject) {
		this.dataObject = dataObject;
	}
	
	public IdLabelObject(Integer id, String label, Object dataObject){
		this.id = id;
		this.label = label;
		this.dataObject = dataObject;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String toString(){
		return label;
	}
}
