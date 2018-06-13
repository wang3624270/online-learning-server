package cn.edu.sdu.uims.component.complex.calendar.schedule;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import cn.edu.sdu.uims.component.complex.calendar.CalendarGraphBase;
import cn.edu.sdu.uims.component.complex.def.UCalendarTemplate;
import cn.edu.sdu.uims.view.UVUtil;

public class ScheduleGraphDay extends CalendarGraphBase {


	public static int DISTANCE_H = 2;

	protected int dayNum = 1;
	
	public ScheduleGraphDay(USchedulePanel schedule){
		super(schedule);
	}
	public void init(){
		
	}

	public void drawDataHead(Graphics g){
		UCalendarTemplate template = calendarPanel.getCalendarTemplate();
		Font oldFont = g.getFont();
		g.setFont(TITLE_FONT);
		Color oldColor = g.getColor();
		g.setColor(BLACK_COLOR);
		g.drawRect(0, 0,template.leftMenuWidth , template.unitHeadHeight);
		int width = template.dayUnitWidth * DAY_NAMES.length /dayNum;
		String str;
		String days[] = calendarPanel.getOneWeekDays();
		for(int col = 0; col < dayNum;col++) {
			g.drawRect(template.leftMenuWidth+ col * width, 0, width,template.unitHeadHeight);
			str = DAY_NAMES[col] + " " + days[col].subSequence(5, 10);
			UVUtil.drawStringBox(g, str, TITLE_FONT, template.leftMenuWidth+ col * width,0, width,template.unitHeadHeight);
		}
		g.setColor(oldColor);
		g.setFont(oldFont);
	}

	public  void drawCurrentDayMark(Graphics g){
	}
	public void drawContentTable(Graphics g){
		drawCurrentDayMark(g);
		drawContentAllDay(g);
		drawContentTime(g);
	}
	public void drawContentAllDay(Graphics g){
		Color oldColor = g.getColor();
		UCalendarTemplate template = calendarPanel.getCalendarTemplate();
		int width = template.dayUnitWidth * DAY_NAMES.length /dayNum;
		g.drawRect(0, template.unitHeadHeight,template.leftMenuWidth , template.allDayUintHeight);
		UVUtil.drawStringBox(g, "全天", TITLE_FONT,0, template.unitHeadHeight,template.leftMenuWidth , template.allDayUintHeight);
		for(int col = 0; col < dayNum;col++) {
			g.drawRect(template.leftMenuWidth+ col * width, template.unitHeadHeight, width,template.allDayUintHeight);
		}
		g.setColor(oldColor);
		
	}
	public void drawContentTime(Graphics g){
		UCalendarTemplate template = calendarPanel.getCalendarTemplate();
		Color oldColor = g.getColor();
		Graphics2D dc2d = (Graphics2D) g;
		Stroke oldStroke = null;
		g.setColor(BLACK_COLOR);
		int width = template.dayUnitWidth * DAY_NAMES.length /dayNum;
		int col, row;
		int y = template.unitHeadHeight+ template.allDayUintHeight+DISTANCE_H;
		for(row = 0;  row< template.timeNames.length;row ++ ) {
			g.drawRect(0, y ,template.leftMenuWidth , template.timeUnitHeight*2);
			UVUtil.drawStringBox(g, template.timeNames[row], TITLE_FONT, 0, y, template.leftMenuWidth , template.timeUnitHeight);
			for(col = 0; col < dayNum;col++) {
				g.drawRect(template.leftMenuWidth+ col * width, y, width,template.timeUnitHeight*2);
			}
			y +=  2* template.timeUnitHeight; 
		}
		oldStroke = dc2d.getStroke();
		dc2d.setStroke(DASH_LINE_STORK);
		g.setColor(GRAY_COLOR);
		y = template.unitHeadHeight+ template.allDayUintHeight+DISTANCE_H;
		for(row = 0;  row< template.timeNames.length;row ++ ) {
			g.drawLine(0, y+ template.timeUnitHeight ,template.leftMenuWidth +dayNum* width, y+ template.timeUnitHeight);
			y +=  2* template.timeUnitHeight; 
		}
		dc2d.setStroke(oldStroke);
		g.setColor(oldColor);
	}
	public Dimension getGraphSize(){
		UCalendarTemplate template = calendarPanel.getCalendarTemplate();
		int w = template.dayUnitWidth*WEEK_DAY_NUM + template.leftMenuWidth;
		int h = template.unitHeadHeight +template.allDayUintHeight+ DISTANCE_H+ 2*template.timeUnitHeight*template.timeNames.length;
		return new Dimension(w,h);
	}

}
