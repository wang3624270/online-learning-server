package cn.edu.sdu.uims.graph.view;

import cn.edu.sdu.uims.trans.UPoint;

public class GraphModelViewParas implements GraphViewParasI{
	private ViewParameter viewParameter;
	private ControlParameter controlParameter;
	private Object data;
	private UPoint shiftPoint;
	
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

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public UPoint getShiftPoint() {
		return shiftPoint;
	}

	public void setShiftPoint(UPoint shiftPoint) {
		this.shiftPoint = shiftPoint;
	}

	public GraphModelViewParas(ViewParameter viewParameter,	ControlParameter controlParameter, Object data,UPoint shiftPoint){
		this.viewParameter = viewParameter;
		this.controlParameter = controlParameter;
		this.data = data;
		this.shiftPoint = shiftPoint;
	}

}
