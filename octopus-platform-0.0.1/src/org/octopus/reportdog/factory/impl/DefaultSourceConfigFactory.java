package org.octopus.reportdog.factory.impl;

import java.io.ByteArrayInputStream;
import java.util.Date;

import org.octopus.reportdog.factory.SourceConfigFactory;
import org.octopus.reportdog.module.SourceModuleConfig;
import org.octopus.reportdog.module.impl.SourceModuleConfigImpl;
import org.octopus.reportdog.service.Reportdog;
import org.xml.sax.InputSource;

public class DefaultSourceConfigFactory extends SourceConfigFactory {

	public String getSourceConfigInfo(String name) {
		// TODO Auto-generated method stub

		return Reportdog.DataSourceConfigInfoDao.getDataSourceConfigInfo(name);
	}

	public InputSource getInputSource(String name) {
		String sourceConfig = this.getSourceConfigInfo(name);

		if (name != null) {
			InputSource inputSource;
			try {
				inputSource = new InputSource(new ByteArrayInputStream(
						sourceConfig.getBytes("gb2312")));
			} catch (Exception e) {
				e.printStackTrace();
				return new InputSource();
			}
			return inputSource;
		}
		return new InputSource();
	}

	public Date getSourceConfigDateInfo(String name) {
		// TODO Auto-generated method stub

		Date d = Reportdog.DataSourceConfigInfoDao
				.getDataSourceConfigDateInfo(name);

		if (d == null) {
			d=Reportdog.dataPanelModelConfigInfoDao
					.getDataPanelModelConfigDateInfo(name);
		}
		return d;
	}

	public SourceModuleConfig createSourceModuleConfig(String name) {
		// TODO Auto-generated method stub
		return new SourceModuleConfigImpl(name);
	}

}
