package cn.edu.sdu.uims.component.textfield;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

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
import cn.edu.sdu.uims.component.menu.UMenu;
import cn.edu.sdu.uims.component.menu.UPopupMenu;
import cn.edu.sdu.uims.def.UElementTemplate;
import cn.edu.sdu.uims.def.UEventAttribute;
import cn.edu.sdu.uims.def.UTextFieldTemplate;
import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.handler.UHandlerI;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.trans.URect;

public class UTextFieldWithSelectedList extends JPanel implements ActionListener, UComponentI {

	protected String componentName;
	protected UPanelI parent = null;
	protected UTextFieldTemplate template;
	protected UElementTemplate elementTemplate;
	private FilterI filter;

	private final JTextField textField = new JTextField();

	private final JButton selectButton = new JButton();

	private List itemList;// 选项列表

	private String title;// 标题

	private Object value;// 默认的值

	public void initContents() {
	}
	
	public UTextFieldWithSelectedList() {
		super();
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		textField.setEditable(false);
		add(textField);

		add(selectButton);
		selectButton.setActionCommand("select");
		selectButton.addActionListener(this);
		selectButton.setText("select");
		selectButton.setPreferredSize(new Dimension(20, 30));
		//

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String cmd = selectButton.getActionCommand();
		if (cmd.equals("select")) {
			if(itemList == null || itemList.size() == 0)
				return;
			SelectItemDialog dialog = null;
			dialog = new SelectItemDialog((Frame)null, title,itemList);
			dialog.setBounds(300, 200, 300, 400);
			dialog.setVisible(true);
			Object obj = dialog.getSelectedObject();
			if (obj != null) {
				setValue(obj);
			}
		}
	}
	//add by gyj
	public Object startDilog()
	{
		SelectItemDialog dialog = null;
		if(parent instanceof Frame)
			dialog = new SelectItemDialog((Frame)parent, title,itemList);
		else
			dialog = new SelectItemDialog((Dialog)parent, title,itemList);
		dialog.setBounds(300, 200, 300, 400);
		dialog.setVisible(true);
		Object obj = dialog.getSelectedObject();
		return obj;

	}

	/**
	 * 设置显示的值
	 * 
	 * @param value
	 */
	public void setValue(Object value) {
		this.value = value;
		if (value != null) {
			this.textField.setText(value.toString());
		} else {
			this.textField.setText("");
		}
	}

	/**
	 * 获取value
	 * 
	 * @return
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * 设置textField是否可编辑
	 * 
	 * @param isEditable
	 */
	public void setEditable(boolean isEditable) {
		textField.setEditable(isEditable);
	}

/**
 * 选项列表dialog
 * 
 * @author Administrator
 * 
 */
	
 class SelectItemDialog extends JDialog implements MouseListener {

	private DefaultListModel listModel;
	
	private List dataList;

	private JList itemJList;

	private Object selectedObj;

	private JPanel queryPanel=new JPanel();
	
	private JTextField field=new JTextField();
	
	private JButton queryButton=new JButton();
		
	public SelectItemDialog(Frame parent, String title, List itemList) {
		super(parent, title, true);
		initDialog(title,itemList);
	}
	public SelectItemDialog(Dialog parent, String title, List itemList) {
		super(parent, title, true);
		initDialog(title,itemList);
	}
	
	public void initDialog(String title, List itemList) {
		queryPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		queryPanel.add(field);
		queryPanel.add(queryButton);
		
		field.setPreferredSize(new Dimension(200,20));
		queryButton.setPreferredSize(new Dimension(80,20));
		queryButton.setText("查询");
		queryButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				queryData();
			}
				
		});
		field.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				queryData();
			}
				
		});

		this.setLayout(new BorderLayout());
		
		this.dataList=itemList;
		if (itemList != null) {
			listModel = new DefaultListModel();
			Iterator iterator = itemList.iterator();
			while (iterator.hasNext()) {
				listModel.addElement(iterator.next());
			}
			itemJList = new JList(listModel);
		}
		itemJList.addMouseListener(this);
		
		this.getContentPane().add(this.queryPanel,BorderLayout.NORTH);
		this.getContentPane().add(new UScrollPane(itemJList),BorderLayout.CENTER);
		
	}
	public void queryData(){
		listModel.removeAllElements();
		String queryStr=field.getText().trim();
		if(dataList!=null){
			Iterator iterator=dataList.iterator();
			while(iterator.hasNext()){
				Object obj=iterator.next();
				if(matchStr(obj.toString(),queryStr)){
					listModel.addElement(obj);
				}
			}
		}
	}
	public Object getSelectedObject() {
		return selectedObj;
	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == itemJList && e.getClickCount() == 2) {
			selectedObj = itemJList.getSelectedValue();
			this.dispose();
		}
	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public boolean matchStr(String fatherStr, String subStr) {
		if (fatherStr != null && subStr != null) {
			if (fatherStr.indexOf(subStr) < fatherStr.length()
					&& fatherStr.indexOf(subStr) >= 0) {
				return true;
			}
		}
		return false;
	}
 }
	@Override
	public void setData(Object obj) {
		if(obj != null && itemList != null && itemList.size() >= 0) {
			String value = obj.toString();
			ListOptionInfo info;
			for(int i = 0;i < itemList.size();i++) {
				info = (ListOptionInfo)itemList.get(i);
				if(info.getValue().equals(value)) {
					setValue(info);
					return;
				}
			}
		}
		setValue(null);
	}

	@Override
	public Object getData() {
		// TODO Auto-generated method stub
		if(this.getValue() == null)
			return null;
		else {
			ListOptionInfo info = (ListOptionInfo)this.getValue();
			return info.getValue();
		}
	}

	
	@Override
	public void updateAddedDatas() {
		// TODO Auto-generated method stub
		if (filter == null) 
			return;
		Object[] os = (Object[]) filter.getAddedData();
		if (os != null && os.length > 0) {
			itemList = new ArrayList();
			for (int i = 0; i < os.length; i++) {
				itemList.add(os[i]);
			}
		}	
	}
	
		
	public FilterI getFilter() {
		return filter;
	}

	public void setFilter(FilterI filter) {
		this.filter = filter;
	}
	public void setBorder(int width, UColor color) {
		if (width == 0)
			setBorder((Border) null);
		else
			setBorder(BorderFactory.createLineBorder(color.color, width));
	}

	public void setBorder(UBorder border) {
		// TODO Auto-generated method stub
		if(border.obj == null)
			this.setBorder((Border) border.obj);
		else
			setBorder((int) border.width, UFactory.getModelSession().getColorByName(border.colorName));
	}


	
	public void setTemplate(UTemplate template) {
		// TODO Auto-generated method stub
		this.template = (UTextFieldTemplate) template;

	}

	public void setColor(UColor c) {
		// TODO Auto-generated method stub
		this.setForeground(c.color);

	}

	public Component getAWTComponent() {
		// TODO Auto-generated method stub
		return this;
	}

	public void setFont(UFont agr0) {
		super.setFont(agr0.font);
	}

	public URect getBoundRect() {
		// TODO Auto-generated method stub
		if(elementTemplate != null)
			return new URect(elementTemplate.x,elementTemplate.y,elementTemplate.w, elementTemplate.h);
		else
			return null;
	}
	
	public UTemplate getTemplate() {
		// TODO Auto-generated method stub
		return template;
	}

	public void addEvents(UEventAttribute[] events) {
		int i;

		for (i = 0; i < events.length; i++) {
			if (events[i].name.equals(EventConstants.EVENT_ACTION)) {
//				this.addActionListener(getUParent().getEventAdaptor());
			} else if(events[i].name.equals(EventConstants.EVENT_FOCUS)) {
				this.addFocusListener(getUParent().getEventAdaptor());
			}else if (events[i].name.equals(EventConstants.EVENT_MOUSE)) {
				this.addMouseListener(getUParent().getEventAdaptor());
			}
			else if(events[i].name.equals(EventConstants.EVENT_CHANGE)){
			}

		}
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
	public UElementTemplate getElementTemplate() {
		// TODO Auto-generated method stub
		return elementTemplate;
	}
	public void setElementTemplate(UElementTemplate elementTemplate) {
		// TODO Auto-generated method stub
		this.elementTemplate = elementTemplate;
	}

	@Override
	public void setBackground(UColor c) {
		// TODO Auto-generated method stub
		if(c != null)
			this.setBackground(c.color);			
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setShellBounds(int x, int y, int w, int h) {
		// TODO Auto-generated method stub
		
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
	public void setFilter1(FilterI filter) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isEditable() {
		// TODO Auto-generated method stub
		return false;
	}



	@Override
	public void setArrangeType(int type) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void setAddedDatas(Object[] obj) {
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
	public String getSelectedText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void insertText(String text) {
		// TODO Auto-generated method stub
		
	}


}
