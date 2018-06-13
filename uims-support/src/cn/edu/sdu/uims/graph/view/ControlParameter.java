package cn.edu.sdu.uims.graph.view;

import java.util.HashMap;

import cn.edu.sdu.uims.trans.UFPoint;


public class ControlParameter {
	public boolean isEdit = false;
	public boolean  useDataParameter= false;
	public boolean isServer = false;
	public HashMap<String, UFPoint> shiftMap;
	
	public ControlParameter(){
		useDataParameter = false;
	}
	public ControlParameter(boolean b){
		useDataParameter = b;
	}

	public boolean isUseDataParameter() {
		return useDataParameter;
	}

	public void setUseDataParameter(boolean useDataParameter) {
		this.useDataParameter = useDataParameter;
	}
	
}
