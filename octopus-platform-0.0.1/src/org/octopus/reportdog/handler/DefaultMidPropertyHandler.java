package org.octopus.reportdog.handler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import org.octopus.reportdog.constants.ReportdogConstants;
import org.octopus.reportdog.form.DefaultDataForm;
import org.sdu.rmi.RmiRequest;
import org.springframework.stereotype.Service;

@Service
public class DefaultMidPropertyHandler implements MidPropertyHandlerI {

	public Object[] tranformMidPropertyData(RmiRequest request) {
		// TODO Auto-generated method stub
		DefaultDataForm userTransForm = (DefaultDataForm) request
				.get(ReportdogConstants.SOURCE_SINGLE_FORM_KEY);
		// System.out.println(userTransForm.get("") + "\t" +
		// userTransForm.get("")
		// + "\t" + userTransForm.get(""));
		HashMap map = userTransForm.getItemMap();
		Set keySet = map.keySet();
		Iterator it = keySet.iterator();
		String key, value;
		while (it.hasNext()) {
			key = (String) it.next();
			value = (String) map.get(key);
			System.out.print(key + ":=" + value + "  ");
		}
		System.out.println();
		return new Integer[]{0};
	}

}
