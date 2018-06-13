package cn.edu.sdu.common.pi;

import java.util.List;

public interface ClientDataDictionaryI extends ClientInitPlugInI{
	List getComboxListByCode(String... typecode);
	List getComboxListByCode(String code);
	String getDataNameByCode(String parent, String code);
}
