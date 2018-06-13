package cn.edu.sdu.uims.component.event;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.EventObject;

import javax.swing.event.TableModelEvent;

import cn.edu.sdu.uims.base.UComponentI;

public class TableEventAdaptor extends UEventAdaptor {
	private UComponentI com = null; 
	private UEventListener parent = null;
	public  TableEventAdaptor(UComponentI com, UEventListener parent){
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
		else if( e instanceof TableModelEvent){
			TableModelEvent te = (TableModelEvent)e;	
			re = new TableCellEvent((Component)com,te.getFirstRow(),te.getLastRow(),te.getColumn());
			parent.processEvent(re, eventName, cmd);
		}else if(e instanceof ActionEvent ){
			ActionEvent ae = (ActionEvent)e;
			re = new ActionEvent(com, ae.getID(),ae.getActionCommand());
			parent.processEvent(re,eventName, cmd);
		} else if( e instanceof KeyEvent){
			KeyEvent ke = (KeyEvent)e;
			ke = new KeyEvent((Component)com,ke.getID(),ke.getWhen(),ke.getModifiers(),ke.getKeyCode(),ke.getKeyChar());
			parent.processEvent(ke, eventName, cmd);
		}else if(e instanceof FocusEvent ){
			FocusEvent fe = (FocusEvent)e;
			fe.setSource(com);
			parent.processEvent(fe,eventName, cmd);
		} 
	}
}
