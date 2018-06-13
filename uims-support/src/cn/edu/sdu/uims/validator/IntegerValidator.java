package cn.edu.sdu.uims.validator;

public class IntegerValidator extends BaseDataValidator {
	@Override
	public boolean isInvalidate(Object o) {
		// TODO Auto-generated method stub
		if(o == null || o.toString().length()==0)
			return true;
		try {
			Integer.parseInt(o.toString());
		} catch (NumberFormatException e) {
			return true;
		}
		return false;
	}

	@Override
	public String getInvalidateMessage() {
		// TODO Auto-generated method stub
		if(msg != null)
			return msg + "不能转换为整数";
		else
			return  "数据不能转换为整数";
			
	}

}
