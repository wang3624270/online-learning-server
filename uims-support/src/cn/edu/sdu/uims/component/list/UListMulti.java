package cn.edu.sdu.uims.component.list;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.common.reportdog.UFont;
import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.base.UBorder;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.base.UScrollPane;
import cn.edu.sdu.uims.component.event.EventConstants;
import cn.edu.sdu.uims.component.event.UEventListener;
import cn.edu.sdu.uims.component.menu.UMenu;
import cn.edu.sdu.uims.component.menu.UPopupMenu;
import cn.edu.sdu.uims.def.UElementTemplate;
import cn.edu.sdu.uims.def.UEventAttribute;
import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.handler.UHandlerI;
import cn.edu.sdu.uims.trans.URect;
import cn.edu.sdu.uims.util.UimsUtils;

public class UListMulti extends JPanel implements UComponentI{

	protected String componentName;
	protected UPanelI parent = null;
	protected FilterI filter= null;
	protected JList jList = null;
	private UPopupMenu popupMenu = null;
	protected UElementTemplate elementTemplate;
	protected boolean mouseEventNeedSend = false;
	protected boolean actionEventNeedSend = false;
	protected boolean listSelectEventNeedSend = false;
	protected JComboBox optionComboBox;
	protected UScrollPane scrollPane;
	private List dataList;
	private boolean enablePopupMenu = true;

	public UListMulti() {
		ListMultiEventProcess pt = new ListMultiEventProcess();
		this.setLayout(new BorderLayout());
		optionComboBox = new JComboBox();
		optionComboBox.addActionListener(pt);
		this.add(optionComboBox, BorderLayout.NORTH);
		scrollPane = new UScrollPane();
		jList = new JList();
		scrollPane.setViewportView(jList);
		jList.addMouseListener(pt);
		jList.addListSelectionListener(pt);
		this.add(scrollPane,BorderLayout.CENTER);
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
	public Object getItemByIndex(int index) {
		if(dataList == null)
			return null;
		if(index < 0 || index >=dataList.size())
			return null;
		return dataList.get(index);
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


	public void setComponentName(String name) {
		this.componentName = name;
	}

	public String getComponentName() {
		return componentName;
	}
	
	public UElementTemplate getElementTemplate() {
		// TODO Auto-generated method stub
		return elementTemplate;
	}


	@Override
	public UPanelI getUParent() {
		// TODO Auto-generated method stub
		return parent;
	}

	@Override
	public void setUParent(UPanelI parent) {
		// TODO Auto-generated method stub
		this.parent = parent;
	}



	public void setElementTemplate(UElementTemplate elementTemplate) {
		// TODO Auto-generated method stub
		this.elementTemplate = elementTemplate;
	}
	
	public void setShellBounds(int x, int y, int w, int h) {
		// TODO Auto-generated method stub
		setBounds(x, y, w, h);
//		this.setLocation(x, y);
//		this.setPreferredSize(new Dimension(w,h));
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

	public void updateAddedDatas() {
		// TODO Auto-generated method stub
		if (filter == null)
			return;
		Object o = filter.getAddedData();
		setAddedDatas((Object[])o);
	}
	public void setAddedDatas(Object[] o) {
		optionComboBox.removeAllItems();
		if (o == null ||  o.length == 0) {
			this.setEnabled(false);
		}
		else {
			if(elementTemplate != null && elementTemplate.addSelectItem) {
				optionComboBox.addItem(UimsUtils.getPleaseSelectInfo());
			}
			for (int i = 0; i < o.length; i++) {
				optionComboBox.addItem(o[i]);
			}
			if(o.length <= 1)
				optionComboBox.setEnabled(false);
			else
				optionComboBox.setEnabled(true);
		}
		
	}
	public void setFont(UFont agr0) {
		// TODO Auto-generated method stub
		setFont(agr0.font);
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
	public void addEvents(UEventAttribute events[]) {
		int i;
		for (i = 0; i < events.length; i++) {
			if (events[i].name.equals(EventConstants.EVENT_LISTSELECTION)) {
				 listSelectEventNeedSend = true;
			} else if (events[i].name.equals(EventConstants.EVENT_ACTION)) {
				actionEventNeedSend = true;
			} else if (events[i].name.equals(EventConstants.EVENT_MOUSE)) {
				mouseEventNeedSend = true;
			}
		}
	}
	public Object getData() {
		return dataList;
	}

	public void setData(Object obj) {
		if(obj instanceof List) {
			dataList = (List)obj;
			if(dataList == null || dataList.size()==0)
				jList.setListData(new ArrayList().toArray());
			else
				jList.setListData(dataList.toArray());
			scrollPane.updateUI();
		}
	}
	private UEventListener getEventProcessor(){
		return this.getUParent().getEventAdaptor();
	}
	private Object getThisObject(){
		return this;
	}
	class ListMultiEventProcess implements MouseListener,ActionListener,ListSelectionListener {
		
		public void mouseClicked(MouseEvent mouseEvent) {
			if (mouseEvent.getButton() == MouseEvent.BUTTON3) {

				if(enablePopupMenu)
					displyPopMenu((Component) mouseEvent.getSource(), mouseEvent
						.getX(), mouseEvent.getY());
			}else {
				if (!mouseEventNeedSend)
					return;
				MouseEvent me = (MouseEvent)mouseEvent;	
				MouseEvent mm = new MouseEvent((Component)getThisObject(),me.getID(), me.getWhen(),me.getModifiers(), me.getX(),me.getY(),me.getClickCount(),me.isPopupTrigger(),me.getButton());
				getEventProcessor().mouseClicked(mm);
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

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if (!actionEventNeedSend)
				return;
			ListOptionInfo info = (ListOptionInfo)optionComboBox.getSelectedItem();
			if(info == null)
				return;
			
			ActionEvent e = new ActionEvent(getThisObject(), 0, info.getValue());
			getEventProcessor().actionPerformed(e);
		}

		@Override
		public void valueChanged(ListSelectionEvent le) {
			// TODO Auto-generated method stub
			if (!listSelectEventNeedSend)
				return;
			Object info = jList.getSelectedValue();
			if(info == null)
				return;
			ListSelectionEvent re = new ListSelectionEvent(getThisObject(),le.getFirstIndex(), le.getLastIndex(),le.getValueIsAdjusting());
			getEventProcessor().valueChanged(re);
		}
	}
	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void setTemplate(UTemplate template) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public UTemplate getTemplate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setHorizontalAlignment(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setVerticalAlignment(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setText(String arg0) {
		// TODO Auto-generated method stub
		
	}





	@Override
	public boolean requestFirstFoucus() {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public boolean hasEmptyFileds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setFilter(FilterI filter) {
		// TODO Auto-generated method stub
		this.filter = filter;		
	}


	@Override
	public void setFilter1(FilterI filter) {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean isEditable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setEditable(boolean b) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setArrangeType(int type) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void setHandler(UHandlerI handler) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void initContents() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setInitComponentData(Object data) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void resetShellBounds(int x, int y, int w, int h) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void repaintComponent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setParameters(HashMap paras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HashMap getParameters() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void setdisplayText(String text) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getdisplayText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getActionComandString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
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
		return null;
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
