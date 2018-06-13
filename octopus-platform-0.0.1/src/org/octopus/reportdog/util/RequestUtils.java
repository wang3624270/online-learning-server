package org.octopus.reportdog.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.octopus.reportdog.form.DefaultDataForm;
import org.octopus.reportdog.service.Reportdog;
import org.sdu.spring_util.ApplicationContextHandle;

public class RequestUtils {

	public static Class applicationClass(String className)
			throws ClassNotFoundException {

		// Look up the class loader to be used
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		if (classLoader == null) {
			classLoader = RequestUtils.class.getClassLoader();
		}

		// Attempt to load the specified class
		return (classLoader.loadClass(className));

	}

	public static Object applicationInstance(String className)
			throws ClassNotFoundException, IllegalAccessException,
			InstantiationException {

		return (applicationClass(className).newInstance());

	}

	public static Object applicationBean(String beanName) {
		if (beanName.equals("defaultDocumentHandler"))
			return Reportdog.defaultDocumentHandler;
		else if (beanName.equals("defaultXLSDocumentHandler"))
			return Reportdog.defaultXLSDocumentHandler;
		else if (beanName.equals("defaultRTFDocumentHandler"))
			return Reportdog.defaultRTFDocumentHandler;
		else
			return ApplicationContextHandle.getBean(beanName);
	}

	public static String keywordReplace(String str,
			DefaultDataForm propertiesForm) {
		if (str != null && !str.equals("")) {
			String strs[];
			str = str.replace('$', ';');
			strs = str.split(";");
			int i, p1, p2;
			String tmp, targetStr = "";
			Object value;
			DefaultDataForm tmpForm;
			List tmpList;
			for (i = 1; i < strs.length; i++) {
				p1 = strs[i].indexOf('{');
				p2 = strs[i].indexOf('}');
				if (p1 < 0)//字符串拆分用";"该开，可能和原字符串中的";"混淆。暂时不做处理
					continue;
				try {
					tmp = strs[i].substring(p1 + 1, p2);
				} catch (Exception e) {
					tmp = "";
				}
				value = propertiesForm.get(tmp);
				if (value instanceof ArrayList) {
					tmpList = (List) value;
					if (tmpList != null && tmpList.size() > 0) {
						tmpForm = (DefaultDataForm) tmpList.get(0);
						value = tmpForm.get(tmp);
					} else {
						value = "";
					}
				}
				strs[i] = value + strs[i].substring(p2 + 1);
			}
			for (i = 0; i < strs.length; i++) {
				targetStr += strs[i];
			}
			return targetStr;
		}
		return null;
	}

	public static byte[] getStreamInf(String str, DefaultDataForm propertiesForm) {
		if (str != null && !str.equals("")) {
			String strs[];
			str = str.replace('$', ';');
			strs = str.split(";");
			int i, p1, p2;
			String tmp, targetStr = "";
			byte[] value;
			DefaultDataForm tmpForm;
			List tmpList;
			for (i = 1; i < strs.length; i++) {
				p1 = strs[i].indexOf('{');
				p2 = strs[i].indexOf('}');
				tmp = strs[i].substring(p1 + 1, p2);
				value = (byte[]) propertiesForm.get(tmp);
				return value;
			}

		}
		return null;
	}

	public static String[] getKeyWordValue(String str, String value) {
		if (str != null && !str.equals("")) {
			String strs[];
			str = str.replace('$', ';');
			strs = str.split(";");
			int p1, p2;
			String tmp;
			if (strs.length > 1) {
				p1 = strs[1].indexOf('{');
				p2 = strs[1].indexOf('}');
				tmp = strs[1].substring(p1 + 1, p2);
				return new String[] { tmp, value };
			}
		}
		return null;
	}

	public static Object getMappingData(DefaultDataForm form, String mapping) {
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

	// /////////////////////////////////////////////////
	public static Object getGlobalVariable(HashMap variableMap,
			String variableKey) {
		int x = variableKey.indexOf('{');
		int y = variableKey.indexOf('}');

		String variableName = variableKey.substring(x + 1, y);
		return variableMap.get(variableName);
		// return null;
	}
}
