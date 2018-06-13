package cn.edu.sdu.uims.component.complex.def;

import org.dom4j.Element;

import cn.edu.sdu.uims.def.UElementTemplate;

public class UCalendarTemplate extends UElementTemplate {

	public int unitWidth = 150;
	public int dayUnitWidth = 140;
	public int infoUnitHeight = 20;
	public int timeUnitHeight = 30;
	public int unitHeadHeight = 30;
	public int leftMenuWidth = 70;
	public int allDayUintHeight = 60;
	public int headFontSize = 10;
	public int dayFontSize = 10;
	public int dayTimeStart = 0;
	public int dayTimeEnd = 23;
	public int infoMinNum = 5;
	public int infoMaxNum = 100;

	public String infoTableName = "uimsScheduleInfoTable";
	public String timeNames[];

	public void getSelfAttribute(Element e1) {
		String str;
		unitWidth = geIntValueFromXml(e1, "unitWidth", unitWidth);
		dayUnitWidth = geIntValueFromXml(e1, "dayUnitWidth", dayUnitWidth);
		infoUnitHeight = geIntValueFromXml(e1, "eventUnitHeight",
				infoUnitHeight);
		timeUnitHeight = geIntValueFromXml(e1, "timeUnitHeight", timeUnitHeight);
		unitHeadHeight = geIntValueFromXml(e1, "unitHeadHeight", unitHeadHeight);
		leftMenuWidth = geIntValueFromXml(e1, "leftMenuWidth", leftMenuWidth);
		allDayUintHeight = geIntValueFromXml(e1, "allDayUintHeight",
				allDayUintHeight);
		headFontSize = geIntValueFromXml(e1, "headFontSize", headFontSize);
		dayFontSize = geIntValueFromXml(e1, "dayFontSize", dayFontSize);
		dayTimeStart = geIntValueFromXml(e1, "dayTimeStart", dayTimeStart);
		dayTimeEnd = geIntValueFromXml(e1, "dayTimeEnd", dayTimeEnd);
		infoMinNum = geIntValueFromXml(e1, "eventMinNum", infoMinNum);
		str = e1.attributeValue("infoTableName");
		if (str != null && str.length() != 0)
			infoTableName = str;
		makeTimeNames();
	}

	public void makeTimeNames() {
		timeNames = new String[dayTimeEnd - dayTimeStart + 1];
		for (int i = dayTimeStart; i <= dayTimeEnd; i++) {
			timeNames[i] = "" + i;
		}
	}
}
