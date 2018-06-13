package cn.edu.sdu.uims.def;

import java.io.Serializable;

public class UEventAttribute implements Serializable{
	public String name;
	public Boolean server; 
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getServer() {
		return server;
	}
	public void setServer(Boolean server) {
		this.server = server;
	}
}
