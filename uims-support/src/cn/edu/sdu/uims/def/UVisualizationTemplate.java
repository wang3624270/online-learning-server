package cn.edu.sdu.uims.def;

import java.util.ArrayList;
import java.util.Iterator;

import org.dom4j.Element;

public class UVisualizationTemplate extends UElementTemplate {
	private ArrayList<String> chartList = new ArrayList<String>();
	private String chartLayout = "locate";
	private String align = "default";

	@Override
	public void getSelfAttribute(Element e1) {
		// TODO Auto-generated method stub
		Iterator it = null;
		it = e1.elementIterator("visualchart");
		if (it.hasNext()) {
			Element c = (Element) it.next();
			String str = c.attributeValue("layout");
			chartLayout = (str == null ? "locate" : str);
			str = c.attributeValue("align");
			align = (str == null ? "default" : str);
			Iterator it1 = c.elementIterator("chartelement");
			Element e;
			while (it1.hasNext()) {
				e = (Element) it1.next();
				chartList.add(e.attributeValue("model"));
			}
			System.out.println(chartList.toString());
		}
	}

	public String getAlignType() {
		return this.align;
	}

	public void changeAlignType(String alignType) {
		this.align = alignType;
	}

	public String getChartLayout() {
		return this.chartLayout;
	}

	public void changeChartLayout(String layout) {
		this.chartLayout = layout;
	}

	public void addChart(String modelName) {
		this.chartList.add(modelName);
	}

	public boolean deleteChart(String modelName) {
		if (this.chartList.contains(modelName)) {
			this.chartList.remove(modelName);
			return true;
		} else
			return false;
	}

	public void clearAllChart() {
		this.chartList.clear();
	}

	public ArrayList<String> getChartList() {
		return this.chartList;
	}
}
