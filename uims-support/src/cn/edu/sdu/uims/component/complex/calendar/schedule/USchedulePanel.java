package cn.edu.sdu.uims.component.complex.calendar.schedule;

import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.util.List;
import java.util.UUID;

import javax.swing.JButton;
import javax.swing.JPanel;

import cn.edu.sdu.uims.component.complex.calendar.CalendarGraphBase;
import cn.edu.sdu.uims.component.complex.calendar.UCalendarPanel;
import cn.edu.sdu.uims.component.complex.def.UCalendarTemplate;
import cn.edu.sdu.uims.component.table.UTablePanel;
import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.def.UTableTemplate;
import cn.edu.sdu.uims.service.UFactory;

public class USchedulePanel extends UCalendarPanel {


	private JButton buttonMonth;
	private JButton buttonWeek;
	private JButton buttonDay;
	private JButton buttonEvent;
	private CardLayout cl;
	private UTablePanel infoTable;
																// week,
																// 2- day

	public JPanel getChangeChangeModeButtonsPanel(){
		JPanel panel;
		panel = new JPanel();
		panel.setLayout(new FlowLayout());
		buttonMonth = new JButton("月");
		buttonMonth.addActionListener(eventPorcessor);
		buttonMonth.setActionCommand("buttonMonth");
		panel.add(buttonMonth);

		buttonWeek = new JButton("周");
		buttonWeek.addActionListener(eventPorcessor);
		buttonWeek.setActionCommand("buttonWeek");
		panel.add(buttonWeek);

		buttonDay = new JButton("日");
		buttonDay.addActionListener(eventPorcessor);
		buttonDay.setActionCommand("buttonDay");
		panel.add(buttonDay);

		buttonEvent = new JButton("信息表");
		buttonEvent.addActionListener(eventPorcessor);
		buttonEvent.setActionCommand("buttonEvent");
		panel.add(buttonEvent);
		return panel;
	}



	public void initCalendarPanel() {
		UCalendarTemplate schudelTemplate = getCalendarTemplate();
		calendarPanel = new JPanel();
		cl = new CardLayout();
		calendarPanel.setLayout(cl);
		calendarGraph = new CalendarGraphBase[3];
		calendarGraph[0] = new ScheduleGraphMonth(this);
		calendarGraph[0].addMouseListener(eventPorcessor);
		calendarPanel.add(getJScrollPane(calendarGraph[0]),
				CURRENT_DISP_MODE_MONTH);
		calendarGraph[1] = new ScheduleGraphWeek(this);
		calendarGraph[1].addMouseListener(eventPorcessor);
		calendarPanel.add(getJScrollPane(calendarGraph[1]),
				CURRENT_DISP_MODE_WEEK);
		calendarGraph[2] = new ScheduleGraphOneDay(this);
		calendarGraph[2].addMouseListener(eventPorcessor);
		calendarPanel.add(getJScrollPane(calendarGraph[2]),
				CURRENT_DISP_MODE_DAY);
		infoTable = new UTablePanel();
		UTableTemplate tableTemplate = (UTableTemplate) UFactory
				.getModelSession().getTemplate(
						UConstants.MAPKEY_DATA_TABLE_FORM,
						schudelTemplate.infoTableName);
		infoTable.setTemplate(tableTemplate);
		infoTable.setUParent(this.getUParent());
		infoTable.init();
		calendarPanel.add(infoTable, CURRENT_DISP_MODE_EVENT);
	}

	public void setOtherData(List tList){
		this.dataStamp = UUID.randomUUID().toString();
		infoTable.setData(tList);		
	}
	public void doAction(String cmd){
		super.doAction(cmd);
	if (cmd.equals("buttonMonth")) {
		doMonth();
	} else if (cmd.equals("buttonWeek")) {
		doWeek();
	} else if (cmd.equals("buttonDay")) {
		doDay();
	} else if (cmd.equals("buttonEvent")) {
		doEvent();
	}
	}

	public void doMonth() {
		currentDispMode = CURRENT_DISP_MODE_MONTH;
		cl.show(calendarPanel, currentDispMode);
		updatePanel();
	}

	public void doWeek() {
		currentDispMode = CURRENT_DISP_MODE_WEEK;
		cl.show(calendarPanel, currentDispMode);
		updatePanel();

	}

	public void doDay() {
		currentDispMode = CURRENT_DISP_MODE_DAY;
		cl.show(calendarPanel, currentDispMode);
		updatePanel();
	}

	public void doEvent() {
		currentDispMode = CURRENT_DISP_MODE_EVENT;
		cl.show(calendarPanel, currentDispMode);
		updatePanel();
	}


	public String getDataStamp() {
		return dataStamp;
	}

	public void setDataStamp(String dataStamp) {
		this.dataStamp = dataStamp;
	}


}
