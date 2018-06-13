package cn.edu.sdu.uims.flex;

import java.io.Serializable;

public class FNameObjectPar implements Serializable{

	public String name;
	public Object ob;

	public FNameObjectPar() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getObject() {
		return ob;
	}

	public void setObject(Object object) {
		this.ob = object;
	}

	public String toString() {
		return name;
	}

}