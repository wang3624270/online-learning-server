package cn.edu.sdu.uims.graph.interaction;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.HashMap;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.common.reportdog.UFont;
import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.base.UBorder;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.component.UActionComponentI;
import cn.edu.sdu.uims.component.button.ui.UBasicToggleButtonUI;
import cn.edu.sdu.uims.component.event.EventConstants;
import cn.edu.sdu.uims.component.event.GraphInteractionEvent;
import cn.edu.sdu.uims.component.event.GraphInteractionListener;
import cn.edu.sdu.uims.component.event.UEventListener;
import cn.edu.sdu.uims.component.menu.UMenu;
import cn.edu.sdu.uims.component.menu.UPopupMenu;
import cn.edu.sdu.uims.def.UButtonTemplate;
import cn.edu.sdu.uims.def.UElementTemplate;
import cn.edu.sdu.uims.def.UEventAttribute;
import cn.edu.sdu.uims.def.UGraphButtonBarTemplate;
import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.handler.UHandlerI;
import cn.edu.sdu.uims.trans.URect;

public class UGraphButtonBar extends JPanel implements UComponentI,
		ActionListener {
	private String componentName;
	private UPanelI parentI;
	private UGraphButtonBarTemplate graphButtonBarbTemplate = null;
	private ActionListener actionListent = null;
	private GraphInteractionListener graphInteractionListener = null;
	private int innerWidth = -1, innerHeight = -1, innerX = -1, innerY = -1;
	protected UElementTemplate elementTemplate;

	public void addEvents(UEventAttribute[] events) {
		UEventListener l = getUParent().getEventAdaptor();
		for (int i = 0; i < events.length; i++) {
			if (events[i].name.equals(EventConstants.EVENT_GRAPHINTERACTION)) {
				if (l instanceof GraphInteractionListener)
					graphInteractionListener = (GraphInteractionListener) l;
			} else if (events[i].name.equals(EventConstants.EVENT_ACTION)) {
				if (l instanceof ActionListener)
					actionListent = (ActionListener) l;
			}
		}
	}

	public void addActionListener(ActionListener l) {
		actionListent = l;
	}

	public URect getBoundRect() {
		// TODO Auto-generated method stub
		return new URect(this.innerX, this.innerY, this.innerWidth,
				this.innerHeight);
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
		return null;
	}

	public FilterI getFilter() {
		// TODO Auto-generated method stub
		return null;
	}


	public UTemplate getTemplate() {
		// TODO Auto-generated method stub
		return graphButtonBarbTemplate;
	}

	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}

	public UPanelI getUParent() {
		// TODO Auto-generated method stub
		return parentI;
	}

	public boolean hasEmptyFileds() {
		// TODO Auto-generated method stub
		return false;
	}

	public void initContents() {
		// TODO Auto-generated method stub
		init();
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
		componentName = name;
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

	}

	public void setHandler(UHandlerI handler) {
		// TODO Auto-generated method stub

	}

	public void setHorizontalAlignment(int arg0) {
		// TODO Auto-generated method stub

	}

	public void setInitComponentData(Object data) {
		// TODO Auto-generated method stub

	}

	public void setPopupMenu(UMenu menu) {
		// TODO Auto-generated method stub

	}

	public void setTemplate(UTemplate template) {
		// TODO Auto-generated method stub
		graphButtonBarbTemplate = (UGraphButtonBarTemplate) template;
	}

	public void setText(String arg0) {
		// TODO Auto-generated method stub

	}

	public void setUParent(UPanelI parent) {
		// TODO Auto-generated method stub
		parentI = parent;
	}

	public void setVerticalAlignment(int arg0) {
		// TODO Auto-generated method stub

	}

	public void updateAddedDatas() {
		// TODO Auto-generated method stub

	}

	public void init() {
		if (graphButtonBarbTemplate == null)
			return;
		if (graphButtonBarbTemplate.row == 1) {
			initFlowButtonBar();
		} else {
			initGridButtonBar();
		}
	}

	public void initFlowButtonBar() {
		setLayout(new FlowLayout(FlowLayout.LEFT, graphButtonBarbTemplate.row, graphButtonBarbTemplate.column));
		ButtonGroup buttonGroup = new ButtonGroup();
		int i = 0;
		int width = 100, height = 30, x = 0, y = 0;
		UComponentI com;
		UButtonTemplate item;
		for (i = 0; i < graphButtonBarbTemplate.items.size(); i++) {
			item = (UButtonTemplate)graphButtonBarbTemplate.items.get(i);
			com = (UComponentI) item.newComponent();
			com.setTemplate(item);
			if (com instanceof AbstractButton) {
				UActionComponentI acom = (UActionComponentI) com;
				acom.setTemplate((UButtonTemplate)graphButtonBarbTemplate.items.get(i));
				acom.initContents();
				acom.setShellBounds(x, y, width, height);
				if (acom instanceof JToggleButton)
					((JToggleButton) acom).setUI(new UBasicToggleButtonUI());
				acom.addActionListener(this);
				Component component = com.getAWTComponent();
				buttonGroup.add((AbstractButton) component);
				add(component);
			}
		}
	}

	public void initGridButtonBar() {
		// TODO Auto-generated method stub
		if (this.graphButtonBarbTemplate == null)
			return;
		GridBagConstraints c;
		int gridx, gridy, gridwidth, gridheight, anchor, fill, ipadx, ipady;
		double weightx, weighty;
		Insets inset;
		GridBagLayout gridbag = new GridBagLayout();
		setLayout(gridbag);
		int col, row, btns = 0;
		ButtonGroup buttonGroup = new ButtonGroup();

		UComponentI com;
		UButtonTemplate item;
		// 计算按钮高度和宽度
		int width = 30, height = 30, x = 0, y = 0;
		if (innerWidth > 0) {
			width = innerWidth / graphButtonBarbTemplate.column;

		}
		if (innerHeight > 0) {
			height = innerHeight / graphButtonBarbTemplate.row;
		}
		int i = 0;
		for (i = 0; i < graphButtonBarbTemplate.items.size(); i++) {
			item = (UButtonTemplate)graphButtonBarbTemplate.items.get(i);
			com = (UComponentI) item.newComponent();
			if (com instanceof AbstractButton) {
				UActionComponentI acom = (UActionComponentI) com;
				acom.setTemplate(item);
				acom.initContents();
				acom.setShellBounds(x, y, width, height);
				if (acom instanceof JToggleButton)
					((JToggleButton) acom).setUI(new UBasicToggleButtonUI());
				acom.addActionListener(this);

				row = btns / graphButtonBarbTemplate.column;
				col = btns % graphButtonBarbTemplate.column;
				gridy = row;
				gridx = col;
				gridwidth = 1;
				gridheight = 1;
				weightx = 0;
				weighty = 0;
				anchor = GridBagConstraints.CENTER;
				fill = GridBagConstraints.NONE;
				inset = new Insets(0, 0, 0, 0);
				ipadx = 0;
				ipady = 0;
				c = new GridBagConstraints(gridx, gridy, gridwidth, gridheight,
						weightx, weighty, anchor, fill, inset, ipadx, ipady);
				Component component = acom.getAWTComponent();

				add(component, c);
				buttonGroup.add((AbstractButton) component);
				btns++;
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		UActionComponentI acom = (UActionComponentI) e.getSource();
		UButtonTemplate template = (UButtonTemplate) acom.getTemplate();
		if (template.userTaskName == null || template.userTaskName.equals("")) {
			if (actionListent != null) {
				ActionEvent aEvent = new ActionEvent(this, e.getID(),
						template.cmd);
				actionListent.actionPerformed(aEvent);
			}
		} else {
			if (graphInteractionListener != null) {
				GraphInteractionEvent re = new GraphInteractionEvent(this,
						template.userTaskName, template.methodName);
				graphInteractionListener.commandSelected(re);
			}
		}
	}

	public void setShellBounds(int x, int y, int w, int h) {
		// TODO Auto-generated method stub
		Dimension dimension = new Dimension(w, h);
		this.setMinimumSize(dimension);
		this.setMaximumSize(dimension);
		this.setPreferredSize(dimension);
		this.innerHeight = h;
		this.innerWidth = w;
		this.innerX = x;
		this.innerY = y;
	}

	public String getDataFormMember() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setDataFormMember(String dataFormMember) {
		// TODO Auto-generated method stub

	}

	public Container getContainer() {
		// TODO Auto-generated method stub
		return null;
	}

	public void setContainer(Container container) {
		// TODO Auto-generated method stub

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

	public GraphInteractionListener getGraphInteractionListener() {
		return graphInteractionListener;
	}

	public void setGraphInteractionListener(
			GraphInteractionListener graphInteractionListener) {
		this.graphInteractionListener = graphInteractionListener;
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
