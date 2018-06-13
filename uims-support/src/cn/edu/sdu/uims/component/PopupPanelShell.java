package cn.edu.sdu.uims.component;

import java.awt.Dialog;
import java.awt.Point;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.awt.event.WindowListener;
import java.util.HashMap;

import javax.swing.JDialog;
import javax.swing.Popup;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.uims.component.panel.UPopupPanel;
import cn.edu.sdu.uims.handler.impl.UFormHandler;

public class PopupPanelShell extends Popup implements WindowFocusListener, WindowListener{
	private JDialog popupWindow;
	UPopupPanel popupPanel;

	public UFormHandler getInnerHandler(){
		return (UFormHandler)popupPanel.getHandler();

	}
	public void setParameters(HashMap map){
		popupPanel.setParameters(map);
	
	}

	public UPopupPanel getPopupPanel() {
		return popupPanel;
	}

	public void setPopupPanel(UPopupPanel popupPanel) {
		this.popupPanel = popupPanel;
	}
/*
	public PopupPanelShell(UPopupPanel panel) {
		super();
		this.popupPanel = panel;
		popupWindow = new JFrame();
		popupWindow.setAlwaysOnTop(true);
//		popupWindow.setType(Type.UTILITY);
		popupWindow.setUndecorated(true);
		popupWindow.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		popupWindow.add(panel.getContainer());
		popupWindow.setSize(panel.getContainer().getWidth(),panel.getContainer().getHeight());
	}
*/
	public PopupPanelShell(UPopupPanel panel, Dialog owner) {
		super();
		this.popupPanel = panel;
		popupWindow = new JDialog(owner);
		popupWindow.addWindowListener(this);
		popupWindow.setModal(true);
//		popupWindow.setAlwaysOnTop(true);
//		popupWindow.setType(Type.UTILITY);
//		popupWindow.setUndecorated(true);
//		popupWindow.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		popupWindow.add(panel.getContainer());
		popupWindow.setSize(panel.getContainer().getWidth(),panel.getContainer().getHeight());
	}
	public void setPopupLocation(Point point){
		popupWindow.setLocation(point.x, point.y );		
	}
	@Override
	public void show() {
		popupWindow.addWindowFocusListener(this);
		popupWindow.setVisible(true);
	}

	@Override
	public void hide() {
		// popupWindow.setVisible(false);
		popupWindow.removeWindowFocusListener(this);
		popupWindow.dispose();
	}

	public void windowGainedFocus(WindowEvent e) {
		// NO-OP
	}

	public void windowLostFocus(WindowEvent e) {
		hide();
	}
	public UFormI getDataForm(){
		return (UFormI) popupPanel.getHandler().getForm();

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		popupWindow.setVisible(false);
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}
