package org.octopus.reportdog.handler;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.map.ListOrderedMap;
import org.octopus.reportdog.constants.ReportdogConstants;
import org.octopus.reportdog.form.DefaultDataForm;
import org.octopus.reportdog.module.PageConfig;
import org.octopus.reportdog.module.SourceDataConfig;
import org.octopus.reportdog.util.RequestUtils;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedCaseInsensitiveMap;

@Service
public class DefaultPageSourceHandler implements SourceHandlerI {

	private JdbcTemplate jdbcTemplate;

	public void process(RmiRequest request, RmiResponse response) {
		// TODO Auto-generated method stub
		DefaultDataForm form = (DefaultDataForm) request
				.get(ReportdogConstants.SOURCE_FORM_KEY);
		SourceDataConfig sourceDataConfig = (SourceDataConfig) request
				.get(ReportdogConstants.SOURCE_DATA_CONFIG_KEY);
		PageConfig pageConfig = (PageConfig) request
				.get(ReportdogConstants.PANEL_PAGE_MODEL_CONFIG_KEY);
		HashMap properties = sourceDataConfig.getProperties();
		Set propertiesSet;
		Iterator propertiesIt;
		String key, sql;
		List valueList, formList = new ArrayList();
		DefaultDataForm propertiesForm;
		ListOrderedMap listOrderedMap, listValueOrderedMap;
		Set orderedMapKeySet, orderedValueMapKeySet;
		Iterator orderedMapKeySetIt, orderedValueMapKeySetIt;
		Object mapValue, mapKey;
		List formPropertiesList;

		//
		HashMap paraHashMap = pageConfig.getProperties();
		Set paraSet = paraHashMap.keySet();
		Iterator paraIt = paraSet.iterator();
		String pagequery = pageConfig.getProperty("sql");
		String start = pageConfig.getProperty("start");
		String currentPage = pageConfig.getProperty("currentPage");
		String countsPerPage = pageConfig.getProperty("countsPerPage");
		String paraStr;
		String paraStrKey;
		String belongDataConfig;
		// String sqls;
		int x, y;
		while (paraIt.hasNext()) {
			paraStrKey = paraIt.next().toString();
			if (paraStrKey.indexOf("sql") == 0) {
				if (filterPara(paraStrKey, "sql", sourceDataConfig.getName())) {
					pagequery = paraHashMap.get(paraStrKey).toString();
				}
			}
		}

		// if (belongPara != null)
		// belongParas = belongPara.split(",");

		int start_int = 0;
		if (start != null) {
			start_int = Integer.parseInt(start);
		}
		int current_page_int = 0;
		if (currentPage != null) {
			current_page_int = Integer.parseInt(currentPage);
		}
		int countsPerPage_int = -1;
		if (countsPerPage != null) {
			countsPerPage_int = Integer.parseInt(countsPerPage);
		}
		int start_pos = start_int + current_page_int * countsPerPage_int;
		List conditionList = (List) request
				.get(ReportdogConstants.EXPORT_LIMIT_CONDITION_KEY);
		if (conditionList == null) {
			if (countsPerPage_int != -1)
				pagequery += " LIMIT " + start_pos + "," + countsPerPage_int;
		}
		String condition = "";
		if (conditionList != null) {

			int i;
			HashMap aMap;
			boolean flag = false;
			if (pagequery.indexOf("where") < 0)
				condition += " where";
			else {
				condition += "";
				flag = true;
			}

			for (i = 0; i < conditionList.size(); i++) {
				aMap = (HashMap) conditionList.get(i);
				if (aMap.get("type") == null)
					aMap.put("type", "iden");
				if (aMap.get("dataConfigName") == null)
					aMap.put("dataConfigName", "allTable");
				if (aMap.get("type").toString().equals("iden")
						&& (aMap.get("dataConfigName").toString()
								.equals("allTable"))
						|| aMap.get("dataConfigName").toString().equals(
								sourceDataConfig.getName())) {
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
		List pagequeryList = jdbcTemplate.queryForList(pagequery + condition);
		int i;
		if (pagequeryList != null && pagequeryList.size() > 0) {
			formPropertiesList = new ArrayList();
			for (i = 0; i < pagequeryList.size(); i++) {
//				listOrderedMap = (ListOrderedMap) pagequeryList.get(i);
				listOrderedMap = new ListOrderedMap();
				LinkedCaseInsensitiveMap tempMap = (LinkedCaseInsensitiveMap) pagequeryList
						.get(i);
				listOrderedMap.putAll(tempMap);
				propertiesForm = new DefaultDataForm();
				orderedMapKeySet = listOrderedMap.keySet();
				orderedMapKeySetIt = orderedMapKeySet.iterator();

				if (conditionList != null) {
					int p;
					HashMap aMap2;
					for (p = 0; p < conditionList.size(); p++) {
						aMap2 = (HashMap) conditionList.get(p);
						if (aMap2.get("type") != null) {
							if (aMap2.get("type").toString().equals("para")) {
								propertiesForm.set(aMap2.get("colName")
										.toString(), aMap2.get("colValue")
										.toString());
							}
						}
					}
				}
				while (orderedMapKeySetIt.hasNext()) {
					mapKey = orderedMapKeySetIt.next();
					mapValue = listOrderedMap.get(mapKey);
					propertiesForm.set(mapKey, mapValue);
				}
				propertiesSet = properties.keySet();
				propertiesIt = propertiesSet.iterator();
				while (propertiesIt.hasNext()) {
					key = (String) propertiesIt.next();
					sql = (String) properties.get(key);
					sql = RequestUtils.keywordReplace(sql, propertiesForm);
					valueList = jdbcTemplate.queryForList(sql);
					if (valueList != null && valueList.size() > 0) {

						int j;
						for (j = 0; j < valueList.size(); j++) {
//							listValueOrderedMap = (ListOrderedMap) valueList
//									.get(j);
							listValueOrderedMap = new ListOrderedMap();
							LinkedCaseInsensitiveMap map = (LinkedCaseInsensitiveMap) valueList
									.get(j);
							listValueOrderedMap.putAll(map);
							
							orderedValueMapKeySet = listValueOrderedMap
									.keySet();
							orderedValueMapKeySetIt = orderedValueMapKeySet
									.iterator();
							while (orderedValueMapKeySetIt.hasNext()) {
								mapKey = orderedValueMapKeySetIt.next();
								mapValue = listValueOrderedMap.get(mapKey);
								propertiesForm.set(mapKey, mapValue);
							}
						}

					}
				}
				formPropertiesList.add(propertiesForm);
			}
			form.set(sourceDataConfig.getName(), formPropertiesList);
		}

	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	// ///////////////////////////////////
	private boolean filterPara(String paraStrKey, String paraName,
			String dataConfigName) {
		int x, y, z;
		String belongDataConfigs;
		String[] belongDataConfigArr;
		if (paraStrKey.indexOf(paraName) == 0) {
			x = paraStrKey.indexOf('{');
			y = paraStrKey.indexOf('}');
			if (x < 0)
				return true;
			else {
				belongDataConfigs = paraStrKey.substring(x + 1, y);
				belongDataConfigArr = belongDataConfigs.split(",");
				for (z = 0; z < belongDataConfigArr.length; z++) {
					if (belongDataConfigArr[z].equals(dataConfigName))
						return true;
				}
			}
		}
		return false;
	}
}
