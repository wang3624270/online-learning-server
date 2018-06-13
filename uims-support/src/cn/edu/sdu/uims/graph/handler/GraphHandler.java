package cn.edu.sdu.uims.graph.handler;

import java.awt.event.KeyEvent;

import cn.edu.sdu.uims.graph.form.GraphViewForm;
import cn.edu.sdu.uims.graph.model.GraphModelI;
import cn.edu.sdu.uims.handler.impl.UToolHandler;

public class GraphHandler extends UToolHandler{

	public void processKeyevent(Object o, String cmd){
		boolean bCtrl = false, bShift = false, bAlt = false;
	KeyEvent keyEvent = (KeyEvent) o;
	bCtrl = keyEvent.isControlDown();
	bShift = keyEvent.isShiftDown();
	bAlt = keyEvent.isAltDown();
	int charCode = keyEvent.getKeyCode(); 
		if(bCtrl && charCode== 45 ) {
			scaleGraph(false);
		}else if(bCtrl && charCode == 61){
			scaleGraph(true);
		}
	}
	public void scaleGraph(boolean isEnlarge) {
		GraphViewForm form = (GraphViewForm)dataForm;
		GraphModelI g2d = form.getCurrentGraphObject();
		g2d.scaleGraph(isEnlarge);
		component.repaintComponent();
	}
}
