package cn.edu.sdu.uims.base;

import java.io.Serializable;


public class CheckObject implements Serializable{

	public boolean bolValue = false;
	public Object value = null;
	
	public CheckObject() {
	}

	public CheckObject(boolean bolValue, Object value) {
		this.bolValue = bolValue;
		this.value = value;
	}
	public String toString (){
		if(value != null)
			return value.toString();
		else
			return null;
	}
}
