package cn.edu.sdu.uims.graph.view;

import java.awt.print.PageFormat;
import java.awt.print.Printable;

import cn.edu.sdu.uims.graph.form.GraphPrintForm;

public interface GraphViewI {
	boolean invokeJob(GraphPrintForm gForm);
	void setGraphPrintForm(GraphPrintForm gForm);
	PageFormat getFormat();
	Printable getPrintable();
}
