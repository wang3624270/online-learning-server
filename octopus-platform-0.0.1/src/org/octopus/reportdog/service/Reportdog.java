package org.octopus.reportdog.service;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;

import org.octopus.reportdog.configParser.ReportParser;
import org.octopus.reportdog.dao.DataPageModelConfigInfoDao;
import org.octopus.reportdog.dao.DataPanelModelConfigInfoDao;
import org.octopus.reportdog.dao.DataSourceConfigInfoDao;
import org.octopus.reportdog.form.DefaultDataForm;
import org.octopus.reportdog.handler.DefaultDocumentHandler;
import org.octopus.reportdog.handler.DefaultRTFDocumentHandler;
import org.octopus.reportdog.handler.DefaultXLSDocumentHandler;
import org.octopus.reportdog.handler.StreamDocumentHandler;
import org.octopus.reportdog.processHandler.StepProcessHandler;
import org.octopus.reportdog.processHandler.exportHandler.DefaultDataInjectShowModelHandler;
import org.octopus.reportdog.processHandler.exportHandler.DefaultEndHandler;
import org.octopus.reportdog.processHandler.exportHandler.DefaultExportReportHandler;
import org.octopus.reportdog.processHandler.exportHandler.DefaultLoadDataHandler;
import org.octopus.reportdog.processHandler.exportHandler.DefaultLoadShowModelHandler;
import org.octopus.reportdog.processHandler.exportHandler.DefaultMidDataGenerateHandler;
import org.octopus.reportdog.processHandler.exportHandler.DefaultPreProcessHandler;
import org.springframework.orm.hibernate4.HibernateTemplate;

public class Reportdog {

	public static HashMap<String, HashMap<String, Object>> configMap = new HashMap();
	private HibernateTemplate hibernateTemplate;
	// ////////////////////////////
	public static StepProcessHandler defaultPreProcessHandler = new DefaultPreProcessHandler();
	public static StepProcessHandler defaultLoadDataHandler = new DefaultLoadDataHandler();
	public static StepProcessHandler defaultMidDataGenerateHandler = new DefaultMidDataGenerateHandler();
	public static StepProcessHandler defaultLoadShowModelHandler = new DefaultLoadShowModelHandler();
	public static StepProcessHandler defaultDataInjectShowModelHandler = new DefaultDataInjectShowModelHandler();
	public static StepProcessHandler defaultExportReportHandler = new DefaultExportReportHandler();
	public static StepProcessHandler defaultEndHandler = new DefaultEndHandler();
	// ////////////////////////////////////////
	public static DataPageModelConfigInfoDao dataPageModelConfigInfoDao = new DataPageModelConfigInfoDao();
	public static DataPanelModelConfigInfoDao dataPanelModelConfigInfoDao = new DataPanelModelConfigInfoDao();
	public static DataSourceConfigInfoDao DataSourceConfigInfoDao = new DataSourceConfigInfoDao();

	// /////////////////////////////////////////////////////////

	public static DefaultDocumentHandler defaultDocumentHandler = new DefaultDocumentHandler();
	public static DefaultXLSDocumentHandler defaultXLSDocumentHandler = new DefaultXLSDocumentHandler();
	public static DefaultRTFDocumentHandler defaultRTFDocumentHandler = new DefaultRTFDocumentHandler();
	public static StreamDocumentHandler streamDocumentHandler = new StreamDocumentHandler();

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
 
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void initReportdog() {
//		dataPageModelConfigInfoDao.setHibernateTemplate(hibernateTemplate);
//		dataPanelModelConfigInfoDao.setHibernateTemplate(hibernateTemplate);
//		DataSourceConfigInfoDao.setHibernateTemplate(hibernateTemplate);
	}

	public HashMap parseConfig(String panelName) {
		HashMap map;
		Date date;
		String str;
		if (configMap.get(panelName) == null) {
			str = dataPanelModelConfigInfoDao
					.getDataPanelModelConfigInfo(panelName);
			ReportParser a = new ReportParser();
			map = a.parseReport(str);
			map.put("date", dataPanelModelConfigInfoDao
					.getDataPanelModelConfigDateInfo(panelName));
			configMap.put(panelName, map);
		} else {
			map = configMap.get(panelName);
			date = dataPanelModelConfigInfoDao
					.getDataPanelModelConfigDateInfo(panelName);

			if (date.compareTo((Date) map.get("date")) > 0) {
				str = dataPanelModelConfigInfoDao
						.getDataPanelModelConfigInfo(panelName);
				ReportParser a = new ReportParser();
				map = a.parseReport(str);
				map.put("date", dataPanelModelConfigInfoDao
						.getDataPanelModelConfigDateInfo(panelName));
				configMap.put(panelName, map);
			}

		}
		return map;
	}

	public HashMap parseConfigFromFile(String path) {
		// InputStream stream = Thread.currentThread().getContextClassLoader()
		// .getResourceAsStream(path);
		InputStream stream = null;
		try {
			stream = this.getClass().getClassLoader().getResource(path)
					.openStream();
		} catch (Exception e) {
		}
		HashMap map;
		ReportParser a = new ReportParser();
		map = a.parseReport(stream);

		return map;
	}

	public Reportdog() {

	}

	public void testReportData(DefaultDataForm form, Integer currentPage) {
		System.out.println(currentPage);
		DefaultDataForm a = new DefaultDataForm();
		a.set("v1", "ccccccc");
		form.addTopDataFormOrList(a);
	}
}