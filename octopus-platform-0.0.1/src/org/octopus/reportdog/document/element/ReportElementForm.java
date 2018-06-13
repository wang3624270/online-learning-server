package org.octopus.reportdog.document.element;

import java.util.ArrayList;
import java.util.List;

public class ReportElementForm {
	private List<ReportElement> elementList = new ArrayList<ReportElement>();

	public void setElementList(List<ReportElement> elementList) {
		this.elementList = elementList;
	}

	public List<ReportElement> getElementList() {
		return elementList;
	}

	public void addReportElement(ReportElement element) {
		elementList.add(element);
	}

}