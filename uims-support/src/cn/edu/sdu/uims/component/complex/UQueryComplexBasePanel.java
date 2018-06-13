package cn.edu.sdu.uims.component.complex;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.common.pi.ClientDataDictionaryI;
import cn.edu.sdu.common.util.DateTimeTool;
import cn.edu.sdu.commoncomponent.form.CommonBaseDataQueryForm;
import cn.edu.sdu.commoncomponent.form.ExtendItemObject;
import cn.edu.sdu.uims.UimsFactory;
import cn.edu.sdu.uims.base.UBorder;
import cn.edu.sdu.uims.component.combobox.UComboBox;
import cn.edu.sdu.uims.component.combobox.UComboBoxDlg;
import cn.edu.sdu.uims.component.combobox.UComboBoxI;
import cn.edu.sdu.uims.component.event.EventConstants;
import cn.edu.sdu.uims.def.UElementTemplate;
import cn.edu.sdu.uims.def.UEventAttribute;
import cn.edu.sdu.uims.def.UFilterTemplate;
import cn.edu.sdu.uims.def.UPanelTemplate;
import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.form.impl.CommonQueryBaseForm;
import cn.edu.sdu.uims.form.impl.RoleControlACtionItemForm;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.util.DataProcessUtils;

public class UQueryComplexBasePanel extends UComplexPanel {
	protected boolean actionEventCanSend = false;
	protected List<String> visibleNameList;
	protected HashMap<String, ExtendItemObject> extendComboBoxMap = null;
	protected HashMap<String, ExtendItemObject> extendFieldMap = null;
	protected HashMap<String, ExtendItemObject> extendButtonMap = null;
	protected HashMap<String, String> actionControlMap = null;
	private boolean isMerge = true;
	protected HashMap doCmdMap;

	public void setBorder(UBorder border) {
		// TODO Auto-generated method stub
		// this.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0),
		// 1));
		this.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 1));
	}
	
	public  CommonQueryBaseForm getDataForm(){
		return null;
	}
	public  void setDataForm(CommonQueryBaseForm form){
	}
	public  List<String> getDefaultVisibleNameList() {
		List visibleNameList = new ArrayList<String>();
		return visibleNameList;
	}
	public  List<String> getVisibleNameListByVisbelItems(String visString) {
		List visibleNameList = new ArrayList<String>();
		StringTokenizer sz = new StringTokenizer(visString, "/");
		visibleNameList = new ArrayList<String>();
		while (sz.hasMoreTokens()) {
			visibleNameList.add(sz.nextToken());
		}
		return visibleNameList;
	}

	public  List<String> getVissibleNameList(HashMap parameters) {
		if (parameters == null) {
			return getDefaultVisibleNameList();
		}
		String str = (String) parameters.get("visibleItems");
		if (str != null && str.length() != 0) {
			return getVisibleNameListByVisbelItems(str);
		}
		return null;
	}
	
	public  UElementTemplate getComboBoxElementTemplate(){
		UElementTemplate e = new UElementTemplate();
		e.multiple = elementTemplate.multiple;
		e.dlgSelect = elementTemplate.dlgSelect;
		e.addSelectItem = true;
		return e;
	}
	public  UElementTemplate getComboBoxElementTemplate(boolean addSelectItem){
		UElementTemplate e = new UElementTemplate();
		e.multiple = elementTemplate.multiple;
		e.dlgSelect = elementTemplate.dlgSelect;
		e.addSelectItem = addSelectItem;
		return e;
	}
	public  UElementTemplate getComboBoxElementTemplate(boolean addSelectItem, boolean addAllItem){
		UElementTemplate e = new UElementTemplate();
		e.multiple = elementTemplate.multiple;
		e.dlgSelect = elementTemplate.dlgSelect;
		e.addSelectItem = addSelectItem;
		e.addAllItem  = addAllItem;
		return e;
	}
	public  UElementTemplate getComboBoxElementTemplate(boolean addSelectItem, boolean addAllItem, boolean multiple,boolean dlgSelect ){
		UElementTemplate e = new UElementTemplate();
		e.multiple = multiple;
		e.dlgSelect = dlgSelect;
		e.addSelectItem = addSelectItem;
		e.addAllItem  = addAllItem;
		return e;
	}
	public int getLayoutType(HashMap parameters) {
		if (parameters == null || parameters.get("layout") == null)
			return FlowLayout.LEFT;
		else if (parameters.get("layout").toString().equals("right"))
			return FlowLayout.RIGHT;
		else if (parameters.get("layout").toString().equals("center"))
			return FlowLayout.CENTER;
		else
			return FlowLayout.LEFT;
	}
	public  Integer changeStringToInteger(String str) {
		if (str == null || str.length() == 0)
			return null;
		else
			return new Integer(str);
	}
	public  Integer[] changeStringsToIntegers(String strs[]) {
		if (strs == null || strs.length == 0)
			return null;
		else {
			Integer is[] = new Integer[strs.length];
			{
				for (int i = 0; i < strs.length; i++) {
					if (strs[i] == null || strs[i].length() == 0)
						is[i] = null;
					else
						is[i] = new Integer(strs[i]);
				}
			}
			return is;
		}
	}

	public int getDefaultQueryMode() {
		return 0;
	}
	public boolean areEqual(Object a[], Object b[]){
		if(a == null && b == null)
			return true;
		if(a == null && b != null || a!= null && b == null )
			return false;
		if( a.length != b.length)
			return false;
		for(int i = 0; i < a.length;i ++) {
			if(!a[i].equals(b[i]))
				return false;
		}
		return true;
	}

	public void setComStatusAttribute(Component com, String name) {
		if (actionControlMap == null || actionControlMap.get(name) == null)
			return;
		String status = actionControlMap.get(name);
		if (status == null || com == null)
			return;
		if (status.equals("1"))
			com.setVisible(true);
		else if (status.equals("2"))
			com.setVisible(false);
		else if (status.equals("3"))
			com.setEnabled(true);
		else if (status.equals("4"))
			com.setEnabled(false);
	}

	public void getRoleControlActionMap() {
		HashMap map = (HashMap) UimsFactory.getClientMainI().getSysRolePanelMap();
		if (map == null)
			return;
		UPanelTemplate pT = (UPanelTemplate) (this.getUParent().getTemplate());
		List list = (List) map.get(pT.name);
		if (list == null || list.size() == 0)
			return;
		int i = 0;
		RoleControlACtionItemForm form;
		for (i = 0; i < list.size(); i++) {
			form = (RoleControlACtionItemForm) list.get(i);
			if (form.getComponentName().equals(this.componentName)
					&& form.getMenuName() != null) {
				if (actionControlMap == null)
					actionControlMap = new HashMap<String, String>();
				actionControlMap.put(form.getMenuName(), form.getStatus());
			}
		}
	}

	public void setComObjectMethod(ExtendItemObject obj) {
		String mStr;
		try {
			if (obj.member != null) {
				mStr = "get" + obj.member.substring(0, 1).toUpperCase()
						+ obj.member.substring(1, obj.member.length());
				obj.getMethod = getDataForm().getClass().getMethod(mStr);
				if (obj.getMethod != null) {
					mStr = "set" + obj.member.substring(0, 1).toUpperCase()
							+ obj.member.substring(1, obj.member.length());
					obj.setMethod = getDataForm().getClass().getMethod(mStr,
							obj.getMethod.getReturnType());
				}
			}
			if (obj.members != null) {
				mStr = "get" + obj.members.substring(0, 1).toUpperCase()
						+ obj.members.substring(1, obj.members.length());
				obj.getMethods = getDataForm().getClass().getMethod(mStr);
				if (obj.getMethods != null) {
					mStr = "set" + obj.members.substring(0, 1).toUpperCase()
							+ obj.members.substring(1, obj.members.length());
					obj.setMethods = getDataForm().getClass().getMethod(mStr,
							obj.getMethods.getReturnType());
				}
			}
		} catch (Exception e) {
		}

	}
	public void clearComAddedData(String comName){
		UComboBoxI com= (UComboBoxI)this.getCompoent(comName);
		com.setAddedDatas(new ArrayList());
		com.setData(null);
		com.setSelectedIndex(-1);
	}

	public void setDefaultFormData() {
	}

	public CommonQueryBaseForm getDefautDataForm() {
		return new CommonQueryBaseForm();
	}
	public void addComponentToContainer(Container c, Component label, Component ... coms){
		if(isMerge) {
			JPanel p = new JPanel();
			p.setLayout(new FlowLayout());
			if(label != null)
			p.add(label);
			if(coms != null && coms.length != 0) {
				for(int i = 0; i < coms.length;i++) 
					p.add(coms[i]);
			}
			c.add(p);
		}
		else {
			if(label != null)
			c.add(label);
			if(coms != null && coms.length == 0) {
				for(int i = 0; i < coms.length;i++) 
					c.add(coms[i]);
			}
		}
	}
	public void initDataForm() {
		CommonQueryBaseForm dataForm = this.getDataForm();
		if (parameters == null || parameters.get("dataFormClass") == null) {
			dataForm = getDefautDataForm();
			setDataForm(dataForm);
		} else {
			String className = (String) parameters.get("dataFormClass");
			ExtendItemObject obj;
			Iterator it;
			String name;
			try {
				dataForm = (CommonQueryBaseForm) Class.forName(className)
						.newInstance();
				setDataForm(dataForm);
			} catch (Exception e) {
				return;
			}
			if (extendComboBoxMap != null) {
				it = extendComboBoxMap.keySet().iterator();
				while (it.hasNext()) {
					name = (String) it.next();
					obj = extendComboBoxMap.get(name);
					setComObjectMethod(obj);
				}
			}
			if (extendFieldMap != null) {
				it = extendFieldMap.keySet().iterator();
				while (it.hasNext()) {
					name = (String) it.next();
					obj = extendFieldMap.get(name);
					setComObjectMethod(obj);
				}
			}
		}
		setDefaultFormData();
	}
	public  List<ListOptionInfo> getOptionListFromString(String value) {
		if (value == null)
			return null;
		StringTokenizer sz = new StringTokenizer(value, ")");
		List<ListOptionInfo> retList = new ArrayList<ListOptionInfo>();
		String str, as;
		int index;
		while (sz.hasMoreTokens()) {
			as = sz.nextToken();
			str = as.substring(1, as.length());
			index = str.indexOf("_");
			if (index > 0)
				retList.add(new ListOptionInfo(str.substring(0, index), str
						.substring(index + 1, str.length())));
		}
		return retList;
	}

	public  HashMap<String, ExtendItemObject> getExtendItemMap(
			String parStr) {
		HashMap<String, ExtendItemObject> map = new HashMap<String, ExtendItemObject>();
		StringTokenizer sz = new StringTokenizer(parStr, "/");
		String tempStr, str;
		StringTokenizer tsz;
		int count;
		String sW;
		String name, value;
		int index;
		while (sz.hasMoreTokens()) {
			tempStr = sz.nextToken();
			tsz = new StringTokenizer(tempStr, ",");
			if (tsz.countTokens() == 0)
				continue;
			ExtendItemObject o = new ExtendItemObject();
			o.addSelectItem = false;
			sW = null;
			while (tsz.hasMoreTokens()) {
				str = tsz.nextToken();
				index = str.indexOf("-");
				if (index < 0)
					continue;
				name = str.substring(0, index);
				value = str.substring(index + 1, str.length());
				if (name.equals("name"))
					o.name = value;
				else if (name.equals("label"))
					o.label = value;
				else if (name.equals("enLabel"))
					o.enLabel = value;
				else if (name.equals("member"))
					o.member = value;
				else if (name.equals("members"))
					o.member = value;
				else if (name.equals("width")) {
					o.width = Integer.parseInt(value);
				} else if (name.equals("dictionary")) {
					o.dictionary = value;
				} else if (name.equals("filter")) {
					o.filter = value;
				} else if (name.equals("option")) {
					o.optionList = getOptionListFromString(value);
				} else if (name.equals("type")) {
					o.type = value;
				} else if (name.equals("addSelectItem")) {
					if (value.equals("true"))
						o.addSelectItem = true;
				}
			}
			if (o.member == null)
				o.member = o.name;
			if (o.members == null)
				o.members = o.name + "s";
			map.put(o.name, o);
		}
		return map;
	}
	
	public  HashMap<String, ExtendItemObject> getExtendNameMap(
			HashMap parameters, String key) {
		if (parameters == null) {
			return null;
		}
		String str = (String) parameters.get(key);
		if (str != null && str.length() != 0) {
			return getExtendItemMap(str);
		}
		return null;
	}

	public void getExtendNameList() {
		extendComboBoxMap = getExtendNameMap(parameters,
				"comboBoxItems");
		extendFieldMap = getExtendNameMap(parameters,
				"fieldItems");
		extendButtonMap = getExtendNameMap(parameters,
				"buttonItems");
	}

	public boolean addSelfComponent(String comName, int h) {
		return false;
	}
	public UComboBoxI newComboBox(boolean multiple, boolean dlgSelect){
		if(multiple  && dlgSelect)
			return new UComboBoxDlg();
		else
			return new UComboBox();
	}
	
	public  UComboBoxI getComboBox(String name, List list, int count, int w, int h){
		UElementTemplate e = getComboBoxElementTemplate();
		UComboBoxI box = newComboBox(e.multiple,e.dlgSelect);
		box.setElementTemplate(e);
		box.initContents();
		box.setComboBoxSize(w, h);
		box.setEditable(true);
		box.addActionListener(this);
		if(name != null)
			setComStatusAttribute(box.getAWTComponent(), name);
		box.setAddedDatas(list);
		box.setData(null);
		return box;
	}
	public void initextendComponent(int h){
		ExtendItemObject obj;
		Iterator it;
		String comName;
		Object f = getDataForm();
		String methodName;
		Method m;
		boolean isEnglish = UimsFactory.getClientMainI().isEnglishVersion();
		Class c, rc;
		try {
			if(f !=null) 
				c = f.getClass();
			else
				c = null;
			if (extendComboBoxMap != null) {
				ClientDataDictionaryI util = UimsFactory.getClientDataDictionaryI();
				List list;
				UComboBoxI tf ;
				UFilterTemplate filterTemplate;
				FilterI filter;
				UElementTemplate ee;
				it = extendComboBoxMap.keySet().iterator();
				while (it.hasNext()) {
					comName = (String) it.next();
					obj = extendComboBoxMap.get(comName);
					ee  = new UElementTemplate();
					ee.dictionary = obj.dictionary;
					ee.addSelectItem = obj.addSelectItem;
					ee.addedDatas = obj.optionList;
					ee.multiple = this.elementTemplate.multiple;
					ee.dlgSelect = this.elementTemplate.dlgSelect;
					tf = newComboBox(ee.multiple,ee.dlgSelect);
					tf.setElementTemplate(ee);
					if( obj.filter != null){
						filterTemplate = UFactory.getModelSession()
									.getFilterTemplateByName(obj.filter);
						if(filterTemplate != null) {
							filter = filterTemplate.newInstance();
							filter.init(filterTemplate.parameter);
							tf.setFilter(filter);
						}
						ee.filter = obj.filter;
					}
					tf.initContents();
					if(obj.width < 80)
						tf.setComboBoxSize(80, 25);
					else
						tf.setComboBoxSize(obj.width, 25);
					tf.addActionListener(this);
					if(obj.doCmd != null && obj.doCmd.length() > 0) {
						doCmdMap.put(tf,obj.doCmd);
					}
					tf.setEditable(true);
					if(obj.member != null) {
						if(c != null) {
							methodName = "get" + obj.member.substring(0,1).toUpperCase() + obj.member.substring(1,obj.member.length());
							m = c.getMethod(methodName);
							if(m != null) {
								obj.getMethod = m;
								rc = m.getReturnType();
								methodName = "set" + obj.member.substring(0,1).toUpperCase() + obj.member.substring(1,obj.member.length());
								m = c.getMethod(methodName,rc);
								if(m != null)
									obj.setMethod = m;
							}
						}
					}
					tf.setData(null);
					if(obj.name != null)
						setComStatusAttribute(tf.getAWTComponent(), obj.name);
					obj.com = tf;
				}
			}
			if (extendFieldMap != null) {
				JTextField tf;
				it = extendFieldMap.keySet().iterator();
				while (it.hasNext()) {
					comName = (String) it.next();
					obj = extendFieldMap.get(comName);
					if(obj.member != null) {
						if(c != null) {
							methodName = "get" + obj.member.substring(0,1).toUpperCase() + obj.member.substring(1,obj.member.length());
							m = c.getMethod(methodName);
							if(m != null) {
								obj.getMethod = m;
								rc = m.getReturnType();
								methodName = "set" + obj.member.substring(0,1).toUpperCase() + obj.member.substring(1,obj.member.length());
								m = c.getMethod(methodName,rc);
								if(m != null)
									obj.setMethod = m;
							}
						}
					}
					tf = new JTextField();
					tf.setColumns(12);
					tf.setPreferredSize(new Dimension(obj.width, h));
					tf.addActionListener(this);
					obj.com = tf;
				}
			}
			if (extendButtonMap != null) {
				JButton b;
				it = extendButtonMap.keySet().iterator();
				while (it.hasNext()) {
					comName = (String) it.next();
					obj = extendButtonMap.get(comName);
					if(isEnglish)
						b = new JButton(obj.enLabel);
					else
						b = new JButton(obj.label);						
					b.setActionCommand(obj.member);
					b.addActionListener(this);
					obj.com = b;
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	public List getDictionaryTypeCodeList(String... typecode) {
		ClientDataDictionaryI util = UimsFactory.getClientDataDictionaryI();
		if (util == null)
			return null;
		List list = (ArrayList) util.getComboxListByCode(typecode);
		return list;
	}
	public void addEvents(UEventAttribute events[]) {
		int i;
		for (i = 0; i < events.length; i++) {
			if (events[i].name.equals(EventConstants.EVENT_ACTION)) {
				actionEventCanSend = true;
			}
		}
	}

	public boolean processComponentChange(ActionEvent e) {
		return false;
	}

	public  void setSelectItemOfComboBox(UComboBoxI box, Object str) {
		if (box == null || str == null || box.getMultiple()){
			if(box!=null)
				box.setSelectedIndex(-1);
			return;
		}
		int count = box.getItemCount();
		if (count == 0)
			return;
		ListOptionInfo info;
		for (int i = 0; i < count; i++) {
			info = (ListOptionInfo) box.getItemAt(i);
			if (info.getValue().equals(str.toString())) {
				box.setSelectedIndex(i);
				return;
			}
		}
	}
	public  void setSelectItemsOfComboBox(UComboBoxI box, Object str) {
		if (box == null || str == null && !box.getMultiple())
			return;
		box.setData(str);
	}
	public String getSelectItemOfComboBox(UComboBoxI box) {
		if (box == null || box.getMultiple())
			return null;
		ListOptionInfo info = (ListOptionInfo) box.getSelectedItem();
		if (info == null)
			return null;
		else
			return info.getValue();
	}
	public  String[] getSelectItemsOfComboBox(UComboBoxI box) {
		if (box == null || !box.getMultiple())
			return null;
		else
			return (String[]) box.getData();
	}

	
	public void setExtendDataToComboBox() {
		CommonQueryBaseForm dataForm = getDataForm();
		if (extendComboBoxMap == null || dataForm == null)
			return;
		JComboBox f;
		Object o;
		ExtendItemObject obj;
		String name;
		Iterator it;
		try {
			it = extendComboBoxMap.keySet().iterator();
			while (it.hasNext()) {
				name = (String) it.next();
				obj = extendComboBoxMap.get(name);
				if (obj.getMethod != null) {
					o = obj.getMethod.invoke(dataForm);
				} else {
					o = dataForm.getAttributeData(obj.member);
				}
				setSelectItemOfComboBox((UComboBox) obj.com,o);
				if (obj.getMethods != null) {
					o = obj.getMethods.invoke(dataForm);
				} else {
					o = dataForm.getAttributeData(obj.members);
				}
				setSelectItemsOfComboBox((UComboBox) obj.com,
						o);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void comboBoxToExtendData() {
		CommonQueryBaseForm dataForm = getDataForm();
		if (extendComboBoxMap == null || dataForm == null)
			return;
		JTextField f;
		Object o;
		ExtendItemObject obj;
		String name;
		Iterator it;
		try {
			it = extendComboBoxMap.keySet().iterator();
			while (it.hasNext()) {
				name = (String) it.next();
				obj = extendComboBoxMap.get(name);
				if (obj.setMethod == null) {
					dataForm.setAttributeData(obj.member,getSelectItemOfComboBox((UComboBox) obj.com));
				} else {
					obj.setMethod.invoke(dataForm, getSelectItemOfComboBox((UComboBox) obj.com));
				}
				if (obj.setMethods == null) {
					dataForm.setAttributeDatas(obj.members, getSelectItemsOfComboBox((UComboBox) obj.com));
				} else {
					String g[] =getSelectItemsOfComboBox((UComboBox) obj.com);
					obj.setMethods.invoke(dataForm, (Object)g);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setExtendDataToTextField() {
		CommonQueryBaseForm dataForm = getDataForm();
		String name;
		Iterator it;
		if (extendFieldMap == null || dataForm == null)
			return;
		JTextField f;
		Object o;
		ExtendItemObject obj;
		try {
			it = extendFieldMap.keySet().iterator();
			while (it.hasNext()) {
				name = (String) it.next();
				obj = extendFieldMap.get(name);
				if (obj.getMethod != null) {
					o = obj.getMethod.invoke(dataForm);
				} else {
					o = dataForm.getAttributeData(obj.member);
				}
				f = (JTextField) obj.com;
				if (o == null)
					f.setText("");
				else
					f.setText(o.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void textFieldToExtendData() {
		CommonQueryBaseForm dataForm = getDataForm();
		String name;
		Iterator it;
		if (extendFieldMap == null || dataForm == null)
			return;
		JTextField f;
		Object o;
		ExtendItemObject obj;
		try {
			it = extendFieldMap.keySet().iterator();
			while (it.hasNext()) {
				name = (String) it.next();
				obj = extendFieldMap.get(name);
				f = (JTextField) obj.com;
				if (obj.getMethod == null) {
					dataForm.setAttributeData(obj.member, f.getText());
				} else {
					o = DataProcessUtils.changeStringToObject(
							obj.getMethod.getReturnType(), f.getText());
					obj.setMethod.invoke(dataForm, o);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void updataExtendComboxAddedData(String comName, List list) {

		if (extendComboBoxMap == null)
			return;
		ExtendItemObject obj = extendComboBoxMap.get(comName);
		if (obj == null)
			return;
		UComboBox tf = (UComboBox) (obj.com);
		if (tf == null)
			return;
		tf.setAddedDatas(list);
	}
	
	
	public Component getSelfComponent(String comName) {
		return null;
	}
	public void clearSelectItems(){
		initDataForm();
		formToComponent();
	}

	public void componentToForm(){
		
	}
	public void formToComponent(){
		
	}
	public Object getData() {
		componentToForm();
		return getDataForm();
	}	
	public void setData(Object obj) {
		this.setData((CommonBaseDataQueryForm)obj);
		formToComponent();
	}

	public Component getCompoent(String comName) {
		return null;
	}
	
	public List getYearList() {
		List yearList = new ArrayList();
		int currentYear = DateTimeTool.getYear();
		for (int year = currentYear+1; year >= 2005; year--) {
			yearList.add(new ListOptionInfo("" + year, "" + year));
		}
		return yearList;
	}

	public List getMonthList() {
		List monthList = new ArrayList();
		for (int month = 1; month <= 12; month++) {
			monthList.add(new ListOptionInfo("" + month, "" + month));
		}
		return monthList;
	}
	public void updateComAddedDataList(String comName, List dataList){
		
	}
}
