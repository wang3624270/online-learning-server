package cn.edu.sdu.uims.filter;

import java.util.List;

public class UFilter implements FilterI {

	protected Object[] arrayObject;

	public Object getAddedData() {
		// TODO Auto-generated method stub
		return arrayObject;
	}

	public void init(String parameter) {
		// TODO Auto-generated method stub

	}

	public void setAddedData(Object[] a) {
		arrayObject = a;

	}

	public void setAddedData(List a) {
		if (a != null) {
			arrayObject = a.toArray();
		} else {
			arrayObject = null;
		}

	}

}
