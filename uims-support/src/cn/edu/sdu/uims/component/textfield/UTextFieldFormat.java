package cn.edu.sdu.uims.component.textfield;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.border.Border;
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
import cn.edu.sdu.uims.component.UClipbordComponet;
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

public class UTextFieldFormat extends JFormattedTextField implements
		LabeValueTransI, ActionListener, UComponentI {
	protected ActionListener containerActionListener = null;
	protected String componentName;
	protected UPanelI parent = null;
	protected UTextFieldTemplate template;
	protected UElementTemplate elementTemplate;
	DocumentListener documnetListener = null;

	public UTextFieldFormat() {
		this(10);
	}

	public UTextFieldFormat(String text) {
		super(text);
	}

	public UTextFieldFormat(int column) {
		super(column);
	}

	public UTextFieldFormat(String text, int column) {
	}

	public void initSet() {
		// this.setBackground(new Color(0,0,0));
		setBorder(BorderFactory.createLineBorder(Color.black, 1));
		addActionListener(this);
		UClipbordComponet c = new UClipbordComponet(this);
		c.init();
	}

	public void actionPerformed(ActionEvent arg0) {
		sendActionEventToParent(arg0);
	}

	public void sendActionEventToParent(ActionEvent arg0) {
		if (containerActionListener != null) {
			ActionEvent e = new ActionEvent(arg0.getSource(), arg0.getID(),
					this.getComponentName());
			containerActionListener.actionPerformed(e);
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
		if (width == 0)
			setBorder((Border) null);
		else
			setBorder(BorderFactory.createLineBorder(color.color, width));
	}

	public void setBorder(UBorder border) {
		// TODO Auto-generated method stub
		if (border.obj == null)
			this.setBorder((Border) border.obj);
		else
			setBorder((int) border.width, UFactory.getModelSession()
					.getColorByName(border.colorName));
	}

	public void setTemplate(UTemplate template) {
		// TODO Auto-generated method stub
		this.template = (UTextFieldTemplate) template;

	}

	public void setVerticalAlignment(int arg0) {
		// TODO Auto-generated method stub

	}

	public Object getData() {
		if (template.removeSpace) {
			String s = getText();
			if (s != null)
				s = s.trim();
			return s;
		} else
			return getText();
	}

	public void setData(Object obj) {
		if (obj != null)
			setText(obj.toString());
		else
			setText("");
	}

	public boolean requestFirstFoucus() {
		if (this.isEditable()) {
			this.requestFocusInWindow();
			return true;
		}
		return false;
	}

	public boolean hasEmptyFileds() {
		// TODO Auto-generated method stub
		if (this.isEditable() && getText().equals(""))
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
				// this.addActionListener(getUParent().getEventAdaptor());
				addContainerActionListener(getUParent().getEventAdaptor());
			} else if (events[i].name.equals(EventConstants.EVENT_FOCUS)) {
				this.addFocusListener(getUParent().getEventAdaptor());
			} else if (events[i].name.equals(EventConstants.EVENT_MOUSE)) {
				this.addMouseListener(getUParent().getEventAdaptor());
			} else if (events[i].name.equals(EventConstants.EVENT_CHANGE)) {
				getDocument().addDocumentListener(new DocumentProcessor());
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
		if (template != null)
			this.setColumns(template.maxLength);
	}

	public void setInitComponentData(Object data) {

	}

	public void setShellBounds(int x, int y, int w, int h) {
		// TODO Auto-generated method stub
		this.setBounds((int) (x * UConstants.SA / UConstants.SB), (int) (y
				* UConstants.SA / UConstants.SB),
				(int) (w * UConstants.SA / UConstants.SB), (int) (h
						* UConstants.SA / UConstants.SB));

		// this.setLocation((int) (x * UConstants.SA / UConstants.SB),(int) (y *
		// UConstants.SA / UConstants.SB));
		// this.setPreferredSize(new Dimension((int) (w * UConstants.SA /
		// UConstants.SB),(int) (h* UConstants.SA / UConstants.SB)));
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
		if (c != null)
			this.setBackground(c.color);
	}

	@Override
	public void insertText(String text) {
		// TODO Auto-generated method stub
		setText(text);
	}

}
