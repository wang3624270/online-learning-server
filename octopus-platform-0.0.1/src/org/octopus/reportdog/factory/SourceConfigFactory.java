package org.octopus.reportdog.factory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.Hashtable;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RuleSetBase;
import org.octopus.reportdog.module.SourceConfigRuleSet;
import org.octopus.reportdog.module.SourceModuleConfig;
import org.octopus.reportdog.util.RequestUtils;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public abstract class SourceConfigFactory {

	protected static Class clazz = null;
	protected static String factoryClass = "org.octopus.reportdog.factory.impl.DefaultSourceConfigFactory";
	// xml对象解析工具，基于栈的
	protected Digester configDigester = null;
	protected Date lastTimestamp = null;
	private static Hashtable<String, SourceModuleConfig> sourceConfigTable = new Hashtable<String, SourceModuleConfig>();
	private static Hashtable<String, Date> timestampTable = new Hashtable<String, Date>();

	public abstract String getSourceConfigInfo(String name);

	public abstract InputSource getInputSource(String name);

	public abstract Date getSourceConfigDateInfo(String name);

	public abstract SourceModuleConfig createSourceModuleConfig(String name);

	public static String getFactoryClass() {
		return (SourceConfigFactory.factoryClass);
	}

	public static void setFactoryClass(String factoryClass) {
		SourceConfigFactory.factoryClass = factoryClass;
		SourceConfigFactory.clazz = null;
	}

	public static SourceConfigFactory createFactory() {
		SourceConfigFactory factory = null;
		try {
			if (clazz == null) {
				clazz = RequestUtils.applicationClass(factoryClass);
			}
			factory = (SourceConfigFactory) clazz.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return factory;
	}

	public SourceModuleConfig initSourceModule(String pageName)
			throws Exception {
		if (!isLastVersion(pageName)) {
			refresh(pageName);
		}
		return sourceConfigTable.get(pageName);

	}

	protected void parseModuleConfigFile(Digester digester, String name)
			throws IOException {

		InputStream input = null;
		try {
			InputSource is = this.getInputSource(name);
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
		lastTimestamp = this.getSourceConfigDateInfo(name);
		Date currentTimestamp = timestampTable.get(name);
		if (currentTimestamp == null) {
			return false;
		} else {
			return lastTimestamp.equals(currentTimestamp);
		}
	}

	private void refresh(String pageName) throws Exception {
		SourceConfigFactory factoryObject = SourceConfigFactory.createFactory();
		SourceModuleConfig config = factoryObject
				.createSourceModuleConfig(pageName);
		Digester digester = initConfigDigester(new SourceConfigRuleSet());
		digester.push(config);
		this.parseModuleConfigFile(digester, pageName);
		this.destroyConfigDigester();
		sourceConfigTable.put(pageName, config);
		if (lastTimestamp == null)
			lastTimestamp = new Date();
		timestampTable.put(pageName, lastTimestamp);
	}

}
