package cn.edu.sdu.uims.component.complex;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.component.USplitPane;
import cn.edu.sdu.uims.component.complex.def.GraphDataAttributeI;
import cn.edu.sdu.uims.component.complex.def.UGraphControlTemplate;
import cn.edu.sdu.uims.component.event.EventConstants;
import cn.edu.sdu.uims.component.event.SelectObjectEvent;
import cn.edu.sdu.uims.component.menu.UMenu;
import cn.edu.sdu.uims.component.menu.UPopupMenu;
import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.def.UEventAttribute;
import cn.edu.sdu.uims.def.UPanelTemplate;
import cn.edu.sdu.uims.service.UFactory;

public class UGraphControlPanel extends UComplexPanel {

	public static Color GRAPH_COLOR_NORMAL = new Color(0, 0, 0);
	public static Color GRAPH_COLOR_SELECT = new Color(0, 192, 0);
	private Object[] graphData;
	private int maxCol = 0;
	private int currentSelectGraphRow, currentSelectGraphCol;
	private JPanel graphPanel = null;
	private JScrollPane scrollPane = null;
	private JPanel infoPanel;
	private GraphControlMouseProcessor mouseListener = new GraphControlMouseProcessor();
	private UPopupMenu popupMenu = null;
	private boolean enablePopupMenu = true;
	private boolean canSelectObject = false;
	private HashMap<String, UPanelI> innerPanelMap = null;
	private CardLayout infoPanelLayout;
	private class GraphControlMouseProcessor implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			processMouseClicked(e);
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

	}

	private void processMouseClicked(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
			if (enablePopupMenu) {
				displyPopMenu((Component) e.getSource(), e.getX(), e.getY());
			}
		} else {
			int modifies = e.getModifiersEx();
			if (modifies == e.CTRL_DOWN_MASK) {
				setCtrlSelectGraph(e.getX(), e.getY());

			} else {
				boolean b = changeCurrentSelectGraph(e.getX(), e.getY());
				if (b && canSelectObject) {
					SelectObjectEvent event = new SelectObjectEvent(this,
							this.getCurrentSelectObject());
					if (getUParent().getEventAdaptor() != null)
						getUParent().getEventAdaptor().processEvent(event,
								EventConstants.EVENT_SELECTOBJECT, "select");
				}
			}
		}

	}

	private void refreshPanel() {
		int width = 1, height = 1;
		if (graphData != null && graphData.length != 0) {
			UGraphControlTemplate temp = (UGraphControlTemplate) elementTemplate;
			width = temp.elementWidth * maxCol;
			height = temp.elementHeight * graphData.length;
		}
		Dimension d = new Dimension(width, height);
		graphPanel.setPreferredSize(d);
		graphPanel.setSize(d);
		scrollPane.updateUI();
		setInfoPanelData(this.getCurrentSelectObject());

	}
	
	
	private void drawElement(Graphics g, GraphDataAttributeI data, int x,
			int y, int w, int h) {
		int dataStatus;
		String text;
		text = data.getText();
		Boolean select = data.getSelect();
		if (select == null || select.equals(false))
			g.setColor(GRAPH_COLOR_NORMAL);
		else
			g.setColor(GRAPH_COLOR_SELECT);
		g.drawRect(x, y, w, h);
		String colorName = data.getTextColor();
		g.setColor(UFactory.getModelSession().getColorByName(colorName).color);
		if(text != null)
			g.drawString(text, x, y + h - 4);

	}

	private void drawGraphControl(Graphics g) {
		if (graphData == null || graphData.length == 0)
			return;
		UGraphControlTemplate temp = (UGraphControlTemplate) elementTemplate;
		int i, j;
		int x, y, w, h;
		GraphDataAttributeI data;
		List<GraphDataAttributeI> graphList;
		for (i = 0; i < graphData.length; i++) {
			graphList = (List<GraphDataAttributeI>) graphData[i];
			if (graphList == null || graphList.size() == 0)
				continue;
			for (j = 0; j < graphList.size(); j++) {
				data = graphList.get(j);
				x = j * temp.elementWidth;
				y = i * temp.elementHeight;
				w = temp.elementWidth;
				h = temp.elementHeight;
				drawElement(g, data, x, y, w, h);
			}
		}
	}

	private void initGraphStatus() {
		maxCol = 0;
		if (graphData == null || graphData.length == 0) {
			currentSelectGraphRow = -1;
			currentSelectGraphCol = -1;
			return;
		}
		int i, j;
		GraphDataAttributeI data;
		currentSelectGraphRow = 0;
		List<GraphDataAttributeI> graphList;
		for (i = 0; i < graphData.length; i++) {
			graphList = (List<GraphDataAttributeI>) graphData[i];
			if (graphList == null || graphList.size() == 0)
				continue;
			if (maxCol < graphList.size())
				maxCol = graphList.size();
			if (currentSelectGraphRow < 1) {
				currentSelectGraphRow = i;
				currentSelectGraphCol = 0;
			}
		}

	}

	private void setCtrlSelectGraph(int x, int y) {
		UGraphControlTemplate temp = (UGraphControlTemplate) elementTemplate;
		int i, j;
		int col = x / temp.elementWidth;
		int row = y / temp.elementHeight;
		if (graphData == null || row >= graphData.length)
			return;
		List list = (List) graphData[row];
		if (list == null || col >= list.size())
			return;
		GraphDataAttributeI graph;
		list = (List) graphData[row];
		graph = (GraphDataAttributeI) list.get(col);
		if (graph.getSelect() == null || graph.getSelect().equals(false))
			graph.setSelect(true);
		else
			graph.setSelect(false);
		graphPanel.repaint();
	}

	private boolean changeCurrentSelectGraph(int x, int y) {
		UGraphControlTemplate temp = (UGraphControlTemplate) elementTemplate;
		int i, j;
		int col = x / temp.elementWidth;
		int row = y / temp.elementHeight;
		if (graphData == null || row >= graphData.length)
			return false;
		List list = (List) graphData[row];
		if (list == null || col >= list.size())
			return false;
		if(col == currentSelectGraphCol && currentSelectGraphRow == row)
			return false;
		currentSelectGraphRow = row;
		currentSelectGraphCol = col;
		setInfoPanelData(this.getCurrentSelectObject());
		return true;
	}
	public void setInfoPanelData(Object obj){
		if(obj == null)
			return;
		String name =  obj.getClass().getName();
		UPanelI pp = innerPanelMap.get(name);
		if(pp != null) {
			pp.setData(obj);
			infoPanelLayout.show(infoPanel, pp.getTemplate().getName());
		}
	}
	
	@Override
	public void initContents() {
		// TODO Auto-generated method stub
		setLayout(new BorderLayout());
		UGraphControlTemplate temp = (UGraphControlTemplate) elementTemplate;
		graphPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				drawGraphControl(g);
			}
		};
		graphPanel.addMouseListener(mouseListener);
		USplitPane sp = new USplitPane(USplitPane.VERTICAL_SPLIT);
		sp.setDividerLocation(temp.divh);
		sp.setDividerSize(temp.divsw);
		scrollPane = new JScrollPane(graphPanel);
		sp.setTopComponent(scrollPane);
		if(temp.addedDatas == null || temp.addedDatas.size() == 0)
			return;
		String panelName;
		innerPanelMap =new HashMap<String, UPanelI>();
		infoPanel = new JPanel();
		infoPanelLayout = new CardLayout();
		infoPanel.setLayout(infoPanelLayout);
		UPanelI pp;
		ListOptionInfo info;
		for(int k = 0; k  <temp.addedDatas.size();k++) {
			info = (ListOptionInfo)temp.addedDatas.get(k);
			panelName = info.getValue();
			UPanelTemplate template = (UPanelTemplate) UFactory.getModelSession()
					.getTemplate(UConstants.MAPKEY_PANEL_FORM,panelName);
			pp = (UPanelI) template.newComponent();
			pp.setStartMenuName("infoPanel");
			pp.setOriginalTemplate(null);
			pp.setPathNameString(template.name);
			pp.setTemplate(template);
			pp.setUParent(this.getUParent());
			pp.addActionListener(this.getUParent().getEventAdaptor());
			pp.init();
			infoPanel.add(pp.getAWTComponent(),template.name);
			innerPanelMap.put(template.dataFormClassName, pp);
		}
		sp.setBottomComponent(infoPanel);
		this.add(sp);
	}

	public void setData(Object o) {
		graphData = (Object[]) o;
		initGraphStatus();
		refreshPanel();
	}

	public void setPopupMenu(UMenu menu) {
		// TODO Auto-generated method stub
		popupMenu = new UPopupMenu();
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

	public void addEvents(UEventAttribute events[]) {
		int i;
		for (i = 0; i < events.length; i++) {
			if (events[i].name.equals(EventConstants.EVENT_SELECTOBJECT)) {
				canSelectObject = true;
			}
		}
	}

	public Object getCurrentSelectObject() {
		if (graphData == null || graphData.length == 0)
			return null;
		List list = (List) graphData[currentSelectGraphRow];
		return list.get(currentSelectGraphCol);
	}
}
