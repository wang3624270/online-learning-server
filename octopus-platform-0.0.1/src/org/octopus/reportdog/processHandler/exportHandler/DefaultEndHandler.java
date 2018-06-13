package org.octopus.reportdog.processHandler.exportHandler;

import org.octopus.reportdog.constants.ReportdogConstants;
import org.octopus.reportdog.handler.DocumentHandlerI;
import org.octopus.reportdog.module.impl.DocWrapper;
import org.octopus.reportdog.processHandler.StepProcessHandler;
import org.octopus.reportdog.util.RequestUtils;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

public class DefaultEndHandler extends StepProcessHandler {
	public void process(RmiRequest request, RmiResponse response)
			throws Exception {
		// 数据操作结束

		DocWrapper modelModuleConfig = (DocWrapper) request
				.get(ReportdogConstants.PANE_MODEL_MODULE_CONFIG_KEY);
		DocumentHandlerI documentHandler = (DocumentHandlerI) RequestUtils
				.applicationBean(modelModuleConfig.getDocumentHandlerName());
		documentHandler.processAfter(request, response);
	}
}