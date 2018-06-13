package cn.edu.sdu.uims.component.complex;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cn.edu.sdu.uims.component.complex.def.URoomPlaneTemplate;
import cn.edu.sdu.uims.component.complex.form.RoomBuildingDataForm;
import cn.edu.sdu.uims.component.complex.form.RoomPlaneDataForm;
import cn.edu.sdu.uims.component.complex.form.RoomRoomDataForm;
import cn.edu.sdu.uims.component.event.EventConstants;
import cn.edu.sdu.uims.component.event.SelectObjectEvent;
import cn.edu.sdu.uims.def.UEventAttribute;

public class URoomPlaneGraphPanel extends UComplexPanel {
	private RoomPlaneDataForm dataForm = null;
	private ViewMouseProcessor viewMouseProcessor = new ViewMouseProcessor();
	private ListSelectionProcessor listSelectionProcessor = new ListSelectionProcessor();
	private boolean canSelectObjectEvent = false;
	private boolean canListSelectionEvent = false;

	private JLabel displayInfo;

	private JPanel infoPanel = null;
	private JScrollPane listScrollPane, viewScrollPane;
	private JList buildingList;
	private RoomPlaneView layoutView;

	public static int BUILDING_BOX_WIDTH = 60;
	public static int ROOM_BOX_WIDTH = 60;
	public static int BOX_HEIGHT = 40;
	public static int BOUND_WIDTH = 20;

	private Point oldP = null, currentP = null;
	public static Color XORCOLOR = new Color(255, 255, 255);

	private boolean isDisplayBuildingName() {
		URoomPlaneTemplate t = (URoomPlaneTemplate) this.elementTemplate;
		return t.displayBuildingName;
	}

	private boolean isBuildingCanSelect() {
		URoomPlaneTemplate t = (URoomPlaneTemplate) this.elementTemplate;
		return t.buildingCanSelect;
	}

	private void initInfoPanel() {
		infoPanel = new JPanel();
		infoPanel.setLayout(new FlowLayout());
		displayInfo = new JLabel(getDisplayString());
		infoPanel.add(displayInfo);
	}

	private void initBuidingListPanel() {
		if (!isBuildingCanSelect())
			return;
		buildingList = new JList();
		buildingList.addListSelectionListener(listSelectionProcessor);
		Dimension d = new Dimension(120, 600);
		listScrollPane = new JScrollPane(buildingList);
		listScrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		listScrollPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		listScrollPane.setPreferredSize(d);
		listScrollPane.setSize(d);
	}

	private String getDisplayString() {
		String text;
		int beds = 0;
		int pers = 0;
		if (dataForm != null) {
			if (dataForm.getPerNums() != null)
				pers = dataForm.getPerNums();
			if (dataForm.getBedNums() != null)
				beds = dataForm.getBedNums();
		}
		URoomPlaneTemplate t = (URoomPlaneTemplate) this.elementTemplate;
		text = t.title + ":" + t.perLabel + ":" + pers + t.bedLabel + ":"
				+ beds;
		return text;
	}

	public void updateInfoPanel() {
		displayInfo.setText(getDisplayString());
	}

	@Override
	public void addEvents(UEventAttribute[] events) {
		// TODO Auto-generated method stub
		for (int i = 0; i < events.length; i++) {
			if (events[i].name.equals(EventConstants.EVENT_SELECTOBJECT)) {
				canSelectObjectEvent = true;
			}
			if(events[i].name.equals(EventConstants.EVENT_LISTSELECTION)) {
				canListSelectionEvent = true;
			}
		}

	}

	public void updateRoomLayoutView() {
		if (viewScrollPane == null)
			return;
		viewScrollPane.updateUI();
	}

	@Override
	public void initContents() {
		// TODO Auto-generated method stub
		initBuidingListPanel();
		initInfoPanel();
		this.setLayout(new BorderLayout());
		layoutView = new RoomPlaneView();
		viewScrollPane = new JScrollPane(layoutView);
		add(viewScrollPane, BorderLayout.CENTER);
		if (listScrollPane != null)
			add(listScrollPane, BorderLayout.EAST);
		add(infoPanel, BorderLayout.NORTH);
	}

	public void setData(Object o) {
		if (o == null)
			dataForm = null;
		else {
			if (o instanceof RoomPlaneDataForm)
				dataForm = (RoomPlaneDataForm) o;
		}
		refreshPanel();
	}

	public Object getData() {
		return dataForm;
	}

	public void clearPanel() {

	}

	public void refreshPanel() {
		if (dataForm == null) {
			clearPanel();
			return;
		}
		if (buildingList != null) {
			List list = dataForm.getBuildingList();
			if (list != null && list.size() != 0) {
				buildingList.setListData(list.toArray());
			} else
				buildingList.setListData(new ArrayList().toArray());
		}
		updateInfoPanel();
		resetImageSize();
		layoutView.repaint();
	}

	public void resetImageSize() {
		int w = 1;
		int h = 1;
		if (dataForm != null) {
			URoomPlaneTemplate t = (URoomPlaneTemplate) this.elementTemplate;
			int row = dataForm.getCellRowNum(t.roomColumn);
			w = t.roomColumn * ROOM_BOX_WIDTH + BOUND_WIDTH * 2;
			if (t.displayBuildingName)
				w += BUILDING_BOX_WIDTH;
			h = row * BOX_HEIGHT + BOUND_WIDTH * 2;
		}
		Dimension d = new Dimension(w, h);
		layoutView.setPreferredSize(d);
		layoutView.setSize(d);
		updateRoomLayoutView();
	}

	public void drawRoom(Graphics g, int x, int y, int w, int h,
			RoomRoomDataForm rForm) {
		String str =rForm.getRoomName();
		if(rForm.getBedNums() != null) {
			str += "-";
			for(int i = 0; i < rForm.getBedNums().length;i++) {
				str += rForm.getBedNums()[i];
			}
		}
		g.drawString(str, x+4, y+32);
		g.drawRect(x+2, y+2, w-4, h-4);
	}

	public void drawBuilding(Graphics g, int x, int y, int w, int h,
			RoomBuildingDataForm bForm) {
		g.drawString(bForm.getBuildingName(), x, y+30);
		g.drawRect(x, y, w, h);
	}

	public void drawRooms(Graphics g) {
		if (dataForm == null || dataForm.getBuildingList() == null
				|| dataForm.getBuildingList().size() == 0)
			return;
		URoomPlaneTemplate t = (URoomPlaneTemplate) this.elementTemplate;
		int xo, yo, i, j, x, y, size, bh;
		xo = BOUND_WIDTH;
		yo = BOUND_WIDTH;
		if (t.displayBuildingName) {
			xo += BUILDING_BOX_WIDTH;
		}
		y = yo;
		RoomBuildingDataForm bForm;
		for (i = 0; i < dataForm.getBuildingList().size(); i++) {
			bForm = dataForm.getBuildingList().get(i);
			if (bForm == null || bForm.getRoomList() == null
					|| bForm.getRoomList().size() == 0)
				continue;
			x = xo;
			size = bForm.getRoomList().size();
			if (t.displayBuildingName) {
				bh = size / t.roomColumn;
				if (size % t.roomColumn != 0)
					bh++;
				drawBuilding(g, xo - BUILDING_BOX_WIDTH, y, BUILDING_BOX_WIDTH,
						BOX_HEIGHT * bh, bForm);
			}
			for (j = 0; j < size; j++) {
				drawRoom(g, x, y, ROOM_BOX_WIDTH, BOX_HEIGHT, bForm
						.getRoomList().get(j));
				x += ROOM_BOX_WIDTH;
				if ((j + 1) % t.roomColumn == 0) {
					x = xo;
					y += BOX_HEIGHT;
				}
			}
			if (size > 0 && (size % t.roomColumn != 0))
				y += BOX_HEIGHT;
		}
	}

	private class RoomPlaneView extends JViewport {
		public RoomPlaneView() {
			this.addMouseListener(viewMouseProcessor);
			this.addMouseMotionListener(viewMouseProcessor);
		}

		public void paint(Graphics g) {
			Dimension size = getSize();
			g.clearRect(0, 0, size.width, size.height);
			drawRooms(g);
		}
	}
	
	private Rectangle getSelectRect(){
		int x, y, w, h;
		if (oldP.x < currentP.x) {
			x = oldP.x;
			w = currentP.x - x;
		} else {
			x = currentP.x;
			w = oldP.x - x;
		}
		if (oldP.y < currentP.y) {
			y = oldP.y;
			h = currentP.y - y;
		} else {
			y = currentP.y;
			h = oldP.y - y;
		}
		return new Rectangle(x,y,w,h);
	}
	private void drawCursor() {
		Graphics g = layoutView.getGraphics();
		Color oldc = g.getColor();
		g.setXORMode(XORCOLOR);
		Rectangle r = this.getSelectRect();
		g.drawRect(r.x, r.y, r.width, r.height);
		g.setPaintMode();
		g.setColor(oldc);
	}
	@Override
	public Object getCurrentSelectObject() {
		// TODO Auto-generated method stub
		Rectangle r = this.getSelectRect();
		List roomList = null;
		if (dataForm == null || dataForm.getBuildingList() == null
				|| dataForm.getBuildingList().size() == 0)
			return roomList;
		URoomPlaneTemplate t = (URoomPlaneTemplate) this.elementTemplate;
		int xo, yo, i, j, x, y, size, bh;
		xo = BOUND_WIDTH;
		yo = BOUND_WIDTH;
		if (t.displayBuildingName) {
			xo += BUILDING_BOX_WIDTH;
		}
		y = yo;
		RoomBuildingDataForm bForm;
		for (i = 0; i < dataForm.getBuildingList().size(); i++) {
			bForm = dataForm.getBuildingList().get(i);
			if (bForm == null || bForm.getRoomList() == null
					|| bForm.getRoomList().size() == 0)
				continue;
			x = xo;
			size = bForm.getRoomList().size();
			for (j = 0; j < size; j++) {
				if( x > r.x && x +ROOM_BOX_WIDTH < r.x + r.width &&
						y > r.y && y +BOX_HEIGHT < r.y + r.height	) {
					if(roomList == null)
						roomList = new ArrayList();
					roomList.add(bForm.getRoomList().get(j));
				}
				x += ROOM_BOX_WIDTH;
				if ((j + 1) % t.roomColumn == 0) {
					x = xo;
					y += BOX_HEIGHT;
				}
			}
			if (size > 0 && (size % t.roomColumn == 0))
				y += BOX_HEIGHT;
		}
		return roomList;
	}

	public void doselectRoom() {
		if(!canSelectObjectEvent)
			return;
		SelectObjectEvent event = new SelectObjectEvent(this,
				this.getCurrentSelectObject());
		if (getUParent().getEventAdaptor() != null)
			getUParent().getEventAdaptor().processEvent(event,
					EventConstants.EVENT_SELECTOBJECT, "select");
	}
	public void doListSelection(ListSelectionEvent e){
		if(!canListSelectionEvent)
			return;
		if (getUParent().getEventAdaptor() == null)
			return;
		ListSelectionEvent et = new ListSelectionEvent(this,e.getFirstIndex(),e.getLastIndex(),e.getValueIsAdjusting());
		getUParent().getEventAdaptor().processEvent(et,
			EventConstants.EVENT_LISTSELECTION, "select");		
	}
	private class ListSelectionProcessor implements ListSelectionListener{

		@Override
		public void valueChanged(ListSelectionEvent e) {
			// TODO Auto-generated method stub
			doListSelection(e);
		}
		
	}
	private class ViewMouseProcessor implements MouseListener,
			MouseMotionListener {

		@Override
		public void mouseDragged(MouseEvent arg0) {
			// TODO Auto-generated method stub
			drawCursor();
			currentP = arg0.getPoint();
			drawCursor();
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub

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
			oldP = arg0.getPoint();
			currentP = oldP;
			drawCursor();
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			drawCursor();
			doselectRoom();
		}

	}

}
