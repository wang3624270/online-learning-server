package cn.edu.sdu.uims.itms.form;

import java.lang.reflect.Method;

import cn.edu.sdu.common.form.UForm;

public class IForm extends UForm implements IFormI{
	public void clearForUse(){
		 
	 }
	public Object getFieldData(String name){
		Object obj = null;
		if(name == null)
			return null;
		String methodName = "get"+name.substring(0,1).toLowerCase()+name.substring(1,name.length());
		try {
		Method method = this.getClass().getMethod(methodName);
		obj = method.invoke(this);
		return obj;
		}catch(Exception e){
			return null;
		}
	}
}
