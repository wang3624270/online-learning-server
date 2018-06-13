package cn.edu.sdu.uims.validator;

import java.util.HashMap;

public class BaseDataValidator implements DataValidatorI {

	protected String msg;
	@Override
	public void init(String msg, HashMap paras) {
		// TODO Auto-generated method stub
		this.msg = msg;
		if(msg == null)
			msg = "数据";
	}

	@Override
	public boolean isInvalidate(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getInvalidateMessage() {
		// TODO Auto-generated method stub
		return msg;
	}

}
