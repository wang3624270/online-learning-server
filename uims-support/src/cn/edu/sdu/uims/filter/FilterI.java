package cn.edu.sdu.uims.filter;

import java.io.Serializable;
import java.util.List;

public interface FilterI extends Serializable{
	 void init(String parameter);
	 Object getAddedData();
	 void setAddedData(Object[] a);
	 void setAddedData(List a);
}
