package org.octopus.reportdog.controller;

import java.util.HashMap;

import org.octopus.reportdog.constants.ReportdogConstants;
import org.octopus.reportdog.module.PageConfig;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;
import org.springframework.stereotype.Service;

/**
 * 控制一个页面的导入和导出
 * 
 * 实现ControllerI接口
 * 
 * @author 刘洋
 * 
 */
@Service
public class PageDataController extends DataController {

	/**
	 * 页面导入导出的具体操作
	 */
	public void process(RmiRequest request, RmiResponse response)
			throws Exception {
		// // TODO Auto-generated method stub
		// // 查找数据来源对象
		// PageConfig pageConfig = (PageConfig) request
		// .get(ImportAndExportConstants.PANEL_PAGE_MODEL_CONFIG_KEY);
		// PanelModelModuleInstance panelModelModuleInstance =
		// (PanelModelModuleInstance) request
		// .get(ImportAndExportConstants.PANE_MODEL_MODULE_INSTANCE_KEY);
		//
		// String handlerName;
		//
		//	
		//
		// String pageName = pageConfig.getName();
		// SourceConfigFactory sourceConfigFactory = SourceConfigFactory
		// .createFactory();
		// SourceModuleConfig sourceModuleConfig = sourceConfigFactory
		// .initSourceModule(pageName);
		// // 加载数据来源
		// HashMap dataConfigs = sourceModuleConfig.getDataConfigs();
		// Set keySet;
		// Iterator keyIt;
		// SourceDataConfig sourceDataConfig;
		// String key;
		// // /HashMap properties;
		// SourceHandlerI handler;
		// DefaultDataForm form = new DefaultDataForm();
		// if (dataConfigs != null) {
		// keySet = dataConfigs.keySet();
		// keyIt = keySet.iterator();
		// while (keyIt.hasNext()) {
		// key = (String) keyIt.next();
		// sourceDataConfig = (SourceDataConfig) dataConfigs.get(key);
		// handlerName = sourceDataConfig.getHandler();
		// // properties=sourceDataConfig.getProperties();
		// // 添加数据
		// request.add(ImportAndExportConstants.SOURCE_DATA_CONFIG_KEY,
		// sourceDataConfig);
		// request.add(ImportAndExportConstants.SOURCE_FORM_KEY, form);
		// handler = (SourceHandlerI) RequestUtils
		// .applicationBean(handlerName);
		// handler.process(request, response);
		// }
		// }
		// // 生成中间数据?用户自己赋值：通过配置文件赋值
		// DefaultDataForm midForm = new DefaultDataForm();
		// HashMap dataMappingConfigs = sourceModuleConfig.getSourceConfigs();
		// SourceMappingConfig sourceMappingConfig = (SourceMappingConfig)
		// dataMappingConfigs
		// .get(ImportAndExportConstants.SOURCE_DATA_MAPPING_KEY);
		// handlerName = sourceMappingConfig.getHandler();
		// MidHandlerI midMappingHandler = (MidHandlerI) RequestUtils
		// .applicationBean(handlerName);
		// request.add(ImportAndExportConstants.SOURCE_DATA_MAPPING_FORM_KEY,
		// midForm);
		// request.add(ImportAndExportConstants.SOURCE_DATA_MAPPING_KEY,
		// sourceMappingConfig);
		// midMappingHandler.tranformMidData(request, response);
		//
		// // 模型加载
		// PageModelModuleInstance modelModuleInstance = new
		// PageModelModuleInstance();
		//
		// panelModelModuleInstance.addPageInstance(modelModuleInstance);
		//
		// PageModelConfigFactory modelConfigFactory = PageModelConfigFactory
		// .createFactory();
		// PageModelModuleConfig modelModuleConfig = modelConfigFactory
		// .initModelModule(pageName);
		// // 中间数据变换
		// TansHandlerI transHandler = (TansHandlerI) RequestUtils
		// .applicationBean(ImportAndExportConstants.DEFAULTTRNSHANDLER_KEY);
		// request.add(ImportAndExportConstants.PAGE_MODEL_MODULE_CONFIG_KEY,
		// modelModuleConfig);
		// request.add(ImportAndExportConstants.PAGE_MODEL_MODULE_INSTANCE_KEY,
		// modelModuleInstance);
		// transHandler.transformData(request, response);
		// // 数据操作
		// if (destinyConfigs != null) {
		// destinyKeySet = destinyConfigs.keySet();
		// destinyKeyIt = destinyKeySet.iterator();
		// while (destinyKeyIt.hasNext()) {
		// destinyKey = (String) destinyKeyIt.next();
		// destinyConfig = (DestinyConfig) destinyConfigs.get(destinyKey);
		// handlerName = destinyConfig.getHandler();
		// destinyHandler = (DestinyHandlerI) RequestUtils
		// .applicationBean(handlerName);
		// destinyHandler.process(request, response);
		// }
		// }
	}

	public HashMap splitMultiPageConfig(RmiRequest request,
			RmiResponse response, String key, int startConfigNum) {

		PageConfig pageConfig = (PageConfig) request
				.get(ReportdogConstants.PANEL_PAGE_MODEL_CONFIG_KEY);
		pageConfig.addProperty("ConfigNum", Integer.toString(startConfigNum));
		pageConfig.addProperty("currentPage", 0 + "");
		HashMap aMap = new HashMap();
		aMap.put("page" + startConfigNum, pageConfig);
		return aMap;

	}
}
