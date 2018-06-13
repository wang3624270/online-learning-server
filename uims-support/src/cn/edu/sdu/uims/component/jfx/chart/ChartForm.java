package cn.edu.sdu.uims.component.jfx.chart;

import java.util.ArrayList;

import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

public class ChartForm {
	public String chartCssUrl;    
	public String chartTitle;
	public String xAxisLabel;
	public String yAxisLabel;
	public ArrayList<XYChart.Series> seriesList;
	public ArrayList<PieChart.Data>pieDataList;
	
	public String getChartCssUrl() {
		return chartCssUrl;
	}
	public void setChartCssUrl(String chartCssUrl) {
		this.chartCssUrl = chartCssUrl;
	}
	public String getChartTitle() {
		return chartTitle;
	}
	public void setChartTitle(String chartTitle) {
		this.chartTitle = chartTitle;
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
	public ArrayList<XYChart.Series> getSeriesList() {
		return seriesList;
	}
	public void setSeriesList(ArrayList<XYChart.Series> seriesList) {
		this.seriesList = seriesList;
	}
	public ArrayList<PieChart.Data> getPieDataList() {
		return pieDataList;
	}
	public void setPieDataList(ArrayList<PieChart.Data> pieDataList) {
		this.pieDataList = pieDataList;
	}
	
	
}
