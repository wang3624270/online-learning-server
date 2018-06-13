package cn.edu.sdu.uims.graph.interaction;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.component.event.GraphInteractionEvent;
import cn.edu.sdu.uims.component.event.GraphInteractionListener;
import cn.edu.sdu.uims.component.event.UEventAdaptor;
import cn.edu.sdu.uims.def.UButtonTemplate;
import cn.edu.sdu.uims.graph.handler.GraphInteractionHandlerI;

public class GraphInteractionProcessor extends UEventAdaptor implements
		GraphInteractionListener {
	private UGraphInteractionPanel graphInteractionPanel = null;

	public GraphInteractionProcessor(UGraphInteractionPanel graphFrame) {
		this.graphInteractionPanel = graphFrame;
	}

	public void commandSelected(GraphInteractionEvent e) {
		// TODO Auto-generated method stub
		if (graphInteractionPanel != null){
			
			graphInteractionPanel.initUserTask(e.getUserTaskName(), e.getMethodName());
			
			
		}
			
	}

	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	public void mouseMoved(MouseEvent e) {
		/*
		 * // TODO Auto-generated method stub UserTask uTask=
		 * graphInteractionPanel.getUserTask(); if (uTask == null) return;
		 * UFPoint p = new UFPoint(e.getX(), e.getY());
		 * uTask.setCurrentPoint(p);
		 * 
		 * uTask.processEvent(ItmsConstants.EVENT_TYPE_MOUSE_MOVE, e);
		 */}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		// /////——------------------------yxj----------
		UComponentI acom = (UComponentI) e.getSource();
		UTemplate temp = (UTemplate) acom.getTemplate();
		if (temp != null && temp instanceof UButtonTemplate) {
			UButtonTemplate template = (UButtonTemplate) temp;
			if (template.userTaskName != null
					&& !template.userTaskName.equals("")) {

				GraphInteractionEvent re = new GraphInteractionEvent(e
						.getSource(), template.userTaskName,
						template.methodName);
				this.commandSelected(re);

			}
		}

		graphInteractionPanel.sendhandlerProcess("processActionEvent",e, e.getActionCommand());
	}

	public void itemStatusChanged(GraphInteractionEvent e) {
		// TODO Auto-generated method stub

	}

	public void checkSatatusSelected(GraphInteractionEvent e) {
		// TODO Auto-generated method stub
		GraphInteractionHandlerI fh;
		graphInteractionPanel.sendhandlerProcess("processCheCeckSatatusSelected", e, null);
	}
}
