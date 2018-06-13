package org.octopus.reportdog.document.element;

import java.util.HashMap;

import org.octopus.reportdog.document.ExportObjectOperator;

public interface ReportElement {

	public void renderElement(ExportObjectOperator ud);

	public void injectContent(Object ob, HashMap paraMap);
}