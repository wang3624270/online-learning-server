package cn.edu.sdu.uims.component.panel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.util.Iterator;

import javax.swing.JPanel;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UPanel;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.base.UScrollPane;
import cn.edu.sdu.uims.component.groupcomponent.UGroupComponentI;
import cn.edu.sdu.uims.component.menu.MenuGenerator;
import cn.edu.sdu.uims.component.menu.UMenu;
import cn.edu.sdu.uims.component.menu.UMenuItem;
import cn.edu.sdu.uims.component.menu.UPopupMenu;
import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.def.UElementTemplate;
import cn.edu.sdu.uims.def.UEventAttribute;
import cn.edu.sdu.uims.def.UMenuTemplate;
import cn.edu.sdu.uims.def.UPanelTemplate;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.service.UHandlerSessionI;
import cn.edu.sdu.uims.trans.URect;

public class UFPanel extends UPanel {
	final static int CONTENT_WIDTH = 1000;

	final static int CONTENT_HEIGHT = 650;
	protected JPanel container;
	protected int comnum = 0;
	protected JPanel innerPanel = null;
	protected int innerX = -1, innerY = -1;
	protected UScrollPane scrollPane = null;
	private boolean useOutContainer = false;
	private Container outContainer = null;
	private UPopupMenu popupMenu = null;

	public UFPanel() {
		this(new UPanelTemplate());
	}

	public UFPanel(UPanelTemplate panelTemplate) {
		super(panelTemplate);
		initContainer();
	}
	
	public void initContainer(){
		container = new UFPanelContent();
		((UFPanelContent) container).setOwner(this);		
	}
	public void setUseOutContainer(boolean isUse){
		useOutContainer = isUse;
	}
	public void setOutCantiner(Container outContainer){
		this.outContainer = outContainer;
	}
	public UGroupComponentI initGroupComponent(boolean isRoot) {
		UGroupComponentI gc = getGroupComponentObject();
		if (isRoot) {
			if(useOutContainer) {
				gc.setContainer(outContainer);
			}else {
				if (!useOutContainer && panelTemplate != null && gc.getCanScrolling() ) {
					scrollPane = new UScrollPane();
					innerPanel = new JPanel();
					MouseEventProcess pt = new MouseEventProcess();
					innerPanel.addMouseListener(pt);
//					innerPanel.setBackground(new Color(0,64,0));
					scrollPane.setViewportView(innerPanel);
					container.setLayout(new BorderLayout());
					container.add(scrollPane);
					gc.setContainer(innerPanel);
				} else {
					gc.setContainer(container);
				}
				container.setPreferredSize(new Dimension(1,1));
			}
		} else {
			gc.setContainer(new JPanel());
		}
		return gc;
	}

	protected void initContentBounds() {
		int w, h;
		w = getInnerWidth();
		h = getInnerHeight();
		container
				.setLocation((CONTENT_WIDTH - w) / 2, (CONTENT_HEIGHT - h) / 2);
		container.setPreferredSize(new Dimension(w, h));
	}

	public void setBorder(int border) {
	}

	public boolean hasEmptyFileds() {
		// TODO Auto-generated method stub
		String name;
		Iterator it = this.getNameIterator();
		UComponentI com;
		while (it.hasNext()) {
			name = (String) it.next();
			com = (UComponentI) componentMap.get(name);
			if (com.hasEmptyFileds())
				return true;
		}
		return false;
	}

	public Component getAWTComponent() {
		return container;
	}

	public void setMenuParant(UMenu menu) {
		int i = 0;
		UMenuItem item;
		for (i = 0; i < menu.getItemCount(); i++) {
			item = (UMenuItem) menu.getItem(i);
			this.setComponentName(item.getActionCommand());
			item.setUParent(this);
		}
	}

	public UFormI initDataByHandlerAfterInitContents() {
		// TODO Auto-generated method stub
		int i;
		UComponentI com;
		UFormI form = null;
		String name;
		UElementTemplate ele;
		UHandlerSessionI session = UFactory.getHandlerSession();
		form= (UFormI)session.getDataFormFromHandler(UHandlerSessionI.CLIENT_TYPE_JAVA,session.getClientSessionId(),getHandlerId());
		if(form != null)
			getFormMemberMethod(form);
		Iterator it = this.getNameIterator();
		while (it.hasNext()) {
			name = (String) it.next();
			com = (UComponentI) componentMap.get(name);
			if (com == null) {
				System.out.println(name + "模版找不到！");
			}
			ele = (UElementTemplate) componentAttributeMap.get(name);
			if (ele.event != null){
				UEventAttribute []ae = new UEventAttribute[ele.event.size()];
				for(int k = 0; k < ae.length;k++) {
					ae[k] = (UEventAttribute)ele.event.get(k);
				}
				com.addEvents(ae);
			}
			if (ele.menu != null) {
				UMenuTemplate menuTemplate = (UMenuTemplate) UFactory
						.getModelSession().getTemplate(
								UConstants.MAPKEY_MENU, ele.menu);
				if (menuTemplate != null) {
					UMenu menu = MenuGenerator.parseMenu(menuTemplate,
							this.eventAdaptor);
					menu.setConditionMenu(menuTemplate.conditionMenu);
					setMenuParant(menu);
					setMenuTimeControl(ele.name,menu);
					com.setPopupMenu(menu);
				}
			}
			if (com instanceof UPanelI)
				((UPanelI) com)
						.initDataByHandlerAfterInitContents();
		}
		if(panelTemplate.menu != null) {
			UMenuTemplate menuTemplate = (UMenuTemplate) UFactory
					.getModelSession().getTemplate(
						UConstants.MAPKEY_MENU, panelTemplate.menu);
			if (menuTemplate != null) {
				UMenu menu = MenuGenerator.parseMenu(menuTemplate,
						this.eventAdaptor);
				menu.setConditionMenu(menuTemplate.conditionMenu);
				setMenuParant(menu);
				setMenuTimeControl(panelTemplate.name,menu);
				setPopupMenu(menu);
			}
		}
		getinitDataFromHandler();
		return form;
	}

	public void resetInnerComponentBounds() {
		if (panelTemplate == null)
			return;
		if (panelTemplate.groupElementTemplate == null
				|| !(panelTemplate.groupElementTemplate.layoutMode
						.equals(UConstants.LAYOUTMODE_ONE)))
			return;
		Dimension size = container.getPreferredSize();
		if (panelTemplate.groupElementTemplate.componentTemplates.size() <= 0)
			return;
		UElementTemplate el = (UElementTemplate)(panelTemplate.groupElementTemplate.componentTemplates.get(0));
		String name = el.name;
		UComponentI com = (UComponentI) componentMap.get(name);
		com.resetShellBounds(0, 0, size.width, size.height);
	}

	public void setShellBounds(int x, int y, int w, int h) {
		// TODO Auto-generated method stub

//		Dimension dimension = new Dimension(w, h);
//		container.setMinimumSize(dimension);
//		container.setMaximumSize(dimension);
//		container.setPreferredSize(dimension);
		this.innerHeight = h;
		this.innerWidth = w;
		this.innerX = x;
		this.innerY = y;
		container.setBounds(innerX, innerY, innerWidth, innerHeight);
	}

	public URect getBoundRect() {
		// TODO Auto-generated method stub
		return new URect(this.innerX, this.innerY, this.innerWidth,
				this.innerHeight);
	}

	public boolean getCanScrolling() {
		// TODO Auto-generated method stub
		return true;
	}

	public void setAbsolutePositon(int x, int y, int w, int h){
		container.setBounds(x,y, w, h);
		setComponentBounds();
		
	}


	public void resetScrollPanelSize() {
		if(innerPanel != null && groupComponent != null && groupComponent.getCanScrolling()) {
			innerRect = groupComponent.makeSubComponentsBox(getAllSubComponents());	
				if(innerRect != null) {
			Dimension dimension = new Dimension(innerRect.w, innerRect.h);
			innerPanel.setMinimumSize(dimension);
			innerPanel.setMaximumSize(dimension);
			innerPanel.setPreferredSize(dimension);
			}
		}
	}

	public void propertyChange(PropertyChangeEvent evt) {
		String nameString = evt.getPropertyName();
		if (!nameString.equals("lastDividerLocation"))
			return;
		resetScrollPanelSize();
	}
	public void doBeforePanelClosed() {
		// TODO Auto-generated method stub
		sendhandlerProcess("doBeforePanelClosed", null, null);
	}

	public void setPopupMenu(UMenu menu) {
		// TODO Auto-generated method stub
		popupMenu = new UPopupMenu();
		int n = menu.getItemCount();
		while (menu.getItemCount() > 0) {
			popupMenu.add(menu.getItem(0));
		}
	}

	public UPopupMenu getUPopupMenu() {
		return popupMenu;
	}

	public void setPopupMenu(UPopupMenu popupMenu) {
		this.popupMenu = popupMenu;
	}

	public void displyPopMenu(Component com, int x, int y) {
		if (popupMenu != null && popupMenu.getSubElements().length >= 1)
			popupMenu.show(com, x, y);
	}

	class MouseEventProcess implements MouseListener {
		public void mouseClicked(MouseEvent mouseEvent) {
			if (mouseEvent.getButton() == MouseEvent.BUTTON3) {
					displyPopMenu((Component) mouseEvent.getSource(), mouseEvent
							.getX(), mouseEvent.getY());
				}
			}

		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
		}

		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}
	}
	
}
