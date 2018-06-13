package cn.edu.sdu.uims.def;

import java.io.Serializable;

public class IdNameObject implements Serializable {

	private Integer id;
	private String name;
	public IdNameObject(){
		
	}
	public IdNameObject(Integer id, String name){
		this.id = id;
		this.name = name;
	}
	public Integer getId() {
		// TODO Auto-generated method stub
		return id;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
}
