package cn.edu.sdu.uims.component.textfield;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.Date;
import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.common.reportdog.UFont;
import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.common.util.DateTimeTool;
import cn.edu.sdu.uims.base.UBorder;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.component.menu.UMenu;
import cn.edu.sdu.uims.component.menu.UPopupMenu;
import cn.edu.sdu.uims.def.UElementTemplate;
import cn.edu.sdu.uims.def.UEventAttribute;
import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.handler.UHandlerI;
import cn.edu.sdu.uims.trans.URect;
import cn.edu.sdu.uims.util.UimsUtils;

public class UTextFiledDateYearMonth extends JPanel implements UComponentI {

	protected String componentName;
	protected UPanelI parent = null;
	protected UElementTemplate elementTemplate;
	private JTextField input;
	private JToggleButton yearMinButton;
	private JToggleButton monthMinButton;
	private JToggleButton monthAddButton;
	private JToggleButton yearAddButton;
	private DateChangeProcessor dateChangeProcessor = new DateChangeProcessor();
	@Override
	public void addEvents(UEventAttribute[] events) {
		// TODO Auto-generated method stub

	}

	@Override
	public Component getAWTComponent() {
		// TODO Auto-generated method stub
		return this;
	}

	@Override
	public String getActionComandString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URect getBoundRect() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getComponentName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getData() {
		// TODO Auto-generated method stub
		return DateTimeTool.getDateByString(input.getText()+"-01");
	}

	@Override
	public UElementTemplate getElementTemplate() {
		// TODO Auto-generated method stub
		return elementTemplate;
	}

	@Override
	public FilterI getFilter() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HashMap getParameters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UTemplate getTemplate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return input.getText();
	}

	@Override
	public UPanelI getUParent() {
		// TODO Auto-generated method stub
		return parent;
	}

	@Override
	public String getdisplayText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasEmptyFileds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}


	@Override
	public boolean isEditable() {
		// TODO Auto-generated method stub
		return false;
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
	public boolean requestFirstFoucus() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void resetShellBounds(int x, int y, int w, int h) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setActionComandString(String str) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setAddedDatas(Object[] obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setArrangeType(int type) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setBorder(UBorder border) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setBorder(int width, UColor color) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setColor(UColor c) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setComponentName(String name) {
		// TODO Auto-generated method stub
		this.componentName = name;
	}

	@Override
	public void setData(Object obj) {
		// TODO Auto-generated method stub
		if (obj != null) {
			Date d= (Date)obj;
			String str =DateTimeTool.getDate(d.getTime());
			input.setText(str);
		}
		else
			setText(this.getDefaultDateString());

	}

	@Override
	public void setEditable(boolean b) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setElementTemplate(UElementTemplate elementTemplate) {
		// TODO Auto-generated method stub
		this.elementTemplate = elementTemplate;
	}

	@Override
	public void setFilter(FilterI filter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFilter1(FilterI filter) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFont(UFont agr0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setHandler(UHandlerI handler) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setHorizontalAlignment(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setInitComponentData(Object data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setParameters(HashMap paras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPopupMenu(UMenu menu) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setShellBounds(int x, int y, int w, int h) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setTemplate(UTemplate template) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setText(String arg0) {
		// TODO Auto-generated method stub
		input.setText(arg0);
	}

	@Override
	public void setUParent(UPanelI parent) {
		// TODO Auto-generated method stub
		this.parent = parent;
	}

	@Override
	public void setVerticalAlignment(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setdisplayText(String text) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateAddedDatas() {
		// TODO Auto-generated method stub

	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub

	}
	@Override
	public void initContents() {
		// TODO Auto-generated method stub
		int width = elementTemplate.w;
		int height = elementTemplate.h;
		this.setLayout(null);
		Icon icon = null;

		yearMinButton = new JToggleButton(); //"<<"
		icon = UimsUtils.getIconByFileName("/icon/leftleftshift.gif");
		yearMinButton.setIcon(icon);
		yearMinButton.addActionListener(dateChangeProcessor);
		yearMinButton.setBounds(0, 0, height, height);
		this.add(yearMinButton);
		
		monthMinButton = new JToggleButton(); //"<"
		icon = UimsUtils.getIconByFileName("/icon/leftshift.gif");
		monthMinButton.setIcon(icon);
		monthMinButton.addActionListener(dateChangeProcessor);
		monthMinButton.setBounds(height, 0, height, height);
		this.add(monthMinButton);

		input = new JTextField(getDefaultDateString());
		input.setDocument(new DateDocument());
		input.setBounds(2*height, 0, width-4*height, height);
		this.add(input);

		

		monthAddButton = new JToggleButton(); //">"
		icon = UimsUtils.getIconByFileName("/icon/rightshift.gif");
		monthAddButton.setIcon(icon);
		monthAddButton.addActionListener(dateChangeProcessor);
		monthAddButton.setBounds(width-2*height, 0, height, height);
		this.add(monthAddButton);

		yearAddButton = new JToggleButton(); //">>"
		icon = UimsUtils.getIconByFileName("/icon/rightrightshift.gif");
		yearAddButton.setIcon(icon);
		yearAddButton.addActionListener(dateChangeProcessor);
		yearAddButton.setBounds(width-height, 0, height, height);
		this.add(yearAddButton);
	}
	public String getDefaultDateString(){
		String defaultValue = DateTimeTool.getNowTimeStr("yyyy-MM-dd");
		return defaultValue.substring(0,7);
	}
	public void addYear(){
		String date = input.getText();
		int year = Integer.parseInt(date.substring(0,4));
		date = (year+1) + date.substring(4,7);
		input.setText(date);
	}
	public void minYear(){
		String date = input.getText();
		int year = Integer.parseInt(date.substring(0,4));
		date = (year-1) + date.substring(4,7);
		input.setText(date);
	}
	public void addMonth(){
		String date = input.getText();
		int year = Integer.parseInt(date.substring(0,4));
		if(date.charAt(5)=='1') {
			if(date.charAt(6) == '2') {
				date = (year+1) + "-01";
			}
			else {
				date = (year) + "-1" + (date.charAt(6)-'0'+1);
			}
		}
		else {
			if(date.charAt(6)=='9') {
				date = (year) + "-10" ;				
			}
			else {
				date = (year) + "-0" + (date.charAt(6)-'0'+1);				
			}
		}
		input.setText(date);
	}
	public void minMonth(){
		String date = input.getText();
		int year = Integer.parseInt(date.substring(0,4));
		if(date.charAt(5)=='1') {
			if(date.charAt(6) == '0') {
				date = (year) + "-09";
			}
			else {
				date = (year) + "-1" + (date.charAt(6)-'0'-1);
			}
		}
		else {
			if(date.charAt(6)=='1') {
				date = (year-1) + "-12" ;				
			}
			else {
				date = (year) + "-0" + (date.charAt(6)-'0'-1);				
			}
		}
		input.setText(date);
	}
	public class DateChangeProcessor implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource() == yearMinButton)
				minYear();
			else if(e.getSource() == yearAddButton)
				addYear();
			else if(e.getSource() == monthMinButton)
				minMonth();
			else if(e.getSource() == monthAddButton)
				addMonth();
		}		
	}
	public class DateDocument extends PlainDocument {
		public void insertString(int offs, String old, AttributeSet a) throws BadLocationException {
//			int length = this.getLength();
//			String str = this.getText(0,length);
//			str = str.substring(0,offs)+ str.substring(offs+1,length);
			super.insertString(offs, old, a);
		}
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
