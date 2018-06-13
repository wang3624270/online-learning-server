package org.octopus.reportdog.factory.impl;

import java.io.ByteArrayInputStream;
import java.util.Date;

import org.octopus.reportdog.dao.DataPageModelConfigInfoDao;
import org.octopus.reportdog.factory.PageModelConfigFactory;
import org.octopus.reportdog.module.impl.DocStructure;
import org.octopus.reportdog.service.Reportdog;
import org.xml.sax.InputSource;

public class DefaultPageModelConfigFactory extends PageModelConfigFactory {

	DataPageModelConfigInfoDao dataModelConfigInfoDao;

	public DocStructure createModelModuleConfig(String name) {
		// TODO Auto-generated method stub
		return new DocStructure(name);
	}

	public InputSource getInputModel(String name) {
		// TODO Auto-generated method stub
		String modelConfig = this.getModelConfigInfo(name);
		InputSource inputSource = null;
		try {
			inputSource = new InputSource(new ByteArrayInputStream(modelConfig
					.getBytes("gb2312")));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inputSource;
	}

	public Date getModelConfigDateInfo(String name) {
		// TODO Auto-generated method stub
		dataModelConfigInfoDao = Reportdog.dataPageModelConfigInfoDao;

		return dataModelConfigInfoDao.getDataPageModelConfigDateInfo(name);
	}

	public String getModelConfigInfo(String name) {
		// TODO Auto-generated method stub
		dataModelConfigInfoDao = Reportdog.dataPageModelConfigInfoDao;

		return dataModelConfigInfoDao.getDataPageModelConfigInfo(name);
	}

}
