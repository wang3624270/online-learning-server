package org.octopus.reportdog.processHandler.exportHandler;

import java.util.HashMap;

import org.octopus.reportdog.constants.ReportdogConstants;
import org.octopus.reportdog.factory.SourceConfigFactory;
import org.octopus.reportdog.form.DefaultDataForm;
import org.octopus.reportdog.handler.MidHandlerI;
import org.octopus.reportdog.module.PageConfig;
import org.octopus.reportdog.module.SourceMappingConfig;
import org.octopus.reportdog.module.SourceModuleConfig;
import org.octopus.reportdog.processHandler.StepProcessHandler;
import org.octopus.reportdog.util.RequestUtils;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;
 
public class DefaultMidDataGenerateHandler extends StepProcessHandler {

	/**
	 * 页面导入导出的具体操作
	 */
	public void process(RmiRequest request, RmiResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		// 生成中间数据?用户自己赋值：通过配置文件赋值
		Integer nowPageNum = Integer.parseInt(request.get(
				ReportdogConstants.NOW_PAGE_NUM_KEY).toString());
		HashMap splitedPageConfigs = (HashMap) request
				.get(ReportdogConstants.SPLIT_PAGE_CONFIGS_KEY);

		DefaultDataForm pageDataForm = (DefaultDataForm) request
				.get(ReportdogConstants.PAGE_DATA_FORM_KEY);

		PageConfig aPageConfig;
		DefaultDataForm midPageDataForm = new DefaultDataForm();
		String handlerName;
		Integer pageCount = splitedPageConfigs.size();

		aPageConfig = (PageConfig) splitedPageConfigs.get("page" + nowPageNum);

		String pageName = aPageConfig.getName();
		SourceModuleConfig sourceModuleConfig = null;
		if (request.get(ReportdogConstants.REPORTDOG_DATA) == null) {
			SourceConfigFactory sourceConfigFactory = SourceConfigFactory
					.createFactory();
			sourceModuleConfig = sourceConfigFactory.initSourceModule(pageName);
		} else
			sourceModuleConfig = (SourceModuleConfig) request
					.get(ReportdogConstants.REPORTDOG_DATA);
		// 加载数据来源

		DefaultDataForm form = (DefaultDataForm) pageDataForm.get("page"
				+ nowPageNum);
		DefaultDataForm midForm = new DefaultDataForm();
		HashMap dataMappingConfigs = sourceModuleConfig.getSourceConfigs();
		SourceMappingConfig sourceMappingConfig = (SourceMappingConfig) dataMappingConfigs
				.get(ReportdogConstants.SOURCE_DATA_MAPPING_KEY);
		handlerName = sourceMappingConfig.getHandler();
		MidHandlerI midMappingHandler = (MidHandlerI) RequestUtils
				.applicationBean(handlerName);
		request.add(ReportdogConstants.SOURCE_FORM_KEY, form);
		request.add(ReportdogConstants.SOURCE_DATA_MAPPING_FORM_KEY, midForm);
		request.add(ReportdogConstants.SOURCE_DATA_MAPPING_KEY,
				sourceMappingConfig);
		midMappingHandler.tranformMidData(request, response);

		midPageDataForm.set("page" + nowPageNum, midForm);

		request.add(ReportdogConstants.MID_PAGE_DATA_FORM_KEY, midPageDataForm);

	}
	// ////////////////////////////////////////////////

}