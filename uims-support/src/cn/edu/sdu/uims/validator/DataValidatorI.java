package cn.edu.sdu.uims.validator;

import java.util.HashMap;

public interface DataValidatorI {
	void init(String msg, HashMap paras);
	boolean isInvalidate(Object o);
	String getInvalidateMessage();
}
