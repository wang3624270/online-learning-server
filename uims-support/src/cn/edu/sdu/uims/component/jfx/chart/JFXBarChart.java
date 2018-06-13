package cn.edu.sdu.uims.component.jfx.chart;

import cn.edu.sdu.uims.trans.URect;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.Chart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class JFXBarChart extends JFXChart{
//	protected JFXBarChartTemplate barChartTemplate;
	protected BarChart barChart;
	
	public void addEvents() {
		// TODO Auto-generated method stub
		
		ObservableList<XYChart.Series> list = barChart.getData();
		for ( final Series series: list) {
			System.out.println("dd");
			Node node = series.getNode();
			ObservableList dataList = series.getData();
			XYChart ch = series.getChart();
			ObjectProperty s = series.dataProperty();
			XYChart.Data data = (Data) dataList.get(0);
			System.out.println("name:"+series.getName()+",node:"+node);
			if(dataList!=null&&dataList.size()>0){
				for(int i=0;i<dataList.size();i++){
					data=(Data) dataList.get(i);
					
					Object data_x = data.getXValue();
					Object data_y = data.getYValue();
					
					final Label caption = new Label("");
	        		
					data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED,
					        new EventHandler<MouseEvent>() {
					            @Override
					            public void handle(MouseEvent e) {
					        		caption.setTextFill(Color.BLACK);
					        	    caption.setStyle("-fx-font: 14 arial;");
					        	    	
//					        		String xStr = data_x.toString();
//									String yStr = data_y.toString();
									
					                caption.setTranslateX(e.getSceneX());
					                caption.setTranslateY(e.getSceneY());
//					                caption.setText(xStr+":" + yStr);
					                
					            	//((Group) barChart.getScene().getRoot()).getChildren().remove(caption);
					        		System.out.println("添加");
					        		
					        		((Group) barChart.getScene().getRoot()).getChildren().add(caption);
					        		
					             }
					   });
					data.getNode().addEventHandler(MouseEvent.MOUSE_EXITED,
					        new EventHandler<MouseEvent>() {
					            @Override
					            public void handle(MouseEvent e) {
					        		System.out.println("移除");
					            	((Group) barChart.getScene().getRoot()).getChildren().remove(caption);
					        		
					            }
					 });
					            

					

				}
			}

		}
		
	}
	
	
    public BarChart getBarChart(){   	
    	barChart=getBarChart(xAxis,yAxis);
    	barChart.setTitle(chartTitle);	
    	
    	return barChart;   	
    }   

	private BarChart getBarChart(Axis xAxis, Axis yAxis) {
		// TODO Auto-generated method stub
    	barChart=new BarChart<String,Number>(xAxis,yAxis);
    	
    	return barChart;
	}
	private BarChart getBarChart(NumberAxis xAxis, NumberAxis yAxis) {
		// TODO Auto-generated method stub
    	barChart=new BarChart<Number,Number>(xAxis,yAxis);
    	
		return barChart;
	}


	public Chart  createChart(){		
		barChart=getBarChart();
        addSeries(barChart);
        //设置数据柱间的距离
        barChart.setBarGap(5);//不同series数据,但series中的key值相同的数柱间距
        barChart.setCategoryGap(20);//series数据中key值不同的数柱之间的距离
        
		return barChart;
	}

	public void addSeries(BarChart<String, Number> barChart) {
		// TODO Auto-generated method stub	
		if(seriesList==null||seriesList.size()==0){
			return;
		}
		for(XYChart.Series<String,Number> series:seriesList){			
			barChart.getData().add(series);
		}	   
		addEvents();
	}

	
	public void initContents() {
		// TODO Auto-generated method stub
		super.initContents();
		
	}

	public URect getBoundRect(){
		return super.getBoundRect();
	}


	

}
