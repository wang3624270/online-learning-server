package org.octopus.auth.jpa_model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sys_fun_res")
public class SysFunRes {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer resid;// 
	private String name;
	private String path;
	
	public Integer getResid() {
		return resid;
	}
	public void setResid(Integer resid) {
		this.resid = resid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	
	
	
	
}
