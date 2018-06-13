package org.octopus.reportdog.processHandler.exportHandler;

import java.util.HashMap;

import org.octopus.reportdog.constants.ReportdogConstants;
import org.octopus.reportdog.factory.PageModelConfigFactory;
import org.octopus.reportdog.module.DocDescription;
import org.octopus.reportdog.module.PageConfig;
import org.octopus.reportdog.module.impl.DocStructure;
import org.octopus.reportdog.module.impl.DocWrapper;
import org.octopus.reportdog.processHandler.StepProcessHandler;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

public class DefaultLoadShowModelHandler extends StepProcessHandler {

	public void process(RmiRequest request, RmiResponse response)
			throws Exception {
		Integer nowPageNum = Integer.parseInt(request.get(
				ReportdogConstants.NOW_PAGE_NUM_KEY).toString());
		HashMap splitedPageConfigs = (HashMap) request
				.get(ReportdogConstants.SPLIT_PAGE_CONFIGS_KEY);

		DocWrapper docWrapper = (DocWrapper) request
				.get(ReportdogConstants.DOC_WRAPPER);

		docWrapper.setPageInstances(new HashMap());

		DocDescription modelModuleInstance;

		HashMap pageModelModuleConfigs = new HashMap();

		DocStructure modelModuleConfig;
		PageConfig aPageConfig;

		aPageConfig = (PageConfig) splitedPageConfigs.get("page" + nowPageNum);
		modelModuleInstance = new DocDescription();
		docWrapper.addPageInstance("page" + nowPageNum,
				modelModuleInstance);
		try {
			if (request.get(ReportdogConstants.REPORTDOG_PAGE) == null) {
				PageModelConfigFactory modelConfigFactory = PageModelConfigFactory
						.createFactory();
				modelModuleConfig = modelConfigFactory
						.initModelModule(aPageConfig.getName());
			} else
				modelModuleConfig = (DocStructure) request
						.get(ReportdogConstants.REPORTDOG_PAGE);
		} catch (Exception e) {
			throw new Exception("DefaultLoadShowModelHandler中显示模型加载失败");
		}
		pageModelModuleConfigs.put("page" + nowPageNum, modelModuleConfig);

		request.add(ReportdogConstants.PAGE_MODEL_MODULE_CONFIGS_KEY,
				pageModelModuleConfigs);

	}
}
