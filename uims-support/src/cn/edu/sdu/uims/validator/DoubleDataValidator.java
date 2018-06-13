package cn.edu.sdu.uims.validator;

public class DoubleDataValidator extends BaseDataValidator {

	public boolean isInvalidate(Object o) {
		// TODO Auto-generated method stub
		/*if(o == null || o.toString().length()==0)
			return true;*/
		try {
			if(o == null || o.toString().length()==0)
				o = 0;
			Double.parseDouble(o.toString());
		} catch (NumberFormatException e) {
			return true;
		}
		return false;
	}

	@Override
	public String getInvalidateMessage() {
		// TODO Auto-generated method stub
		return msg + "数值错误";
	}
}
