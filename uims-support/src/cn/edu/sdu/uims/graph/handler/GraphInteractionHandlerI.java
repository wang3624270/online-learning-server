package cn.edu.sdu.uims.graph.handler;

import cn.edu.sdu.uims.component.event.GraphInteractionEvent;
import cn.edu.sdu.uims.itms.event.ItmsEvent;

public interface GraphInteractionHandlerI {
	void processCheCeckSatatusSelected(GraphInteractionEvent e);
	String getTransformState(ItmsEvent ie);
	boolean selectDrageElement(ItmsEvent ie);
}
