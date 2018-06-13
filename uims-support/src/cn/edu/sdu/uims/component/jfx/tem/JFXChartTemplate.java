package cn.edu.sdu.uims.component.jfx.tem;

import org.dom4j.Element;

import cn.edu.sdu.uims.def.UElementTemplate;

public class JFXChartTemplate extends UElementTemplate {
	public boolean rootVisible = true;

	private String xAxisType;
	private String yAxisType;
	private String chartTitle;
	private String xAxisLabel;
	private String yAxisLabel;
	private String chartCssUrl;
	
	public void getSelfAttribute(Element e1){
		String str;
		name = e1.attributeValue("name");
		str = e1.attributeValue("rootVisible");
		if("false".equals(str))
			rootVisible = false;
		chartTitle = e1.attributeValue("chartTitle");
		yAxisType = e1.attributeValue("yAxisType");
		xAxisType = e1.attributeValue("xAxisType");
		xAxisLabel = e1.attributeValue("xAxisLabel");
		yAxisLabel = e1.attributeValue("yAxisLabel");
		chartCssUrl=e1.attributeValue("chartCssUrl");
		if(chartCssUrl==null||"".equals(chartCssUrl)){
			chartCssUrl="cn/edu/sdu/uims/component/jfx/chart/jfxChart.css";
		}
	}
	
	
	public String getChartTitle() {
		return chartTitle;
	}

	public void setChartTitle(String chartTitle) {
		this.chartTitle = chartTitle;
	}

	public String getxAxisType() {
		return xAxisType;
	}
	public void setxAxisType(String xAxisType) {
		this.xAxisType = xAxisType;
	}


	public String getyAxisType() {
		return yAxisType;
	}


	public void setyAxisType(String yAxisType) {
		this.yAxisType = yAxisType;
	}


	public String getxAxisLabel() {
		return xAxisLabel;
	}

	public void setxAxisLabel(String xAxisLabel) {
		this.xAxisLabel = xAxisLabel;
	}

	public String getyAxisLabel() {
		return yAxisLabel;
	}

	public void setyAxisLabel(String yAxisLabel) {
		this.yAxisLabel = yAxisLabel;
	}


	public String getChartCssUrl() {
		return chartCssUrl;
	}


	public void setChartCssUrl(String chartCssUrl) {
		this.chartCssUrl = chartCssUrl;
	}

}
