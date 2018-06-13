package cn.edu.sdu.uims.component.menu;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.util.HashMap;

import javax.swing.ButtonGroup;
import javax.swing.JMenuBar;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.common.reportdog.UFont;
import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.UimsFactory;
import cn.edu.sdu.uims.base.UBorder;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.component.event.CommonActionListener;
import cn.edu.sdu.uims.component.tree.UTreeMenu;
import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.def.UElementTemplate;
import cn.edu.sdu.uims.def.UEventAttribute;
import cn.edu.sdu.uims.def.UMenuBarTemplate;
import cn.edu.sdu.uims.def.UMenuItemTemplate;
import cn.edu.sdu.uims.def.UMenuTemplate;
import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.handler.UHandlerI;
import cn.edu.sdu.uims.trans.URect;

public class UMenuBar extends JMenuBar implements UComponentI {
	private CommonActionListener commonActionListener = new CommonActionListener();
	private UMenuBarTemplate menuBarTemplate;
	public String name = null;
	public String content = null;
	private String componentName;
	private UPanelI parent = null;
	private UTreeMenu leftTreeMenu = null;
	protected UElementTemplate elementTemplate;
	LeftPanelShowProcess leftPanelShowProcess;

	public void UMenuBar() {
	}

	public void setTemplate(UTemplate template) {
		menuBarTemplate = (UMenuBarTemplate) template;
	}

	public void init() {
		if (menuBarTemplate == null)
			return;
		int size = menuBarTemplate.items.length;
		UMenu menu = null;
		UMenuItem menuItem = null;
		leftPanelShowProcess = new LeftPanelShowProcess();

		// 循环构�1�7�menu

		if (size > 1)
			for (int i = 0; i < size; i++) {
				if ((menuBarTemplate.items[i]) instanceof UMenuTemplate) {
					menu = parseMenu((UMenuTemplate) menuBarTemplate.items[i]);
					if (menu != null) {
						add(menu);
					}
				} else if (menuBarTemplate.items[i] instanceof UMenuItemTemplate) {
					UMenuItemTemplate itemTemplate = (UMenuItemTemplate) menuBarTemplate.items[i];
					menu = parseFirstMenuItem(itemTemplate, commonActionListener);
					if (menu != null) {
						add(menu);
					}

				}
			}
		else {
			int j, k;
			UMenu aMenu;

			try {
				for (int i = 0; i < size; i++) {

					menu = parseMenu((UMenuTemplate) menuBarTemplate.items[i]);

					k = menu.getPopupMenu().getComponents().length;
					for (j = 0; j < k; j++) {

						if (menu.getPopupMenu().getComponents()[0] instanceof UMenuItem) {
							aMenu = new UMenu(((UMenuItem) menu.getPopupMenu()
									.getComponents()[0]).getText());
							aMenu.add(menu.getPopupMenu().getComponents()[0]);
							add(aMenu);

						} else {
							add(menu.getPopupMenu().getComponents()[0]);
						}
					}
				}
			} catch (Exception e) {
				// e.printStackTrace();
			}
			// ///////////////////////////////////////////
		}
		this.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				System.out.print("ddd");
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}

	public UMenu parseMenu(UMenuTemplate menuTemplate) {
		if (menuTemplate == null)
			return null;
		String nodeName = "";
		boolean b = UimsFactory.getClientMainI().isEnglishVersion();
		if (b && menuTemplate.enContent != null
				&& !menuTemplate.enContent.trim().equals("")) {
			nodeName = menuTemplate.enContent;
		} else if (menuTemplate.content != null
				&& !menuTemplate.content.trim().equals("")) {
			nodeName = menuTemplate.content;
		} else if (menuTemplate.name != null
				&& !menuTemplate.name.trim().equals("")) {
			nodeName = menuTemplate.name;
		}

		UMenu menu = new UMenu(nodeName);

		UMenuItemTemplate itemTemplate = null;
		UMenu childMenu = null;
		UMenuItem menuItem = null;
		URadioMenuItem radioItem = null;
		ButtonGroup group = new ButtonGroup();

		// 根据菜单项的类型进行不同的处琄1�7
		for (int i = 0; i < menuTemplate.items.length; i++) {
			itemTemplate = menuTemplate.items[i];
			// 子菜卄1�7
			if (itemTemplate.type == UConstants.COMPONENT_MENU_MENU) {
				childMenu = parseMenu((UMenuTemplate) itemTemplate);
				if (childMenu != null) {
					menu.add(childMenu);
				}
			}
			// 普�1�7�菜单项
			else if (itemTemplate.type == UConstants.COMPONENT_MENU_ITEM) {
				menuItem = parseMenuItem(itemTemplate, commonActionListener);
				if (menuItem != null) {
					menu.add(menuItem);
				}
			}
			// radio菜单顄1�7
			else if (itemTemplate.type == UConstants.COMPONENT_RADIO_MENU_ITEM) {
				radioItem = parseRadioMenuItem(itemTemplate,
						commonActionListener);
				if (radioItem != null) {
					group.add(radioItem);
					menu.add(radioItem);
				}

			}
			// 菜单分割纄1�7
			else if (itemTemplate.type == UConstants.COMPONENT_SEPARATOR) {
				menu.addSeparator();
			}
		}
		return menu;
	}

	/**
	 * 转换普�1�7�菜单项
	 * 
	 * @param itemTemplate
	 *            菜单项模牄1�7
	 * @param listener
	 *            action事件监听耄1�7
	 * @return 菜单项组件对豄1�7
	 */
	private UMenuItem parseMenuItem(UMenuItemTemplate itemTemplate,
			ActionListener listener) {
		if (itemTemplate == null) {
			return null;
		}
		String nodeName = "";
		boolean b = UimsFactory.getClientMainI().isEnglishVersion();
		if (b && itemTemplate.enContent != null
				&& !itemTemplate.enContent.trim().equals("")) {
			nodeName = itemTemplate.enContent;
		} else if (itemTemplate.content != null
				&& !itemTemplate.content.trim().equals("")) {
			nodeName = itemTemplate.content;
		} else if (itemTemplate.name != null
				&& !itemTemplate.name.trim().equals("")) {
			nodeName = itemTemplate.name;
		}

		UMenuItem menuItem = new UMenuItem();
		menuItem.addActionListener(listener);
		menuItem.setText(nodeName);
		menuItem.setActionCommand(itemTemplate.cmd);
		menuItem.setSelected(itemTemplate.defaultSelected);
		menuItem.setTemplate(itemTemplate);
		return menuItem;

	}
	private UMenu parseFirstMenuItem(UMenuItemTemplate itemTemplate,
			ActionListener listener) {
		if (itemTemplate == null) {
			return null;
		}
		String nodeName = "";
		boolean b = UimsFactory.getClientMainI().isEnglishVersion();
		if (b && itemTemplate.enContent != null
				&& !itemTemplate.enContent.trim().equals("")) {
			nodeName = itemTemplate.enContent;
		} else if (itemTemplate.content != null
				&& !itemTemplate.content.trim().equals("")) {
			nodeName = itemTemplate.content;
		} else if (itemTemplate.name != null
				&& !itemTemplate.name.trim().equals("")) {
			nodeName = itemTemplate.name;
		}

		UMenu menu = new UMenu(nodeName);
		UMenuItem menuItem = new UMenuItem();
		menuItem.addActionListener(listener);
		menuItem.setText(nodeName);
		menuItem.setActionCommand(itemTemplate.cmd);
		menuItem.setSelected(itemTemplate.defaultSelected);
		menuItem.setTemplate(itemTemplate);
		menu.add(menuItem);
		return menu;
	}

	public void setLeftTreeMenu(UTreeMenu leftTreeMenu) {
		this.leftTreeMenu = leftTreeMenu;
	}

	public UTreeMenu getLeftTreeMenu() {
		return this.leftTreeMenu;
	}

	/**
	 * 转换radio菜单顄1�7
	 * 
	 * @param itemTemplate
	 *            菜单项模牄1�7
	 * @param listener
	 *            action事件监听耄1�7
	 * @return radio菜单顄1�7
	 */
	private URadioMenuItem parseRadioMenuItem(UMenuItemTemplate itemTemplate,
			ActionListener listener) {
		if (itemTemplate == null) {
			return null;
		}

		URadioMenuItem menuItem = new URadioMenuItem(itemTemplate.content);
		menuItem.addActionListener(listener);
		menuItem.setActionCommand(itemTemplate.cmd);
		menuItem.setSelected(itemTemplate.defaultSelected);
		return menuItem;

	}

	/**
	 * 增加事件监听
	 * 
	 * @param listener
	 */
	public void addActionListener(ActionListener listener) {
		commonActionListener.addListener(listener);
	}

	/**
	 * 移出事件监听
	 * 
	 * @param listener
	 */
	public void removeActionListener(ActionListener listener) {
		commonActionListener.removeListener(listener);
	}

	public URect getBoundRect() {
		// TODO Auto-generated method stub
		if (elementTemplate != null)
			return new URect(elementTemplate.x, elementTemplate.y,
					elementTemplate.w, elementTemplate.h);
		else
			return null;
	}

	public void setHorizontalAlignment(int arg0) {
		// TODO Auto-generated method stub

	}

	public void setVerticalAlignment(int arg0) {
		// TODO Auto-generated method stub

	}

	public void setText(String arg0) {
		// TODO Auto-generated method stub

	}

	public void setColor(UColor c) {
		// TODO Auto-generated method stub

	}

	public void setBorder(UBorder border) {
		// TODO Auto-generated method stub

	}

	public void setFont(UFont agr0) {
		// TODO Auto-generated method stub

	}

	public boolean requestFirstFoucus() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setData(Object obj) {
		// TODO Auto-generated method stub

	}

	public Object getData() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean hasEmptyFileds() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setFilter(FilterI filter) {
		// TODO Auto-generated method stub

	}

	public void setFilter1(FilterI filter) {
		// TODO Auto-generated method stub

	}

	public boolean isEditable() {
		// TODO Auto-generated method stub
		return false;
	}

	public void setEditable(boolean b) {
		// TODO Auto-generated method stub

	}

	public void setArrangeType(int type) {
		// TODO Auto-generated method stub

	}

	public void setBorder(int width, UColor color) {
		// TODO Auto-generated method stub

	}

	public UTemplate getTemplate() {
		// TODO Auto-generated method stub
		return menuBarTemplate;
	}

	public void addEvents(UEventAttribute[] events) {
		// TODO Auto-generated method stub

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

	}

	public void setInitComponentData(Object data) {

	}

	// /////////////////////////////////////////////////////////
	class LeftPanelShowProcess implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			System.out.println(e.getActionCommand() + "ff");
			leftTreeMenu.removeActionListener(null);
			leftTreeMenu.removeAll();
			leftTreeMenu.refresh(e.getActionCommand());
			leftTreeMenu.updateUI();

		}
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

	public Component getAWTComponent() {
		// TODO Auto-generated method stub
		return this;
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
