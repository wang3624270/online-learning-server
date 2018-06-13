package cn.edu.sdu.uims.component.event;

import java.util.Date;
import java.util.EventObject;

public class CalendarEvent extends EventObject {

	private int selectIndex;
	private Date selectDate;
	private Object dataObject;
	
	public CalendarEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}

	public CalendarEvent(Object source,  Date selectDate, Object dataObject, int selectIndex) {
		this(source);
		this.selectIndex = selectIndex;
		this.selectDate = selectDate;
		this.dataObject = dataObject;
	}
	public int getSelectIndex(){
		return selectIndex;
	}
	public Date getSelectDate(){
		return selectDate;
	}

	public Object getDataObject() {
		return dataObject;
	}

	public void setDataObject(Object dataObject) {
		this.dataObject = dataObject;
	}

	public void setSelectIndex(int selectIndex) {
		this.selectIndex = selectIndex;
	}

	public void setSelectDate(Date selectDate) {
		this.selectDate = selectDate;
	}

}
