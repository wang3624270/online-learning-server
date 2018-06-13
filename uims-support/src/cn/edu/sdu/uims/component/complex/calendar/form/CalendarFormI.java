package cn.edu.sdu.uims.component.complex.calendar.form;

import java.util.Date;

public interface CalendarFormI {
	Integer getId();
	Date  getDate();
	Date  getEndDate();
	String getDispyString();
	String getHref();
}
