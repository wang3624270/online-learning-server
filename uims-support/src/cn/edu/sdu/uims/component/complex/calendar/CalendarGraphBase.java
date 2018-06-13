package cn.edu.sdu.uims.component.complex.calendar;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Stroke;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import cn.edu.sdu.uims.component.complex.calendar.form.CalendarFormI;
import cn.edu.sdu.uims.component.complex.def.UCalendarTemplate;

public class CalendarGraphBase extends JPanel {

	public static Stroke DASH_LINE_STORK = new BasicStroke(1.0f,
			BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 3.5f, new float[] {
					2, 1 }, 0f);
	public static Color BLACK_COLOR = new Color(0, 0, 0);
	public static Color GRAY_COLOR = new Color(128, 128, 128);
	public static Color BACKGROUND_COLOR = new Color(214, 214, 214);
	public static Color EVENT_COLOR = new Color(0, 0, 192);
	public static Color EVENT_COLOR_REAL = new Color(0, 192, 192);
	public static Color TITLE_COLOR = new Color(255, 255, 255);
	public static Font TITLE_FONT = new Font("宋体", Font.PLAIN, 16);
	public static Font DAY_FONT = new Font("宋体", Font.PLAIN, 8);
	public static int WEEK_DAY_NUM = 7;

	protected UCalendarTemplate template;

	protected JScrollPane scrollPane;
	protected UCalendarPanel calendarPanel;
	public static String DAY_NAMES[] = { "星期日", "星期一", "星期二", "星期三", "星期四",
			"星期五", "星期六" };

	public CalendarGraphBase(UCalendarPanel calendarPanel) {
		this.calendarPanel = calendarPanel;
		template = calendarPanel.getCalendarTemplate();
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

	public Dimension getGraphSize() {
		return new Dimension(1, 1);
	}

	public void init() {
		this.addMouseListener(calendarPanel.getEventPorcessor());
	}

	public void paintComponent(Graphics g) {
		resetPanelSize();
		super.paintComponent(g);
		drawCalendarTable(g);
		drawDataTable(g);
	}

	public void drawCalendarTable(Graphics g) {
		drawDataHead(g);
		drawContentTable(g);
	}

	public void drawDataHead(Graphics g) {
	}

	public void drawContentTable(Graphics g) {
	}

	public void preProcessData() {
	}

	public void drawDataTable(Graphics g) {
	}

	public void resetPanelSize() {
		Dimension d = this.getGraphSize();
		this.setSize(d);
		this.setPreferredSize(d);
		scrollPane.updateUI();
	}
	public CalendarFormI getSelectCalendarInfoForm(int x, int y){
		return null;
	}
	public MonthViewDayUnit getSelectCurrentCalendarDate( int x, int y){
		return null;
	}
	
}
