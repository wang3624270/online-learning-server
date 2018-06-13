package cn.edu.sdu.commoncomponent.util;

import java.io.Serializable;

public class IdNameNumObject implements Serializable{
	private Integer id;
	private String num;
	private String name;
	public IdNameNumObject(){
	}
	public IdNameNumObject(Integer id, String num, String name){
		this.id= id;
		this.num = num;
		this.name = name;
	}
	public String toString(){
		if(num == null)
			return "";
		else
			return num + "-" + name;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}
