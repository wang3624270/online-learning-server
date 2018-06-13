package cn.edu.sdu.uims.filter;

import java.util.List;

import cn.edu.sdu.uims.form.impl.UTreeNodeForm;

public class UTreeFilter implements TreeFilterI {

	private String rootName;
	protected Object sonlist;
	
	public void setAddedData(List a) {
		// TODO Auto-generated method stub
		sonlist = a;
	}

	public void setAddedData(UTreeNodeForm root) {
		// TODO Auto-generated method stub
		sonlist = root;
	}

	public Object getAddedData() {
		// TODO Auto-generated method stub
		return sonlist;
	}


	public void init(String parameter) {
		// TODO Auto-generated method stub
		rootName = parameter;
	}


	public void setAddedData(Object[] a) {
		// TODO Auto-generated method stub
		
	}
	public String getRootName(){
		return rootName;
	}

}
