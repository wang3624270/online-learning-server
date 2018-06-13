package cn.edu.sdu.uims.component.jfx.chart;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.common.reportdog.UFont;
import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.base.UBorder;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.component.jfx.tem.JFXChartTemplate;
import cn.edu.sdu.uims.component.menu.UMenu;
import cn.edu.sdu.uims.component.menu.UPopupMenu;
import cn.edu.sdu.uims.def.UElementTemplate;
import cn.edu.sdu.uims.def.UEventAttribute;
import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.handler.UHandlerI;
import cn.edu.sdu.uims.trans.URect;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.Chart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

public class JFXChart extends JFXPanel implements UComponentI {
	protected HashMap parameters;
	protected String componentName;
	protected UPanelI parent = null;
	protected UElementTemplate elementTemplate;
	protected  Scene scene = null;
	protected Chart chart = null;
	protected JFXChartTemplate chartTemplate;
	protected Axis xAxis;
	protected Axis yAxis;
    protected Integer chartHeight;
    protected Integer chartWidth;
    
	//protected ChartForm chartForm;
    protected String chartCssUrl;   
	protected String chartTitle;
	protected String xAxisType;
	protected String yAxisType;
	protected String xAxisLabel;
	protected String yAxisLabel;
	protected ArrayList<XYChart.Series> seriesList;
	protected ArrayList<PieChart.Data> pieDataList;
	
	public Integer getChartHeight() {
		return chartHeight;
	}
	public void setChartHeight(Integer chartHeight) {
		this.chartHeight = chartHeight;
	}
	public Integer getChartWidth() {
		return chartWidth;
	}
	public void setChartWidth(Integer chartWidth) {
		this.chartWidth = chartWidth;
	}
	public String getChartCssUrl() {
		return chartCssUrl;
	}
	public void setChartCssUrl(String chartCssUrl) {
		this.chartCssUrl = chartCssUrl;
	}
	public JFXChartTemplate getChartTemplate() {
		return chartTemplate;
	}
	public void setChartTemplate(JFXChartTemplate chartTemplate) {
		this.chartTemplate = chartTemplate;
	}
	public Chart getChart(){
		return chart;
	}
	public Chart  createChart(){
		return null;
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
	public Axis getxAxis() {
		return xAxis;
	}
	public void setxAxis(Axis xAxis) {
		this.xAxis = xAxis;
	}
	public Axis getyAxis() {
		return yAxis;
	}
	public void setyAxis(Axis yAxis) {
		this.yAxis = yAxis;
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
	//将ChartForm对象中存有的chart信息加载到JFXChart组件的对应信息中
	public void initChartInfoOfChartForm(ChartForm form) {
		// TODO Auto-generated method stub
		if(form==null)
			return;
		if(form.getChartTitle()!=null)
			chartTitle=form.getChartTitle();
		if(form.getChartCssUrl()!=null)
			chartCssUrl=form.getChartCssUrl();
		if(form.getxAxisLabel()!=null)
			xAxisLabel=form.getxAxisLabel();
		if(form.getyAxisLabel()!=null)
			yAxisLabel=form.getyAxisLabel();
		if(form.getSeriesList()!=null)
			seriesList=form.getSeriesList();
		if(form.getPieDataList()!=null)
			pieDataList=form.getPieDataList();
	}
		
	
	public void initJFX(){
		Scene scene = new Scene(new Group(),javafx.scene.paint.Color.color(0.937255,0.937255,0.937255));

        getChartInfo();
        if(chartCssUrl!=null&&!"".equals(chartCssUrl)){
            scene.getStylesheets().add(chartCssUrl);
		}
        chart = createChart();
        chart.setTitle(chartTitle);
       // Scene scene1 = new Scene(chart,chartHeight,chartWidth);
        chart.setPrefSize(chartWidth, chartHeight);
        xAxis.setTickLabelGap(10);
        yAxis.setTickLabelGap(10);
        

        ((Group)scene.getRoot()).getChildren().add(chart);
        
        this.setScene(scene);		
	}
	//
	

	private void getChartInfo() {
		// TODO Auto-generated method stub
		JFXChartTemplate tp = (JFXChartTemplate)chartTemplate;
		chartHeight= tp.getH()-50;
		chartWidth = tp.getW()-100;

		if(chartTitle==null||"".equals(chartTitle)){
			chartTitle = tp.getChartTitle();
			if(chartTitle==null||"".equals(chartTitle)){
				chartTitle="数据柱状图";
			}
		}
		if(xAxisType==null||"".equals(xAxisType))
			xAxisType = tp.getxAxisType();
		if(yAxisType==null||"".equals(yAxisType))
			yAxisType = tp.getyAxisType();
		if(xAxisType!=null&&"Number".equals(xAxisType)){
			xAxis = new NumberAxis();			
		}else{
			xAxis = new CategoryAxis();
			xAxisType="String";
		}
		if(yAxisType!=null&&"String".equals(yAxisType)){
			yAxis = new CategoryAxis();
		}else{
			yAxis = new NumberAxis();
			yAxisType="Number";
		}
		
		if(xAxisLabel==null||xAxisLabel.equals(""))
			xAxisLabel = tp.getxAxisLabel();
		if(yAxisLabel==null||"".equals(yAxisLabel))
			yAxisLabel = tp.getyAxisLabel();
		
		if(yAxisLabel==null||"".equals(yAxisLabel)){
			yAxis.setLabel("Y");
		}else{
			yAxis.setLabel(yAxisLabel);
		}
		if(xAxisLabel==null||"".equals(xAxisLabel)){
			xAxis.setLabel("X");
		}else{
			xAxis.setLabel(xAxisLabel);       
		}
		if(chartCssUrl==null||"".equals(chartCssUrl)){
    		chartCssUrl=tp.getChartCssUrl();
		}
	
	}
	@Override
	public void initContents() {
		// TODO Auto-generated method stub
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initJFX();
            }
        });
	
	}

	@Override
	public void addEvents(UEventAttribute[] events) {
		// TODO Auto-generated method stub
	}

	@Override
	public Component getAWTComponent() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public String getActionComandString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URect getBoundRect() {
		// TODO Auto-generated method stub
		if (elementTemplate != null)
			return new URect(elementTemplate.x, elementTemplate.y,
					elementTemplate.w, elementTemplate.h);
		else
			return null;
	}

	@Override
	public String getComponentName() {
		// TODO Auto-generated method stub
		return componentName;
	}

	public Object getData() {
		return null;
	}

	public void setData(Object obj) {

	}
	public void setParameters(HashMap map) {
		parameters = map;
	}

	@Override
	public UElementTemplate getElementTemplate() {
		// TODO Auto-generated method stub
		//return this.elementTemplate;
		return chartTemplate;

	}

	@Override
	public FilterI getFilter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap getParameters() {
		// TODO Auto-generated method stub
		return parameters;
	}


	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UPanelI getUParent() {
		// TODO Auto-generated method stub
		return parent;
	}

	@Override
	public String getdisplayText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasEmptyFileds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isEditable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onClose() {
		// TODO Auto-generated method stub
		// control.closeDevice();
	}

	@Override
	public void repaintComponent() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean requestFirstFoucus() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void resetShellBounds(int x, int y, int w, int h) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setActionComandString(String str) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setAddedDatas(Object[] obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setArrangeType(int type) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setBorder(UBorder border) {
		// TODO Auto-generated method stub
		// this.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0),
		// 1));
		this.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 0));
	}

	@Override
	public void setBorder(int width, UColor color) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setColor(UColor c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setComponentName(String name) {
		// TODO Auto-generated method stub
		this.componentName = name;
	}

	@Override
	public void setEditable(boolean b) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setElementTemplate(UElementTemplate elementTemplate) {
		// TODO Auto-generated method stub
		//this.elementTemplate = elementTemplate;
		chartTemplate=(JFXChartTemplate) elementTemplate;
	}

	@Override
	public void setFilter(FilterI filter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFilter1(FilterI filter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFont(UFont agr0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setHandler(UHandlerI handler) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setHorizontalAlignment(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setInitComponentData(Object data) {
		// TODO Auto-generated method stub

	}


	@Override
	public void setPopupMenu(UMenu menu) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setShellBounds(int x, int y, int w, int h) {
		// TODO Auto-generated method stub

	}

	@Override
	public UTemplate getTemplate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTemplate(UTemplate template) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setText(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setUParent(UPanelI parent) {
		// TODO Auto-generated method stub
		this.parent = parent;
	}

	@Override
	public void setVerticalAlignment(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setdisplayText(String text) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAddedDatas() {
		// TODO Auto-generated method stub

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void processDispControlAfterInited() {
		// TODO Auto-generated method stub
	}


	@Override
	public int[] getSelectedIndices() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Object getSelectedValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UPopupMenu getUPopupMenu() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendDataToForm(UFormI form) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String[] getAddedInnerTextFiledValues() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clearAddedInnerTextFiled() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setScreenOwner(UComponentI screenOwner) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UComponentI getSubComponent(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUserData(Object obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEnablePopupMenu(boolean enable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLabel(String label) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getCurrentSelectObject() {
		// TODO Auto-generated method stub
		return null;
	}
	public void AddCommpntToContainer(Container container){
		container.add(this.getAWTComponent());
	}

	@Override
	public void setBackground(UColor c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getSelectedText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertText(String text) {
		// TODO Auto-generated method stub
		
	}




}
