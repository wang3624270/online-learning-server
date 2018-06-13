package cn.edu.sdu.uims.hcims.form;

import cn.edu.sdu.uims.itms.cursor.ICursorDataPoints;
import cn.edu.sdu.uims.itms.form.IForm;

public class NewPointForm extends IForm {
	public ICursorDataPoints pointsSeg1;
	public Points3Form formSeg2;
	public NewPointForm(){
		pointsSeg1 = new ICursorDataPoints();
		formSeg2 = new Points3Form();
	}

	public void clearForUse(){
		pointsSeg1.clearContent();
		formSeg2.clearForUse();
	}

	public ICursorDataPoints getPointsSeg1() {
		return pointsSeg1;
	}

	public void setPointsSeg1(ICursorDataPoints pointsSeg1) {
		this.pointsSeg1 = pointsSeg1;
	}

	public Points3Form getFormSeg2() {
		return formSeg2;
	}

	public void setFormSeg2(Points3Form formSeg2) {
		this.formSeg2 = formSeg2;
	}
}
