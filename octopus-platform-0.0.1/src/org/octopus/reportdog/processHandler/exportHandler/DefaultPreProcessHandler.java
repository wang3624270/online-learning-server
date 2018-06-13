package org.octopus.reportdog.processHandler.exportHandler;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.octopus.reportdog.constants.ReportdogConstants;
import org.octopus.reportdog.controller.DataController;
import org.octopus.reportdog.factory.DocWrapperFactory;
import org.octopus.reportdog.factory.SourceConfigFactory;
import org.octopus.reportdog.form.DefaultDataForm;
import org.octopus.reportdog.handler.DocumentHandlerI;
import org.octopus.reportdog.handler.SourceHandlerI;
import org.octopus.reportdog.module.PageConfig;
import org.octopus.reportdog.module.SourceDataConfig;
import org.octopus.reportdog.module.SourceModuleConfig;
import org.octopus.reportdog.module.impl.DocWrapper;
import org.octopus.reportdog.processHandler.StepProcessHandler;
import org.octopus.reportdog.service.Reportdog;
import org.octopus.reportdog.util.RequestUtils;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

public class DefaultPreProcessHandler extends StepProcessHandler {
	public void process(RmiRequest request, RmiResponse response)
			throws Exception {

		String panelName = null;

		if (request.get("parseFinish") == null) {
			if (request.get(ReportdogConstants.DESTINYNAME_KEY) != null)
				panelName = (String) request
						.get(ReportdogConstants.DESTINYNAME_KEY);
			if (request.get(ReportdogConstants.PANELNAME_KEY) != null)
				panelName = (String) request
						.get(ReportdogConstants.PANELNAME_KEY);
		}

		// 查找数据操作列表

		// 操作数据

		String handlerName;

		// 数据操作初始化
		DocWrapper modelModuleConfig = null;
		if (request.get(ReportdogConstants.REPORTDOG_WRAPPER) == null) {
			DocWrapperFactory modelConfigFactory = DocWrapperFactory
					.createFactory();
			if (panelName != null)
				try {
					modelModuleConfig = modelConfigFactory
							.initModelModule(panelName);
				} catch (Exception e) {
					e.printStackTrace();
					throw new Exception("DefaultPreProcessHandler中找不到名为"
							+ panelName + "的模板");

				}
		} else

			modelModuleConfig = (DocWrapper) request
					.get(ReportdogConstants.REPORTDOG_WRAPPER);
		String documentHandlerName = modelModuleConfig.getDocumentHandlerName();
		DocumentHandlerI documentHandler = null;
		if (modelModuleConfig.getType() != null
				&& modelModuleConfig.getType().length() > 0) {
			if (modelModuleConfig.getType().equals("pdf"))
				documentHandler = Reportdog.defaultDocumentHandler;

			else if (modelModuleConfig.getType().equals("xls")
					|| modelModuleConfig.getType().equals("excel")) {
				documentHandler = Reportdog.defaultXLSDocumentHandler;
				modelModuleConfig
						.setDocumentHandlerName("defaultXLSDocumentHandler");
			} else if (modelModuleConfig.getType().equals("rtf")
					|| modelModuleConfig.getType().equals("doc")) {
				documentHandler = Reportdog.defaultRTFDocumentHandler;
				modelModuleConfig
						.setDocumentHandlerName("defaultRTFDocumentHandler");
			}

		} else
			documentHandler = (DocumentHandlerI) RequestUtils
					.applicationBean(documentHandlerName);
		if (documentHandler == null)
			throw new Exception("DefaultPreProcessHandler中documentHandlerName "
					+ documentHandlerName + "无法找到");

		request.add(ReportdogConstants.DOC_WRAPPER, modelModuleConfig);
		request.add(ReportdogConstants.PANE_MODEL_MODULE_CONFIG_KEY,
				modelModuleConfig);
		// //////////////////////////////////////////////////////////////

		HashMap pageConfigs = modelModuleConfig.getPageConfigs();

		Set keySet = pageConfigs.keySet();
		Iterator keyIt = keySet.iterator();
		String key;
		PageConfig pageConfig;
		DataController dataController;

		HashMap splitedPageConfigs = new HashMap();
		HashMap aMap;

		// /////////////////////////////

		while (keyIt.hasNext()) {
			key = (String) keyIt.next();
			pageConfig = (PageConfig) pageConfigs.get(key);
			dataController = (DataController) RequestUtils
					.applicationBean(pageConfig.getHandler());
			SourceModuleConfig sourceModuleConfig = null;
			try {
				if (request.get(ReportdogConstants.REPORTDOG_DATA) == null) {
					SourceConfigFactory sourceConfigFactory = SourceConfigFactory
							.createFactory();
					sourceModuleConfig = sourceConfigFactory
							.initSourceModule(pageConfig.getName());
				} else
					sourceModuleConfig = (SourceModuleConfig) request
							.get(ReportdogConstants.REPORTDOG_DATA);
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception(
						"DefaultPreProcessHandler类中数据模型加载失败,请检查数据模型配置");
			}
			HashMap sourceDataConfigs = sourceModuleConfig.getDataConfigs();
			Set keySet1;
			Iterator keyIt1;
			SourceDataConfig sourceDataConfig;
			String key1;

			Object handler = null;
			DefaultDataForm form = new DefaultDataForm();
			if (sourceDataConfigs != null) {
				form = new DefaultDataForm();
				keySet1 = sourceDataConfigs.keySet();
				keyIt1 = keySet1.iterator();
				while (keyIt1.hasNext()) {
					key1 = (String) keyIt1.next();
					sourceDataConfig = (SourceDataConfig) sourceDataConfigs
							.get(key1);
					if (sourceDataConfig.getIsGlobalDataConfig() == 0)
						continue;
					if (sourceDataConfig.getProvider().equals("")) {
						handlerName = sourceDataConfig.getHandler();
						request.add(
								ReportdogConstants.PANEL_PAGE_MODEL_CONFIG_KEY,
								pageConfig);
						request.add(ReportdogConstants.SOURCE_FORM_KEY, form);
						request.add(ReportdogConstants.SOURCE_DATA_CONFIG_KEY,
								sourceDataConfig);
						handler = (SourceHandlerI) RequestUtils
								.applicationBean(handlerName);
						if (handler == null)
							throw new Exception("数据块"
									+ sourceDataConfig.getName() + "的处理程序"
									+ handlerName + "未找到");
						try {
							if (sourceDataConfig.getMethod() != null) {
								Method method = null;
								try {
									method = handler.getClass().getMethod(
											sourceDataConfig.getMethod(),
											RmiRequest.class,
											RmiResponse.class);

								} catch (Exception ee) {
									throw new Exception("在数据块处理程序"
											+ handlerName + "中未找到处理方法"
											+ sourceDataConfig.getMethod());
								}
								method.invoke(handler, request, response);
							} else {
								Method method = null;
								try {
									method = handler.getClass().getMethod(
											"process", RmiRequest.class,
											RmiResponse.class);

								} catch (Exception ee) {
									throw new Exception("在数据块处理程序"
											+ handlerName + "中未找到处理方法"
											+ sourceDataConfig.getMethod());
								}
								method.invoke(handler, request, response);
							}
						} catch (Exception e) {
							e.printStackTrace();
							throw new Exception("数据块处理程序" + handlerName
									+ "抛出异常\n" + e.getMessage());
						}
					} else {

						handler = RequestUtils.applicationBean(sourceDataConfig
								.getProvider());
						if (handler == null)
							throw new Exception("数据块"
									+ sourceDataConfig.getName() + "的处理程序"
									+ sourceDataConfig.getProvider() + "未找到");

						if (sourceDataConfig.getMethod() != null) {
							Method method = null;

							try {
								method = handler.getClass().getMethod(
										sourceDataConfig.getMethod(),
										HashMap.class);

							} catch (Exception ee) {
								throw new Exception("在数据块处理程序"
										+ sourceDataConfig.getProvider()
										+ "中未找到处理方法"
										+ sourceDataConfig.getMethod());
							}
							method.invoke(handler,
									(HashMap) request.get("contextMap"));
							request.add(
									ReportdogConstants.TOTAL_PAGE_COUNT,
									((HashMap) request.get("contextMap"))
											.get(ReportdogConstants.TOTAL_PAGE_COUNT));
						} else {
							Method method = null;
							try {
								method = handler.getClass().getMethod(
										"process", HashMap.class);

							} catch (Exception ee) {
								throw new Exception("在数据块处理程序"
										+ sourceDataConfig.getProvider()
										+ "中未找到处理方法"
										+ sourceDataConfig.getMethod());
							}
							method.invoke(handler,
									(HashMap) request.get("contextMap"));
							request.add(
									ReportdogConstants.TOTAL_PAGE_COUNT,
									((HashMap) request.get("contextMap"))
											.get(ReportdogConstants.TOTAL_PAGE_COUNT));
						}

						// /////////////////////////////////////////////////////////////////
					}
				}
			}
		}
		keyIt = keySet.iterator();
		// //////////////////////////////
		while (keyIt.hasNext()) {
			key = (String) keyIt.next();
			pageConfig = (PageConfig) pageConfigs.get(key);
			dataController = (DataController) RequestUtils
					.applicationBean(pageConfig.getHandler());
			request.add(ReportdogConstants.PANEL_PAGE_MODEL_CONFIG_KEY,
					pageConfig);
			aMap = dataController.splitMultiPageConfig(request, response, key,
					splitedPageConfigs.size());
			if (aMap != null)
				splitedPageConfigs.putAll(aMap);

		}
		request.add(ReportdogConstants.NOW_PAGE_NUM_KEY, 0);
		request.add(ReportdogConstants.SPLIT_PAGE_CONFIGS_KEY,
				splitedPageConfigs);
		documentHandler.processBefore(request, response);
		if (request.get("contextMap") != null)
			((HashMap) request.get("contextMap")).put(
					ReportdogConstants.TMP_FILE_NAME_KEY,
					request.get(ReportdogConstants.TMP_FILE_NAME_KEY));
		// ////////////////////////////////////
	}
	// ////////////////////////////////////////////////
}