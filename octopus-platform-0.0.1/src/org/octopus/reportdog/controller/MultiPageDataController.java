package org.octopus.reportdog.controller;

import java.util.HashMap;
import java.util.List;

import org.octopus.reportdog.constants.ReportdogConstants;
import org.octopus.reportdog.module.PageConfig;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;
import org.springframework.jdbc.core.JdbcTemplate;

public class MultiPageDataController extends DataController {

	private JdbcTemplate jdbcTemplate;

	public void process(RmiRequest request, RmiResponse response)
			throws Exception {
		// // TODO Auto-generated method stub
		// PageConfig pageConfig = (PageConfig) request
		// .get(ImportAndExportConstants.PANEL_PAGE_MODEL_CONFIG_KEY);
		// List conditionList = (List) request
		// .get(ImportAndExportConstants.EXPORT_LIMIT_CONDITION_KEY);
		// String countSql = (String) pageConfig.getProperty("countSql");
		// String start = pageConfig.getProperty("start");
		// String countsPerPage = pageConfig.getProperty("countsPerPage");
		//
		// int start_int = 0;
		// if (start != null) {
		// start_int = Integer.parseInt(start);
		// }
		// int count_per_page_int = 0;
		// if (countsPerPage != null) {
		// count_per_page_int = Integer.parseInt(countsPerPage);
		// }
		// String condition = "";
		// if (conditionList != null) {
		//
		// int i;
		// HashMap aMap;
		// boolean flag = false;
		// if (countSql.indexOf("where") < 0)
		// condition += " where";
		// else {
		// condition += "";
		// flag = true;
		// }
		//
		// for (i = 0; i < conditionList.size(); i++) {
		// aMap = (HashMap) conditionList.get(i);
		// if (aMap.get("type") == null)
		// aMap.put("type", "iden");
		// if (aMap.get("dataConfigName") == null)
		// aMap.put("dataConfigName", "allTable");
		// if (aMap.get("type").toString().equals("iden")
		// && (aMap.get("dataConfigName").toString()
		// .equals("allTable"))) {
		// if (flag == false)
		// condition += " " + aMap.get("colName") + "=" + "'"
		// + aMap.get("colValue") + "'";
		// else
		// condition += " and " + aMap.get("colName") + "=" + "'"
		// + aMap.get("colValue") + "'";
		// flag = true;
		// }
		//
		// }
		// }
		//
		// int totalCount = jdbcTemplate.queryForInt(countSql + condition);
		// DataController dataController;
		// // do {
		//
		// // ////////////////////////////////////////////////////
		// // if(pageConfig.getProperty(currentPage))
		// // pageConfig.addProperty("currentPage", current_page_int + "");
		// dataController = (DataController) RequestUtils
		// .applicationBean(ImportAndExportConstants.PAGEDATACONTROLLER_KEY);
		// dataController.process(request, response);
		//
		// // /////////////////////////////////////////////////////////
		// // } while ((current_page_int * count_per_page_int + start_int) <
		// // totalCount);

	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	// ////////////////////////////////////////////////////

	public HashMap splitMultiPageConfig(RmiRequest request,
			RmiResponse response, String key, int startConfigNum) {
		PageConfig pageConfig = (PageConfig) request
				.get(ReportdogConstants.PANEL_PAGE_MODEL_CONFIG_KEY);

		String countSql = (String) pageConfig.getProperty("countSql");
		String start = pageConfig.getProperty("start");
		String countsPerPage = pageConfig.getProperty("countsPerPage");
		List conditionList = (List) request
				.get(ReportdogConstants.EXPORT_LIMIT_CONDITION_KEY);
		int current_page_int = 0;
		int start_int = 0;
		String condition = "";
		if (conditionList != null) {

			int i;
			HashMap aMap;
			boolean flag = false;
			if (countSql != null)
				if (countSql.indexOf("where") < 0)
					condition += " where";
				else {
					condition += "";
					flag = true;
				}
			else
				condition += " where";
			for (i = 0; i < conditionList.size(); i++) {
				aMap = (HashMap) conditionList.get(i);
				if (aMap.get("type") == null)
					aMap.put("type", "iden");
				if (aMap.get("dataConfigName") == null)
					aMap.put("dataConfigName", "allTable");
				if (aMap.get("type").toString().equals("iden")
						&& (aMap.get("dataConfigName").toString()
								.equals("allTable"))) {
					if (flag == false)
						condition += " " + aMap.get("colName") + "=" + "'"
								+ aMap.get("colValue") + "'";
					else
						condition += " and " + aMap.get("colName") + "=" + "'"
								+ aMap.get("colValue") + "'";
					flag = true;
				}

			}
		}
		if (start != null) {
			start_int = Integer.parseInt(start);
		}
		int count_per_page_int = 1;
		if (countsPerPage != null) {
			count_per_page_int = Integer.parseInt(countsPerPage);
		}
		int totalCount = 0;
		if (request.get(ReportdogConstants.TOTAL_PAGE_COUNT) == null)
			if (request.get(ReportdogConstants.TOTAL_COUNT) != null) {
				totalCount = Integer.parseInt(request.get(
						ReportdogConstants.TOTAL_COUNT).toString());

			} else {
				try {
					totalCount = jdbcTemplate.queryForObject(countSql + condition, Integer.class);
				} catch (Exception e) {
					System.out.println("在多页显示过程中，未设定显示总数或获取显示总数的SQL语句错误");
				}

			}
		// DataController dataController;

		HashMap aMap = new HashMap();
		// aMap.put(key, pageConfig);
		PageConfig aConfig;
		if (request.get(ReportdogConstants.TOTAL_PAGE_COUNT) != null) {
			int i;
			int count = Integer.parseInt(request.get(
					ReportdogConstants.TOTAL_PAGE_COUNT).toString());
			for (i = 0; i < count; i++) {
				aConfig = pageConfig.clone();
				aConfig.addProperty("currentPage", current_page_int + "");
				aMap.put("page" + startConfigNum, aConfig);
				current_page_int++;
				startConfigNum++;
			}
		} else {
			do {
				// pageConfig.addProperty("currentPage", current_page_int + "");

				// pageConfig.addProperty("ConfigNum", Integer
				// .toString(startConfigNum));
				aConfig = pageConfig.clone();
				aConfig.addProperty("currentPage", current_page_int + "");
				aMap.put("page" + startConfigNum, aConfig);
				current_page_int++;
				startConfigNum++;

			} while ((current_page_int * count_per_page_int + start_int) < totalCount);
			request.add(ReportdogConstants.TOTAL_PAGE_COUNT, current_page_int);
		}
		return aMap;
	}
}
