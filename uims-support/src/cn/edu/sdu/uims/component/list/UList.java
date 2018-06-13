package cn.edu.sdu.uims.component.list;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.common.reportdog.UFont;
import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.base.UBorder;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.base.UScrollPane;
import cn.edu.sdu.uims.component.event.EventConstants;
import cn.edu.sdu.uims.component.event.UScrollPanelEventAdaptor;
import cn.edu.sdu.uims.component.menu.UMenu;
import cn.edu.sdu.uims.component.menu.UPopupMenu;
import cn.edu.sdu.uims.def.UElementTemplate;
import cn.edu.sdu.uims.def.UEventAttribute;
import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.handler.UHandlerI;
import cn.edu.sdu.uims.trans.URect;

public class UList extends UScrollPane implements UComponentI {

	protected String componentName;
	protected UPanelI parent = null;
	protected FilterI filter = null;
	protected JList jList = null;
	protected UScrollPanelEventAdaptor eventProcessor = null;
	private UPopupMenu popupMenu = null;
	private boolean enablePopupMenu = true;
	protected UElementTemplate elementTemplate;
	protected boolean mouseEventNeedSend = false;
	protected boolean listSelectionEventNeedSend = false;
	protected ListSelectionListener listSelectionListener = null;
	
	public UList() {
		jList = new JList();
		setViewportView(jList);
		MouseEventProcess pt = new MouseEventProcess();
		jList.addMouseListener(pt);
		jList.addListSelectionListener(new ListSelectionProcess());
	}
	public JList getJListObject(){
		return jList; 
	}
	public Object getSelectedValues() {
		return jList.getSelectedValues();
	}
	
	public int[] getSelectedIndices(){
		return jList.getSelectedIndices();
	}

	public Object getSelectedValue() {
		return jList.getSelectedValue();
	}
	
	public void removeSelectionInterval(int index0,
            int index1){
		jList.removeSelectionInterval(index0, index1);
		this.updateUI();
		
	}

	public URect getBoundRect() {
		// TODO Auto-generated method stub
		if(elementTemplate != null)
			return new URect(elementTemplate.x,elementTemplate.y,elementTemplate.w, elementTemplate.h);
		else
			return null;
	}

	public Component getAWTComponent() {
		// TODO Auto-generated method stub
		return this;
	}

	public Object getData() {
		// TODO Auto-generated method stub
		return this.jList.getSelectedValue();
	}

	public UTemplate getTemplate() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean hasEmptyFileds() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isEditable() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean requestFirstFoucus() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setArrangeType(int type) {
		// TODO Auto-generated method stub

	}

	public void setBorder(UBorder border) {
		// TODO Auto-generated method stub
		if (border.obj == null || border.obj instanceof Border)
			this.setBorder((Border) border.obj);
	}

	public void setBorder(int width, UColor color) {
		// TODO Auto-generated method stub
		if (width == 0)
			setBorder((Border) null);
		else
			setBorder(BorderFactory.createLineBorder(color.color, width));
	}

	public void setColor(UColor c) {
		// TODO Auto-generated method stub
		this.setForeground(c.color);
	}

	public void setData(Object obj) {
		// TODO Auto-generated method stub

	}

	public void setEditable(boolean b) {
		// TODO Auto-generated method stub

	}

	public void setFilter(FilterI filter) {
		// TODO Auto-generated method stub
		this.filter = filter;
	}

	public void setFilter1(FilterI filter) {
		// TODO Auto-generated method stub

	}

	public void setFont(UFont agr0) {
		// TODO Auto-generated method stub
		setFont(agr0.font);
	}

	public void setHorizontalAlignment(int arg0) {
		// TODO Auto-generated method stub

	}

	public void setTemplate(UTemplate template) {
		// TODO Auto-generated method stub

	}

	public void setText(String arg0) {
		// TODO Auto-generated method stub

	}

	public void setVerticalAlignment(int arg0) {
		// TODO Auto-generated method stub

	}

	public void addEvents(UEventAttribute events[]) {
		int i;
		eventProcessor = new UScrollPanelEventAdaptor(this, getUParent()
				.getEventAdaptor());				
		for (i = 0; i < events.length; i++) {
			if (events[i].name.equals(EventConstants.EVENT_LISTSELECTION)) {
//				jList.addListSelectionListener(eventProcessor);
				this.listSelectionEventNeedSend = true;
			} else if (events[i].name.equals(EventConstants.EVENT_MOUSE)) {
				mouseEventNeedSend = true;
			}
		}
	}


	public void setHandler(UHandlerI handler) {
		// TODO Auto-generated method stub

	}

	public void setComponentName(String name) {
		this.componentName = name;
	}

	public String getComponentName() {
		return componentName;
	}

	public void init() {
		// TODO Auto-generated method stub

	}

	public void setAddedDatas(Object a[]) {
		if (a == null)
			jList.setListData(new ArrayList().toArray());
		else {
			jList.setListData(a);
		}
		this.updateUI();
	}

	public UPanelI getUParent() {
		// TODO Auto-generated method stub
		return parent;
	}

	public void setUParent(UPanelI parent) {
		// TODO Auto-generated method stub
		this.parent = parent;
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

	public FilterI getFilter() {
		// TODO Auto-generated method stub
		return filter;
	}
	public void updateAddedDatas(Object o) {
		// TODO Auto-generated method stub
		if (o == null)
			this.jList.setListData(new ArrayList().toArray());
		else {
			Object a[] = (Object[]) o;
			this.jList.setListData(a);
		}
		this.updateUI();
	}

	public void updateAddedDatas() {
		// TODO Auto-generated method stub
		if (filter == null)
			return;
		Object o = filter.getAddedData();
		updateAddedDatas(o);
	}

	public void initContents() {
		// TODO Auto-generated method stub
	}
	public void setInitComponentData(Object data){
		
	}
	
	protected void processValueChanged(ListSelectionEvent arg0){
		
	}
	protected void clearSelectedItem(ListSelectionEvent arg0){
		
	}
	class ListSelectionProcess implements ListSelectionListener{

		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			// TODO Auto-generated method stub
			processValueChanged(arg0);
			if(eventProcessor != null && listSelectionEventNeedSend)
				eventProcessor.valueChanged(arg0);
			if(listSelectionListener != null)
				listSelectionListener.valueChanged(arg0);
			clearSelectedItem(arg0);
		}
		
	}
	class MouseEventProcess implements MouseListener {
		public void mouseClicked(MouseEvent mouseEvent) {
			if (mouseEvent.getButton() == MouseEvent.BUTTON3) {
				if(enablePopupMenu) {
					displyPopMenu((Component) mouseEvent.getSource(), mouseEvent
							.getX(), mouseEvent.getY());
				}
			}else {
				if(eventProcessor != null && mouseEventNeedSend)
					eventProcessor.mouseClicked(mouseEvent);
			}
		}

		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}
	}
	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setShellBounds(int x, int y, int w, int h) {
		// TODO Auto-generated method stub
		setBounds(x, y, w, h);
//		this.setLocation(x, y);
		this.setPreferredSize(new Dimension(w,h));
	}
	public void resetShellBounds(int x, int y, int w, int h) {
		// TODO Auto-generated method stub
		
	}
	public void onClose(){
		
	}
	public void repaintComponent() {
	}
	public void setParameters(HashMap paras){
		
	}
	public HashMap getParameters(){
		return null;
	}
	public UElementTemplate getElementTemplate() {
		// TODO Auto-generated method stub
		return elementTemplate;
	}



	public void setElementTemplate(UElementTemplate elementTemplate) {
		// TODO Auto-generated method stub
		this.elementTemplate = elementTemplate;
	}
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}
	public String getdisplayText() {
		// TODO Auto-generated method stub
		return null;
	}


	public void setdisplayText(String text) {
		// TODO Auto-generated method stub
		
	}
	public String getActionComandString() {
		// TODO Auto-generated method stub
		return null;
	}


	public void setActionComandString(String str) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void processDispControlAfterInited() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void sendDataToForm(UFormI form) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Object[] getAddedInnerTextFiledValues() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void clearAddedInnerTextFiled() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setScreenOwner(UComponentI screenOwner) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public UComponentI getSubComponent(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setUserData(Object obj) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setEnablePopupMenu(boolean enable) {
		// TODO Auto-generated method stub
		enablePopupMenu = enable;
	}
	@Override
	public void setLabel(String label) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Object getCurrentSelectObject() {
		// TODO Auto-generated method stub
		return this.getSelectedValue();
	}
	public ListSelectionListener getListSelectionListener() {
		return listSelectionListener;
	}
	public void setListSelectionListener(ListSelectionListener listSelectionListener) {
		this.listSelectionListener = listSelectionListener;
	}
	@Override
	public void setBackground(UColor c) {
		// TODO Auto-generated method stub
	}
	@Override
	public String getSelectedText() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void insertText(String text) {
		// TODO Auto-generated method stub
		
	}


}
