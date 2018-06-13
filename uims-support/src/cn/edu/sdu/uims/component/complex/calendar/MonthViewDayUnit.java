package cn.edu.sdu.uims.component.complex.calendar;

import java.util.Date;
import java.util.List;

import cn.edu.sdu.uims.component.complex.calendar.form.CalendarFormI;


public class MonthViewDayUnit {
	public int col;
	public int row;
	public Date day;
	public String dayLabel = "";
	public int[] occupyArr;
	public int currentInfoNumSapce;
	public int currentInfoMaxPosition;
	public List<CalendarFormI> dataList;
	public int index = 0;

	public MonthViewDayUnit(int infoMinNum, int infoMaxNum) {
		currentInfoNumSapce = infoMinNum;
		occupyArr = new int[infoMaxNum];
		currentInfoMaxPosition = 0;
		int i;
		for (i = 0; i < infoMaxNum; i++)
			occupyArr[i] = 0;
	}
}