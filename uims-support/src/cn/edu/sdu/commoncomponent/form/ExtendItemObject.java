package cn.edu.sdu.commoncomponent.form;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;

import cn.edu.sdu.common.form.ListOptionInfo;

public class ExtendItemObject implements Serializable {
	public String name;
	public String label,enLabel;
	public String member,members;
	public int width;
	public String dictionary;
	public String filter;
	public boolean addSelectItem;
	public Method getMethod;
	public Method setMethod;
	public Method getMethods;
	public Method setMethods;
	public String type;
	public String doCmd;
	public Object com;
	public List<ListOptionInfo> optionList;
}
