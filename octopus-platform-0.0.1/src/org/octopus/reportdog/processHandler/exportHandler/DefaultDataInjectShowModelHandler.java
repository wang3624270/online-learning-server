package org.octopus.reportdog.processHandler.exportHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.octopus.reportdog.constants.ReportdogConstants;
import org.octopus.reportdog.form.DefaultDataForm;
import org.octopus.reportdog.handler.TansHandlerI;
import org.octopus.reportdog.module.DocDescription;
import org.octopus.reportdog.module.impl.DocStructure;
import org.octopus.reportdog.module.impl.DocWrapper;
import org.octopus.reportdog.processHandler.StepProcessHandler;
import org.octopus.reportdog.util.RequestUtils;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

public class DefaultDataInjectShowModelHandler extends StepProcessHandler {
	public void process(RmiRequest request, RmiResponse response)
			throws Exception {
		Integer nowPageNum = Integer.parseInt(request.get(
				ReportdogConstants.NOW_PAGE_NUM_KEY).toString());
		HashMap pageModelModuleConfigs = (HashMap) request
				.get(ReportdogConstants.PAGE_MODEL_MODULE_CONFIGS_KEY);
		DocWrapper docWrapper = (DocWrapper) request
				.get(ReportdogConstants.DOC_WRAPPER);

		DefaultDataForm midPageDataForm = (DefaultDataForm) request
				.get(ReportdogConstants.MID_PAGE_DATA_FORM_KEY);

		DocDescription docDescription;

		DocStructure docStructure;

		DefaultDataForm aMidPageDataForm;
		TansHandlerI transHandler = (TansHandlerI) RequestUtils
				.applicationBean(ReportdogConstants.DEFAULTTRNSHANDLER_KEY);

		aMidPageDataForm = (DefaultDataForm) midPageDataForm.get("page"
				+ nowPageNum);

		docStructure = (DocStructure) pageModelModuleConfigs.get("page"
				+ nowPageNum);
		docDescription = docWrapper.getPageInstance("page" + nowPageNum);

		request.add(ReportdogConstants.DOC_STRUCTURE, docStructure);
		request.add(ReportdogConstants.SOURCE_DATA_MAPPING_FORM_KEY,
				aMidPageDataForm);
		request.add(ReportdogConstants.DOC_DESCRIPTION, docDescription);
		transHandler.transformData(request, response);
		if (request.get("dataStructureList") != null) {
			List list = (ArrayList) request.get("dataStructureList");
			((DocDescription) request.get(ReportdogConstants.DOC_DESCRIPTION))
					.getp_List();
			list.addAll(((DocDescription) request
					.get(ReportdogConstants.DOC_DESCRIPTION)).getp_List());
		}
	}
}