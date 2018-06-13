package org.octopus.reportdog.processHandler.exportHandler;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.octopus.reportdog.constants.ReportdogConstants;
import org.octopus.reportdog.factory.SourceConfigFactory;
import org.octopus.reportdog.form.DefaultDataForm;
import org.octopus.reportdog.module.PageConfig;
import org.octopus.reportdog.module.SourceDataConfig;
import org.octopus.reportdog.module.SourceModuleConfig;
import org.octopus.reportdog.processHandler.StepProcessHandler;
import org.octopus.reportdog.util.RequestUtils;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

public class DefaultLoadDataHandler extends StepProcessHandler {

	/**
	 * 页面导入导出的具体操作
	 */
	public void process(RmiRequest request, RmiResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		// 查找数据来源对象
		Integer nowPageNum = Integer.parseInt(request.get(
				ReportdogConstants.NOW_PAGE_NUM_KEY).toString());
		HashMap splitedPageConfigs = (HashMap) request
				.get(ReportdogConstants.SPLIT_PAGE_CONFIGS_KEY);

		// String pageName = pageConfig.getName();

		PageConfig aPageConfig;
		DefaultDataForm pageDataForm = new DefaultDataForm();

		// HashMap sourceModuleConfigs = new HashMap();
		SourceModuleConfig sourceModuleConfig = null;
		aPageConfig = (PageConfig) splitedPageConfigs.get("page" + nowPageNum);
		if (request.get(ReportdogConstants.REPORTDOG_DATA) == null)
			try {
				SourceConfigFactory sourceConfigFactory = SourceConfigFactory
						.createFactory();
				sourceModuleConfig = sourceConfigFactory
						.initSourceModule(aPageConfig.getName());
			} catch (Exception e) {
				throw new Exception(
						"DefaultLoadDataHandler类中数据模型加载失败,请检查数据模型配置");
			}
		else
			sourceModuleConfig = (SourceModuleConfig) request
					.get(ReportdogConstants.REPORTDOG_DATA);
		// sourceModuleConfigs.put(aPageConfig.getName(),
		// sourceModuleConfig);
		// 加载数据来源
		HashMap dataConfigs = sourceModuleConfig.getDataConfigs();
		Set keySet;
		Iterator keyIt;
		SourceDataConfig sourceDataConfig;
		String key;
		// /HashMap properties;
		// SourceHandlerI handler;
		DefaultDataForm form = new DefaultDataForm();

		if (dataConfigs != null) {
			form = new DefaultDataForm();
			keySet = dataConfigs.keySet();
			keyIt = keySet.iterator();
			while (keyIt.hasNext()) {
				key = (String) keyIt.next();
				sourceDataConfig = (SourceDataConfig) dataConfigs.get(key);
				if (sourceDataConfig.getIsGlobalDataConfig() == 1)
					continue;

				// properties=sourceDataConfig.getProperties();
				// 添加数据
				request.add(ReportdogConstants.PANEL_PAGE_MODEL_CONFIG_KEY,
						aPageConfig);
				request.add(ReportdogConstants.SOURCE_FORM_KEY, form);
				request.add(ReportdogConstants.SOURCE_DATA_CONFIG_KEY,
						sourceDataConfig);

				Object handler;
				if (sourceDataConfig.getProvider().equals("")) {
					handler = RequestUtils.applicationBean(sourceDataConfig
							.getHandler());

					if (sourceDataConfig.getMethod() != null) {
						Method method = null;
						try {
							method = handler.getClass().getMethod(
									sourceDataConfig.getMethod(),
									RmiRequest.class, RmiResponse.class);

						} catch (Exception ee) {
							throw new Exception("在数据块处理程序"
									+ sourceDataConfig.getHandler()
									+ "中未找在处理方法" + sourceDataConfig.getMethod());
						}
						method.invoke(handler, request, response);
					} else {
						Method method = null;
						try {
							method = handler.getClass().getMethod("process",
									RmiRequest.class, RmiResponse.class);

						} catch (Exception ee) {
							throw new Exception("在数据块处理程序"
									+ sourceDataConfig.getHandler()
									+ "中未找在处理方法" + sourceDataConfig.getMethod());
						}
						method.invoke(handler, request, response);
					}
					// handler.process(request, response);

				} else {
					form.setName(sourceDataConfig.getName());
					handler = RequestUtils.applicationBean(sourceDataConfig
							.getProvider());
					Integer currentPage = Integer.parseInt(aPageConfig
							.getProperty("currentPage").toString());
					if (sourceDataConfig.getMethod() != null) {
						Method method = null;
						try {
							method = handler.getClass().getMethod(
									sourceDataConfig.getMethod(),
									DefaultDataForm.class, Integer.class,
									HashMap.class);

						} catch (Exception ee) {
							throw new Exception("在数据块处理程序"
									+ sourceDataConfig.getProvider()
									+ "中未找在处理方法" + sourceDataConfig.getMethod());
						}
						try {
							method.invoke(handler, form, currentPage,
									(HashMap) request.get("contextMap"));
						} catch (Exception e) {
							e.printStackTrace();
							System.out.println("数据块处理程序"
									+ sourceDataConfig.getProvider() + "中的处理方法"
									+ sourceDataConfig.getMethod() + "拋出异常");
						}
					} else {
						Method method = null;
						try {
							method = handler.getClass().getMethod("process",
									DefaultDataForm.class, Integer.class,
									HashMap.class);

						} catch (Exception ee) {
							throw new Exception("在数据块处理程序"
									+ sourceDataConfig.getProvider()
									+ "中未找在处理方法" + sourceDataConfig.getMethod());
						}
						try {
							method.invoke(handler, form, currentPage,
									(HashMap) request.get("contextMap"));
						} catch (Exception e) {
							e.printStackTrace();
							System.out.println("数据块处理程序"
									+ sourceDataConfig.getProvider() + "中的处理方法"
									+ sourceDataConfig.getMethod() + "拋出异常");
						}
					}
					// handler.process(request, response);
				}

			}
			pageDataForm.set("page" + nowPageNum, form);

		}

		request.add(ReportdogConstants.PAGE_DATA_FORM_KEY, pageDataForm);

	}
	// ////////////////////////////////////////////////

}