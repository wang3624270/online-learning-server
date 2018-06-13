package cn.edu.sdu.uims.component.list;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MouseInputListener;

import cn.edu.sdu.uims.base.UScrollPane;

public class UListPopup extends JPopupMenu implements MouseInputListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7465139957743575288L;

	protected JList jList;

	protected UScrollPane pane;

	private ArrayList<ListSelectionListener> listeners = new ArrayList<ListSelectionListener>();

	private int preferredHeight = 100;
	private int preferredWidth = 200;

	public UListPopup() {
		super();
		// TODO Auto-generated constructor stub
		setLayout(new BorderLayout());
		jList = new JList();
		jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jList.addMouseListener(this);
		jList.addMouseMotionListener(this);
		jList.setModel(new DefaultListModel());
		pane = new UScrollPane(jList);
		pane.setBorder(null);
		add(pane, BorderLayout.CENTER);
	}
	public void setPopupPreferredSize() {
		if (this.jList.getModel() != null) {
			int height = 18 * getJList().getModel().getSize() + 2;
			if(height < preferredHeight)
				height = preferredHeight;
			this.setPreferredSize(new Dimension(
					getWidth(getJList().getModel()), height));
			pane.updateUI();

		}
	}

	public int getWidth(ListModel model) {
		DefaultListModel dModel = (DefaultListModel) model;
		Object[] info = (Object[]) dModel.toArray();
		Toolkit kit = Toolkit.getDefaultToolkit();
		FontMetrics fm = kit.getFontMetrics(new Font("Arial", Font.PLAIN, 18));
		if (info != null && info.length != 0) {
			int width = 0;
			for (int i = 0; i < info.length; i++) {
				if (width < fm.stringWidth(info[i].toString()))
					width = fm.stringWidth(info[i].toString());
			}
			if(width < preferredWidth)
				width = preferredWidth;
			return width;
		}
		return preferredWidth;
	}

	public int getItemCount() {
		DefaultListModel model = (DefaultListModel) jList.getModel();
		return model.getSize();
	}

	public Object getItem(int index) {
		DefaultListModel model = (DefaultListModel) jList.getModel();
		return model.get(index);
	}

	public void addItem(Object o) {
		DefaultListModel model = (DefaultListModel) jList.getModel();
		model.addElement(o);
		jList.repaint();
	}

	public void removeItem(Object o) {
		DefaultListModel model = (DefaultListModel) jList.getModel();
		model.removeElement(o);
		jList.repaint();
	}

	public void setList(Iterable iterable) {
		DefaultListModel model = new DefaultListModel();
		for (Object o : iterable) {
			model.addElement(o);
		}
		jList.setModel(model);
		jList.repaint();
	}
	public void  setDataList(List list){
		jList.setListData(list.toArray());
	}

	

	public void setList(Enumeration e) {
		DefaultListModel model = new DefaultListModel();
		while (e.hasMoreElements()) {
			model.addElement(e.nextElement());
		}
		jList.setModel(model);
		jList.repaint();
	}

	public void setList(Object... objects) {
		DefaultListModel model = new DefaultListModel();
		for (Object o : objects) {
			model.addElement(o);
		}
		jList.setModel(model);
		jList.repaint();
	}

	public void addListSelectionListener(ListSelectionListener l) {
		if (!listeners.contains(l))
			listeners.add(l);
	}

	public void setSelectedIndex(int index) {
		if (index >= jList.getModel().getSize())
			index = 0;
		if (index < 0)
			index = jList.getModel().getSize() - 1;
		jList.ensureIndexIsVisible(index);
		jList.setSelectedIndex(index);
	}

	public Object getSelectedValue() {
		return jList.getSelectedValue();
	}

	public int getSelectedIndex() {
		return jList.getSelectedIndex();
	}

	public boolean isSelected() {
		return jList.getSelectedIndex() != -1;
	}

	public void setLastOneSelected() {
		int count = jList.getModel().getSize();
		if (count > 0) {
			jList.ensureIndexIsVisible(count - 1);
			jList.setSelectedIndex(count - 1);
		}
	}
	public void setSelectObject(Object o){
		Object ot;
		if(o == null) {
			jList.setSelectedIndex(0);
			return;
		}
		for(int i = 0; i < jList.getModel().getSize();i++){
			ot = jList.getModel().getElementAt(i); 
			if(ot.equals(o)) {
				jList.setSelectedIndex(i);
				return;
			}
		}
		jList.setSelectedIndex(0);
	}
	public void removeListSelectionListener(ListSelectionListener l) {
		if (listeners.contains(l))
			listeners.remove(l);
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		if (jList.getSelectedIndex() != -1)
			fireValueChanged(new ListSelectionEvent(jList, jList
					.getSelectedIndex(), jList.getSelectedIndex(), true));

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == jList) {
			Point location = e.getPoint();
			Rectangle r = new Rectangle();
			jList.computeVisibleRect(r);
			if (r.contains(location)) {
				updateListBoxSelectionForEvent(e, false);
			}
		}

	}

	protected void updateListBoxSelectionForEvent(MouseEvent anEvent,
			boolean shouldScroll) {

		Point location = anEvent.getPoint();
		if (jList == null) {
			return;
		}
		int index = jList.locationToIndex(location);
		if (index == -1) {
			if (location.y < 0) {
				index = 0;
			} else {
				index = jList.getModel().getSize() - 1;
			}
		}
		if (jList.getSelectedIndex() != index) {
			jList.setSelectedIndex(index);
			if (shouldScroll) {
				jList.ensureIndexIsVisible(index);
			}
		}
	}

	private void fireValueChanged(ListSelectionEvent e) {
		for (ListSelectionListener l : listeners) {
			l.valueChanged(e);
		}
	}
	public JList getJList(){
		return jList;
	}
	public void restScrollPane(){
		pane.updateUI();
	}
	public void setSelectionMode(int selectionModel){
		if(jList != null)
		jList.setSelectionMode(selectionModel);
	
	}
}
