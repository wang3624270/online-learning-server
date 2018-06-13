package cn.edu.sdu.uims.component.event;

import java.awt.event.ActionEvent;
import java.awt.event.AdjustmentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.lang.reflect.Method;
import java.util.EventObject;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TreeSelectionEvent;

import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.component.menu.UMenuItem;

public class UEventAdaptor implements  UEventListener{
	private  UPanelI panel = null;
	public  UEventAdaptor(){
		this(null);
	}
	public UEventAdaptor(UPanelI p){
		this.panel = p;
	}
	public void processEvent(EventObject e, String eventName, String cmd){
		if(eventName == null)
			return;
		if(panel == null || !panel.isFinishedInit()){
			return;
		}
		UComponentI com = (UComponentI)e.getSource();
		if(com  == null)
			return ;
		UPanelI parent = com.getUParent();
		if(parent == null)
			return ;
		if(com instanceof UMenuItem){
			parent.sendhandlerProcess("processPopupMenuEvent",(ActionEvent)e, null);
			return ;
		}
		Method method;
		String methodName = "process"+ eventName.substring(0,1).toUpperCase() + eventName.substring(1,eventName.length())+"Event";
		parent.sendhandlerProcess(methodName, e, cmd);
		if(com.getElementTemplate() != null)
			panel.closePopUpPanel(com.getElementTemplate().returnPanel);

	}
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		processEvent(e, EventConstants.EVENT_ACTION, null);
	}

	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		processEvent(e,EventConstants.EVENT_ITEM, null);
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		processEvent(e,EventConstants.EVENT_MOUSE, "clicked");
	}

	public void mouseEntered(MouseEvent e) {
//		processEvent(e,EventConstants.EVENT_MOUSEMOTION, "entered");
	}

	public void mouseExited(MouseEvent e) {
//		processEvent(e,EventConstants.EVENT_MOUSE, "exited");
	}

	public void mousePressed(MouseEvent e) {
//		processEvent(e,EventConstants.EVENT_MOUSE, "pressed");

	}

	public void mouseReleased(MouseEvent e) {
		processEvent(e,EventConstants.EVENT_MOUSE, "released");
	}

	public void mouseDragged(MouseEvent e) {
//		processEvent(e,EventConstants.EVENT_MOUSE, "dragged");
	}

	public void mouseMoved(MouseEvent e) {
//		processEvent(e,EventConstants.EVENT_MOUSE, "moved");
	}

	public void keyPressed(KeyEvent e) {
		processEvent(e,EventConstants.EVENT_KEY, "pressed");
	}

	public void keyReleased(KeyEvent e) {
//		processEvent(e,EventConstants.EVENT_KEY, "released");

	}

	public void keyTyped(KeyEvent e) {
		processEvent(e,EventConstants.EVENT_KEY, "typed");

	}

	public void adjustmentValueChanged(AdjustmentEvent e) {
		processEvent(e,EventConstants.EVENT_ADJUSTMENT, null);		
	}

	public void windowActivated(WindowEvent e) {
//		processEvent(e,EventConstants.EVENT_WINDOW,"activated");
		
	}

	public void windowClosed(WindowEvent e) {
//		processEvent(e,EventConstants.EVENT_WINDOW,"closed");
		
	}

	public void windowClosing(WindowEvent e) {
		processEvent(e,EventConstants.EVENT_WINDOW,"closing");
		
	}

	public void windowDeactivated(WindowEvent e) {
//		processEvent(e,EventConstants.EVENT_WINDOW,"deactivated");
		
	}

	public void windowDeiconified(WindowEvent e) {
//		processEvent(e,EventConstants.EVENT_WINDOW,"deiconified");
		
	}

	public void windowIconified(WindowEvent e) {
//		processEvent(e,EventConstants.EVENT_WINDOW,"iconified");
		
	}

	public void windowOpened(WindowEvent e) {
//		processEvent(e,EventConstants.EVENT_WINDOW,"opened");
		
	}
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		processEvent(e,EventConstants.EVENT_CHANGE,"change");	
	}
	
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		processEvent(e,EventConstants.EVENT_LISTSELECTION,"change");			
	}

	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub
		processEvent(e,EventConstants.EVENT_TABLEMODE,"change");			
		
	}
	public void valueChanged(TreeSelectionEvent e) {
		// TODO Auto-generated method stub
		processEvent(e,EventConstants.EVENT_TREESELECTION,"change");					
	}

	public void elementSelection(GraphEvent e) {
		// TODO Auto-generated method stub
		processEvent(e,EventConstants.EVENT_GRAPH,"change");					
		
	}
	public void calendarSelection(CalendarEvent e) {
		// TODO Auto-generated method stub
		processEvent(e,EventConstants.EVENT_CALENDAR,"change");					
		
	}
	public void componentSelection(GroupComponentEvent e) {
		// TODO Auto-generated method stub
		processEvent(e,EventConstants.EVENT_GROUPCOMPONENT,"change");					
		
	}

	
	public void selectObjectSelection(SelectObjectEvent e) {
		// TODO Auto-generated method stub
		processEvent(e,EventConstants.EVENT_SELECTOBJECT,"change");					
		
	}

	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub
		processEvent(e,EventConstants.EVENT_FOCUS,"gained");					
		
	}

	public void focusLost(FocusEvent e) {
		// TODO Auto-generated method stub
		processEvent(e,EventConstants.EVENT_FOCUS,"lost");					
	}
	@Override
	public void onTimer(UTimerEvent e) {
		// TODO Auto-generated method stub
		processEvent(e,EventConstants.EVENT_TIMER,"timer");					
		
	}

}
