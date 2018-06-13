package cn.edu.sdu.uims.component.complex;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JLabel;

import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.common.pi.ClientDataDictionaryI;
import cn.edu.sdu.uims.UimsFactory;
import cn.edu.sdu.uims.component.combobox.UComboBox;
import cn.edu.sdu.uims.def.UElementTemplate;
import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.filter.MultiLevelFilterI;
import cn.edu.sdu.uims.util.UimsUtils;

public class UMultiLevelComboBox extends  UComplexPanel {
	ClientDataDictionaryI util = UimsFactory.getClientDataDictionaryI();
	private JLabel jLabel;
	private UComboBox comboBox[];
	private boolean isFinished = false;
	private FilterI filter;
	private Object[] getDictionaryList(){
		 Object [] a= new Object[0];
		if(elementTemplate == null || elementTemplate.dictionary == null || elementTemplate.dictionary.length() == 0) 
			return a;
		List list =  util.getComboxListByCode(elementTemplate.dictionary);
		if(list == null)
			return a;
		else
			return list.toArray();
	}
	public void initContents(){
		isFinished = false;
		if(elementTemplate == null)
			return;
		int i;
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		jLabel = new JLabel(elementTemplate.text);
		int colNum = elementTemplate.colNum;
		UElementTemplate e = new UElementTemplate();
		comboBox = new UComboBox[colNum];
		for(i = 0; i < comboBox.length;i++) {
			comboBox[i] = new UComboBox();
			e = new UElementTemplate();
			if(i == 0)
				e.dictionary = elementTemplate.dictionary;
			else
				e.dictionary = null;
			e.multiple = elementTemplate.multiple;
			e.addAllItem = elementTemplate.addAllItem;
			e.addSelectItem = elementTemplate.addSelectItem;
			comboBox[i].setElementTemplate(elementTemplate);
			comboBox[i].initContents();
			comboBox[i].setEditable(true);
			if(i < comboBox.length-1)
				comboBox[i].setActionListener(this);
		}
		comboBox[0].setAddedDatas(getDictionaryList());
		for(i = 0; i < comboBox.length;i++)
			comboBox[i].setData(null);
		AddCommpntToContainer(this);
		isFinished = true;
	}
	public void AddCommpntToContainer(Container container){
		container.add(jLabel);
		for(int i = 0; i <comboBox.length;i++) {
			container.add(comboBox[i].getAWTComponent());
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(!isFinished)
			return;
		UComboBox com = (UComboBox)e.getSource();
		int i = getActionComIndex(com);
		if(i < 0)
			return;
		if(this.elementTemplate.dictionary != null)
			updateNextComItemListDictionary(i);
		else
			updateNextComItemList(i);
			
	}
	public int getActionComIndex(UComboBox com) {
		if(com == null)
			return -1;
		for(int i = 0; i < comboBox.length-1;i++) {
			if(comboBox[i] == com)
				return i;
		}
		return -1;
	}
	public void updateNextComItemList(int index) {
		if(filter == null || !(filter instanceof MultiLevelFilterI))
			return;
		String codes[] = null;
		if(this.elementTemplate.multiple) {
			codes =  (String[])comboBox[index].getData();
		}else {
			Object o =  UimsUtils.getComboBoxSelectedValue(comboBox[index]);
			if(o != null) {
				codes = new String[]{(String)o};
			}
		}
		if( codes == null || codes.length == 0)  {
			comboBox[index+1].setAddedDatas((Object[])null);
			comboBox[index+1].setData(null);
			return;
		}
		MultiLevelFilterI f =  (MultiLevelFilterI)filter;
		List list = f.getAddedDataList(codes, index);
		if(list == null || list.size()== 0) {
			comboBox[index+1].setAddedDatas((Object[])null);			
		}else  {
			comboBox[index+1].setAddedDatas(list.toArray());
		}
		comboBox[index+1].setData(null);
	}
	public void updateNextComItemListDictionary(int index) {
		String sa [] = (String[]) comboBox[index].getData();
		if(sa == null || sa.length == 0) {
			comboBox[index+1].setAddedDatas((Object[])null);
			return;
		}
		int i, j;
		String code[];
		StringTokenizer sz;
		List temp;
		ListOptionInfo info;
		List list = new ArrayList();
		for(i = 0; i< sa.length;i++) {
			sz = new StringTokenizer(sa[i],"-");
			if(elementTemplate.dictionary != null) {
				code = new String[sz.countTokens()+1];
				code[0] = elementTemplate.dictionary;
				for(j = 1; j < code.length;j++)
					code[j] = sz.nextToken();
				temp =  util.getComboxListByCode(code);
			}else {
				code = new String[sz.countTokens()];
				for(j = 0; j < code.length;j++)
					code[j] = sz.nextToken();
				temp = this.getUParent().getHandler().getNextComItemList(this.getComponentName(), code);
			}
			if(temp!= null && temp.size() != 0) {
				for(j = 0; j < temp.size();j++) {
					info = (ListOptionInfo)temp.get(j);
					info.setValue(sa[i]+"-"+info.getValue());
					list.add(info);					
				}
			}
		}
		comboBox[index+1].setAddedDatas(list.toArray());
		comboBox[index+1].setData(null);
	}
	public Object getData(){
		if(elementTemplate.multiple) {
			String sa[][] = new String[elementTemplate.colNum][];
			for(int i = 0; i < comboBox.length; i++) {
				sa[i] = (String [])comboBox[i].getData();
			}
			return sa;
		}else {
			String sa[] = new String[elementTemplate.colNum];
			for(int i = 0; i < comboBox.length; i++) {
				sa[i] = (String)UimsUtils.getComboBoxSelectedValue(comboBox[i]);
			}
			return sa;
		}
	}
	public void setData(Object obj){
		if(obj == null ) {
			for(int i = 0; i < comboBox.length;i++)
				comboBox[i].setData(null);
		}else {
			
			int i;
			if(elementTemplate.multiple) {
				String[][] d = (String [][])obj;
				for(i = 0; i< d.length;i++) {
					comboBox[i].setData(d[i]);
				}
				if(d.length < comboBox.length) {
					for(i = d.length; i< comboBox.length;i++) {
						comboBox[i].setData(null);
					}
				}
			}else {
				String[] d = (String [])obj;
				for(i = 0; i< d.length;i++) {
					if(d[i] != null)
						UimsUtils.setItemSelectedInComboBox(d[i],comboBox[i]);
					else {
						if(comboBox[i].getItemCount() > 0)
							if(elementTemplate.setFirstItem)
								comboBox[i].setSelectedIndex(0);
							else
								comboBox[i].setSelectedIndex(-1);
						else
							UimsUtils.setItemSelectedInComboBox("",comboBox[i]);
					}
				}
				if(d.length < comboBox.length) {
					for(i = d.length; i< comboBox.length;i++) {
						comboBox[i].setData(null);
					}
				}				
			}
		}
	}
	public void setFilter(FilterI filter) {
		// TODO Auto-generated method stub
		this.filter = filter;
	}
	public FilterI getFilter() {
		// TODO Auto-generated method stub
		return filter;
	}
	public void updateAddedDatas() {
		// TODO Auto-generated method stub
		if(comboBox == null || comboBox.length < 1 || comboBox[0] == null)
			return;
		if (filter == null)
			return;
		Object o = filter.getAddedData();
		if (o != null)
			comboBox[0].setAddedDatas((Object[]) o);
		else
			comboBox[0].setAddedDatas((Object[])null);
	}
	public void setAddedDatas(Object[] o) {
		if(comboBox == null || comboBox.length < 1 || comboBox[0] == null)
			return;
		comboBox[0].setAddedDatas(o);
	}
	@Override
	public void setEditable(boolean b) {
		// TODO Auto-generated method stub
		if(comboBox == null)
			return;
		for(int i = 0; i <comboBox.length;i++) {
			if(comboBox[i] != null) {
				comboBox[i].setEnabled(b);
//				comboBox[i].setEditable(b);
			}
		}
	}

}
