package cn.edu.sdu.uims.component.button;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.pi.ClientDataDictionaryI;
import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.common.reportdog.UFont;
import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.UimsFactory;
import cn.edu.sdu.uims.base.UBorder;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.base.UTemplateComponentI;
import cn.edu.sdu.uims.component.event.EventConstants;
import cn.edu.sdu.uims.component.menu.UMenu;
import cn.edu.sdu.uims.component.menu.UPopupMenu;
import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.def.UButtonGroupTemplate;
import cn.edu.sdu.uims.def.UElementTemplate;
import cn.edu.sdu.uims.def.UEventAttribute;
import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.handler.UHandlerI;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.trans.URect;

public class UButtonGroup extends JPanel implements UTemplateComponentI {
	protected UButtonGroupTemplate groupTemplate = null;

	private static final long serialVersionUID = 1L;
	protected String componentName;
	protected UPanelI parent;
	protected AbstractButton buttons[];
	protected UElementTemplate elementTemplate;
	protected ActionListener eventProcessor;
	protected boolean canActionEvent = false;

	protected class ActionEventProcessor implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (canActionEvent) {
				processActionEvent(e);
			}

		}
	}
	public void processActionEvent(ActionEvent e){
		ActionEvent e1 = new ActionEvent(this, e.getID(),e.getActionCommand());
		getUParent().getEventAdaptor().actionPerformed(e1);
	}
			
	protected AbstractButton getComponentButton() {
		return new JButton();
	}

	protected Border getGoupBorder() {
		return null;
	}

	public UButtonGroup() {
	}

	public void init() {
	}

	public void initButtonGroup() {
	}

	public void addEvents(UEventAttribute[] events) {
		// TODO Auto-generated method stub
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

	public String getComponentName() {
		// TODO Auto-generated method stub
		return componentName;
	}

	public Object getData() {
		return null;
	}

	public FilterI getFilter() {
		// TODO Auto-generated method stub
		return null;
	}

	public UTemplate getTemplate() {
		// TODO Auto-generated method stub
		return groupTemplate;
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
		return false;
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
	}

	public void setBorder(int width, UColor color) {
	}

	public void setColor(UColor c) {
		// TODO Auto-generated method stub

	}

	public void setComponentName(String name) {
		// TODO Auto-generated method stub
		this.componentName = name;
	}

	public void setData(Object obj) {
		// TODO Auto-generated method stub
	}

	public void setEditable(boolean b) {
		// TODO Auto-generated method stub

	}

	public void setFilter(FilterI filter) {
		// TODO Auto-generated method stub
	}

	public void setFilter1(FilterI filter) {
		// TODO Auto-generated method stub

	}

	public void setFont(UFont agr0) {
		// TODO Auto-generated method stub
		super.setFont(agr0.font);
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
		groupTemplate = (UButtonGroupTemplate) template;
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

	public void initComponents() {
		// TODO Auto-generated method stub

	}

	public ActionListener getEventProcessor() {
		return new ActionEventProcessor();
	}

	public void setButtonAttribute(int i) {
//		buttons[i].setSize(groupTemplate.buttons[i].w, groupTemplate.h);
		buttons[i].setEnabled(elementTemplate.enable);
		buttons[i].addActionListener(eventProcessor);
	}

	public void initContents() {
		// TODO Auto-generated method stub
		int i;
		UHandlerI h = this.getUParent().getHandler();
		eventProcessor = this.getEventProcessor();
		Border border = getGoupBorder();
		if (border != null)
			this.setBorder(border);
		List dataList = null;
		int size = 0;
		dataList = h.getInitAddedDataList(this.componentName);
		size = 0;
		if(dataList != null && dataList.size() != 0) {
			size = dataList.size();
		}
		if(size == 0) {
			ClientDataDictionaryI util = UimsFactory.getClientDataDictionaryI();
			if (elementTemplate.dictionary != null && util != null) {
				dataList = (ArrayList) util
						.getComboxListByCode(elementTemplate.dictionary);
				if (dataList != null)
					size = dataList.size();
			}
		}
		if(size == 0) {
			size = groupTemplate.buttons.length;
		}
		if (groupTemplate.arrangeType == UConstants.TEXTARRANGE_HORIZONTAL) {
			setLayout(new GridLayout(1, size));
		}
		if (groupTemplate.arrangeType == UConstants.TEXTARRANGE_VERTICAL) {
			setLayout(new GridLayout(size, 1));
		} else if (groupTemplate.arrangeType == UConstants.TEXTARRANGE_GRID) {
			int r = size / groupTemplate.colnum;
			if (groupTemplate.colnum != 1 && size % groupTemplate.colnum != 0)
				r++;
			setLayout(new GridLayout(r, groupTemplate.colnum));
		} else {
			int align;
			if(groupTemplate.horizontalAlignment == UConstants.ALIGNMENT_CENTER) {
					align = FlowLayout.CENTER;
				}else if(groupTemplate.horizontalAlignment == UConstants.ALIGNMENT_LEFT){
					align = FlowLayout.LEFT;
				}else if(groupTemplate.horizontalAlignment == UConstants.ALIGNMENT_RIGHT){
					align = FlowLayout.RIGHT;
				}else  {
					align = FlowLayout.CENTER;			
				}			
			setLayout(new FlowLayout(align));
		}
		buttons = new AbstractButton[size];
		ListOptionInfo info;
		initButtonGroup();
		boolean b = UimsFactory.getClientMainI().isEnglishVersion();
		for (i = 0; i < size; i++) {
			buttons[i] = this.getComponentButton();
			buttons[i].setFont(UFactory.getModelSession().getFontByName(
					groupTemplate.fontName).font);
			add(buttons[i]);
			if (dataList != null) {
				info = (ListOptionInfo) dataList.get(i);
				if(b)
					buttons[i].setText(info.getEnLabel());
				else
					buttons[i].setText(info.getLabel());
				buttons[i].setActionCommand(info.getValue());
			} else {

				if(b)
					buttons[i].setText(groupTemplate.buttons[i].enContent);
				else
					buttons[i].setText(groupTemplate.buttons[i].content);
				buttons[i].setActionCommand(groupTemplate.buttons[i].cmd);

			}
			buttons[i].setSize(groupTemplate.comWidth, groupTemplate.comHeight);
			setButtonAttribute(i);
		}
	}

	public void initHandlerObject() {
		// TODO Auto-generated method stub

	}

	public void ResetComponentContent() {
		// TODO Auto-generated method stub

	}

	public void setInitComponentData(Object data) {

	}

	public String getText() {
		// TODO Auto-generated method stub
		return null;
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
	public void resetTemplate(UTemplate template) {
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
	public boolean testInvalidateData() {
		// TODO Auto-generated method stub
		return false;
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
