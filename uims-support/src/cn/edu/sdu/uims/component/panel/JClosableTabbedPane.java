package cn.edu.sdu.uims.component.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.Serializable;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

/**
 * 
 * @author Administrator
 *
 * 解决了多个tab点击关闭时的UI数组越界问题 〄1�7
 * 此问题在JDK-1.5.12之前存在，之后还不确定，可能已经修正
 * 赵鹏 2008/03/06
 */
public class JClosableTabbedPane extends JTabbedPane implements Serializable {
	public static final String ON_TAB_CLOSE = "ON_TAB_CLOSE";
	public static final String ON_TAB_SELECT = "ON_TAB_SELECT";

	public static final String ON_TAB_DOUBLECLICK = "ON_TAB_DOUBLECLICK";

	private JPopupMenu popup = null;

	JPanel lastSelectPanel = null;

	public JClosableTabbedPane() {
		super();
		init();
	}

	public JClosableTabbedPane(int tabPlacement) {
		super(tabPlacement);
		init();
	}

	public JClosableTabbedPane(int tabPlacement, int tabLayoutPolicy) {
		super(tabPlacement, tabLayoutPolicy);
		init();
	}
	public JPanel getSelectedPanel() {
		if (getSelectedComponent() == null)
			return null;
		Component[] c = ((JPanel) getSelectedComponent())
				.getComponents();
		if (c.length > 0) {
			return (JPanel) c[0];

		}
		return null;
	}

	protected void init() {
		addMouseListener(new DefaultMouseAdapter());

		 this.addChangeListener(new ChangeListener() {
		 public void stateChanged(ChangeEvent e) {
		
		  if (lastSelectPanel != null
						&& lastSelectPanel instanceof SuperPanel) {
//					System.out.println(lastSelectPanel + " deact");
					((SuperPanel) lastSelectPanel).deactivated();
				}

				JPanel p = getSelectedPanel();
				if (p != null && p instanceof SuperPanel) {
					((SuperPanel) p).activated();
				}

				lastSelectPanel = (JPanel) p;
					}
				});
	}
	public void setPopup(JPopupMenu popup) {
		this.popup = popup;
	}

	public void setIconDrawCenter(int index, boolean drawCenter) {
		((CloseIcon) getIconAt(index)).setDrawCenter(drawCenter);
		repaint();
	}

	public JPopupMenu getPopup() {
		return popup;
	}

	public boolean isDrawCenter(int index) {
		return ((CloseIcon) getIconAt(index)).isDrawCenter();
	}

	protected EventListenerList closeListenerList = new EventListenerList();

	public void addCloseListener(ActionListener l) {
		closeListenerList.add(ActionListener.class, l);
	}

	public void removeCloseListener(ActionListener l) {
		closeListenerList.remove(ActionListener.class, l);
	}

	protected void fireClosed(ActionEvent e) {
		Component c = this.getSelectedComponent();
		if (c != null && c instanceof SuperPanel) {
			((SuperPanel) c).closed();
		}
		Object[] listeners = closeListenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ActionListener.class) {
				((ActionListener) listeners[i + 1]).actionPerformed(e);
			}
		}
	}
	protected void fireSelected(ActionEvent e) {
	}

	class DefaultMouseAdapter extends MouseAdapter {
		CloseIcon icon;

		/**
		 * 用mouseClicked 方法替代了mousePressed和mouseReleased方法
		 * 每次鼠标点击后调用UpdateUI方法，防止出现tabPane UI数组越界问题
		 * 此问题在JDK-1.5.12之前存在，之后还不确定，可能已经修正
		 * 赵鹏 修改 2008/03/06
		 */
		public void mouseClicked(MouseEvent e) {
			if (icon != null) {
				icon.setPressed(false);
				icon = null;
				repaint();
			}
			if ((e.getButton() == MouseEvent.BUTTON1)) {
				int index = indexAtLocation(e.getX(), e.getY());
				if (index != -1) {
					icon = (CloseIcon) getIconAt(index);
					if (icon.getBounds().contains(e.getPoint())) {
						icon.setPressed(true);
						fireClosed(new ActionEvent(e.getComponent(),
								ActionEvent.ACTION_PERFORMED, ON_TAB_CLOSE));
					} else if (e.getClickCount() == 2) {
						fireClosed(new ActionEvent(e.getComponent(),
								ActionEvent.ACTION_PERFORMED,
								ON_TAB_DOUBLECLICK));
					}
					else {
						fireSelected(new ActionEvent(e.getComponent(),
								ActionEvent.ACTION_PERFORMED, ON_TAB_SELECT));						
					}
				}
			}

			if (popup != null && SwingUtilities.isRightMouseButton(e)
					&& indexAtLocation(e.getX(), e.getY()) != -1) {

				popup.show(e.getComponent(), e.getX(), e.getY());
			}
			updateUI();//刷新UI ，防止出现tabPane UI数组越界问题
		}
	}

	public Icon getIconAt(int index) {
		Icon icon = super.getIconAt(index);
		if (icon == null || !(icon instanceof CloseIcon)) {
			super.setIconAt(index, new CloseIcon());
		}
		return super.getIconAt(index);
	}

	class CloseIcon implements Icon {
		Rectangle rec = new Rectangle(0, 0, 15, 16);

		private boolean pressed = false;

		private boolean drawCenter = true;

		public synchronized void paintIcon(Component c, Graphics g, int x1,
				int y1) {
			int x = x1, y = y1;
			if (pressed) {
				x++;
				y++;
			}
			rec.x = x;
			rec.y = y;
			Color oldColor = g.getColor();

			g.setColor(UIManager.getColor("TabbedPane.highlight"));
			g.drawLine(x, y, x, y + rec.height);
			g.drawLine(x, y, x + rec.width, y);
			g.setColor(UIManager.getColor("TabbedPane.shadow"));
			g.drawLine(x, y + rec.height, x + rec.width, y + rec.height);
			g.drawLine(x + rec.width, y, x + rec.width, y + rec.height);
			g.setColor(UIManager.getColor("TabbedPane.foreground"));

			// draw X
			// left top
			g.drawRect(x + 4, y + 4, 1, 1);
			g.drawRect(x + 5, y + 5, 1, 1);
			g.drawRect(x + 5, y + 9, 1, 1);
			g.drawRect(x + 4, y + 10, 1, 1);
			// center
			if (drawCenter) {
				g.drawRect(x + 6, y + 6, 1, 1);
				g.drawRect(x + 8, y + 6, 1, 1);
				g.drawRect(x + 6, y + 8, 1, 1);
				g.drawRect(x + 8, y + 8, 1, 1);
			}
			// right top
			g.drawRect(x + 10, y + 4, 1, 1);
			g.drawRect(x + 9, y + 5, 1, 1);
			// right bottom
			g.drawRect(x + 9, y + 9, 1, 1);
			g.drawRect(x + 10, y + 10, 1, 1);
			g.setColor(oldColor);
		}

		private void drawRec(Graphics g, int x, int y) {
			g.drawRect(x, y, 1, 1);
		}

		public Rectangle getBounds() {
			return rec;
		}

		public void setBounds(Rectangle rec) {
			this.rec = rec;
		}

		public int getIconWidth() {
			return rec.width;
		}

		public int getIconHeight() {
			return rec.height;
		}

		public void setPressed(boolean pressed) {
			this.pressed = pressed;
		}

		public void setDrawCenter(boolean drawCenter) {
			this.drawCenter = drawCenter;
		}

		public boolean isPressed() {
			return pressed;
		}

		public boolean isDrawCenter() {
			return drawCenter;
		}
	};

	public static void main(String[] args) {
		try {
            UIManager.setLookAndFeel("com.nilo.plaf.nimrod.NimRODLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		JFrame frame = new JFrame("JCloseableTabbedPane Demo");
		frame.getContentPane().setLayout(new BorderLayout());
		final JClosableTabbedPane tab = new JClosableTabbedPane();
		tab.add(new SuperPanel(), "TabbedPane");
		tab.add(new SuperPanel(), "Has");
		tab.add(new SuperPanel(), "Popup");
		tab.add(new SuperPanel(), "PopupMenu");

		tab.setIconDrawCenter(1, false);

		tab.addCloseListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals(tab.ON_TAB_CLOSE)) {
					tab.removeTabAt(tab.getSelectedIndex());
					tab.updateUI();
				}
			}
		});
		JPopupMenu menu = new JPopupMenu();
		for (int i = 0; i < 10; i++) {
			menu.add(new JMenuItem("item " + i));
		}
		tab.setPopup(menu);
		JButton button=new JButton();
        button.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e)
        	{
        		
        		tab.add(new SuperPanel(), "temp");
        	}
        }
        );
        frame.getContentPane().add(button, BorderLayout.SOUTH);
		frame.getContentPane().add(tab, BorderLayout.CENTER);
		frame.setSize(500, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}