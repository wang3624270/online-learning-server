package cn.edu.sdu.uims.validator;

public class NaturalNumberValidator extends BaseDataValidator {
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
		if(Integer.parseInt(o.toString())<0){
			return true;
		}
		return false;
	}

	@Override
	public String getInvalidateMessage() {
		// TODO Auto-generated method stub
		if(msg != null)
			return msg + "必须为非负整数";
		else
			return  "数据必须为非负整数";
			
	}
}
