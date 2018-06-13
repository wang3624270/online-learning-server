package cn.edu.sdu.common.form;

import java.io.Serializable;


public interface UFormI extends Serializable, FormI{
	 Object [] toArray();
	 void getDependFieldValues();
	 Object getAttributeObject(String attributeName);
	 void setAttributeObject(String attributeName,Object obj);
	 Object getAttributeObject(String attributeName,Integer index);
	 void setAttributeObject(String attributeName,Object obj, Integer index);
	 void setAttributeByParse(String value);
}
