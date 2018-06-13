package cn.edu.sdu.uims.component.complex.calendar.schedule;

import java.util.ArrayList;
import java.util.List;

public class MonthViewStruct {

	private int startDayOffset = 0;
	private int endDayOffset = 0;
	private int monthViewStartX;
	private int monthViewStartY;
	public List<MonthViewCoordinate> coordinateList = new ArrayList<MonthViewCoordinate>();
	public int getStartDayOffset() {
		return startDayOffset;
	}
	public void setStartDayOffset(int startDayOffset) {
		this.startDayOffset = startDayOffset;
	}
	public int getEndDayOffset() {
		return endDayOffset;
	}
	public void setEndDayOffset(int endDayOffset) {
		this.endDayOffset = endDayOffset;
	}
	public int getMonthViewStartX() {
		return monthViewStartX;
	}
	public void setMonthViewStartX(int monthViewStartX) {
		this.monthViewStartX = monthViewStartX;
	}
	public int getMonthViewStartY() {
		return monthViewStartY;
	}
	public void setMonthViewStartY(int monthViewStartY) {
		this.monthViewStartY = monthViewStartY;
	}
	public List<MonthViewCoordinate> getCoordinateList() {
		return coordinateList;
	}
	public void setCoordinateList(List<MonthViewCoordinate> coordinateList) {
		this.coordinateList = coordinateList;
	}

	public int getCrossDays() {
		return endDayOffset - startDayOffset + 1;
	}

}
