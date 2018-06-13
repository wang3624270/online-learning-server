package cn.edu.sdu.uims.graph.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.List;

import javax.swing.JViewport;

import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.component.menu.UMenu;
import cn.edu.sdu.uims.component.menu.UPopupMenu;
import cn.edu.sdu.uims.def.UPanelTemplate;
import cn.edu.sdu.uims.frame.UClientFrame;
import cn.edu.sdu.uims.graph.interaction.UGraphInteractionPanel;
import cn.edu.sdu.uims.graph.model.GraphModelI;
import cn.edu.sdu.uims.graph.model.drag.DrageDataI;
import cn.edu.sdu.uims.hcims.UserTask;
import cn.edu.sdu.uims.itms.ItmsConstants;
import cn.edu.sdu.uims.trans.UFPoint;
import cn.edu.sdu.uims.trans.UPoint;

public class UViewport extends JViewport implements UCanvasI {
	private UGraphViewSuper graphView;
	private UserTask userTask = null;
	private UPopupMenu popupMenu = null;
	private ViewProcessorAdaptor eventProcessor = new ViewProcessorAdaptor();
	private boolean conditionMenu = false;
	private UFPoint mousePoint0, mousePoint1;
	public UViewport(UGraphViewSuper graphView) {
		this.graphView = graphView;
	}

	public void initContent() {
		this.addMouseListener(eventProcessor);
		this.addMouseMotionListener(eventProcessor);
		// this.addKeyListener(eventProcessor);
		UClientFrame frame = UClientFrame.getInstance();
		if (frame != null)
			frame.addViewKeyListener(eventProcessor);
	}

	public void paint(Graphics g) {
		Dimension size = getSize();
		g.clearRect(0, 0, size.width, size.height);
		draw(g);
	}

	public UFPoint viewToLogic(Point p) {
		GraphModelI g2d = graphView.getCurrentGraph();
		if (g2d == null)
			return null;
		ViewParameter pp = graphView.getViewParameter();
		if (pp == null)
			return null;
		return pp.m.viewToLogic(new UPoint(p.x,p.y));
	}

	public void draw(Graphics g) {
		GraphModelI g2d = graphView.getCurrentGraph();
		if (g2d == null)
			return;
		ViewParameter p = graphView.getViewParameter();
		ControlParameter c = graphView.getControlParameter();
		g2d.draw(g, new GraphModelViewParas(p, c, g2d.getGraphDataForm(), new UPoint(0,0)));
	}

	public void setPopupMenu(UMenu menu) {
		// TODO Auto-generated method stub
		conditionMenu = menu.isConditionMenu();
		popupMenu = new UPopupMenu();
		int i;
		int n = menu.getItemCount();
		while (menu.getItemCount() > 0) {
			popupMenu.add(menu.getItem(0));
		}
	}

	public UPopupMenu getUPopupMenu() {
		return popupMenu;
	}

	public void setPopupMenu(UPopupMenu popupMenu) {
		this.popupMenu = popupMenu;
	}

	public void displyPopMenu(Component com, int x, int y) {
		if (popupMenu != null && popupMenu.getSubElements().length >= 1)
			popupMenu.show(com, x, y);
	}

	public void setUserTask(UserTask userTask) {
		// TODO Auto-generated method stub
		this.userTask = userTask;
	}

	private class ViewProcessorAdaptor implements MouseListener,
			MouseMotionListener, KeyListener {

		public void mouseClicked(MouseEvent e) {
			UFPoint fp;
			if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
			fp = viewToLogic(e.getPoint());
			if (fp == null)
				return;
			if (userTask != null) {
				userTask.setCurrentPoint(fp);
				userTask
						.processEvent(ItmsConstants.EVENT_TYPE_lEFT_DOUBLE_CLICK, e);
				return;
			}
			}
		}

		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			UFPoint fp;
			if (e.getButton() == MouseEvent.BUTTON3) {
				fp = viewToLogic(e.getPoint());
				if (!conditionMenu) {
					if(graphView != null && graphView.getViewPopMenuTester() != null && !(graphView.getViewPopMenuTester().canDisplayPopMenu(fp)))  {
						return;
					}
					displyPopMenu((Component) e.getSource(), e.getX(), e.getY());
				} else {
					if (fp == null)
						return;
					List list = getSelectedElements(fp);
					if (list != null) {
						displyPopMenu((Component) e.getSource(), e.getX(), e
								.getY());
					}
				}
			} else {

					fp = viewToLogic(e.getPoint());
					if (fp == null)
						return;
					if (userTask != null) {
						userTask.setCurrentPoint(fp);
						userTask
								.processEvent(ItmsConstants.EVENT_TYPE_LEFT_DOWN, e);
						return;
					}
					if(graphView!= null ) {
						if(graphView.getCanSelectObject()) {
							graphView.processSelectObjectEvent(e, fp);
						}					
					}
				}
			}

		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
		}

		public void mouseDragged(MouseEvent e) {
			// TODO Auto-generated method stub
			if (userTask == null)
				return;
			UFPoint fp = viewToLogic(e.getPoint());
			if (fp == null)
				return;
			userTask.setCurrentPoint(fp);
			userTask.processEvent(ItmsConstants.EVENT_TYPE_MOUSE_DRAG, e);
		}

		public void mouseMoved(MouseEvent e) {
			// TODO Auto-generated method stub
			if (userTask == null)
				return;
			UFPoint fp = viewToLogic(e.getPoint());
			if (fp == null)
				return;
			userTask.setCurrentPoint(fp);
			userTask.processEvent(ItmsConstants.EVENT_TYPE_MOUSE_MOVE, e);
		}

		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub
			if (userTask == null)
				return;
			char c = e.getKeyChar();
			if (c == ' ')//空格键
				userTask.processEvent(ItmsConstants.EVENT_TYPE_SPACE_KEY_DOWN , e);
			//added by yxj 2010-4-17
			else if (c == '')//ESC键
				userTask.processEvent(ItmsConstants.EVENT_TYPE_ESC_KEY_DOWN , e);
			else if (c == '\n')//回车键
				userTask.processEvent(ItmsConstants.EVENT_TYPE_ENTER_KEY_DOWN , e);
			else if (c == '\b'){
				//回退键//Backspace:返回缺省任务键
				
				UPanelI panel=graphView.getUParent();
				UTemplate tp = panel.getTemplate();
				
				 if(tp instanceof UPanelTemplate) {
					 
					 UPanelTemplate ptp = (UPanelTemplate)tp;
					 if(panel instanceof UGraphInteractionPanel){
						 
						 UGraphInteractionPanel  interactonPanel=(UGraphInteractionPanel)panel;
						 interactonPanel.initUserTask(ptp.defaultUserTaskName, ptp.defaultMethodName);
					 }
					 
				 }
			}				
			 
		}
	}

	public void removeKeylisterFromFrame() {
		UClientFrame frame = UClientFrame.getInstance();
		if (frame != null)
			frame.removeViewKeyListener(eventProcessor);

	}

	public ViewParameter getViewParameter() {
		// TODO Auto-generated method stub
		if (graphView == null)
			return null;
		else
			return graphView.getViewParameter();
	}

	public List getSelectedElements(UFPoint fp) {
		GraphModelI g2d = graphView.getCurrentGraph();
		if (g2d == null)
			return null;
		return g2d.getSelectElementByPoint(fp);

	}

	public Dimension getGraphViewSize() {
		// TODO Auto-generated method stub
		return this.getSize();
	}
	public DrageDataI getDrageData(UFPoint fp){
		GraphModelI g2d = graphView.getCurrentGraph();
		if (g2d == null)
			return null;
		return g2d.getDrageData();
	}

	
}
