//描述:公共组件--显示区
//修改者:赵新晓
//最后修改日期:2009.5.12
package cn.edu.sdu.uims.component;

import java.awt.Color;
import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.util.HashMap;

import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;

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

public class UHtmlArea extends UScrollPane implements UComponentI,
		HyperlinkListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JEditorPane area;//
	private UPanelI parent = null;
	private String componentName;
	private UScrollPanelEventAdaptor eventProcessor = null;
	protected UElementTemplate elementTemplate;

	public UHtmlArea() {
		area = new JEditorPane();
		setSytleHtml();
		area.setEditable(false);
		area.addHyperlinkListener(this);
		setViewportView(area);
	}

	public void init() {
		// TODO Auto-generated method stub

	}

	public void setSytleHtml() {
		area.setContentType("text/html");
	}

	public void setSytlePlain() {
		area.setContentType("text/plain");
	}

	// ////////////////////////////////////////////
	public void addEvents(UEventAttribute[] events) {
		// TODO Auto-generated method stub
		int i;
		eventProcessor = new UScrollPanelEventAdaptor(this, getUParent()
				.getEventAdaptor());
		for (i = 0; i < events.length; i++) {
			if (events[i].name.equals(EventConstants.EVENT_KEY)) {
				area.addKeyListener(eventProcessor);
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

	public String getComponentName() {
		// TODO Auto-generated method stub
		return componentName;
	}

	public Object getData() {
		// TODO Auto-generated method stub
		return area.getText();
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

	public boolean isEditable() {
		// TODO Auto-generated method stub
		return area.isEditable();
	}

	public boolean requestFirstFoucus() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setAddedDatas(Object[] obj) {
		// TODO Auto-generated method stub

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

	}

	public void setComponentName(String name) {
		// TODO Auto-generated method stub
		this.componentName = name;
	}


	public void setEditable(boolean b) {
		// TODO Auto-generated method stub
		area.setEditable(b);
	}

	public void setFilter(FilterI filter) {
		// TODO Auto-generated method stub

	}

	public void setFilter1(FilterI filter) {
		// TODO Auto-generated method stub

	}

	public void setFont(UFont agr0) {
		// TODO Auto-generated method stub
		area.setFont(agr0.font);
	}

	public void setHandler(UHandlerI handler) {
		// TODO Auto-generated method stub

	}

	public void setHorizontalAlignment(int arg0) {
		// TODO Auto-generated method stub

	}

	public void setTemplate(UTemplate template) {
		// TODO Auto-generated method stub

	}

	public void setText(String arg0) {
		// TODO Auto-generated method stub
		area.setText(arg0);
	}

	public void setUParent(UPanelI parent) {
		// TODO Auto-generated method stub
		this.parent = parent;
	}

	public void setVerticalAlignment(int arg0) {
		// TODO Auto-generated method stub

	}

	public void setPopupMenu(UMenu menu) {
		// TODO Auto-generated method stub

	}

	public FilterI getFilter() {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateAddedDatas() {
		// TODO Auto-generated method stub

	}

	public void initContents() {
		// TODO Auto-generated method stub

	}

	public void setInitComponentData(Object data) {

	}

	public int getCaretPosition() {
		return area.getCaretPosition();
	}

	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}

	// /////////////////////////////////////////////
	public void insert(String str, Color theColor) {
		SimpleAttributeSet attrset = new SimpleAttributeSet();
		StyleConstants.setForeground(attrset, theColor);
		insert(str, attrset);
	}

	// /////////////////
	private void insert(String str, AttributeSet attrset) {
		Document docs = area.getDocument();
		try {
			docs.insertString(docs.getLength(), str, attrset);
		} catch (Exception e) {
		}
	}

	public void setShellBounds(int x, int y, int w, int h) {
		// TODO Auto-generated method stub
		setBounds(x, y, w, h);
		// this.setLocation(x, y);
		// this.setPreferredSize(new Dimension(w,h));
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

	}

	@Override
	public void hyperlinkUpdate(HyperlinkEvent e) {
		// TODO Auto-generated method stub
		if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
			JEditorPane pane = (JEditorPane) e.getSource();
			if (e instanceof HTMLFrameHyperlinkEvent) {
				HTMLFrameHyperlinkEvent evt = (HTMLFrameHyperlinkEvent) e;
				HTMLDocument doc = (HTMLDocument) pane.getDocument();
				doc.processHTMLFrameHyperlinkEvent(evt);
			} else {
				try {
					pane.setPage(e.getURL());
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
		}

	}
	public void setData(Object obj) {
		// TODO Auto-generated method stub
		if (obj != null) {
			String text = obj.toString();
			if(text.startsWith("http")) 
				try {
					area.setPage(text);
				}catch(Exception e){
					e.printStackTrace();
				}
			else
				area.setText(text);
		} else {
			area.setText("");
		}

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
