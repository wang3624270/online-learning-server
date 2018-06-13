package cn.edu.sdu.uims.component.event;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.event.ListSelectionEvent;

import cn.edu.sdu.uims.base.UComponentI;

public class UScrollPanelEventAdaptor extends UEventAdaptor {
	private UComponentI com = null; 
	private UEventListener parent = null;
	public  UScrollPanelEventAdaptor(UComponentI com, UEventListener parent){
		this.com = com;
		this.parent = parent;
	}
	public  void processEvent(EventObject e, String eventName, String cmd){
		EventObject re = null;
		if( e instanceof MouseEvent){
			MouseEvent me = (MouseEvent)e;	
			re = new MouseEvent((Component)com,me.getID(), me.getWhen(),me.getModifiers(), me.getX(),me.getY(),me.getClickCount(),me.isPopupTrigger(),me.getButton());
			parent.processEvent(re, eventName, cmd);
		}
		else if( e instanceof ListSelectionEvent){
			ListSelectionEvent le = (ListSelectionEvent)e;	
			re = new ListSelectionEvent((Component)com,le.getFirstIndex(), le.getLastIndex(),le.getValueIsAdjusting());
			parent.processEvent(re, eventName, cmd);
		}
	}
}
