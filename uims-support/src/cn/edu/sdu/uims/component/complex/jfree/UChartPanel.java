package cn.edu.sdu.uims.component.complex.jfree;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JScrollPane;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.component.complex.UComplexPanel;
import cn.edu.sdu.uims.def.UChartTemplate;

public class UChartPanel extends UComplexPanel {

	protected JFreeChart pieChart = null;
	protected ChartPanel chartPanel = null;
	protected UChartTemplate template;
	protected JScrollPane sp;
	
	public String getTitle(){
		if(template != null && template.title != null)
			return template.title.content;
		else
			return "标题";
	}
	@Override
	public void setTemplate(UTemplate template) {
		// TODO Auto-generated method stub
		this.template = (UChartTemplate)template;
	}
	@Override
	public void initContents() {
		chartPanel = new ChartPanel(pieChart);
		sp = new JScrollPane(chartPanel);
		setLayout(new BorderLayout());
		add(sp, BorderLayout.CENTER);
	}
	public JFreeChart getChartObjectByDataList(List dataList){
		return null;
	}
	
	public void setData(Object o) {
		if( o == null || !(o instanceof List)) 
			pieChart = null;	
		else
			pieChart = getChartObjectByDataList((List)o);
		chartPanel.setChart(pieChart);
//		chartPanel.updateUI();
		chartPanel.setPreferredSize(getCanvasSize());
		sp.updateUI();
	}
	public Dimension getCanvasSize(){
		return new Dimension(1,1);
	}
}
