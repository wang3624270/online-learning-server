package cn.edu.sdu.uims.component.list;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.common.reportdog.UFont;
import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.base.UBorder;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.base.UScrollPane;
import cn.edu.sdu.uims.component.event.UScrollPanelEventAdaptor;
import cn.edu.sdu.uims.component.menu.UMenu;
import cn.edu.sdu.uims.component.menu.UPopupMenu;
import cn.edu.sdu.uims.def.UElementTemplate;
import cn.edu.sdu.uims.def.UEventAttribute;
import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.handler.UHandlerI;
import cn.edu.sdu.uims.trans.URect;

public class UListSelect extends JPanel implements UComponentI {

	private JList jList;
	private UPanelI parent = null;
	private FilterI filter = null;
	private String componentName;
	private UScrollPanelEventAdaptor eventProcessor = null;
	private JComboBox jComboBox= null;
	private List dataList = new ArrayList();
	private boolean canAdd = false;
	protected UElementTemplate elementTemplate;

	public UListSelect(){
		jList = new JList();
		UScrollPane sp = new UScrollPane();
		sp.setViewportView(jList);
		setLayout(new BorderLayout());
		add(sp,BorderLayout.CENTER);
		jComboBox = new JComboBox();
		jComboBox.setEditable(false);
		add(jComboBox,BorderLayout.SOUTH);
		jList.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if(e.getClickCount() == 2)
					deleteItem();
			}

		});
		jComboBox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				addSelectItemToList();
			}
		});
	}
	public void deleteItem(){
		int index;
		index = jList.getSelectedIndex();
		if(index >= 0){
			dataList.remove(index);
			jList.setListData(dataList.toArray());
			jList.updateUI();
		}
	}
	public void addSelectItemToList(){
		if(!canAdd)
			return;
		if(jComboBox.getSelectedIndex()< 0)
			return;
		Object o = jComboBox.getSelectedItem();
		int i;
		for(i=0; i< dataList.size();i++){
			if(dataList.get(i)== o)
				return;
		}
		dataList.add(o);
		jList.setListData(dataList.toArray());
		jList.updateUI();
	}
	public void addEvents(UEventAttribute[] events) {
		// TODO Auto-generated method stub
		
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

	public String getComponentName() {
		// TODO Auto-generated method stub
		return componentName;
	}

	public Object getData() {
		return dataList.toArray();
	}

	public FilterI getFilter() {
		// TODO Auto-generated method stub
		return filter;
	}


	public UTemplate getTemplate() {
		// TODO Auto-generated method stub
		return null;
	}

	public UPanelI getUParent() {
		// TODO Auto-generated method stub
		return parent;
	}

	public boolean hasEmptyFileds() {
		// TODO Auto-generated method stub
		return false;
	}

	public void init() {
		// TODO Auto-generated method stub
		
	}

	public void initContents() {
		// TODO Auto-generated method stub
		
	}

	public boolean isEditable() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean requestFirstFoucus() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setAddedDatas(Object[] o) {
		// TODO Auto-generated method stub
		canAdd = false;
		jComboBox.removeAllItems();
		if (o != null) {
			Object a[] = (Object[]) o;
			for (int i = 0; i < a.length; i++) {
				jComboBox.addItem(a[i]);
			}
		}
		jComboBox.setSelectedIndex(-1);
		canAdd = true;
	}

	public void setArrangeType(int type) {
		// TODO Auto-generated method stub
		
	}

	public void setBorder(UBorder border) {
		// TODO Auto-generated method stub
		
	}

	public void setBorder(int width, UColor color) {
		// TODO Auto-generated method stub
		
	}

	public void setColor(UColor c) {
		// TODO Auto-generated method stub
		jList.setForeground(c.color);
		jComboBox.setForeground(c.color);		
	}

	public void setComponentName(String name) {
		// TODO Auto-generated method stub
		componentName = name;
	}

	public void setData(Object obj) {
		// TODO Auto-generated method stub
		Object []a  = (Object[])obj;
		dataList = new ArrayList();
		for(int i = 0;i < a.length;i++){
			dataList.add(a[i]);
		}
		jList.setListData(dataList.toArray());
		jList.updateUI();
	}

	public void setEditable(boolean b) {
		// TODO Auto-generated method stub
		jComboBox.setEditable(b);		
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
		jList.setFont(agr0.font);
		jComboBox.setFont(agr0.font);
		
	}

	public void setHandler(UHandlerI handler) {
		// TODO Auto-generated method stub
		
	}

	public void setHorizontalAlignment(int arg0) {
		// TODO Auto-generated method stub
		
	}

	public void setPopupMenu(UMenu menu) {
		// TODO Auto-generated method stub
		
	}

	public void setTemplate(UTemplate template) {
		// TODO Auto-generated method stub
		
	}

	public void setText(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void setUParent(UPanelI parent) {
		// TODO Auto-generated method stub
		this.parent = parent;
	}

	public void setVerticalAlignment(int arg0) {
		// TODO Auto-generated method stub
		
	}

	public void updateAddedDatas() {
		// TODO Auto-generated method stub
		
	}
	public void setInitComponentData(Object data){
		
	}
	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setShellBounds(int x, int y, int w, int h) {
		// TODO Auto-generated method stub
		setBounds(x, y, w, h);
//		this.setLocation(x, y);
//		this.setPreferredSize(new Dimension(w,h));
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
	public int[] getSelectedIndices() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Object getSelectedValue() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public UPopupMenu getUPopupMenu() {
		// TODO Auto-generated method stub
		return null;
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
