package cn.edu.sdu.uims.component.tree;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.ListModel;
import javax.swing.event.MouseInputListener;
import javax.swing.event.TreeSelectionListener;

import cn.edu.sdu.uims.base.UScrollPane;

public class UTreePopup extends JPopupMenu implements MouseInputListener{
	protected JTree jTree;

	protected UScrollPane pane;

	private ArrayList<TreeSelectionListener> listeners = new ArrayList<TreeSelectionListener>();

	private int preferredHeight = 100;
	private int preferredWidth = 200;

	public UTreePopup() {
		super();
		// TODO Auto-generated constructor stub
		setLayout(new BorderLayout());
		jTree = new JTree();
//		jTree.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		jTree.addMouseListener(this);
		jTree.addMouseMotionListener(this);
//		jTree.setModel(new DefaultListModel());
		pane = new UScrollPane(jTree);
		pane.setBorder(null);
		add(pane, BorderLayout.CENTER);
	}
	public void setPopupPreferredSize() {
//		if (this.jList.getModel() != null) {
//			int height = 18 * getJList().getModel().getSize() + 2;
//			this.setPreferredSize(new Dimension(
//					getWidth(getJList().getModel()), height));
		this.setPreferredSize(new Dimension(120,200));
			pane.updateUI();

//		}
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
			return width;
		}
		return preferredWidth;
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
