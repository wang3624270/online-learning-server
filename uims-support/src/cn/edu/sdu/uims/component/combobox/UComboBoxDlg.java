package cn.edu.sdu.uims.component.combobox;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.pi.ClientDataDictionaryI;
import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.common.reportdog.UFont;
import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.UimsFactory;
import cn.edu.sdu.uims.base.CheckObject;
import cn.edu.sdu.uims.base.UBorder;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.component.event.EventConstants;
import cn.edu.sdu.uims.component.list.UListCheck;
import cn.edu.sdu.uims.component.menu.UMenu;
import cn.edu.sdu.uims.component.menu.UPopupMenu;
import cn.edu.sdu.uims.def.UElementTemplate;
import cn.edu.sdu.uims.def.UEventAttribute;
import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.handler.UHandlerI;
import cn.edu.sdu.uims.trans.URect;
import cn.edu.sdu.uims.util.UimsUtils;

public class UComboBoxDlg extends JPanel implements UComponentI, UComboBoxI {
	protected boolean canActionEvent = false;
	private ActionListener actionListener = null;
	private UPanelI parent = null;
	private String componentName;
	private FilterI filter;
	protected UElementTemplate elementTemplate;
	protected boolean bEnabled = true;
	protected boolean bEditable = true;
	private ComboBoxDialog dlg;
	private JTextField field;
	private JButton open;
	private JButton clear;
	private JButton selectAll;
	private JButton ok;
	public UListCheck itemList;
	public Dimension comboBoxPreferredSize = new Dimension(1,1);
	public Dimension comboBoxSize = new Dimension(100,25);
	private class ComboBoxDialog extends JDialog implements ActionListener,WindowListener{
		public ComboBoxDialog (){
			Container c = this.getContentPane();
			c.setLayout(new BorderLayout());
			JPanel p = new JPanel();
			p.setLayout(new FlowLayout());
			p.setBorder(BorderFactory.createLineBorder(new Color(0,0,0)));
			clear = new JButton("清空");
			clear.setActionCommand("clear");
			clear.addActionListener(this);
			p.add(clear);
			selectAll = new JButton("全选");
			selectAll.setActionCommand("selectAll");
			selectAll.addActionListener(this);
			p.add(selectAll);
			ok = new JButton("返回");
			ok.setActionCommand("ok");
			ok.addActionListener(this);
			p.add(ok);
			c.add(p, BorderLayout.SOUTH);
			itemList = new UListCheck();
			itemList.initContents();
			Dimension d = new Dimension(80,200);
			itemList.setPreferredSize(d);
			itemList.setSize(d);
			c.add(itemList);
//			this.setModalityType(DEFAULT_MODALITY_TYPE);
			this.setUndecorated(true);
			this.setSize(230, 400);
			this.setLocation(100, 100);
			this.addWindowListener(this);
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String amd = e.getActionCommand();
			if(amd.equals("clear")) {
				itemList.doAll(false);
			}else if(amd.equals("selectAll")) {
				itemList.doAll(true);
			}else {
				doReturn();
			}
		}
		@Override
		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void windowClosing(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void windowClosed(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void windowIconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void windowDeiconified(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void windowActivated(WindowEvent e) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void windowDeactivated(WindowEvent e) {
			// TODO Auto-generated method stub
			doReturn();
		}
	}
	public void doReturn(){
		field.setText(getSelectedString());
		dlg.setVisible(false);
		if (actionListener != null)
				actionListener.actionPerformed(getActionEvent());
		if (canActionEvent) {
				getUParent().getEventAdaptor().actionPerformed(
								getActionEvent());
		}		
	}
	public void setEditable(boolean b){
		bEditable = b;
	}
	
	public ActionListener getActionListener() {
		return actionListener;
	}

	public void setActionListener(ActionListener actionListener) {
		this.actionListener = actionListener;
	}

	public void addInitDataList() {
		if (elementTemplate == null)
			return;
		List list = null;
		List checkList = null;
		if (elementTemplate.dictionary != null
				&& elementTemplate.dictionary.length() > 0) {
			ClientDataDictionaryI util = UimsFactory.getClientDataDictionaryI();
			if (util != null)
				list = util.getComboxListByCode(elementTemplate.dictionary);
		} else if (filter != null) {
			Object[] os = (Object[]) filter.getAddedData();
			if (os != null && os.length > 0) {
				list = new ArrayList();
				for (int i = 0; i < os.length; i++) {
					list.add(os[i]);
				}
			}
		} else if (elementTemplate.addedDatas != null) {
			list = elementTemplate.addedDatas;
		}
		if (list == null) {
			list = getCompnetDefautAddedDatasList();
		}
		if (list != null && list.size() != 0) {
			checkList = new ArrayList();
			ListOptionInfo info;
			for (int i = 0; i < list.size(); i++) {
				info = (ListOptionInfo) list.get(i);
				checkList.add(new CheckObject(false, info));
			}
		}
		itemList.setData(checkList);
		getComponentPreferredSize();
		
	}
	@Override
	public void setComboBoxSize(int w, int h) {
		// TODO Auto-generated method stub
		comboBoxSize = new Dimension(w,h);
		field.setPreferredSize(new Dimension(comboBoxSize.width-30,comboBoxSize.height-5));
		field.setSize(comboBoxSize.width-30,comboBoxSize.height-5);
		open.setPreferredSize(new Dimension(20,20));
		open.setSize(new Dimension(20,20));
		this.setPreferredSize(new Dimension(comboBoxSize.width,comboBoxSize.height));
		this.setSize(comboBoxSize.width,comboBoxSize.height);
	}
	public void getComponentPreferredSize(){
		Object []dataArray = (Object[])itemList.getData();
		if(dataArray == null || dataArray.length == 0 ) {
			comboBoxPreferredSize.width = comboBoxSize.width;
			comboBoxPreferredSize.height = comboBoxSize.height;
		}
		else {
			int c = 0, r = dataArray.length;
			CheckObject c1;
			ListOptionInfo info;
			for(int i = 0; i < dataArray.length;i++) {
				c1 = (CheckObject)dataArray[i];
				if(c1 == null || c1.value == null)
					continue;
				info = (ListOptionInfo)c1.value;
				if(info.getLabel() != null && info.getLabel().length() > c) 
					c = info.getLabel().length();
			}
			comboBoxPreferredSize.width = c * 15 + 20;
			if(r < 5) {
				comboBoxPreferredSize.height = r *25;				
			}
			else if(r < 15)
				comboBoxPreferredSize.height = r *20;
			else
				comboBoxPreferredSize.height = r *19;					
		}
		Dimension  screensize   = Toolkit.getDefaultToolkit().getScreenSize();
		if(comboBoxPreferredSize.height > screensize.height - 160)
			comboBoxPreferredSize.height = screensize.height - 160;
		int w,h;
		if(comboBoxPreferredSize.width < 200)
			w = 200;
		else
			w = comboBoxPreferredSize.width;
		h = comboBoxPreferredSize.height+28;
		dlg.setSize(w,h); 
	}
	public void visibleComboBoxDialog(){
		Point p = this.getLocationOnScreen();
		Dimension d = this.getSize();
		Dimension dd = dlg.getSize();
		Dimension  screensize   = Toolkit.getDefaultToolkit().getScreenSize();
		int px,py;
		 if(p.x + dd.width > screensize.width -5)
			 px = screensize.width -5 -dd.width;
		 else
			 px = p.x;
		 if(p.y +d.height + dd.height > screensize.height -5)
			 py = screensize.height -5 -dd.height;
		 else
			 py = p.y +d.height;
		dlg.setLocation(px,py);
		dlg.setVisible(true);
	}
	
	public void initContents() {
		// TODO Auto-generated method stub
		dlg = new ComboBoxDialog();
		setLayout(new FlowLayout());
		field = new JTextField();
		field.setEditable(false);
		this.add(field);
		open = new JButton();
		Icon icon = UimsUtils.getCSClientIcon("downarrow.png");
		open.setIcon(icon);
		open.setSelectedIcon(icon);
		open.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Object [] a = (Object [])itemList.getData();
				if(a == null || a.length == 0)
					return;
				visibleComboBoxDialog();				
			}
		});
		this.add(open);
		addInitDataList();
	}

	public String getSelectedString() {
		Object [] a = (Object [])itemList.getData();
		if(a == null || a.length == 0)
			return "";
		String str = "";
		CheckObject jcb;
		for (int i = 0; i < a.length; i++) {
			jcb = (CheckObject) a[i];
			if (jcb.bolValue) {
				str += jcb.toString() + ";";
			}
		}
		if (str.length() == 0)
			return str;
		else
			return str.substring(0, str.length() - 1);
	}

	private boolean itemSelected() {
		if (getSelectedItem() instanceof CheckObject) {
			CheckObject jcb = (CheckObject) getSelectedItem();
			jcb.bolValue = (!jcb.bolValue);
			setSelectedIndex(getSelectedIndex());
			return true;
		} else {
			return false;
		}
	}

	private ActionEvent getActionEvent() {
		return new ActionEvent(this, 0, this.getComponentName(), 0);
	}

	public void addEvents(UEventAttribute events[]) {
		int i;
		for (i = 0; i < events.length; i++) {
			if (events[i].name.equals(EventConstants.EVENT_ACTION)) {
				canActionEvent = true;
			}
		}
	}

	public URect getBoundRect() {
		// TODO Auto-generated method stub
		if (elementTemplate != null)
			return new URect(elementTemplate.x, elementTemplate.y,
					elementTemplate.w, elementTemplate.h);
		else
			return null;
	}

	public Component getAWTComponent() {
		// TODO Auto-generated method stub
		return this;
	}

	public void setBorder(int width, UColor color) {
		if (width == 0)
			setBorder((Border) null);
		else
			setBorder(BorderFactory.createLineBorder(color.color, width));
	}

	public void setBorder(UBorder border) {
		if (border.obj == null || border.obj instanceof Border)
			this.setBorder((Border) border.obj);
	}

	public Object getData() {
		ListOptionInfo t;
		Object dataArray[] = (Object [])itemList.getData();
		if(dataArray == null || dataArray.length == 0) {
			return new String[0];
		}
		List vc = new ArrayList();
		for (int i = 0; i < dataArray.length; i++) {
			CheckObject jcb = (CheckObject) dataArray[i];
			if (jcb.bolValue) {
				if (jcb.value instanceof ListOptionInfo) {
					t = (ListOptionInfo) jcb.value;
					vc.add(t.getValue());
				} else
					vc.add(jcb.value);
			}
		}
		String sa[] = new String[vc.size()];
		for (int i = 0; i < vc.size(); i++)
			sa[i] = (String) vc.get(i);
		return sa;
	}

	public void setData(Object obj) {
		int i, j;
		boolean b;
		Object []dataArray = (Object [])itemList.getData();
		if(dataArray == null || dataArray.length == 0)
			return;
		if (obj != null) {
			Object[] list = (Object[]) obj;
			for (i = 0; i < dataArray.length; i++) {
				CheckObject jcb = (CheckObject) dataArray[i];
				b = false;
				j = 0;
				while (!b && j < list.length) {
					if (list[j] instanceof ListOptionInfo
							&& list[j].equals(jcb.value))
						b = true;
					else if (list[j].equals(((ListOptionInfo) jcb.value)
							.getValue()))
						b = true;
					j++;
				}
				jcb.bolValue = b;
			}
		} else {
			for (i = 0; i < dataArray.length; i++) {
				CheckObject jcb = (CheckObject) dataArray[i];
				jcb.bolValue = false;
			}
		}
		field.setText(getSelectedString());
	}

	@Override
	public void setFilter(FilterI filter) {
		// TODO Auto-generated method stub
		this.filter = filter;
	}

	@Override
	public FilterI getFilter() {
		// TODO Auto-generated method stub
		return filter;
	}

	public void setTemplate(UTemplate template) {
		// TODO Auto-generated method stub
		this.elementTemplate = (UElementTemplate) template;
	}

	public void setEnabled(boolean b) {
		bEnabled = b;
		super.setEnabled(bEnabled);
	}

	public void setAddedDatas(List list) {

		if (list == null || list.size() == 0) {
			setAddedDatas((Object[]) null);
		} else {
			setAddedDatas(list.toArray());
		}

	}

	public void setAddedDatas(Object[] o) {
		Object co[] = null;
		if(o != null && o.length > 0) {
			co = new Object[o.length];
			ListOptionInfo info;
			for (int i = 0; i < o.length; i++) {
				info = (ListOptionInfo) o[i];
				co[i] = new CheckObject(false, info);
			}
		}
		itemList.setData(co);
		getComponentPreferredSize();
	}

	public void setComponentName(String name) {
		this.componentName = name;
	}

	public String getComponentName() {
		return componentName;
	}

	public UPanelI getUParent() {
		// TODO Auto-generated method stub
		return parent;
	}

	public void setUParent(UPanelI parent) {
		// TODO Auto-generated method stub
		this.parent = parent;
	}

	public void updateAddedDatas() {
		// TODO Auto-generated method stub
		if (filter == null)
			return;
		Object o = filter.getAddedData();
		if (o != null)
			this.setAddedDatas((Object[]) o);
		else
			this.setAddedDatas((Object[]) null);
	}

	public Object getLabel(Object value) {
		// TODO Auto-generated method stub
		return value;
	}

	public void setShellBounds(int x, int y, int w, int h) {
		// TODO Auto-generated method stub
		setBounds(x, y, w, h);
		// this.setLocation(x, y);
		// this.setPreferredSize(new Dimension(w,h));
	}

	public UElementTemplate getElementTemplate() {
		// TODO Auto-generated method stub
		return elementTemplate;
	}

	public void setElementTemplate(UElementTemplate elementTemplate) {
		// TODO Auto-generated method stub
		this.elementTemplate = elementTemplate;
	}

	public List getCompnetDefautAddedDatasList() {
		return null;
	}

	public void addItem(Object item) {
	}

	public int getSelectedIndex() {
		return 0;
	}

	public void removeAllItems() {

	}

	// ///////////////////////////// interface
	@Override
	public boolean getMultiple() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Object getSelectedItem() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItemAt(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSelectedIndex(int index) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMaximumRowCount(int c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub

	}

	@Override
	public UTemplate getTemplate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setVerticalAlignment(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setColor(UColor c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setBackground(UColor c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFont(UFont agr0) {
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
	public void setFilter1(FilterI filter) {
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
	public void setPopupMenu(UMenu menu) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setInitComponentData(Object data) {
		// TODO Auto-generated method stub

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
	public Object getSelectedValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int[] getSelectedIndices() {
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
	public void insertText(String text) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addActionListener(ActionListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHorizontalAlignment(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setText(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isEditable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSelectedText() {
		// TODO Auto-generated method stub
		return null;
	}


}
