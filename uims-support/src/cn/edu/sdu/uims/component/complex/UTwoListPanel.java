package cn.edu.sdu.uims.component.complex;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;

import cn.edu.sdu.common.form.USFormI;
import cn.edu.sdu.uims.component.event.EventConstants;
import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.def.UEventAttribute;

public class UTwoListPanel extends UComplexPanel {

	private JButton leftToRightButton;
	private JButton rightToLeftButton;
	private JList leftList;
	private JList rightList;
	private JScrollPane leftScrollPane;
	private JScrollPane rightScrollPane;
	private List dataList = null;
	private Object [] moveObjects;
	private boolean actionEventNeedSend = false;
	private TowListInnerEventProcesser eventProcessor = new TowListInnerEventProcesser();
	@Override
	public void initContents() {
		// TODO Auto-generated method stub
		this.setLayout(null);
		int width = 400;
		int height = 200;
		int dw = 5;
		int mw = 60;
		int mh = 30;
		if(elementTemplate != null) {
			width = elementTemplate.w;
			height = elementTemplate.h;
		}
		int lrw = (width - mw)/2-2*dw;
		int x,y;
		rightToLeftButton = new JButton("《《");
		x = lrw+ 2*dw;
		y = (((height-2*dw)/2 -mh)/2);
		rightToLeftButton.setBounds(x,y, mw,mh);
		
		leftToRightButton = new JButton("》》");
		y +=   height/2;
		leftToRightButton.setBounds(x,y, mw,mh);
		
		leftList = new JList();
		leftScrollPane = new JScrollPane();
		leftScrollPane.setViewportView(leftList);
		leftScrollPane.getVerticalScrollBar().setPreferredSize(
				new Dimension(UConstants.SCROLL_WIDTH, UConstants.SCROLL_WIDTH));
		leftScrollPane.getHorizontalScrollBar().setPreferredSize(
				new Dimension(UConstants.SCROLL_WIDTH, UConstants.SCROLL_WIDTH));
		leftScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		leftScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		x= dw;
		y = dw;
		leftScrollPane.setBounds(x,y,lrw, height-2*dw);
		
		rightList = new JList();
		rightScrollPane = new JScrollPane();
		rightScrollPane.setViewportView(rightList);
		rightScrollPane.getVerticalScrollBar().setPreferredSize(
				new Dimension(UConstants.SCROLL_WIDTH, UConstants.SCROLL_WIDTH));
		rightScrollPane.getHorizontalScrollBar().setPreferredSize(
				new Dimension(UConstants.SCROLL_WIDTH, UConstants.SCROLL_WIDTH));
		rightScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		rightScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		x= width-dw-lrw;
		y = dw;
		rightScrollPane.setBounds(x,y,lrw, height-2*dw);
		
		this.add(leftToRightButton);
		this.add(leftScrollPane);
		this.add(rightScrollPane);
		this.add(rightToLeftButton);
		leftList.addMouseListener(eventProcessor);
		rightList.addMouseListener(eventProcessor);
		rightToLeftButton.addActionListener(eventProcessor);
		leftToRightButton.addActionListener(eventProcessor);


	}	
	public void addEvents(UEventAttribute events[]) {
		int i;
		for (i = 0; i < events.length; i++) {
			if (events[i].name.equals(EventConstants.EVENT_ACTION)) {
				actionEventNeedSend = true;
			}
		}
	}

	
	public Object getData() {
		return dataList;
	}

	public void setData(Object obj) {
		if(obj instanceof List) {
			dataList = (List)obj;
			setDataList();
		}
	}

	public void setDataList() {		
		setListData(leftList,true);
		setListData(rightList,false);
		leftScrollPane.updateUI();
		rightScrollPane.updateUI();

	}
	public void setListData(JList jList, boolean status){
		List list = this.getStatusList(status);
		if(list == null || list.size()==0)
			jList.setListData(new ArrayList().toArray());
		else
			jList.setListData(list.toArray());
		
	}
	public List getStatusList(boolean b) {
		if(dataList == null || dataList.size() == 0)
			return null;
		List list = new ArrayList();
		Object o;
		USFormI f;
		for (int i = 0; i < dataList.size(); i++) {
			o = dataList.get(i);
			if(o == null || !(o instanceof USFormI))
				continue; 
				f = (USFormI)o;
				if (f.getSelect().booleanValue() == b)
					list.add(o);
		}
		return list;
	}
	public void moveSelectListObjects(Object [] o, boolean status) {
		int i;
		USFormI f;
		if(o == null || o.length == 0)
			return;
		moveObjects = o;
		for(i = 0; i < o.length;i++) {
			f = (USFormI) o[i];
			f.setSelect(status);
		}
		setDataList();
		if(actionEventNeedSend) {
			String name = componentName;
			if(status)
				name += "True";
			else
				name += "False";
			ActionEvent e= new ActionEvent(this,0,name);
			this.getUParent().getEventAdaptor().actionPerformed(e);
		}
	}
	private class TowListInnerEventProcesser implements MouseListener, ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int i;
			if(e.getSource() == rightToLeftButton) {
				Object o[] = rightList.getSelectedValues();
				moveSelectListObjects(o,true);
			}else if(e.getSource() == leftToRightButton){
				Object o[] = leftList.getSelectedValues();
				moveSelectListObjects(o, false);
			}
		}
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			if(e.getClickCount()== 2) {
				if(e.getSource()== leftList) {
					Object o[] = leftList.getSelectedValues();
					moveSelectListObjects(o, false);
				}else if(e.getSource() == rightList) {
					Object o[] = rightList.getSelectedValues();
					moveSelectListObjects(o,true);
				}
			}
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
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		
	}
	public Object[] getMoveObjects() {
		return moveObjects;
	}
	public void setMoveObjects(Object[] moveObjects) {
		this.moveObjects = moveObjects;
	}
}
