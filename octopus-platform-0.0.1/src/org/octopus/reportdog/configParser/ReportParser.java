package org.octopus.reportdog.configParser;

import java.beans.PropertyDescriptor;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.tree.DefaultAttribute;
import org.octopus.reportdog.module.CellConfig;
import org.octopus.reportdog.module.DocBrick_P;
import org.octopus.reportdog.module.DocBrick_PageFooter;
import org.octopus.reportdog.module.DocBrick_Paper;
import org.octopus.reportdog.module.PageConfig;
import org.octopus.reportdog.module.RowConfig;
import org.octopus.reportdog.module.SourceDataConfig;
import org.octopus.reportdog.module.SourceMappingConfig;
import org.octopus.reportdog.module.data_provider.DataPoint;
import org.octopus.reportdog.module.impl.DocStructure;
import org.octopus.reportdog.module.impl.DocWrapper;
import org.octopus.reportdog.module.impl.SourceModuleConfigImpl;
import org.octopus.reportdog.state.MidDataMappingState;
import org.xml.sax.Attributes;

public class ReportParser {

	public HashMap<String, Object> parseReport(String description) {

		SAXReader sb = new SAXReader();
		Document doc = null;
		Element root = null;
		String propStr;
		Method method;
		// sb.setEncoding("gb2312");
		DocWrapper docWrapper = null;
		DocStructure pageModelModuleConfig = null;
		SourceModuleConfigImpl sourceModuleConfig = null;
		List list;
		try {
			InputStream inputStream = new ByteArrayInputStream(
					description.getBytes());

			doc = sb.read(inputStream);
			root = doc.getRootElement();
			list = root.elements();
			List propList;
			HashMap<String, Object> map = new HashMap<String, Object>();
			int i;
			for (i = 0; i < list.size(); i++) {
				if (((Element) list.get(i)).getName().equals("media")) {
					docWrapper = parseMedia(((Element) list.get(i)));
				} else if (((Element) list.get(i)).getName().equals("page")) {
					pageModelModuleConfig = parsePage((Element) list.get(i));
					docWrapper.getPageConfigs().putAll(
							pageModelModuleConfig.pageConfigs);
				} else if (((Element) list.get(i)).getName().equals("data")) {
					sourceModuleConfig = parseDataSource((Element) list.get(i),
							map);

				}

			}

			map.put("reportdog_wrapper", docWrapper);
			map.put("reportdog_page", pageModelModuleConfig);
			map.put("reportdog_data", sourceModuleConfig);
			return map;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public HashMap<String, Object> parseReport(InputStream description) {

		SAXReader sb = new SAXReader();
		Document doc = null;
		Element root = null;
		String propStr;
		Method method;
		// sb.setEncoding("gb2312");
		DocWrapper docWrapper = null;
		DocStructure docStructure = null;
		SourceModuleConfigImpl sourceModuleConfig = null;
		List list;
		try {
			doc = sb.read(description);
			root = doc.getRootElement();
			list = root.elements();
			List propList;
			HashMap<String, Object> map = new HashMap<String, Object>();
			int i;
			for (i = 0; i < list.size(); i++) {
				if (((Element) list.get(i)).getName().equals("media")) {
					docWrapper = parseMedia(((Element) list.get(i)));
				} else if (((Element) list.get(i)).getName().equals("page")) {
					docStructure = parsePage((Element) list.get(i));
					docWrapper.getPageConfigs()
							.putAll(docStructure.pageConfigs);
				} else if (((Element) list.get(i)).getName().equals("data")) {
					sourceModuleConfig = parseDataSource((Element) list.get(i),
							map);

				}

			}
			// docStructure.set
			docStructure.setPaperInfo(docWrapper.getPaperInfo());

			map.put("reportdog_wrapper", docWrapper);
			map.put("reportdog_page", docStructure);
			map.put("reportdog_data", sourceModuleConfig);

			return map;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public DocWrapper parseMedia(Element root) {
		DocWrapper docWrapper = new DocWrapper();
		PropertyDescriptor pd;
		List propList;
		String propStr;
		Method method;
		propList = root.attributes();
		propStr = root.getName();

		int i;
		for (i = 0; i < propList.size(); i++) {
			try {
				pd = new PropertyDescriptor(
						((DefaultAttribute) propList.get(i)).getName(),
						docWrapper.getClass());
				method = pd.getWriteMethod();

				transData(method, docWrapper, pd.getPropertyType().toString(),
						((DefaultAttribute) propList.get(i)).getValue());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		List<Element> eList = root.elements();
		for (i = 0; i < eList.size(); i++) {
			if (eList.get(i).getName().equals("paper")) {
				DocBrick_Paper bPaper = new DocBrick_Paper();
				bPaper.setCurrentElement(eList.get(i));
				bPaper.parse();
				docWrapper.setPaperInfo(bPaper);
			}
		}
		if (docWrapper.getPaperInfo() == null) {
			DocBrick_Paper p = new DocBrick_Paper();
			p.setType(docWrapper.getPaper());
			docWrapper.setPaperInfo(p);
		}
		return docWrapper;
	}

	public DocStructure parsePage(Element root) {
		DocStructure docStructure = new DocStructure();
		PropertyDescriptor pd;
		List propList, list;
		String propStr;
		Method method;
		propList = root.attributes();
		propStr = root.getName();
		int i;
		for (i = 0; i < propList.size(); i++) {
			try {
				pd = new PropertyDescriptor(
						((DefaultAttribute) propList.get(i)).getName(),
						docStructure.getClass());
				method = pd.getWriteMethod();
				// method.invoke(pageModelModuleConfig,
				// ((DefaultAttribute) propList.get(i)).getValue());
				transData(method, docStructure,
						pd.getPropertyType().toString(),
						((DefaultAttribute) propList.get(i)).getValue());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		PageConfig pageConfig = new PageConfig();

		pageConfig.setName(((DocStructure) docStructure).getName());
		pageConfig.setHandler(((DocStructure) docStructure).getHandler());
		if (((DocStructure) docStructure).getPageModel() != null
				&& ((DocStructure) docStructure).getPageModel().equals(
						"singlePage"))
			pageConfig.setHandler("pageDataController");
		else if (((DocStructure) docStructure).getPageModel() != null
				&& ((DocStructure) docStructure).getPageModel().equals(
						"multiplePage"))
			pageConfig.setHandler("multiPageDataController");
		docStructure.pageConfigs.put(pageConfig.getName(), pageConfig);
		list = root.elements();
		Attributes attributes;

		DocBrick_P paragraphConfig;
		for (i = 0; i < list.size(); i++) {
			if (((Element) list.get(i)).getName().equals("parameter")) {
				pageConfig.addProperty(((Element) list.get(i))
						.attribute("name").getValue(), ((Element) list.get(i))
						.attribute("value").getValue());
			} else if (((Element) list.get(i)).getName().equals("paragraph")) {
				docStructure
						.addBrick_P(parseParagraph(((Element) list.get(i))));
			} else if (((Element) list.get(i)).getName()
					.equals("edit-function")) {
			} else if (((Element) list.get(i)).getName().equals("page-footer")) {

				DocBrick_PageFooter pf = new DocBrick_PageFooter();
				pf.setCurrentElement((Element) list.get(i));
				pf.parse();
				docStructure.setPageFooter(pf);
			}
		}
		return docStructure;
	}

	public DocBrick_P parseParagraph(Element root) {
		DocBrick_P paragraphConfig = new DocBrick_P();
		PropertyDescriptor pd;
		List propList, list;
		String propStr;
		Method method;
		propList = root.attributes();
		propStr = root.getName();

		int i;
		for (i = 0; i < propList.size(); i++) {
			try {
				pd = new PropertyDescriptor(
						((DefaultAttribute) propList.get(i)).getName(),
						paragraphConfig.getClass());
				method = pd.getWriteMethod();
				transData(method, paragraphConfig, pd.getPropertyType()
						.getName(),
						((DefaultAttribute) propList.get(i)).getValue());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		list = root.elements();
		for (i = 0; i < list.size(); i++) {
			if (((Element) list.get(i)).getName().equals("tr")) {
				paragraphConfig.addRowConfig(parseRow((Element) list.get(i)));
			}

		}
		// //////////////
		return paragraphConfig;
	}

	private void transData(Method method, Object object, String type,
			String content) {
		try {

			if (type.equals("float"))
				method.invoke(object, Float.parseFloat(content));
			else if (type.equals("int") || type.equals("java.lang.Integer"))
				method.invoke(object, Integer.parseInt(content));
			else if (type.equals("boolean"))
				method.invoke(object, Boolean.parseBoolean(content));
			else
				method.invoke(object, content);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public RowConfig parseRow(Element root) {
		RowConfig rowConfig = new RowConfig();
		PropertyDescriptor pd;
		List propList, list;
		String propStr;
		Method method;
		propList = root.attributes();
		propStr = root.getName();

		int i;
		for (i = 0; i < propList.size(); i++) {
			try {
				pd = new PropertyDescriptor(
						((DefaultAttribute) propList.get(i)).getName(),
						rowConfig.getClass());
				method = pd.getWriteMethod();
				// method.invoke(rowConfig, ((DefaultAttribute) propList.get(i))
				// .getValue());
				transData(method, rowConfig, pd.getPropertyType().toString(),
						((DefaultAttribute) propList.get(i)).getValue());
			} catch (Exception e) {
			}
		}
		list = root.elements();
		for (i = 0; i < list.size(); i++) {
			if (((Element) list.get(i)).getName().equals("td")) {
				rowConfig.addCellConfig(parseCol((Element) list.get(i)));
			}

		}
		// //////////////
		return rowConfig;

	}

	public CellConfig parseCol(Element root) {
		CellConfig cellConfig = new CellConfig();
		PropertyDescriptor pd;
		List propList, list;
		String propStr;
		Method method;
		propList = root.attributes();
		propStr = root.getName();

		int i;
		for (i = 0; i < propList.size(); i++) {
			try {
				pd = new PropertyDescriptor(
						((DefaultAttribute) propList.get(i)).getName(),
						cellConfig.getClass());
				method = pd.getWriteMethod();
				// method.invoke(cellConfig, ((DefaultAttribute)
				// propList.get(i))
				// .getValue());
				transData(method, cellConfig, pd.getPropertyType().toString(),
						((DefaultAttribute) propList.get(i)).getValue());
			} catch (Exception e) {
			}
		}

		return cellConfig;

	}

	public SourceModuleConfigImpl parseDataSource(Element root, HashMap paraMap) {
		SourceModuleConfigImpl sourceModuleConfig = new SourceModuleConfigImpl();
		PropertyDescriptor pd;
		List propList, list;
		String propStr;
		Method method;
		propList = root.attributes();
		propStr = root.getName();

		int i;

		list = root.elements();
		SourceDataConfig sourceDataConfig = null;
		DataPoint dataPoint = null;
		SourceMappingConfig sourceMappingConfig = new SourceMappingConfig();
		MidDataMappingState state;
		HashMap<String, DataPoint> dataPointMap = new HashMap<String, DataPoint>();
		for (i = 0; i < list.size(); i++) {
			if (((Element) list.get(i)).getName().equals("block")) {
				sourceDataConfig = parseDataBlock((Element) list.get(i));
				sourceModuleConfig.addSourceDataConfig(sourceDataConfig);
				state = new MidDataMappingState();
				state.setProperty(sourceDataConfig.getId());
				state.setValue(sourceDataConfig.getId());
				sourceMappingConfig
						.addProperty(sourceDataConfig.getId(), state);
			}
			//
			else if (((Element) list.get(i)).getName().equals("point")) {
				dataPoint = parseDataPoint((Element) list.get(i));
				dataPointMap.put(dataPoint.getId(), dataPoint);
			}
		}
		paraMap.put("data-point-map", dataPointMap);
		sourceModuleConfig.addSourceDataConfig(sourceDataConfig);
		sourceModuleConfig.addSourceMappingConfig(sourceMappingConfig);
		return sourceModuleConfig;
	}

	public DataPoint parseDataPoint(Element root) {
		DataPoint dataPoint = new DataPoint();
		PropertyDescriptor pd;
		List propList, list;
		String propStr;
		Method method;
		propList = root.attributes();
		propStr = root.getName();

		int i;
		propList = root.attributes();

		list = root.elements();
		for (i = 0; i < propList.size(); i++) {
			try {
				pd = new PropertyDescriptor(
						((DefaultAttribute) propList.get(i)).getName(),
						dataPoint.getClass());
				method = pd.getWriteMethod();
				transData(method, dataPoint, pd.getPropertyType().toString(),
						((DefaultAttribute) propList.get(i)).getValue());
			} catch (Exception e) {
			}
		}
		return dataPoint;
	}

	public SourceDataConfig parseDataBlock(Element root) {
		SourceDataConfig sourceDataConfig = new SourceDataConfig();
		PropertyDescriptor pd;
		List propList, list;
		String propStr;
		Method method;
		propList = root.attributes();
		propStr = root.getName();

		int i;
		propList = root.attributes();

		list = root.elements();
		for (i = 0; i < propList.size(); i++) {
			try {
				pd = new PropertyDescriptor(
						((DefaultAttribute) propList.get(i)).getName(),
						sourceDataConfig.getClass());
				method = pd.getWriteMethod();
				transData(method, sourceDataConfig, pd.getPropertyType()
						.toString(),
						((DefaultAttribute) propList.get(i)).getValue());
			} catch (Exception e) {
			}
		}

		for (i = 0; i < list.size(); i++) {
			if (((Element) list.get(i)).getName().equals("parameter")) {
				sourceDataConfig.addProperty(
						((Element) list.get(i)).attribute("name").getValue(),
						((Element) list.get(i)).attribute("value").getValue());
			}

		}
		return sourceDataConfig;

	}

}