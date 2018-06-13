package cn.edu.sdu.uims.component.workbench;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.Icon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import cn.edu.sdu.uims.base.UNameObjectPar;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.component.panel.JClosableTabbedPane;
import cn.edu.sdu.uims.component.panel.SuperPanel;
import cn.edu.sdu.uims.def.UPanelTemplate;
import cn.edu.sdu.uims.frame.UClientFrame;
import cn.edu.sdu.uims.handler.ToolCommandHandlerI;
import cn.edu.sdu.uims.util.UimsUtils;

public class UWorkbenchTable extends UWorkbench implements ComponentListener {
	protected JClosableTabbedPane tabbedPane;
	private Icon icon = null;

	public void init() {
		initComponents();
		addComponmentsToContainer();
	}

	public void addComponmentsToContainer() {
		container.setLayout(new BorderLayout());
		tabbedPane = new JClosableTabbedPane();
		container.add(tabbedPane);
		//tabbedPane.setPreferredSize(new Dimension(500, 500));
		tabbedPane.addComponentListener(this);
		tabbedPane.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent arg0) {
				JPanel currSelectPanel = tabbedPane.getSelectedPanel();
				int index = tabbedPane.getSelectedIndex();
				if (currSelectPanel != null) {
					// set toolbar button's state
					disableToolBarButtons();
					setToolBarState((UPanelI) ((UNameObjectPar) panelList
							.get(index)).getObject());
				}
			}
		});

		tabbedPane.addCloseListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand().equals(
						JClosableTabbedPane.ON_TAB_CLOSE)
						|| e.getActionCommand().equals(
								JClosableTabbedPane.ON_TAB_DOUBLECLICK)) {
					int ret = UimsUtils.messageBoxChoice("确认要关掉当前的操作窗口吗！");
					if(ret == JOptionPane.YES_OPTION)
						removeSelectedTabbedPane();
				}
			}
		});
		// ���õ���˵�
		JPopupMenu menu = new JPopupMenu();
		JMenuItem item = new JMenuItem("关闭");
		item.addActionListener(new ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent e) {
				removeSelectedTabbedPane();
			}
		});
		menu.add(item);

		item = new JMenuItem("关闭所有");
		item.addActionListener(new ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent e) {
				removeAllPanel();
			}
		});
		menu.add(item);

		tabbedPane.setPopup(menu);

	}

	protected void addPanel(String name, String tabName, UPanelI inPanel) {
		// ImagePanel panel = new ImagePanel();
		int isExit = isPanelExist(name);
		if (isExit == -1) {
			if (UClientFrame.getFrame().isCloseCurrentPaneWhenAddPanel()) {
				super.removeAllPanel();
				tabbedPane.removeAll();
			}
			if(UClientFrame.getFrame().getMaxOpenPanelNum() > 0) {
				int max = panelList.size();
				if(max == UClientFrame.getFrame().getMaxOpenPanelNum()) {
					int ret = UimsUtils.messageBoxChoice("确认保持最近打开的"+UClientFrame.getFrame().getMaxOpenPanelNum()+"个窗口，取消为关闭所有打开的窗口！");
					if(ret == UimsUtils.YES_OPTION) {
						UNameObjectPar o = (UNameObjectPar)panelList.get(0);
						super.removePanelByName(o.getName());
						tabbedPane.remove(0);						
					}else{
						super.removeAllPanel();
						tabbedPane.removeAll();
					}
						
				}
			}
			String [] closePanels = inPanel.getClosePanels();
			if(closePanels != null) {
				int retI;
				for(int k = 0; k < closePanels.length;k++ ) {
					retI = removePanelByName(closePanels[k]);
					if(retI >= 0)
						tabbedPane.remove(retI);
				}
			}
			panelList.add(new UNameObjectPar(name, inPanel));
			JPanel p = (JPanel) inPanel.getAWTComponent();
			ImagePanel panel = new ImagePanel();
			panel.setLayout(new BorderLayout());
			panel.add(p);
			setCenter(p);

			UPanelTemplate template = (UPanelTemplate) inPanel.getTemplate();
			if (template != null) {
				String iconName = template.tabIconName;
				icon = UimsUtils.getIconByFileName(iconName);
			}
			tabbedPane.addTab(tabName, icon, panel);
			// inPanel.setOpaque(true);
			tabbedPane.setSelectedComponent(panel);
			setToolBarState(inPanel);
		} else {
			tabbedPane.setSelectedIndex(isExit);
		}
	}

	public void removeAllPanel() {
		super.removeAllPanel();
		if(tabbedPane !=null)
			tabbedPane.removeAll();
		disableToolBarButtons();
		resizeTabbedPane();
	}

	public void removeSelectedTabbedPane() {
		// �ص�ѡ��tabbedPane
		// ��Ҫ��panel list��ɾ���panel
		// to be continue������---- finished
		int index = tabbedPane.getSelectedIndex();

		if (index > -1) {
			Object o = tabbedPane.getSelectedPanel();
			if (o instanceof ToolCommandHandlerI) {
				ToolCommandHandlerI selectedPanel = (ToolCommandHandlerI) o;
				selectedPanel.doBeforePanelClosed();
			}
			if (o instanceof SuperPanel) {
				SuperPanel p = (SuperPanel) o;
				p.onClose();
			}
			tabbedPane.removeTabAt(index);
			panelList.remove(index);

		}
		resizeTabbedPane();
		// ���tabbedPane��û��panel��������toolBar������
//		if (tabbedPane.getTabCount() == 0)
//			disableToolBarButtons();
//		else {
			disableToolBarButtons();
			JPanel currSelectPanel = tabbedPane.getSelectedPanel();
			index = tabbedPane.getSelectedIndex();
			if (currSelectPanel != null) {
				// set toolbar button's state
				setToolBarState((UPanelI) ((UNameObjectPar) panelList
						.get(index)).getObject());
			}

//		}
	}

	void resizeTabbedPane() {
		// resize tabbedPane
		// get the max size of panels in panelList
		int size = panelList.size();
		double width = 0, height = 0;
		for (int i = 0; i < size; i++) {
			UNameObjectPar par = (UNameObjectPar) panelList.get(i);
			UPanelI pi = (UPanelI) par.getObject();
			JPanel panel = (JPanel) pi.getAWTComponent();
			Dimension d = panel.getSize();
			if (width < d.getWidth())
				width = d.getWidth();
			if (height < d.getHeight())
				height = d.getHeight();
		}
		tabbedPane.setPreferredSize(new Dimension((int) width,
				(int) height + 15));
	}

	void setCenter(JPanel inPanel) {
		Dimension outPanelSize;
		outPanelSize = tabbedPane.getSize();
		double outWidth = outPanelSize.getWidth();
		double outHeight = outPanelSize.getHeight();
		inPanel.setBounds((int) 0, (int) 0, // 35�1�7�1�7�0�4�1�7�1�7tab�1�7�1�7�0�5�1�7�0�0�1�2�1�7
				(int) outWidth, (int) outHeight - 30);
		inPanel.updateUI();
	}

	public int isPanelExist(String panelName) {
		int i = -1;
		JPanel p = null;
		int size = panelList.size();
		for (i = 0; i < size; i++) {
			UNameObjectPar par = (UNameObjectPar) panelList.get(i);
			String name = par.getName();
			if (name.equals(panelName)) {
				tabbedPane.setSelectedIndex(i);
				break;
			}
		}
		if (i == size)
			return -1;
		return i;
	}

	void setToolBarState(UPanelI panel) {
		UClientFrame fram = UClientFrame.getInstance();
		fram.enableToolBarButtons(panel.getToolActions());
	}

	void disableToolBarButtons() {
		UClientFrame frame = UClientFrame.getFrame();
		frame.disableToolBarButtons();
	}

	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub

	}

	public void componentShown(ComponentEvent e) {
		int size = panelList.size();
		for (int i = 0; i < size; i++) {
			UNameObjectPar par = (UNameObjectPar) panelList.get(i);
			UPanelI pi = (UPanelI) par.getObject();
			JPanel panel = (JPanel) pi.getAWTComponent();
			setCenter(panel);
		}

	}

	public UPanelI getCurrentPanel() {
		int index = tabbedPane.getSelectedIndex();
		if(index < 0)
			return null;
		else 
			return (UPanelI) ((UNameObjectPar) panelList.get(index)).getObject();
	}

}
