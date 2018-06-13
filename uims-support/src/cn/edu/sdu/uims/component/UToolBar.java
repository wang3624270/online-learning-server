package cn.edu.sdu.uims.component;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JToolBar;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.common.reportdog.UFont;
import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.UimsFactory;
import cn.edu.sdu.uims.base.UBorder;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.component.event.CommonActionListener;
import cn.edu.sdu.uims.component.event.EventConstants;
import cn.edu.sdu.uims.component.menu.UMenu;
import cn.edu.sdu.uims.component.menu.UPopupMenu;
import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.def.UElementTemplate;
import cn.edu.sdu.uims.def.UEventAttribute;
import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.frame.def.UToolTemplate;
import cn.edu.sdu.uims.frame.def.UToolbarTemplate;
import cn.edu.sdu.uims.handler.UHandlerI;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.trans.URect;
import cn.edu.sdu.uims.util.UimsUtils;

public class UToolBar extends JToolBar implements UComponentI {
	protected ActionListener actionListener = null;
	protected TooBarStatusProcessor tbListener = null; 
	protected UToolbarTemplate template;
	protected HashMap comMap = new HashMap();
	protected List comList = new ArrayList();
	protected String componentName;
	protected UPanelI parent;
	protected UElementTemplate elementTemplate;
	public UToolBar() {
		actionListener = new CommonActionListener();
	}

	public void setTemplate(UTemplate template) {
		this.template = (UToolbarTemplate) template;
	}

	public void addActionListener(ActionListener listener) {
		((CommonActionListener) actionListener).addListener(listener);
	}

	/**
	 * 移出事件监听
	 * 
	 * @param listener
	 */
	public void removeActionListener(ActionListener listener) {
		((CommonActionListener) actionListener).removeListener(listener);
	}

	public void init() {
		if (template == null)
			return;
		if (template.row == 1) {
			initFlowToolBar();
		} else {
			initGridToolBar();
		}
		this.setFloatable(template.floatable);
		disableToolBarButtons();

	}

	protected void initFlowToolBar() {
		setLayout(UimsUtils.getFlowLayout(template.alignment, template.hgap, template.vgap));
		setOpaque(false);
		if(template.selectMode != null) {
			tbListener = new  TooBarStatusProcessor();
		}
//		this.setBackground(UFactory.getModelSession().getColorByName("propertymsTitleBgColor").color);
		super.setBorder(null);
		ButtonGroup buttonGroup = new ButtonGroup();
		int i = 0;
		int  x = 0, y = 0;
		UComponentI com;
		UToolTemplate item;
		boolean isEnglish = UimsFactory.getClientMainI().isEnglishVersion();
		Icon icon;
		for (i = 0; i < template.items.length; i++) {
			item = template.items[i];
			com = (UComponentI) item.newComponent();
			com.setTemplate(item);
			com.setUParent(parent);
			if (com instanceof AbstractButton) {
				UActionComponentI acom = (UActionComponentI) com;
				comMap.put(template.items[i].name, com.getAWTComponent());
				comList.add(com.getAWTComponent());
//				icon = CommonMethod.getIconByFileName(item.iconName);
				icon = UimsUtils.getCSClientIcon(item.iconName);
				if(item.colorName != null)
					acom.setColor(UFactory.getModelSession().getColorByName(item.colorName));
				acom.setIcon(icon);
				if (icon == null) {
					if(template.fontName != null)
						acom.setFont(UFactory.getModelSession().getFontByName(template.fontName));
					com.setText(item.name);
				} else
					com.setText(null);
				if(item.cmd != null && !item.cmd.equals("")) 
					acom.setActionCommand(item.cmd);
				else
					acom.setActionCommand(item.name);					
				if(acom instanceof JButton) {
					JButton jb = (JButton)acom;
					if(item.selectedIconName != null) {
						icon = UimsUtils.getCSClientIcon(item.selectedIconName);
						jb.setSelectedIcon(icon);					
					}
					if(item.pressedIconName != null) {
						icon = UimsUtils.getCSClientIcon(item.pressedIconName);
						jb.setPressedIcon(icon);					
					}
					jb.setBorder(null);
					jb.setOpaque(false);
					jb.setContentAreaFilled(false);//不绘制按钮区域 
					jb.setBorderPainted(false); //不绘制边框
				}
				acom.setTemplate(item);
				if (getActionListener() != null)
					acom.addActionListener(getActionListener());
				if(tbListener != null)
					acom.addActionListener(tbListener);
				Component component = com.getAWTComponent();			
				buttonGroup.add((AbstractButton) component);
				if(template.labelAlignment == UConstants.ALIGNMENT_TIP) {
					if(isEnglish)
						acom.setToolTipText(item.enContent);
					else
						acom.setToolTipText(item.content);						
					acom.setShellBounds(x, y, template.width, template.height);
					add(component);
				} else if(template.labelAlignment == UConstants.ALIGNMENT_NULL) {
					acom.setShellBounds(x, y, template.width, template.height);
					add(component);
				}else{
					JPanel tp = new JPanel();
					component.setSize(template.width- template.labelWidth, template.height -template.labelHeight);
					tp.setSize(template.width, template.height);
					tp.setOpaque(false);
					tp.setLayout(new BorderLayout());
					tp.add(component,BorderLayout.CENTER);
					JLabel l;
					if(isEnglish)
						l = new JLabel(item.enContent);
					else
						l = new JLabel(item.content);
					l.setOpaque(false);
					l.setHorizontalAlignment(JLabel.CENTER);
					if(template.fontName != null)
						l.setFont(UFactory.getModelSession().getFontByName(template.fontName).font);
					tp.add(l,UimsUtils.getBorderLayoutValue(template.labelAlignment));
					tp.setBounds(x, y, template.width, template.height);
					add(tp);
				}
			} else if (com instanceof JSeparator) {// 分割符等等
				this.addSeparator();
			}

		}
	}

	public ActionListener getActionListener() {

		return actionListener;
	}

	protected void initGridToolBar() {
		GridBagConstraints c;
		int gridx, gridy, gridwidth, gridheight, anchor, fill, ipadx, ipady;
		double weightx, weighty;
		Insets inset;
		GridBagLayout gridbag = new GridBagLayout();
		setLayout(gridbag);
		int col, row, btns = 0;
		ButtonGroup buttonGroup = new ButtonGroup();
		int x = 0, y = 0;
		UComponentI com;
		UToolTemplate item;
		Icon icon;
		int i = 0;
		for (i = 0; i < template.items.length; i++) {
			item = template.items[i];
			com = (UComponentI) item.newComponent();
			com.setTemplate(item);
			com.setUParent(parent);
			if (com instanceof AbstractButton) {
				UActionComponentI acom = (UActionComponentI) com;
				comMap.put(template.items[i].name, com.getAWTComponent());
				comList.add(com.getAWTComponent());
				icon = UimsUtils.getCSClientIcon(item.iconName);
				acom.setFont(UFactory.getModelSession().getFontByName(template.fontName));
				acom.setIcon(icon);
				acom.setColor(UFactory.getModelSession().getColorByName(item.colorName));
				if (icon == null) {
					com.setText(item.name);
				} else
					com.setText("");
				acom.setToolTipText(item.name);
				acom.setActionCommand(item.name);
				acom.setShellBounds(x, y, template.width, template.height);
				acom.addActionListener(actionListener);

				row = btns / template.col;
				col = btns % template.col;
				gridy = row;
				gridx = col;
				gridwidth = 1;
				gridheight = 1;
				weightx = 0;
				weighty = 0;
				anchor = GridBagConstraints.WEST;
				fill = GridBagConstraints.NONE;
				inset = new Insets(0, 0, 0, 0);
				ipadx = 0;
				ipady = 0;
				c = new GridBagConstraints(gridx, gridy, gridwidth, gridheight,
						weightx, weighty, anchor, fill, inset, ipadx, ipady);
				Component component = com.getAWTComponent();
				add(component, c);
				buttonGroup.add((AbstractButton) component);
				btns++;
			} else if (com instanceof JSeparator) {// 分割符等等
				this.addSeparator();
			}
		}
	}

	protected void resetToolBarListener() {
		AbstractButton com;
		if (getActionListener() == null)
			return;
		for (int i = 0; i < comList.size(); i++) {
			if (comList.get(i) instanceof AbstractButton) {
				com = (AbstractButton) comList.get(i);
				com.addActionListener(getActionListener());
			}
		}
	}

	void initToolbarButton(JButton btn, String btnName, String cmdName,
			Icon icon) {
	}

	public void disableToolBarButtons() {
		int i;
		AbstractButton b;
		if(template.defaultEnable)
			return;
		for (i = 0; i < comList.size(); i++) {
			b = (AbstractButton) comList.get(i);
			b.setEnabled(false);
		}
	}

	public void enableToolBarButtons(String[] acts) {
		int i;
		Object o;
		JButton b;
		if (acts == null)
			return;
		for (i = 0; i < acts.length; i++) {
			o = comMap.get(acts[i]);
			if (o == null)
				continue;
			b = (JButton) o;
			b.setEnabled(true);
		}
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
	}

	public void setBorder(int width, UColor color) {
		// TODO Auto-generated method stub

	}

	public void setColor(UColor c) {
		// TODO Auto-generated method stub

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
		super.setFont(agr0.font);
	}

	public void setHorizontalAlignment(int arg0) {
		// TODO Auto-generated method stub

	}

	public void setTemplate(Object Template) {
		// TODO Auto-generated method stub

	}

	public void setText(String arg0) {
		// TODO Auto-generated method stub

	}

	public void setVerticalAlignment(int arg0) {
		// TODO Auto-generated method stub

	}

	public UTemplate getTemplate() {
		// TODO Auto-generated method stub
		return null;
	}

	public void addEvents(UEventAttribute[] events) {
		// TODO Auto-generated method stub
		int i;
		for (i = 0; i < events.length; i++) {
			if (events[i].name.equals(EventConstants.EVENT_ACTION)) {
				addActionListener(getUParent().getEventAdaptor());
			} 
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

	public FilterI getFilter() {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateAddedDatas() {
		// TODO Auto-generated method stub

	}

	public void initContents() {
		// TODO Auto-generated method stub
		init();

	}

	public void setInitComponentData(Object data) {

	}

	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setShellBounds(int x, int y, int w, int h) {
		// TODO Auto-generated method stub
		// setBounds(x, y, w, h);
		this.setLocation(x, y);
		Dimension dimension = new Dimension(w, h);
		this.setSize(dimension);
		this.setMinimumSize(dimension);
		this.setMaximumSize(dimension);
		this.setPreferredSize(dimension);
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
	public void processToolStatus( AbstractButton dcom){
		AbstractButton com;
		if(template.selectMode.equals("multiple")) {
			if(dcom.isSelected()) {
				dcom.setSelected(false);
			}else {
				dcom.setSelected(true);
			}
		}else {
			for (int i = 0; i < comList.size(); i++) {
				if (comList.get(i) instanceof AbstractButton) {
					com = (AbstractButton) comList.get(i);
					com.setSelected(false);
				}
			}
			dcom.setSelected(true);
		}

	}
	private class TooBarStatusProcessor implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			processToolStatus((AbstractButton)e.getSource());
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
