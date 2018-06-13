package cn.edu.sdu.uims.component.textfield;

public class UTextFieldInt extends UTextField {
	public Object getData() {
		return new Integer(getText());
	}
}
