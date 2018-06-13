package org.octopus.reportdog.factory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.Hashtable;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RuleSetBase;
import org.octopus.reportdog.module.PageModelConfigRuleSet;
import org.octopus.reportdog.module.impl.DocStructure;
import org.octopus.reportdog.util.RequestUtils;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public abstract class PageModelConfigFactory {

	protected static Class clazz = null;
	protected static String factoryClass = "org.octopus.reportdog.factory.impl.DefaultPageModelConfigFactory";
	// xml对象解析工具，基于栈的
	protected Digester configDigester = null;
	protected Date lastTimestamp = null;
	private static Hashtable<String, DocStructure> modelConfigTable = new Hashtable<String, DocStructure>();
	private static Hashtable<String, Date> timestampTable = new Hashtable<String, Date>();
	
	public abstract String getModelConfigInfo(String name);
	public abstract InputSource getInputModel(String name);
	public abstract Date getModelConfigDateInfo(String name);
	public abstract DocStructure createModelModuleConfig(String name);
	public static String getFactoryClass() {
		return (PageModelConfigFactory.factoryClass);
	}

	public static void setFactoryClass(String factoryClass) {
		PageModelConfigFactory.factoryClass = factoryClass;
		PageModelConfigFactory.clazz = null;
	}

	public static PageModelConfigFactory createFactory() {
		PageModelConfigFactory factory = null;
		try {
			if (clazz == null) {
				clazz = RequestUtils.applicationClass(factoryClass);
			}
			factory = (PageModelConfigFactory) clazz.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return factory;
	}

	public DocStructure initModelModule(String pageName)
			throws Exception {
		if (!isLastVersion(pageName)) {
			refresh(pageName);
		}
		return modelConfigTable.get(pageName);

	}

	protected void parseModuleConfigFile(Digester digester, String name)
			throws IOException {

		InputStream input = null;
		try {
			InputSource is = this.getInputModel(name);
			digester.parse(is);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} finally {
			if (input != null) {
				input.close();
			}
		}
	}

	public Digester initConfigDigester(RuleSetBase ruleSetBase) {
		configDigester = new Digester();
		configDigester.setNamespaceAware(true);
		configDigester.setValidating(true);
		configDigester.setUseContextClassLoader(true);
		configDigester.addRuleSet(ruleSetBase);
		return configDigester;
	}

	public void destroyConfigDigester() {
		this.configDigester = null;

	}

	private boolean isLastVersion(String name) {
		lastTimestamp = this.getModelConfigDateInfo(name);
		Date currentTimestamp = timestampTable.get(name);
		if (currentTimestamp == null) {
			return false;
		} else {
			return lastTimestamp.equals(currentTimestamp);
		}
	}

	private void refresh(String pageName) throws Exception{
		PageModelConfigFactory factoryObject = PageModelConfigFactory
				.createFactory();
		DocStructure config = factoryObject
				.createModelModuleConfig(pageName);
		Digester digester = initConfigDigester(new PageModelConfigRuleSet());
		digester.push(config);
		this.parseModuleConfigFile(digester, pageName);
		this.destroyConfigDigester();
		modelConfigTable.put(pageName, config);
		timestampTable.put(pageName, lastTimestamp);
	}

}
