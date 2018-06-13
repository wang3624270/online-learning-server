package cn.edu.sdu.uims.service;

import java.util.HashMap;

import cn.edu.sdu.uims.graph.form.GraphPrintForm;
import cn.edu.sdu.uims.graph.handler.GraphPrintHandler;
import cn.edu.sdu.uims.graph.model.GraphModelI;

public class UimsService {
	static private UimsService instance = null;
	static public  UimsService getInstance() {
		if(instance == null) {
			instance = new UimsService();
		}
		return instance;
	}
	public GraphPrintHandler  getPrintHandler(String printTemplateName) {
		return getPrintHandler(printTemplateName,null);
	}
	public GraphPrintHandler  getPrintHandler(String printTemplateName, String printFormName0) {
		return getPrintHandler(printTemplateName,printFormName0,null, null);
	}

	public GraphPrintHandler  getPrintHandler(String printTemplateName,String printFormName0,String handlerName0, HashMap map) {
		String handlerName, printFormName;
		GraphPrintHandler handler = null;
		GraphPrintForm printForm = null;
		handlerName = handlerName0;
		if(handlerName  == null) {
			handlerName = "cn.edu.sdu.uims.graph.handler.GraphPrintHandler";
		}
		printFormName = printFormName0;
		if(printFormName == null) {
			printFormName = "cn.edu.sdu.uims.graph.form.GraphPrintForm";
		}
		try {
			handler = (GraphPrintHandler)Class.forName(handlerName).newInstance();
			printForm = (GraphPrintForm)Class.forName(printFormName).newInstance();
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		handler.setForm(printForm);
		GraphModelI g2d = UFactory.getModelSession()
				.getGraphModel2DObject(printTemplateName);
		printForm.setCurrentGraphObject(g2d);
		printForm.makePrintData(map);
		g2d.makeGraphDataForm();
		g2d.setLayerDataForm(0, printForm);
		return handler;
	}
	public GraphPrintHandler  getPrintHandler(GraphPrintForm printForm) {
		String handlerName = "cn.edu.sdu.uims.graph.handler.GraphPrintHandler";
		GraphPrintHandler handler = null;
		try {
			handler = (GraphPrintHandler)Class.forName(handlerName).newInstance();
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		handler.setForm(printForm);
		return handler;
	}
}
