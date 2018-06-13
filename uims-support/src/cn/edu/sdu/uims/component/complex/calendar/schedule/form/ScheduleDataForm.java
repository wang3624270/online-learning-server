package cn.edu.sdu.uims.component.complex.calendar.schedule.form;

import java.util.Date;

import cn.edu.sdu.common.form.UForm;

public class ScheduleDataForm extends UForm {
	private Date currentDate;

	public Date getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}
}
