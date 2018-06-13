package cn.edu.sdu.uims.component.complex.calendar.schedule;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Date;
import java.util.List;

import cn.edu.sdu.common.util.DateTimeTool;
import cn.edu.sdu.uims.component.complex.calendar.form.CalendarFormI;
import cn.edu.sdu.uims.component.complex.calendar.form.CalendarFormObjectI;
import cn.edu.sdu.uims.component.complex.def.UCalendarTemplate;

public class ScheduleGraphOneDay extends ScheduleGraphDay {

	public ScheduleGraphOneDay(USchedulePanel schedule) {
		super(schedule);
		// TODO Auto-generated constructor stub
		dayNum = 1;
	}
	public void drawDataTable(Graphics g) {
		List<CalendarFormObjectI> dataList = (List<CalendarFormObjectI>) calendarPanel.getData();
		if(dataList == null || dataList.size() == 0)
			return;
		UCalendarTemplate template = calendarPanel.getCalendarTemplate();
		String cDay = calendarPanel.getCurrentDateString();
		Color oldColor = g.getColor();
		Date wDs [] = calendarPanel.getWeekStartEndDate();
		String day[] = calendarPanel.getOneWeekDays();
		String sDateStr, eDateStr;
		g.setColor(EVENT_COLOR);
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
			sDateStr = DateTimeTool.parseDateTime(sDate, "yyyy-MM-dd");
			eDateStr = DateTimeTool.parseDateTime(eDate, "yyyy-MM-dd");
			if(sDateStr.compareTo(cDay) >0 || eDateStr.compareTo(cDay) <0)
				continue;
				if (sDateStr.equals(cDay)) {
					sTime = sDate.getHours();
				}else  {
					sTime = 0;
				}
				if(eDateStr.equals(cDay)) {
					eTime =eDate.getHours();
				}else {
					eTime = 23;
				}
				for(row = sTime;  row<= eTime;row ++ ) {
					g.fillRect(
							template.leftMenuWidth + 1,
							y+ row*template.timeUnitHeight*2+1, 
							width-2,
							template.timeUnitHeight*2-2);
				}
		}
		g.setColor(oldColor);
	}

}
