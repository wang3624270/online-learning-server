package cn.edu.sdu.uims.def;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.filter.FilterI;

public class UFilterTemplate extends UTemplate {
	public String parameter;
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public FilterI newInstance(){
		FilterI instance = null;
		if(instance == null ){
			try{
				instance = (FilterI) Class.forName(className).newInstance();
			}catch(Exception e){
				instance = null;
				System.out.println("classname = "+className);
				e.printStackTrace();
			}
		}
		return instance;
	}
	public void read(DataInputStream in) throws IOException {
		super.read(in);
		parameter = readString(in);
	}
	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		writeString(out, parameter);
	}
}
