package cn.edu.sdu.uims.component.textfield;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.common.reportdog.UFont;
import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.base.UBorder;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.component.LabeValueTransI;
import cn.edu.sdu.uims.component.event.EventConstants;
import cn.edu.sdu.uims.component.menu.UMenu;
import cn.edu.sdu.uims.component.menu.UPopupMenu;
import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.def.UElementTemplate;
import cn.edu.sdu.uims.def.UEventAttribute;
import cn.edu.sdu.uims.def.UTextFieldTemplate;
import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.handler.UHandlerI;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.trans.URect;

public class ULabelTextField extends JPanel implements LabeValueTransI,
		ActionListener, UComponentI {
	protected ActionListener containerActionListener = null;
	protected String componentName;
	protected UPanelI parent = null;
	protected UTextFieldTemplate template;
	protected UElementTemplate elementTemplate;
	DocumentListener documnetListener = null;
	protected UTextField textField;

	public ULabelTextField() {
		textField = new UTextField();
	}

	public void initSet() {
		textField.setBorder(BorderFactory.createLineBorder(Color.black, 1));
		textField.addActionListener(this);
	}

	public void actionPerformed(ActionEvent arg0) {
		if (containerActionListener != null) {
			containerActionListener.actionPerformed(arg0);
		}
	}

	public void addContainerActionListener(ActionListener l) {
		containerActionListener = l;

	}

	public FilterI getFilter() {
		return null;
	}

	public void setFilter(FilterI filter) {
	}

	public FilterI getFilter1() {
		return null;
	}

	public void setFilter1(FilterI filter) {
	}

	public void init() {

	}

	public void setBorder(int width, UColor color) {
	}

	public void setBorder(UBorder border) {
		// TODO Auto-generated method stub
	}

	public void setTemplate(UTemplate template) {
		// TODO Auto-generated method stub
		this.template = (UTextFieldTemplate) template;

	}

	public void setVerticalAlignment(int arg0) {
		// TODO Auto-generated method stub

	}

	public Object getData() {
		return getText();
	}

	public void setData(Object obj) {
		if (obj != null)
			setText(obj.toString());
		else
			setText("");
	}

	public boolean requestFirstFoucus() {
		if (textField.isEditable()) {
			textField.requestFocus();
			return true;
		}
		return false;
	}

	public boolean hasEmptyFileds() {
		// TODO Auto-generated method stub
		if (textField.isEditable() && textField.getText().equals(""))
			return true;
		else
			return false;
	}

	public UHandlerI getURuleI() {
		// TODO Auto-generated method stub
		return null;
	}

	public void getBinuessData() {
		// TODO Auto-generated method stub

	}

	public void setBinuessData() {
		// TODO Auto-generated method stub

	}

	public void submit() {
		// TODO Auto-generated method stub

	}

	public void setBorderType(int type) {
		// TODO Auto-generated method stub

	}

	public void setColor(UColor c) {
		// TODO Auto-generated method stub

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
		if (elementTemplate != null)
			return new URect(elementTemplate.x, elementTemplate.y,
					elementTemplate.w, elementTemplate.h);
		else
			return null;
	}

	public void setArrangeType(int type) {
		// TODO Auto-generated method stub

	}

	public UTemplate getTemplate() {
		// TODO Auto-generated method stub
		return template;
	}

	public void addEvents(UEventAttribute[] events) {
		int i;

		for (i = 0; i < events.length; i++) {
			if (events[i].name.equals(EventConstants.EVENT_ACTION)) {
				textField.addActionListener(getUParent().getEventAdaptor());
			} else if (events[i].name.equals(EventConstants.EVENT_FOCUS)) {
				textField.addFocusListener(getUParent().getEventAdaptor());
			} else if (events[i].name.equals(EventConstants.EVENT_MOUSE)) {
				textField.addMouseListener(getUParent().getEventAdaptor());
			} else if (events[i].name.equals(EventConstants.EVENT_CHANGE)) {
				textField.getDocument().addDocumentListener(
						new DocumentProcessor());
			}

		}
	}

	public class DocumentProcessor implements DocumentListener {

		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub

		}

		public void sendMessageToUParent() {
			ChangeEvent e = new ChangeEvent(this);
			getUParent().getEventAdaptor().processEvent(e, "change",
					componentName);
		}
	}

	public void setAddedDatas(Object[] o) {
		// TODO Auto-generated method stub

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

	}

	public void updateAddedDatas() {
		// TODO Auto-generated method stub

	}

	public Object getLabel(Object value) {
		// TODO Auto-generated method stub
		return value;
	}

	public void initContents() {
		// TODO Auto-generated method stub
		initSet();
		setLayout(new FlowLayout());
		UFont f = UFactory.getModelSession().getFontByName(elementTemplate.fontName);
		UColor c = UFactory.getModelSession().getColorByName(elementTemplate.colorName);
		int lw = template.lw;
		if(lw < 0 )
			lw = template.w/2;
		int fw = template.lw-lw;
		if(elementTemplate.text != null){
			JLabel l = new JLabel(elementTemplate.text);
			l.setAlignmentX(JLabel.RIGHT_ALIGNMENT);
			l.setFont(f.font);
			l.setForeground(c.color);
			l.setSize(lw,template.h);
			add(l);
		}
		if (template != null && template.maxLength >1)
			textField.setColumns(template.maxLength);
		else
			textField.setColumns(10);			
		textField.setAlignmentX(textField.LEFT_ALIGNMENT);
		textField.setFont(f.font);
		textField.setForeground(c.color);
		textField.setSize(lw,template.h);
		add(textField);
	}

	public void setInitComponentData(Object data) {

	}

	public void setShellBounds(int x, int y, int w, int h) {
		// TODO Auto-generated method stub
		this.setBounds((int) (x * UConstants.SA / UConstants.SB), (int) (y
				* UConstants.SA / UConstants.SB),
				(int) (w * UConstants.SA / UConstants.SB), (int) (h
						* UConstants.SA / UConstants.SB));

	}

	public void resetShellBounds(int x, int y, int w, int h) {
		// TODO Auto-generated method stub

	}

	public void onClose() {

	}

	public void repaintComponent() {
	}

	public void setParameters(HashMap paras) {

	}

	public HashMap getParameters() {
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
	public void setHorizontalAlignment(int arg0) {
		// TODO Auto-generated method stub
		textField.setHorizontalAlignment(arg0);
	}

	@Override
	public void setText(String arg0) {
		// TODO Auto-generated method stub
		textField.setText(arg0);
	}

	@Override
	public boolean isEditable() {
		// TODO Auto-generated method stub
		return textField.isEditable();
	}

	@Override
	public void setEditable(boolean b) {
		// TODO Auto-generated method stub
		textField.setEditable(b);
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return textField.getText();
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
