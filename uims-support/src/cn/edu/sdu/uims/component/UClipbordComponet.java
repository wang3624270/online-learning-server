package cn.edu.sdu.uims.component;

import java.awt.Component;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import cn.edu.sdu.uims.UimsFactory;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.component.menu.UPopupMenu;

public class UClipbordComponet implements ActionListener, MouseListener {
	private UComponentI com;
	private JPopupMenu popupMenu = null;

	
	public UClipbordComponet(UComponentI com){
		this.com = com;
	}
	public void init(){
		Component c = com.getAWTComponent();
		c.addMouseListener(this);
		
		popupMenu = new UPopupMenu();
		JMenuItem item;
		item = new JMenuItem();
		item.setText("粘帖");
		item.setActionCommand("doPaste");
		item.addActionListener(this);
		popupMenu.add(item);
		item = new JMenuItem();
		item.setText("复制");
		item.setActionCommand("doCopy");
		item.addActionListener(this);
		popupMenu.add(item);

	}
	public void displyPopMenu(Component com, int x, int y) {
		if (popupMenu != null && popupMenu.getSubElements().length >= 1)
			popupMenu.show(com, x, y);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getButton() == MouseEvent.BUTTON3) {
			displyPopMenu((Component) e.getSource(), e
					.getX(), e.getY());
		}
	
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String cmd = e.getActionCommand();
		if(cmd.equals("doPaste")) {
			clipbordContentToComponent();
		}else if(cmd.equals("doCopy")){
			componentContentToClipboard();
		}
	}
	public void componentContentToClipboard() {
		Clipboard c = UimsFactory.getClientMainI().getClipboard();
		if (c == null)
			return;
		String temp = com.getSelectedText();
		if (temp == null || temp.length() == 0)
			return;
		StringSelection text = new StringSelection(temp);
		c.setContents(text, null);
	}

	public void clipbordContentToComponent() {
		Clipboard c = UimsFactory.getClientMainI().getClipboard();
		if (c == null)
			return;
		String text = null;
		Transferable contents = c.getContents(com.getAWTComponent());
		DataFlavor flavor = DataFlavor.stringFlavor;
		contents.isDataFlavorSupported(flavor);
		try {
//			Object obj =  contents.getTransferData(flavor);
			text = (String) contents.getTransferData(flavor);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (text != null && text.length() != 0) {
			com.insertText(text);
		}
	}

}
