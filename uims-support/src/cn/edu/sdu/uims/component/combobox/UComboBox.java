package cn.edu.sdu.uims.component.combobox;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.border.Border;
import javax.swing.plaf.metal.MetalComboBoxEditor;

import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.pi.ClientDataDictionaryI;
import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.common.reportdog.UFont;
import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.UimsFactory;
import cn.edu.sdu.uims.base.CheckObject;
import cn.edu.sdu.uims.base.UBorder;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UPanelI;
import cn.edu.sdu.uims.component.LabeValueTransI;
import cn.edu.sdu.uims.component.event.EventConstants;
import cn.edu.sdu.uims.component.menu.UMenu;
import cn.edu.sdu.uims.component.menu.UPopupMenu;
import cn.edu.sdu.uims.def.UElementTemplate;
import cn.edu.sdu.uims.def.UEventAttribute;
import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.handler.UHandlerI;
import cn.edu.sdu.uims.trans.URect;
import cn.edu.sdu.uims.util.UimsUtils;

public class UComboBox extends JComboBox implements LabeValueTransI,
		UComponentI, UComboBoxI {
	private MyComboBoxEditor myComboBoxEditor = null;
	protected boolean canActionEvent = false;
	private ActionListener actionListener = null;
	private UPanelI parent = null;
	private String componentName;
	private FilterI filter;
	protected UElementTemplate elementTemplate;

	protected boolean bEnabled = true;
	private boolean isCanEvent = false;

	public boolean getMultiple() {
		if (elementTemplate != null)
			return elementTemplate.multiple;
		else
			return false;
	}

	public class MyComboBoxEditor extends MetalComboBoxEditor {
		@Override
		public void setItem(Object arg0) {
			// TODO Auto-generated method stub
			setEditText(getSelectedString());
		}

		public void setEditText(String str) {
			editor.setText(str);
		}
	}

	public ActionListener getActionListener() {
		return actionListener;
	}

	public void setActionListener(ActionListener actionListener) {
		this.actionListener = actionListener;
	}

	public UComboBox() {
	}

	public void init() {
	}

	public List getCompnetDefautAddedDatasList() {
		return null;
	}

	public void addInitDataList() {
		if (elementTemplate == null)
			return;
		List list = null;
		if (elementTemplate.dictionary != null
				&& elementTemplate.dictionary.length() > 0) {
			ClientDataDictionaryI util = UimsFactory.getClientDataDictionaryI();
			if (util != null)
				list = util.getComboxListByCode(elementTemplate.dictionary);
		} else if (filter != null) {
			Object[] os = (Object[]) filter.getAddedData();
			if (os != null && os.length > 0) {
				list = new ArrayList();
				for (int i = 0; i < os.length; i++) {
					list.add(os[i]);
				}
			}
		} else if (elementTemplate.addedDatas != null) {
			list = elementTemplate.addedDatas;
		}
		if (list == null) {
			list = getCompnetDefautAddedDatasList();
		}
		if (list == null || list.size() == 0)
			return;
		this.removeAllItems();
		if (elementTemplate.multiple) {
			ListOptionInfo info;
			for (int i = 0; i < list.size(); i++) {
				info = (ListOptionInfo) list.get(i);
				addItem(new CheckObject(false, info));
			}
		} else {
			if (elementTemplate.addSelectItem) {
				addItem(UimsUtils.getPleaseSelectInfo());
			}
			if (elementTemplate.addAllItem) {
				addItem(UimsUtils.getSelectAllInfo("-2"));
			}
			for (int i = 0; i < list.size(); i++) {
				addItem(list.get(i));
			}
		}

	}

	public void initContents() {
		// TODO Auto-generated method stub
		this.setMaximumRowCount(20);
		if (elementTemplate.multiple) {
			myComboBoxEditor = new MyComboBoxEditor();
			this.setEditor(myComboBoxEditor);
			setRenderer(new CheckListCellRenderer());
			this.setActionCommand(this.getComponentName());
			this.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					if (itemSelected()) {
						if (actionListener != null)
							actionListener.actionPerformed(ae);
						if (canActionEvent ) {
							getUParent().getEventAdaptor().actionPerformed(
									getActionEvent());
						}
					}
				}
			});
		} else {
			this.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					if (actionListener != null)
						actionListener.actionPerformed(ae);
					if (canActionEvent && isCanEvent) {
						getUParent().getEventAdaptor().actionPerformed(ae);
					}
				}
			});
		}
		addInitDataList();
		isCanEvent = true;
	}

	public String getSelectedString() {
		int count = this.getItemCount();
		String str = "";
		CheckObject jcb;
		for (int i = 0; i < count; i++) {
			jcb = (CheckObject) this.getItemAt(i);
			if (jcb.bolValue) {
				str += jcb.toString() + ";";
			}
		}
		if (str.length() == 0)
			return str;
		else
			return str.substring(0, str.length() - 1);
	}

	private boolean itemSelected() {
		if (getSelectedItem() instanceof CheckObject) {
			CheckObject jcb = (CheckObject) getSelectedItem();
			jcb.bolValue = (!jcb.bolValue);
			setSelectedIndex(getSelectedIndex());
			return true;
		} else {
			return false;
		}
	}

	private ActionEvent getActionEvent() {
		return new ActionEvent(this, 0, this.getComponentName(), 0);
	}

	public void addEvents(UEventAttribute events[]) {
		int i;
		if (elementTemplate.multiple) {
			for (i = 0; i < events.length; i++) {
				if (events[i].name.equals(EventConstants.EVENT_ACTION)) {
					canActionEvent = true;
				}
			}
		} else {
			for (i = 0; i < events.length; i++) {
				if (events[i].name.equals(EventConstants.EVENT_ACTION)) {
					canActionEvent = true;
//					addActionListener(getUParent().getEventAdaptor());
				} else if (events[i].name.equals(EventConstants.EVENT_ITEM)) {
					addItemListener(getUParent().getEventAdaptor());
				}
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

	public void setBorder(int width, UColor color) {
		if (width == 0)
			setBorder((Border) null);
		else
			setBorder(BorderFactory.createLineBorder(color.color, width));
	}

	public void setBorder(UBorder border) {
		if (border.obj == null || border.obj instanceof Border)
			this.setBorder((Border) border.obj);
	}

	public Object getData() {
		if (elementTemplate.multiple) {
			ListOptionInfo t;
			List vc = new ArrayList();
			for (int i = 0; i < getItemCount(); i++) {
				CheckObject jcb = (CheckObject) getItemAt(i);
				if (jcb.bolValue) {
					if (jcb.value instanceof ListOptionInfo) {
						t = (ListOptionInfo) jcb.value;
						vc.add(t.getValue());
					} else
						vc.add(jcb.value);
				}
			}
			String sa[] = new String[vc.size()];
			for (int i = 0; i < vc.size(); i++)
				sa[i] = (String) vc.get(i);
			return sa;

		} else {
			if (elementTemplate.dictionary == null) {
				Object o = getSelectedItem();
				if (o == null)
					return null;
				else
					return this.getSelectedItem().toString();
			} else {
				return UimsUtils.getComboBoxSelectedValue(this);
			}
		}
	}

	public void setData(Object obj) {
		if (elementTemplate.multiple) {
			int i, j;
			boolean b;
			if (obj != null) {
				Object[] list = (Object[]) obj;
				for (i = 0; i < getItemCount(); i++) {
					CheckObject jcb = (CheckObject) getItemAt(i);
					b = false;
					j = 0;
					while (!b && j < list.length) {
						if (list[j] instanceof ListOptionInfo
								&& list[j].equals(jcb.value))
							b = true;
						else if (list[j].equals(((ListOptionInfo) jcb.value)
								.getValue()))
							b = true;
						j++;
					}
					jcb.bolValue = b;
				}
			} else {
				for (i = 0; i < getItemCount(); i++) {
					CheckObject jcb = (CheckObject) getItemAt(i);
					jcb.bolValue = false;
				}
			}
			myComboBoxEditor.setEditText(getSelectedString());
		} else {
			if (elementTemplate.dictionary != null) {
				if (obj != null)
					UimsUtils.setItemSelectedInComboBox(obj, this);
				else {
					if (this.getItemCount() > 0)
						this.setSelectedIndex(0);
					else
						UimsUtils.setItemSelectedInComboBox("", this);
				}
			} else {
				if (obj != null)
					this.setText(obj.toString());
			}
		}
	}

	public UTemplate getTemplate() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean hasEmptyFileds() {
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

	public void setColor(UColor c) {
		// TODO Auto-generated method stub

	}

	public void setFilter(FilterI filter) {
		// TODO Auto-generated method stub
		this.filter = filter;
	}

	public void setFilter1(FilterI filter) {
		// TODO Auto-generated method stub

	}

	public void setFont(UFont agr0) {
		// TODO Auto-generated method stub
		setFont(agr0.font);
	}

	public void setHorizontalAlignment(int arg0) {
		// TODO Auto-generated method stub

	}

	public void setTemplate(UTemplate template) {
		// TODO Auto-generated method stub
		this.elementTemplate = (UElementTemplate) template;
	}

	public void setText(String arg0) {
		// TODO Auto-generated method stub

	}

	public void setVerticalAlignment(int arg0) {
		// TODO Auto-generated method stub
	}

	public void setEnabled(boolean b) {
		bEnabled = b;
		super.setEnabled(bEnabled);
	}

	public void setAddedDatas(List list) {

		if (list == null || list.size() == 0) {
			setAddedDatas((Object[]) null);
		} else {
			setAddedDatas(list.toArray());
		}

	}

	public void setAddedDatas(Object[] o) {
		isCanEvent = false;
		if (elementTemplate.multiple) {
			removeAllItems();
			if (o == null || o.length == 0) {
				super.setEnabled(false);
			} else {
				ListOptionInfo info;
				for (int i = 0; i < o.length; i++) {
					info = (ListOptionInfo) o[i];
					addItem(new CheckObject(false, info));
				}
				super.setEnabled(bEnabled);
			}
		} else {
			removeAllItems();
			if (o == null || o.length == 0) {
				super.setEnabled(false);
			} else {
				if (elementTemplate != null && elementTemplate.addSelectItem) {
					addItem(UimsUtils.getPleaseSelectInfo());
				}
				if (elementTemplate.addAllItem) {
					addItem(UimsUtils.getSelectAllInfo("-2"));
				}
				for (int i = 0; i < o.length; i++) {
					addItem(o[i]);
				}
				if (o.length < 1)
					super.setEnabled(false);
				else
					super.setEnabled(bEnabled);
			}
		}
		isCanEvent = true;
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
		return filter;
	}

	public void updateAddedDatas() {
		// TODO Auto-generated method stub
		if (filter == null)
			return;
		Object o = filter.getAddedData();
		if (o != null)
			this.setAddedDatas((Object[]) o);
		else
			this.setAddedDatas((Object[]) null);
	}

	public Object getLabel(Object value) {
		// TODO Auto-generated method stub
		return value;
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

	@Override
	public void setComboBoxSize(int w, int h) {
		// TODO Auto-generated method stub
		
	}

}
