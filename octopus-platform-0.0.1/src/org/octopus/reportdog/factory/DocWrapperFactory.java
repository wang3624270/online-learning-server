package org.octopus.reportdog.factory;

import java.io.ByteArrayInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.Hashtable;

import org.apache.commons.digester.Digester;
import org.apache.commons.digester.RuleSetBase;
import org.octopus.reportdog.dao.DataPanelModelConfigInfoDao;
import org.octopus.reportdog.module.PanelModelConfigRuleSet;
import org.octopus.reportdog.module.impl.DocWrapper;
import org.octopus.reportdog.service.Reportdog;
import org.octopus.reportdog.util.RequestUtils;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class DocWrapperFactory {

	protected static Class clazz = null;
	protected static String factoryClass = "org.octopus.reportdog.factory.DocWrapperFactory";
	// xml对象解析工具，基于栈的
	protected Digester configDigester = null;
	protected Date lastTimestamp = null;
	private static Hashtable<String, DocWrapper> modelConfigTable = new Hashtable<String, DocWrapper>();
	private static Hashtable<String, Date> timestampTable = new Hashtable<String, Date>();

	public static String getFactoryClass() {
		return (DocWrapperFactory.factoryClass);
	}

	public static void setFactoryClass(String factoryClass) {
		DocWrapperFactory.factoryClass = factoryClass;
		DocWrapperFactory.clazz = null;
	}

	public static DocWrapperFactory createFactory() {
		DocWrapperFactory factory = null;
		try {
			if (clazz == null) {
				clazz = RequestUtils.applicationClass(factoryClass);
			}
			factory = (DocWrapperFactory) clazz.newInstance();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return factory;
	}

	public DocWrapper initModelModule(String PanelName) throws Exception {
		if (!isLastVersion(PanelName)) {
			refresh(PanelName);
		}
		return modelConfigTable.get(PanelName);

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

	private void refresh(String PanelName) throws Exception {
		DocWrapperFactory factoryObject = DocWrapperFactory.createFactory();
		DocWrapper config = factoryObject.createModelModuleConfig(PanelName);
		Digester digester = initConfigDigester(new PanelModelConfigRuleSet());
		digester.push(config);
		this.parseModuleConfigFile(digester, PanelName);
		this.destroyConfigDigester();
		modelConfigTable.put(PanelName, config);
		timestampTable.put(PanelName, lastTimestamp);
	}

	DataPanelModelConfigInfoDao dataModelConfigInfoDao;

	public DocWrapper createModelModuleConfig(String name) {
		// TODO Auto-generated method stub
		return new DocWrapper(name);
	}

	public InputSource getInputModel(String name) {
		// TODO Auto-generated method stub
		String modelConfig = this.getModelConfigInfo(name);
		InputSource inputSource = new InputSource(new ByteArrayInputStream(
				modelConfig.getBytes()));
		return inputSource;
	}

	public Date getModelConfigDateInfo(String name) {
		// TODO Auto-generated method stub
		dataModelConfigInfoDao = Reportdog.dataPanelModelConfigInfoDao;

		return dataModelConfigInfoDao.getDataPanelModelConfigDateInfo(name);
	}

	public String getModelConfigInfo(String name) {
		// TODO Auto-generated method stub
		dataModelConfigInfoDao = Reportdog.dataPanelModelConfigInfoDao;

		return dataModelConfigInfoDao.getDataPanelModelConfigInfo(name);
	}

}
