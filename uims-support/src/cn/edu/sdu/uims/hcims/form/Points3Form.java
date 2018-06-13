package cn.edu.sdu.uims.hcims.form;

import cn.edu.sdu.uims.itms.cursor.ICursorDataPoints;
import cn.edu.sdu.uims.itms.form.IForm;

public class Points3Form extends IForm {
	public ICursorDataPoints points1 = new ICursorDataPoints();
	public ICursorDataPoints points2 = new ICursorDataPoints();
	public ICursorDataPoints points3 = new ICursorDataPoints();

	public void clearForUse() {
		points1.clearContent();
		points2.clearContent();
		points3.clearContent();
	}

	public ICursorDataPoints getPoints1() {
		return points1;
	}

	public void setPoints1(ICursorDataPoints points1) {
		this.points1 = points1;
	}

	public ICursorDataPoints getPoints2() {
		return points2;
	}

	public void setPoints2(ICursorDataPoints points2) {
		this.points2 = points2;
	}

	public ICursorDataPoints getPoints3() {
		return points3;
	}

	public void setPoints3(ICursorDataPoints points3) {
		this.points3 = points3;
	}
}
