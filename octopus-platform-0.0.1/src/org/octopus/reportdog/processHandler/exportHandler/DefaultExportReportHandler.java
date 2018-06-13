package org.octopus.reportdog.processHandler.exportHandler;

import java.util.HashMap;
import java.util.Set;

import org.octopus.reportdog.constants.ReportdogConstants;
import org.octopus.reportdog.handler.DocumentHandlerI;
import org.octopus.reportdog.module.DocDescription;
import org.octopus.reportdog.module.impl.DocWrapper;
import org.octopus.reportdog.processHandler.StepProcessHandler;
import org.octopus.reportdog.util.RequestUtils;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

public class DefaultExportReportHandler extends StepProcessHandler {

	public void process(RmiRequest request, RmiResponse response)
			throws Exception {

		Integer nowPageNum = Integer.parseInt(request.get(
				ReportdogConstants.NOW_PAGE_NUM_KEY).toString());

		HashMap splitedPageConfigs = (HashMap) request
				.get(ReportdogConstants.SPLIT_PAGE_CONFIGS_KEY);

		DocWrapper docWrapper = (DocWrapper) request
				.get(ReportdogConstants.DOC_WRAPPER);

		String handlerName;
		DocDescription pageModelModuleInstance;
		Set moduleInstanceKeySet;

		int pageInstanceSize = docWrapper.getPageInstances().size();

		int i;

		pageModelModuleInstance = (DocDescription) docWrapper
				.getPageInstance("page" + nowPageNum);

		DocWrapper modelModuleConfig = (DocWrapper) request
				.get(ReportdogConstants.PANE_MODEL_MODULE_CONFIG_KEY);
		DocumentHandlerI documentHandler = (DocumentHandlerI) RequestUtils
				.applicationBean(modelModuleConfig.getDocumentHandlerName());
		if (request.get(ReportdogConstants.REPORTDOG_REGENERATE) != null)
			if (Boolean.parseBoolean(request.get(
					ReportdogConstants.REPORTDOG_REGENERATE).toString()) == true) {
				documentHandler.processAfter(request, response);
				documentHandler.processBefore(request, response);

				if (request.get("contextMap") != null)
					((HashMap) request.get("contextMap")).put(
							ReportdogConstants.TMP_FILE_NAME_KEY,
							request.get(ReportdogConstants.TMP_FILE_NAME_KEY));

			}
		documentHandler.process(request, response);

		// ///////////////////////////////////////////////////////////////

	}
}