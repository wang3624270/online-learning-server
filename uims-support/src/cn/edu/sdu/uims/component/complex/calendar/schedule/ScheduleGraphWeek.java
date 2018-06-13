package cn.edu.sdu.uims.component.complex.calendar.schedule;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Date;
import java.util.List;

import cn.edu.sdu.common.util.DateTimeTool;
import cn.edu.sdu.uims.component.complex.calendar.form.CalendarFormI;
import cn.edu.sdu.uims.component.complex.calendar.form.CalendarFormObjectI;
import cn.edu.sdu.uims.component.complex.def.UCalendarTemplate;

public class ScheduleGraphWeek extends ScheduleGraphDay {

	public ScheduleGraphWeek(USchedulePanel schedule) {
		super(schedule);
		dayNum = WEEK_DAY_NUM;
	}

	public void drawCurrentDayMark(Graphics g) {
		Color oldColor = g.getColor();
		String day[] = calendarPanel.getOneWeekDays();
		String cDay = calendarPanel.getCurrentDateString();
		g.setColor(BACKGROUND_COLOR);
		UCalendarTemplate template = calendarPanel.getCalendarTemplate();
		for (int col = 0; col < dayNum; col++) {
			if (cDay.equals(day[col])) {
				g.fillRect(
						template.leftMenuWidth + col * template.dayUnitWidth,
						template.unitHeadHeight, template.dayUnitWidth,
						template.allDayUintHeight + DISTANCE_H
								+ template.timeUnitHeight * template.timeNames.length);
			}
		}
		g.setColor(oldColor);
	}
	
	public void drawDataTable(Graphics g) {
		List<CalendarFormObjectI> dataList = (List<CalendarFormObjectI>) calendarPanel.getData();
		if(dataList == null || dataList.size() == 0)
			return;
		UCalendarTemplate template = calendarPanel.getCalendarTemplate();
		Color oldColor = g.getColor();
		Date wDs [] = calendarPanel.getWeekStartEndDate();
		String day[] = calendarPanel.getOneWeekDays();
		String sDateStr, eDateStr;
		int i;
		CalendarFormI cal;
		Date sDate, eDate;
		int sTime, eTime;
		int width = template.dayUnitWidth * DAY_NAMES.length /dayNum;
		int y = template.unitHeadHeight+ template.allDayUintHeight+DISTANCE_H;
		int row;
		for(i = 0; i < dataList.size();i++) {
			cal = dataList.get(i).getCalendar();
			sDate = cal.getDate();
			eDate = cal.getEndDate();
			if(eDate.before(wDs[0]) || sDate.after(wDs[1]))
				continue;
			sDateStr = DateTimeTool.parseDateTime(sDate, "yyyy-MM-dd");
			eDateStr = DateTimeTool.parseDateTime(eDate, "yyyy-MM-dd");
			for (int col = 0; col < dayNum; col++) {
				if(day[col].compareTo(sDateStr) < 0 || day[col].compareTo(eDateStr) > 0)
					continue;
				if (sDateStr.equals(day[col])) {
					sTime = sDate.getHours();
				}else  {
					sTime = 0;
				}
				if(eDateStr.equals(day[col])) {
					eTime =eDate.getHours();
				}else {
					eTime = 23;
				}
				g.setColor(EVENT_COLOR);
				for(row = sTime;  row<= eTime;row ++ ) {
					g.fillRect(
							template.leftMenuWidth + col * width+1,
							y+ row*template.timeUnitHeight*2+1, 
							width-2,
							template.timeUnitHeight*2-2);
				}
				if(sDateStr.equals(day[col])) {
					g.setColor(TITLE_COLOR);
					g.drawString(cal.getDispyString(),
							template.leftMenuWidth + col * width+2,
							y+ sTime*template.timeUnitHeight*2+template.timeUnitHeight+2);					
				}
			}
		}
		g.setColor(oldColor);
	}

}
