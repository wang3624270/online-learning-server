package cn.edu.sdu.uims.component.complex.calendar;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import cn.edu.sdu.common.util.DateTimeTool;
import cn.edu.sdu.uims.component.complex.UComplexPanel;
import cn.edu.sdu.uims.component.complex.calendar.form.CalendarFormI;
import cn.edu.sdu.uims.component.complex.calendar.form.CalendarFormObjectI;
import cn.edu.sdu.uims.component.complex.def.UCalendarTemplate;
import cn.edu.sdu.uims.component.event.CalendarEvent;
import cn.edu.sdu.uims.component.event.EventConstants;
import cn.edu.sdu.uims.component.menu.UMenu;
import cn.edu.sdu.uims.component.menu.UPopupMenu;
import cn.edu.sdu.uims.def.UEventAttribute;

public class UCalendarPanel extends UComplexPanel {

	public static String CURRENT_DISP_MODE_MONTH = "month";
	public static String CURRENT_DISP_MODE_WEEK = "week";
	public static String CURRENT_DISP_MODE_DAY = "day";
	public static String CURRENT_DISP_MODE_EVENT = "event";

	protected CalendarEventProcessor eventPorcessor = new CalendarEventProcessor();
	protected UPopupMenu popupMenu = null;
	protected boolean sendCalendarEvent = false; 
	protected Date currentDate;
	protected Date[] monthStartEndDate = new Date[2];
	protected Date[] weekStartEndDate = new Date[2];
	protected String currentDateString;
	protected String multiWeekDays[];
	protected String oneWeekDays[];
	protected String dataStamp = "";
	protected SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	protected String currentDispMode = CURRENT_DISP_MODE_MONTH; // 0- month, 1-
	protected JButton buttonPrev;
	protected JButton buttonNext;
	protected JButton buttonToday;

	protected JLabel calendarTitle;
	protected JPanel actionBar;
	protected JPanel calendarPanel;
	protected CalendarGraphBase[] calendarGraph;
	private boolean enablePopupMenu = true;

	private List dataList = null;


	public Date[] getMonthStartEndDate() {
		return monthStartEndDate;
	}

	public void setMonthStartEndDate(Date[] monthStartEndDate) {
		this.monthStartEndDate = monthStartEndDate;
	}

	public Date[] getWeekStartEndDate() {
		return weekStartEndDate;
	}

	public void setWeekStartEndDate(Date[] weekStartEndDate) {
		this.weekStartEndDate = weekStartEndDate;
	}

	public String[] getMultiWeekDays() {
		return multiWeekDays;
	}

	public void setMultiWeekDays(String[] multiWeekDays) {
		this.multiWeekDays = multiWeekDays;
	}

	public String[] getOneWeekDays() {
		return oneWeekDays;
	}

	public void setOneWeekDays(String[] oneWeekDays) {
		this.oneWeekDays = oneWeekDays;
	}

	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;
	}

	public String getCurrentDateString() {
		return currentDateString;
	}

	public void setCurrentDateString(String currentDateString) {
		this.currentDateString = currentDateString;
	}

	public String changeDateToString(Date date) {
		return dateFormat.format(date);
	}

	

	public Date changeStringToDate(String dateString) {
		try {
			return dateFormat.parse(dateString);
		} catch (Exception e) {
			return null;
		}
	}


	public String getCurrentDispMode() {
		return currentDispMode;
	}

	public void setCurrentDispMode(String currentDispMode) {
		this.currentDispMode = currentDispMode;
	}

	public CalendarEventProcessor getEventPorcessor() {
		return eventPorcessor;
	}

	public void setEventPorcessor(CalendarEventProcessor eventPorcessor) {
		this.eventPorcessor = eventPorcessor;
	}

	public UCalendarTemplate getCalendarTemplate() {
		if (this.elementTemplate == null) {
			this.elementTemplate = new UCalendarTemplate();
		}
		return (UCalendarTemplate) elementTemplate;
	}

	protected JScrollPane getJScrollPane(CalendarGraphBase graph) {
		JScrollPane p = new JScrollPane(graph);
		graph.setScrollPane(p);
		return p;
	}

	public String getDispCalendarTitle() {
		if (currentDispMode.equals(CURRENT_DISP_MODE_MONTH)
				|| currentDispMode.equals(CURRENT_DISP_MODE_EVENT)) {
			return DateTimeTool.getScheduleMonth(currentDate);
		} else if (currentDispMode.equals(CURRENT_DISP_MODE_WEEK)) {
			return DateTimeTool.changeDateStringToChines(oneWeekDays[0])
					+ "  --  "
					+ DateTimeTool.changeDateStringToChines(oneWeekDays[6]);
		} else
			return DateTimeTool.getScheduleDay(currentDate);
	}
	
	public void updatePanel() {
		calendarTitle.setText(this.getDispCalendarTitle());
		getWeekDaysData();
		int i;
		for (i = 0; i < calendarGraph.length; i++) { 
			calendarGraph[i].preProcessData();
		}
		calendarPanel.repaint();
	}
	
	public  void initData() {
		currentDispMode = CURRENT_DISP_MODE_MONTH;
		currentDate = Calendar.getInstance().getTime();
		currentDateString = changeDateToString(currentDate);
		this.updatePanel();
	}

	public void getWeekDaysData() {
		multiWeekDays = DateTimeTool
				.getScheduleMultiWeekDaysString(currentDate);
		oneWeekDays = DateTimeTool.getScheduleOneWeekDaysString(currentDate);
		monthStartEndDate[0] = changeStringToDate(multiWeekDays[0]
				+ " 00:00:00");
		monthStartEndDate[1] = changeStringToDate(multiWeekDays[multiWeekDays.length - 1]
				+ " 23:59:59");
		weekStartEndDate[0] = changeStringToDate(oneWeekDays[0] + " 00:00:00");
		weekStartEndDate[1] = changeStringToDate(oneWeekDays[6] + " 23:59:59");
		// geDayHeightData();
	}
	
	public JPanel getChangeMonthButtonsPanel(){
		JPanel panel;
		panel = new JPanel();
		panel.setLayout(new FlowLayout());
		buttonPrev = new JButton("<");
		buttonPrev.addActionListener(eventPorcessor);
		buttonPrev.setActionCommand("buttonPrev");
		panel.add(buttonPrev);

		buttonNext = new JButton(">");
		buttonNext.addActionListener(eventPorcessor);
		buttonNext.setActionCommand("buttonNext");
		panel.add(buttonNext);

		buttonToday = new JButton("今天");
		buttonToday.addActionListener(eventPorcessor);
		buttonToday.setActionCommand("buttonToday");
		panel.add(buttonToday);
		return panel;
	}
	public JPanel getChangeChangeModeButtonsPanel(){
		return null;
	}
	public void initActionBar() {
		actionBar = new JPanel();
		calendarTitle = new JLabel(getDispCalendarTitle());
		calendarTitle.setHorizontalAlignment(JLabel.CENTER);
		actionBar.setLayout(new BorderLayout());
		actionBar.add(getChangeMonthButtonsPanel(), BorderLayout.EAST);
		actionBar.add(calendarTitle, BorderLayout.CENTER);
		JPanel tp = getChangeChangeModeButtonsPanel();
		if(tp != null)
			actionBar.add(tp, BorderLayout.WEST);
	}


	public void initCalendarPanel() {
		UCalendarTemplate schudelTemplate = getCalendarTemplate();
		calendarPanel = new JPanel();
		calendarPanel.setLayout(new BorderLayout());
		calendarGraph = new CalendarGraphBase[1];
		calendarGraph[0] = new CalendarGraphMonth(this);
		calendarGraph[0].addMouseListener(eventPorcessor);
		calendarPanel.add(getJScrollPane(calendarGraph[0]),BorderLayout.CENTER);
	}


	@Override
	public void initContents() {
		initActionBar();
		initCalendarPanel();
		this.setLayout(new BorderLayout());
		add(actionBar, BorderLayout.NORTH);
		add(calendarPanel, BorderLayout.CENTER);
		initData();
	}
	public void setOtherData(List tList){
	}
	@Override
	public Object getData() {
		return dataList;
	}
	
	@Override
	public void setData(Object data) {
		dataList = (List<CalendarFormObjectI>) data;
		List tList = null; 
		if(dataList !=null && dataList.size()!=0){
			tList = new ArrayList();
			for(int i = 0; i < dataList.size();i++) {
				CalendarFormObjectI o = (CalendarFormObjectI)dataList.get(i);
				tList.add(o.getCalendar());
			}
		}
		setOtherData(tList);
		updatePanel();
	}

	
	public void doAction(String cmd) {
		if (cmd.equals("buttonPrev")) {
			doPrev();
		} else if (cmd.equals("buttonNext")) {
			doNext();
		} else if (cmd.equals("buttonToday")) {
			doToday();
		}
	}

	public void doPrev() {
		if (currentDispMode.equals(CURRENT_DISP_MODE_MONTH)
				|| currentDispMode.equals(CURRENT_DISP_MODE_EVENT)) {
			currentDate = DateTimeTool.prevMonth(currentDate);
		} else if (currentDispMode.equals(CURRENT_DISP_MODE_WEEK)) {
			currentDate = DateTimeTool.prevWeek(currentDate);
		} else if (currentDispMode.equals(CURRENT_DISP_MODE_DAY)) {
			currentDate = DateTimeTool.prevDay(currentDate);
		}
		currentDateString = changeDateToString(currentDate);
		updatePanel();
	}

	public void doNext() {
		if (currentDispMode.equals(CURRENT_DISP_MODE_MONTH)
				|| currentDispMode.equals(CURRENT_DISP_MODE_EVENT)) {
			currentDate = DateTimeTool.nextMonth(currentDate);
		} else if (currentDispMode.equals(CURRENT_DISP_MODE_WEEK)) {
			currentDate = DateTimeTool.nextWeek(currentDate);
		} else if (currentDispMode.equals(CURRENT_DISP_MODE_DAY)) {
			currentDate = DateTimeTool.nextDay(currentDate);
		}
		currentDateString = changeDateToString(currentDate);
		updatePanel();

	}
	public MonthViewDayUnit getSelectCurrentCalendarDate(CalendarGraphBase graphPanel, int x, int y){
		return graphPanel.getSelectCurrentCalendarDate(x, y);
	}

	public int getSelectCurrentCalendarObjectIndex(CalendarGraphBase graphPanel, int x, int y){
		if(graphPanel == null)
			return -1;
		CalendarFormI form = graphPanel.getSelectCalendarInfoForm(x, y);
		if(form == null)
			return -1;
		if(dataList == null || dataList.size()== 0)
			return  -1;
		CalendarFormI f;
		for(int i = 0; i < dataList.size();i++) {
			CalendarFormObjectI o = (CalendarFormObjectI)dataList.get(i);
			f  = o.getCalendar();
			if(f.getId().equals(form.getId())) {
				return i;
			}
		}
		return -1;
	}

	public void doToday() {
		currentDate = Calendar.getInstance().getTime();
		currentDateString = changeDateToString(currentDate);
		updatePanel();
	}

	public void setPopupMenu(UMenu menu) {
		// TODO Auto-generated method stub
		popupMenu = new UPopupMenu();
		int n = menu.getItemCount();
		while (menu.getItemCount() > 0) {
			popupMenu.add(menu.getItem(0));
		}
	}

	public UPopupMenu getUPopupMenu() {
		return popupMenu;
	}

	public void setPopupMenu(UPopupMenu popupMenu) {
		this.popupMenu = popupMenu;
	}

	public void displyPopMenu(Component com, int x, int y) {
		if (popupMenu != null && popupMenu.getSubElements().length >= 1)
			popupMenu.show(com, x, y);
	}

	public void addEvents(UEventAttribute events[]) {
		int i;
		for (i = 0; i < events.length; i++) {
			if (events[i].name.equals(EventConstants.EVENT_CALENDAR)) {
				sendCalendarEvent = true;
			}
		}
	}

	public void doSelectCurrentCalendar(CalendarGraphBase graphPanel, int x, int y){
		MonthViewDayUnit dateUnit = getSelectCurrentCalendarDate(graphPanel, x,y);
		Date date = dateUnit.day;
		int index = getSelectCurrentCalendarObjectIndex(graphPanel, x, y);
		if(index < 0 && dateUnit == null)
			return;
		if(sendCalendarEvent) {
			CalendarEvent e =new CalendarEvent(this,date,dateUnit.dataList,index);
			this.getUParent().getEventAdaptor().calendarSelection(e);
		}
	}

	protected class CalendarEventProcessor implements ActionListener,
			MouseListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			String cmd = arg0.getActionCommand();
			doAction(cmd);
		}

		@Override
		public void mouseClicked(MouseEvent mouseEvent) {
			// TODO Auto-generated method stub
			if (mouseEvent.getButton() == MouseEvent.BUTTON3) {
				if(enablePopupMenu)
					displyPopMenu((Component) mouseEvent.getSource(),
						mouseEvent.getX(), mouseEvent.getY());
			} else {
				// if(eventProcessor != null)
				// eventProcessor.mouseClicked(mouseEvent);
				Object source = mouseEvent.getSource();
				if (source instanceof CalendarGraphBase) {
					doSelectCurrentCalendar((CalendarGraphBase) source,
							mouseEvent.getX(), mouseEvent.getY());
				}
			}

		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}
	}

	@Override
	public void setEnablePopupMenu(boolean enable) {
		// TODO Auto-generated method stub
		enablePopupMenu = enable;
	
	}
	
}
