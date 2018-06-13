package cn.edu.sdu.uims.component.jfx.chart;

import cn.edu.sdu.uims.trans.URect;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Group;
import javafx.scene.chart.Chart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class JFXPieChart extends JFXChart{
	protected PieChart pieChart;
	double dataSum=0;
	Label caption = new Label("");
	Integer laberLineLength;
	
	public Integer getLaberLineLength() {
		return laberLineLength;
	}

	public void setLaberLineLength(Integer laberLineLength) {
		this.laberLineLength = laberLineLength;
	}

	public Chart  createChart(){	
		pieChart=new PieChart();
        addSeries(pieChart);
		return pieChart;
	}

	public void addSeries(PieChart pieChart) {
		// TODO Auto-generated method stub	
		if(pieDataList==null||pieDataList.size()==0){
			return;
		}

		for(PieChart.Data series:pieDataList){			
			pieChart.getData().add(series);
		}
		if(laberLineLength!=null&&laberLineLength>0){
			pieChart.setLabelLineLength(laberLineLength);
		}else{
			pieChart.setLabelLineLength(50);
		}
		
		pieChart.setLegendSide(Side.LEFT);
		
		addEvents();
	}

	private void addEvents() {
		// TODO Auto-generated method stub
		dataSum=0;
		for (final PieChart.Data data : pieChart.getData()) {
			dataSum=dataSum+data.getPieValue();
			
	            data.getNode().addEventHandler(MouseEvent.MOUSE_ENTERED,
	                new EventHandler<MouseEvent>() {
	                    @Override public void handle(MouseEvent e) {
	             	        caption.setTextFill(Color.BLACK);
	             	        caption.setStyle("-fx-font: 18 arial;");
	             	        
	                        caption.setTranslateX(e.getSceneX());
	                        caption.setTranslateY(e.getSceneY());
	                        if(dataSum==0)
	                        	caption.setText(data.getName()+":"+String.valueOf(data.getPieValue()) );
                        	caption.setText(data.getName()+":"+String.valueOf(data.getPieValue())+",百分比："+String.format("%.2f",(data.getPieValue()/dataSum)*100)+"%" );
                        	((Group) pieChart.getScene().getRoot()).getChildren().add(caption);
                        	
	                    }
	                });
	            
	            
	            data.getNode().addEventHandler(MouseEvent.MOUSE_EXITED,
				        new EventHandler<MouseEvent>() {
				            @Override
				            public void handle(MouseEvent e) {
				            	((Group) pieChart.getScene().getRoot()).getChildren().remove(caption);
				        		
				            }
				 });
				            

	            
	        }
	          
	}

	public void initContents() {
		// TODO Auto-generated method stub
		super.initContents();
		
	}

	public URect getBoundRect(){
		return super.getBoundRect();
	}


}
