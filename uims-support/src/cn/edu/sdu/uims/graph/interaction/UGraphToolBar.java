package cn.edu.sdu.uims.graph.interaction;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cn.edu.sdu.uims.component.UActionComponentI;
import cn.edu.sdu.uims.component.UToolBar;
import cn.edu.sdu.uims.component.event.EventConstants;
import cn.edu.sdu.uims.component.event.GraphInteractionEvent;
import cn.edu.sdu.uims.component.event.GraphInteractionListener;
import cn.edu.sdu.uims.component.event.UEventListener;
import cn.edu.sdu.uims.def.UButtonTemplate;
import cn.edu.sdu.uims.def.UEventAttribute;
import cn.edu.sdu.uims.trans.URect;

public class UGraphToolBar extends UToolBar {
	private ActionListener parentActionListent = null;
	private GraphInteractionListener graphInteractionListener = null;

	private int innerWidth = -1, innerHeight = -1, innerX = -1, innerY = -1;

	private class ToolActionProcessor implements ActionListener {
		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			UActionComponentI acom = (UActionComponentI) e.getSource();
			UButtonTemplate template = (UButtonTemplate) acom.getTemplate();
			if (template.userTaskName == null
					|| template.userTaskName.equals("")) {
				if (parentActionListent != null) {
					ActionEvent aEvent = new ActionEvent(acom, e.getID(),
							template.cmd);
					parentActionListent.actionPerformed(aEvent);
				}
			} else {
				if (graphInteractionListener != null) {
					GraphInteractionEvent re = new GraphInteractionEvent(acom,
							template.userTaskName, template.methodName);
					graphInteractionListener.commandSelected(re);
				}
			}
			// TODO Auto-generated method stub
		}
	}

	public UGraphToolBar() {
		actionListener = new ToolActionProcessor();
	}

	public void addEvents(UEventAttribute[] events) {
		UEventListener l = getUParent().getEventAdaptor();
		for (int i = 0; i < events.length; i++) {
			if (events[i].name.equals(EventConstants.EVENT_GRAPHINTERACTION)) {
				if (l instanceof GraphInteractionListener)
					graphInteractionListener = (GraphInteractionListener) l;
			} else if (events[i].name.equals(EventConstants.EVENT_ACTION)) {
				if (l instanceof ActionListener)
					parentActionListent = (ActionListener) l;
			}
		}
	}

	public void initGraphContent() {
		// TODO Auto-generated method stub
		init();
	}

	public String getDataFormMember() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setDataFormMember(String dataFormMember) {
		// TODO Auto-generated method stub

	}

	public void disableToolBarButtons() {
	}

	public void enableToolBarButtons(String[] acts) {
	}

	public Container getContainer() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setContainer(Container container) {
		// TODO Auto-generated method stub

	}

	public URect getBoundRect() {
		// TODO Auto-generated method stub
		return new URect(this.innerX, this.innerY, this.innerWidth,
				this.innerHeight);
	}

	public void setShellBounds(int x, int y, int w, int h) {
		// TODO Auto-generated method stub
		Dimension dimension = new Dimension(w, h);
		this.setMinimumSize(dimension);
		this.setMaximumSize(dimension);
		this.setPreferredSize(dimension);
		this.innerHeight = h;
		this.innerWidth = w;
		this.innerX = x;
		this.innerY = y;
	}

}
