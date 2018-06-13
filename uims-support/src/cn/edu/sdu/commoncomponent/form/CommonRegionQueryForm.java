package cn.edu.sdu.commoncomponent.form;

import java.util.HashMap;

import cn.edu.sdu.common.form.UForm;

public class CommonRegionQueryForm extends UForm {
	private String province;
	private String city;
	private String town;
	private String addr;
	private HashMap parameters;
	
	public HashMap getParameters() {
		return parameters;
	}
	public void setParameters(HashMap parameters) {
		this.parameters = parameters;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
}
