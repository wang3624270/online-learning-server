package cn.edu.sdu.uims.graph.form;

import java.util.HashMap;

import cn.edu.sdu.uims.graph.view.ControlParameter;
import cn.edu.sdu.uims.graph.view.ViewParameter;
import cn.edu.sdu.uims.graph.view.WVTrans;
import cn.edu.sdu.uims.trans.UMatrix;


public class GraphPrintForm extends GraphViewForm {
	private transient ControlParameter controlParameter;
	private transient ViewParameter viewParameter;
	public GraphPrintForm() {
		controlParameter = new ControlParameter(false);
	}
	public ViewParameter getViewParameter() {
		return viewParameter;
	}
	public void setViewParameter(ViewParameter viewParameter) {
		this.viewParameter = viewParameter;
	}
	public ControlParameter getControlParameter() {
		return controlParameter;
	}
	public void setControlParameter(ControlParameter controlParameter) {
		this.controlParameter = controlParameter;
	}
	public void makeViewParameter(){
		if(this.currentGraphObject == null) {
			viewParameter = new ViewParameter();
		}
		else {
			double graphWidth = this.currentGraphObject.getGraphWidth();
			double graphHeight = this.currentGraphObject.getGraphHeight();
//			float graphDpi = this.currentGraphObject.getGraphDpi();
			double imageWidth = this.currentGraphObject.getImageWidth();
			double imageHeight = this.currentGraphObject.getImageHeight();
//			float imageDpi = this.currentGraphObject.getImageDpi();
			WVTrans wv = new WVTrans();
			wv.setWindows(0,0, graphWidth,graphHeight);
			wv.setViewport(0,0,imageWidth,imageHeight);
			wv.makeWVMatrix();
			UMatrix mt = new UMatrix();
			viewParameter = new ViewParameter(wv.m,mt);
		}
	}
	public void makePrintData(HashMap map){
		
	}
	public String getOutputFileName(){
		return null;
	}
	public int getPrintPages(){
		if(currentGraphObject == null)
			return 0;
		else 
			return currentGraphObject.getLayerSize();
	}
}
