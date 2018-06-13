package cn.edu.sdu.uims.component.complex.calendar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextLayout;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cn.edu.sdu.common.util.DateTimeTool;
import cn.edu.sdu.uims.component.complex.calendar.form.CalendarFormI;
import cn.edu.sdu.uims.component.complex.calendar.form.CalendarFormObjectI;
import cn.edu.sdu.uims.view.UVUtil;

public class CalendarGraphMonth extends CalendarGraphBase {

	protected int dayRowY1[];
	protected MonthViewDayUnit[][] dayUnitArr;
	protected int[] currentRowCapability;

	public CalendarGraphMonth(UCalendarPanel calenderPanel) {
		super(calenderPanel);
		// TODO Auto-generated constructor stub
	}

	public void drawDataHead(Graphics g) {
		Color oldColor = g.getColor();
		g.setColor(BLACK_COLOR);

		int colNum = DAY_NAMES.length;
		int uintWidth = template.unitWidth;
		int uintHeight = (template.unitHeadHeight);
		int xo = 0;
		int yo = 0;
		Font font = TITLE_FONT;
		Font oldFont = g.getFont();
		g.setFont(font);
		for (int i = 0; i < colNum; i++) {
			g.drawRect(xo + i * uintWidth, yo, uintWidth, uintHeight);
			UVUtil.drawStringBox(g, DAY_NAMES[i], font, xo + i * uintWidth, yo,
					uintWidth, uintHeight);
		}
		g.setFont(oldFont);
		g.setColor(oldColor);
	}
	
	private String getDisplayStr(List<CalendarFormI> list){
		String str = "";
		if(list == null || list.size() == 0) {
			return str;
		}
		for(int i = 0; i < list.size();i++){
			str += list.get(i).getDispyString();
			if(i < list.size()-1)
				str += "\n";
		}
		return str;
	}
	public void drawDayDateObject(Graphics g, int x1, int y1, int uintWidth , int height, List<CalendarFormI> list, int index){
		Color oldColor = g.getColor();
		g.setColor(BLACK_COLOR);

		Font font = TITLE_FONT;
		Font oldFont = g.getFont();
		g.setFont(font);
//		UVUtil.drawStringBox(g, getDisplayStr(list), BLACK_COLOR,font, x, y,uintWidth, height,UConstants.ALIGNMENT_LEFT,
//				UConstants.ALIGNMENT_CENTER, 0, false,null,0 );
        String m_text = getDisplayStr(list);
        int m_width = uintWidth;
        int m_height = height;

        AttributedString styledText = new AttributedString(m_text);
        AttributedCharacterIterator m_iterator = styledText.getIterator();
        int m_start = m_iterator.getBeginIndex();
        int m_end = m_iterator.getEndIndex();
        Graphics2D g2 = (Graphics2D) g;
        FontRenderContext frc = g2.getFontRenderContext();

        LineBreakMeasurer measurer = new LineBreakMeasurer(m_iterator, frc);
        measurer.setPosition(m_start);

        float x = x1, y = y1;
        while (measurer.getPosition() < m_end)
        {
            TextLayout layout = measurer.nextLayout(m_width);

            y += layout.getAscent();
            float dx = layout.isLeftToRight() ?
                    0 : m_width - layout.getAdvance();

            layout.draw(g2, x + dx, y);
            y += layout.getDescent() + layout.getLeading();
        }
		g.setFont(oldFont);
		g.setColor(oldColor);
		
	}

	
	public void drawDayUint(Graphics g, int row, int col, int y, int height,
			String day, List<CalendarFormI> list,int index) {

		String currentDay = calendarPanel.getCurrentDateString();
		Color oldColor = g.getColor();
		int uintWidth = template.unitWidth;
		int x = col * uintWidth;

		if (day.equals(currentDay)) {
			g.setColor(BACKGROUND_COLOR);
			g.fillRect(x + 1, y + 1, uintWidth - 2, height - 2);
		}
		g.setColor(BLACK_COLOR);
		g.drawRect(x, y, uintWidth, height);
		Font oldFont = g.getFont();
		Font font = DAY_FONT;
		g.setFont(font);
		if (!day.substring(5, 7).equals(currentDay.substring(5, 7)))
			g.setColor(GRAY_COLOR);
		if (day.charAt(8) == '0') {
			g.drawString(day.substring(9, 10), x + uintWidth - 8, y + 10);
		} else {
			g.drawString(day.substring(8, 10), x + uintWidth - 16, y + 10);
		}
		g.setFont(oldFont);
		g.setColor(oldColor);
		if(list != null) {
			drawDayDateObject(g, x, y, uintWidth , height, list, index);
		}
	}

	public void computeRowCapability() {
		int i;
		currentRowCapability = new int[calendarPanel.getMultiWeekDays().length / 7];
		for (i = 0; i < currentRowCapability.length; i++)
			currentRowCapability[i] = template.infoMinNum;

	}

	protected void genDayUnitArr() {
		int col, row;
		Date day = calendarPanel.getMonthStartEndDate()[0];
		String days[] = calendarPanel.getMultiWeekDays();
		int rowNum = days.length / WEEK_DAY_NUM;
		MonthViewDayUnit dayUnit;
		dayUnitArr = new MonthViewDayUnit[calendarPanel.getMultiWeekDays().length / 7][7];
		for (row = 0; row < rowNum; row++) {
			for (col = 0; col < DAY_NAMES.length; col++) {

				dayUnit = new MonthViewDayUnit(template.infoMinNum,
						template.infoMaxNum);
				dayUnit.row = row;
				dayUnit.col = col;
				dayUnit.dayLabel = days[row * WEEK_DAY_NUM + col];
				dayUnit.day = day;
				dayUnitArr[row][col] = dayUnit;
				day = DateTimeTool.nextDay(day);
			}
		}
	}

	protected void genDayHeightData() {
		dayRowY1 = new int[calendarPanel.getMultiWeekDays().length / 7];
		int i;
		dayRowY1[0] = template.unitHeadHeight + template.infoUnitHeight
				+ template.infoUnitHeight * currentRowCapability[0];
		for (i = 1; i < dayRowY1.length; i++) {
			dayRowY1[i] = dayRowY1[i - 1] + template.infoUnitHeight
					+ template.infoUnitHeight * (currentRowCapability[i]);
		}
	}

	public Dimension getGraphSize() {
		int w = template.unitWidth * WEEK_DAY_NUM;
		return new Dimension(w, dayRowY1[dayRowY1.length - 1]);

	}

	public void clearViewData() {
		int i,j;
		if(dayUnitArr == null)
			return;
		for(i = 0; i < dayUnitArr.length;i++) {
			for(j = 0; j < dayUnitArr[i].length;j++) {
				dayUnitArr[i][j].dataList = null;
			}
		}
	}

	public void preProcessViewData(List<CalendarFormObjectI> dataList) {
		HashMap<String, List<CalendarFormI>> map = new HashMap<String, List<CalendarFormI>>();
		int i,j;
		CalendarFormI form;
		String dateStr;
		List<CalendarFormI> list;
		for (i = 0; i < dataList.size(); i++) {
			form = dataList.get(i).getCalendar();
			dateStr = DateTimeTool.getTimeStr(form.getDate(), "yyyy-MM-dd");
			list = map.get(dateStr);
			if (list == null) {
				list = new ArrayList<CalendarFormI>();
				map.put(dateStr, list);
			}
			list.add(form);
		}
		for(i = 0; i < dayUnitArr.length;i++) {
			for(j = 0; j < dayUnitArr[i].length;j++) {
				dayUnitArr[i][j].dataList = map.get(DateTimeTool.getTimeStr(dayUnitArr[i][j].day, "yyyy-MM-dd"));
			}
		}
	}

	public void preProcessData() {
		clearViewData();
		computeRowCapability();
		genDayUnitArr();
		List<CalendarFormObjectI> dataList = (List<CalendarFormObjectI>) calendarPanel.getData();
		if (dataList != null && dataList.size() > 0)
			preProcessViewData(dataList);
		genDayHeightData();
	}

	public void drawContentTable(Graphics g) {
		int col, row;
		int y0 = template.unitHeadHeight;
		for (row = 0; row < dayUnitArr.length; row++) {
			for (col = 0; col < dayUnitArr[row].length; col++) {
				drawDayUint(g, row, col, y0, dayRowY1[row] - y0,
						dayUnitArr[row][col].dayLabel, dayUnitArr[row][col].dataList,dayUnitArr[row][col].index);
			}
			y0 = dayRowY1[row];
		}
	}

	public void drawDataTable(Graphics g) {
		
	}
	public MonthViewDayUnit getSelectCurrentCalendarDate( int x, int y){
		int col, row,x0;
		int uintWidth = template.unitWidth;
		int y0 = template.unitHeadHeight;
		for (row = 0; row < dayUnitArr.length; row++) {
			for (col = 0; col < dayUnitArr[row].length; col++) {
				x0 = col * uintWidth;
				if(x > x0 && x< x0 + uintWidth && y > y0 && y < dayRowY1[row])
					return dayUnitArr[row][col];
			}
			y0 = dayRowY1[row];
		}
		return null;
	}

}
