package cn.edu.sdu.uims.graph.view;

import cn.edu.sdu.uims.trans.UPoint;

public interface GraphViewParasI {
	ViewParameter getViewParameter();
	ControlParameter getControlParameter();
	Object getData();
	UPoint getShiftPoint();
}
