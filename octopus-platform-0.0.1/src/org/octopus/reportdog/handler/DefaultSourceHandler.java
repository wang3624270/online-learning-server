package org.octopus.reportdog.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.map.ListOrderedMap;
import org.octopus.reportdog.constants.ReportdogConstants;
import org.octopus.reportdog.form.DefaultDataForm;
import org.octopus.reportdog.module.SourceDataConfig;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
@Service
public class DefaultSourceHandler implements SourceHandlerI {

	private JdbcTemplate jdbcTemplate;

	public void process(RmiRequest request, RmiResponse response) {
		// TODO Auto-generated method stub
		DefaultDataForm form = (DefaultDataForm) request
				.get(ReportdogConstants.SOURCE_FORM_KEY);
		SourceDataConfig sourceDataConfig = (SourceDataConfig) request
				.get(ReportdogConstants.SOURCE_DATA_CONFIG_KEY);
		HashMap properties = sourceDataConfig.getProperties();
		Set propertiesSet = properties.keySet();
		Iterator propertiesIt = propertiesSet.iterator();
		String key, sql;
		List valueList, formList = new ArrayList();
		DefaultDataForm propertiesForm, midForm = new DefaultDataForm();
		ListOrderedMap listOrderedMap;
		Set orderedMapKeySet;
		Iterator orderedMapKeySetIt;
		Object mapValue, mapKey;
		List formPropertiesList;
		while (propertiesIt.hasNext()) {
			key = (String) propertiesIt.next();
			sql = (String) properties.get(key);
			valueList = jdbcTemplate.queryForList(sql);

			//
			if (valueList != null && valueList.size() > 0) {
				formPropertiesList = new ArrayList();
				int i;
				for (i = 0; i < valueList.size(); i++) {
					propertiesForm = new DefaultDataForm();
					listOrderedMap = (ListOrderedMap) valueList.get(i);
					orderedMapKeySet = listOrderedMap.keySet();
					orderedMapKeySetIt = orderedMapKeySet.iterator();
					while (orderedMapKeySetIt.hasNext()) {
						mapKey = orderedMapKeySetIt.next();
						mapValue = listOrderedMap.get(mapKey);
						propertiesForm.set(mapKey, mapValue);
					}
					formPropertiesList.add(propertiesForm);
				}
				midForm.set(key, formPropertiesList);
			}
		}
		form.set(sourceDataConfig.getName(), midForm);
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}
