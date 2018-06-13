package cn.edu.sdu.common.form;
import java.lang.reflect.Method;
public class UForm  implements UFormI {
	/**
	 * 
	 */

	/**
	 * added by liuyang
	 * 
	 * @param form
	 */
	public boolean equals(UForm form) {
		return this.equals(form);
	}

	/**
	 * added by liuyang
	 * 
	 * @param form
	 */
	public void copy(UForm form) {
	}

	public Object[] toArray() {
		return null;
	}

	public void getDependFieldValues() {

	}

	public Object getAttributeObject(String attributeName) {
		Method method = null;
		String methodName = null;
		methodName = "get" + attributeName.substring(0, 1).toUpperCase()
				+ attributeName.substring(1, attributeName.length());
		try {
			method = this.getClass().getMethod(methodName);
			return method.invoke(this);
		} catch (Exception e) {
			return null;
		}
	}


	@Override
	public Object getAttributeObject(String attributeName, Integer index) {
		// TODO Auto-generated method stub
		Method method = null;
		String methodName = null;
		methodName = "get"
			+ attributeName.substring(0, 1).toUpperCase()
			+ attributeName.substring(1, attributeName.length());
		try {
		method = this.getClass().getMethod(methodName,
				Integer.class);
		return  method.invoke(this, index);
		}catch(Exception e) {
			return null;
		}
	}

	@Override
	public void setAttributeObject(String attributeName, Object obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAttributeObject(String attributeName, Object obj,
			Integer index) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAttributeByParse(String value) {
		// TODO Auto-generated method stub
		
	}


}
