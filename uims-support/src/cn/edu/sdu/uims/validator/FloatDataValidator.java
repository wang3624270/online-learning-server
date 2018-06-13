package cn.edu.sdu.uims.validator;

public class FloatDataValidator extends BaseDataValidator {
	@Override

	public boolean isInvalidate(Object o) {
		// TODO Auto-generated method stub
		if(o == null || o.toString().length()==0)
			return true;
		try {
			Float.parseFloat(o.toString());
		} catch (NumberFormatException e) {
			return true;
		}
		return false;
	}

	@Override
	public String getInvalidateMessage() {
		// TODO Auto-generated method stub
		return msg + "不能转换为浮点数";
	}
}
