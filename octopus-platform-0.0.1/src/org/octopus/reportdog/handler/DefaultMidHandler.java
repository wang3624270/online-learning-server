package org.octopus.reportdog.handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.octopus.reportdog.constants.ReportdogConstants;
import org.octopus.reportdog.form.DefaultDataForm;
import org.octopus.reportdog.module.SourceMappingConfig;
import org.octopus.reportdog.state.MidDataMappingState;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;
import org.springframework.stereotype.Service;

@Service
public class DefaultMidHandler implements MidHandlerI {

	public void tranformMidData(RmiRequest request, RmiResponse response) {
		// TODO Auto-generated method stub
		DefaultDataForm form = (DefaultDataForm) request
				.get(ReportdogConstants.SOURCE_FORM_KEY);
		DefaultDataForm midForm = (DefaultDataForm) request
				.get(ReportdogConstants.SOURCE_DATA_MAPPING_FORM_KEY);

		SourceMappingConfig sourceMappingConfig = (SourceMappingConfig) request
				.get(ReportdogConstants.SOURCE_DATA_MAPPING_KEY);
		HashMap properties = sourceMappingConfig.getProperties();
		String key;
		MidDataMappingState mapping;
		Set propertiesSet = properties.keySet();
		Iterator propertiesIt = propertiesSet.iterator();
		Object mappingData;
		while (propertiesIt.hasNext()) {
			key = (String) propertiesIt.next();
			mapping = (MidDataMappingState) properties.get(key);
			mappingData = this.getMappingData(form, mapping.getValue());
			midForm.set(key, mappingData);
		}
		form = null;
	}

	public Object getMappingData(DefaultDataForm form, String mapping) {
		mapping = mapping.replace('.', ':');
		String[] mappings = mapping.split(":");
		if (mappings != null && mappings.length > 0) {
			int i;
			String str;
			Object midObject = form.get(mappings[0]);
			Method method;
			String methodName = "get";
			List tmpList;
			for (i = 1; i < mappings.length; i++) {
				str = mappings[i];
				if (midObject instanceof ArrayList) {
					tmpList = (List) midObject;
					if (tmpList != null && tmpList.size() > 0) {
						midObject = ((List) midObject).get(0);
					} else {
						return null;
					}
				}
				try {
					method = midObject.getClass().getDeclaredMethod(methodName,
							Object.class);
					midObject = method.invoke(midObject, str);
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return midObject;
		}
		return null;
	}

}
