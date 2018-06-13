package cn.edu.sdu.uims.hcims.form;

import cn.edu.sdu.uims.itms.cursor.ICursorDataPoints;
import cn.edu.sdu.uims.itms.form.IForm;

public class PointForm extends IForm {
	//public ICursorDataPoints cursorPointsToImg;//points
	private ICursorDataPoints points;//points

	public PointForm() {
		points = new ICursorDataPoints();
	}

	public void clearForUse() {
		points.clearContent();
	}

	public ICursorDataPoints getpoints() {
		return points;
	}

	public void setpoints(ICursorDataPoints cursorPointsToImg) {
		this.points = cursorPointsToImg;
	}

}
