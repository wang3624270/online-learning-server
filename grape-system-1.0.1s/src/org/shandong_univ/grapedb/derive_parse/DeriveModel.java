package org.shandong_univ.grapedb.derive_parse;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.dom4j.Element;
import org.dom4j.tree.DefaultAttribute;

public class DeriveModel {

	public void parseModel(Element element) throws Exception {

		Element root = element;
		List list = root.elements();
		List propList = root.attributes();

		int i;
		String propStr;
		Method method;
		PropertyDescriptor pd;
		for (i = 0; i < propList.size(); i++) {

			propStr = ((DefaultAttribute) propList.get(i)).getName();
			propStr = propStr.substring(0, 1).toUpperCase()
					+ propStr.substring(1);
			try {

				pd = new PropertyDescriptor(
						((DefaultAttribute) propList.get(i)).getName(),
						this.getClass());
				method = pd.getWriteMethod();

				transData(method, this, pd.getPropertyType().toString(),
						((DefaultAttribute) propList.get(i)).getValue());
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		list = root.elements();
		Element e;
		// BusinessBaseModule nowNode = null;
		// BusinessBaseTransition nowTransition = null;
		boolean otherContent = false;
		for (i = 0; i < list.size(); i++) {

		}
		String name = "";

	}

	private void transData(Method method, Object object, String type,
			String content) {
		try {

			if (type.equals("float"))
				method.invoke(object, Float.parseFloat(content));
			else if (type.equals("int"))
				method.invoke(object, Integer.parseInt(content));
			else
				method.invoke(object, content);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}