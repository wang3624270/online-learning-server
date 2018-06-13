package cn.edu.sdu.uims.component.complex.calendar.schedule;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import cn.edu.sdu.common.util.DateTimeTool;
import cn.edu.sdu.uims.component.complex.calendar.CalendarGraphMonth;
import cn.edu.sdu.uims.component.complex.calendar.MonthViewDayUnit;
import cn.edu.sdu.uims.component.complex.calendar.UCalendarPanel;
import cn.edu.sdu.uims.component.complex.calendar.form.CalendarFormI;
import cn.edu.sdu.uims.component.complex.calendar.form.CalendarFormObjectI;
import cn.edu.sdu.uims.component.complex.calendar.schedule.form.WorkScheWorkForm;
import cn.edu.sdu.uims.component.complex.def.UCalendarTemplate;

public class ScheduleGraphMonth extends CalendarGraphMonth {

	public ScheduleGraphMonth(UCalendarPanel calenderPanel) {
		super(calenderPanel);
		// TODO Auto-generated constructor stub
	}

	private List<MouthViewEventGraph> eventGraphList = new ArrayList<MouthViewEventGraph>();


	// currentInfoMaxPosition

	public void clearViewData(){
		eventGraphList.clear();		
	}
	public void preProcessViewData(List<CalendarFormObjectI> dataList) {		
		WorkScheWorkForm form;
		Date s, e;

		MouthViewEventGraph eventGraph;
		int i;
		UCalendarTemplate template = calendarPanel.getCalendarTemplate();
		Date monthStartEndDate[] = calendarPanel.getMonthStartEndDate();
		String days[] = calendarPanel.getMultiWeekDays();
		for (i = 0; i < dataList.size(); i++) {
			form = (WorkScheWorkForm)(dataList.get(i).getCalendar());
			eventGraph = new MouthViewEventGraph();
			eventGraph.setScheduleInfoForm(form);
			eventGraph.makeViewData();
			s = form.getScheduleStartDate();
			e = form.getScheduleEndDate();
			makeEventViewCoordinateData(s, e, monthStartEndDate, days,
						eventGraph.getView());
			eventGraphList.add(eventGraph);
		}
		Collections.sort(eventGraphList, new EventGraphComparator());
		for (i = 0; i < eventGraphList.size(); i++) {
			calDayUnitPosition(eventGraphList.get(i));
		}
	}


	private void makeEventViewCoordinateData(Date s, Date e,
			Date monthStartEndDate[], String days[], MonthViewStruct view) {
		int d1, d2, row, col1, col2;

		if (e.before(monthStartEndDate[0]) || s.after(monthStartEndDate[1]))
			return;
		if (s.before(monthStartEndDate[0]))
			d1 = 0;
		else
			d1 = DateTimeTool.getDiffDays(monthStartEndDate[0], s);
		if (e.after(monthStartEndDate[1]))
			d2 = 34;
		else
			d2 = DateTimeTool.getDiffDays(monthStartEndDate[0], e);

		if (d2 >= days.length)
			d2 = days.length - 1;

		view.setStartDayOffset(d1);
		view.setEndDayOffset(d2);

		row = d1 / 7;
		col1 = d1 % 7;

		MonthViewCoordinate c;

		view.coordinateList.clear();

		while (row * 7 <= d2) {
			c = new MonthViewCoordinate();
			c.x = col1;
			c.y = row;
			view.coordinateList.add(c);
			if (row * 7 + 7 <= d2) {
				col2 = 6;
			} else {
				col2 = d2 - row * 7;
			}
			c = new MonthViewCoordinate();
			c.x = col2;
			c.y = row;
			view.coordinateList.add(c);
			col1 = 0;
			row++;
		}

	}


	public void calDayUnitPosition(MouthViewEventGraph eventGraph) {
			calDayUnitPosition(eventGraph.getView());
	}

	public void calDayUnitPosition(MonthViewStruct eventGraph) {

		int i, j, k, x, y, x2;
		int p;
		MonthViewDayUnit dayUnit;
		boolean flag;
		for (i = 0; i < eventGraph.coordinateList.size(); i = i + 2) {
			x = eventGraph.coordinateList.get(i).x;
			y = eventGraph.coordinateList.get(i).y;
			x2 = eventGraph.coordinateList.get(i + 1).x;
			dayUnit = dayUnitArr[y][x];

			for (j = 0; j < dayUnit.occupyArr.length; j++) {
				flag = true;
				for (k = x; k <= x2; k++) {

					if (dayUnitArr[y][k].occupyArr[j] == 1) {
						flag = false;
						break;
					}

				}
				if (flag) {
					eventGraph.coordinateList.get(i).dayUnitPosition = j;
					eventGraph.coordinateList.get(i + 1).dayUnitPosition = j;
					for (k = x; k <= x2; k++) {
						dayUnitArr[y][k].occupyArr[j] = 1;
						if (j > dayUnitArr[y][k].currentInfoMaxPosition)
							dayUnitArr[y][k].currentInfoMaxPosition = j;
					}
					p = 0;
					for (k = 0; k <= 6; k++) {
						if (p < dayUnitArr[y][k].currentInfoMaxPosition)
							p = dayUnitArr[y][k].currentInfoMaxPosition;
					}
					if ((p + 1) > currentRowCapability[y])
						currentRowCapability[y] = p + 1;
					break;
				}
			}

		}

	}


	public void drawDataTable(Graphics g) {

		drawEventGraphs(g);
	}

	public void drawEventGraphs(Graphics g) {
		int i, j;
		int unitWidth = template.unitWidth;
		int uintHeight = template.infoUnitHeight;
		int x, y;

		MouthViewEventGraph graph;
		MonthViewStruct view;
		int h = template.unitHeadHeight;
		g.setFont(TITLE_FONT);
		for (i = 0; i < eventGraphList.size(); i++) {
			graph = eventGraphList.get(i);
			view = graph.getView();
			if (view != null) {
				for (j = 0; j < view.coordinateList.size(); j = j + 2) {
					x = view.coordinateList.get(j).x * unitWidth + 1;
					if (view.coordinateList.get(j).y != 0)
						h = dayRowY1[view.coordinateList.get(j).y - 1];
					y = h + uintHeight + uintHeight
							* view.coordinateList.get(j).dayUnitPosition + 1;
					g.setColor(EVENT_COLOR);
					g.fillRect(x, y, (view.coordinateList.get(j + 1).x
							- view.coordinateList.get(j).x + 1)
							* unitWidth - 2, uintHeight - 2);
					g.setColor(TITLE_COLOR);
					if (eventGraphList.get(i).getScheduleInfoForm().getTitle() != null)
						g.drawString(eventGraphList.get(i)
								.getScheduleInfoForm().getTitle(), x, y
								+ uintHeight - 4);
				}
			}
		}

	}
	public MouthViewEventGraph getCurrentSelectEnent(int x, int y) {
		return null;
	}

	class EventGraphComparator implements Comparator {

		public int compare(Object arg0, Object arg1) {
			MouthViewEventGraph e0 = (MouthViewEventGraph) arg0;
			MouthViewEventGraph e1 = (MouthViewEventGraph) arg1;

			int flag = Integer.valueOf(e0.getCrossDays()).compareTo(
					Integer.valueOf(e1.getCrossDays()));
			return -flag;
		}

	}

	public CalendarFormI getSelectCalendarInfoForm(int xt, int yt) {
		int i, j;
		int unitWidth = template.unitWidth;
		int uintHeight = template.infoUnitHeight;
		int x, y;

		MouthViewEventGraph graph;
		MonthViewStruct view;
		int h = template.unitHeadHeight;
		for (i = 0; i < eventGraphList.size(); i++) {
			graph = eventGraphList.get(i);
			view = graph.getView();
			if (view == null)
				continue;
			for (j = 0; j < view.coordinateList.size(); j = j + 2) {
				x = view.coordinateList.get(j).x * unitWidth + 1;
				if (view.coordinateList.get(j).y != 0)
					h = dayRowY1[view.coordinateList.get(j).y - 1];
				y = h + uintHeight + uintHeight
						* view.coordinateList.get(j).dayUnitPosition + 1;
				if (xt >= x
						&& xt <= x
								+ (view.coordinateList.get(j + 1).x
										- view.coordinateList.get(j).x + 1)
								* unitWidth && yt >= y && yt <= y + uintHeight)
					return eventGraphList.get(i).getScheduleInfoForm();
			}
		}
		return null;
	}

}
