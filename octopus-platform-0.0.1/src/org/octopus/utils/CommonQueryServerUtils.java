package org.octopus.utils;

public class CommonQueryServerUtils {
	public static CommonQueryServerUtils instance;
	public static void setInstance(CommonQueryServerUtils obj){
		instance = obj;
	}
	public static CommonQueryServerUtils getInstance(){
		return instance;
	}
	public String getPersonName(Integer personId){
		return "";
	}
}
