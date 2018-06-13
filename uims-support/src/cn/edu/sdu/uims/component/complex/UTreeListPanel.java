package cn.edu.sdu.uims.component.complex;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTabbedPane;

import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.uims.component.complex.def.UTreeListTemplate;
import cn.edu.sdu.uims.component.list.UList;
import cn.edu.sdu.uims.component.tree.UTreePanel;
import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.filter.TreeFilterI;
import cn.edu.sdu.uims.form.IdNameObjectI;
import cn.edu.sdu.uims.form.impl.UTreeNodeForm;

public class UTreeListPanel extends UComplexPanel {
	
	private JTabbedPane tabbedPane;
	protected UList uList = null;
	protected UTreePanel uTree = null;
	private TreeFilterI filter = null;
	protected FilterI filter1 = null;

	public void initContents() {
		UTreeListTemplate template = (UTreeListTemplate)elementTemplate;
		if(template == null)
			return;
		if(template.dispModel == 1) {
			uTree = new UTreePanel();
			uTree.initContents();
			add(uTree);
		}
		else if(template.dispModel == 2) {
			uList= new UList();
			uList.initContents();
			add(uList);
		}else {
			uList= new UList();
			uList.initContents();
			uTree = new UTreePanel();
			uTree.initContents();
			setLayout(new BorderLayout());
			tabbedPane = new JTabbedPane();
			setPreferredSize(new Dimension(1,1));
			add(tabbedPane);
			tabbedPane.addTab(template.treeTitle, null, uTree.getAWTComponent());
			tabbedPane.addTab(template.listTitle, null, uList.getAWTComponent());
		}
	}
	public void setFilter(FilterI filter) {
		// TODO Auto-generated method stub
		this.filter = (TreeFilterI) filter;
	}

	public void setFilter1(FilterI filter) {
		// TODO Auto-generated method stub
		this.filter1 = filter;
	}
	public FilterI getFilter() {
		// TODO Auto-generated method stub
		return filter;
	}
	public FilterI getFilter1() {
		// TODO Auto-generated method stub
		return filter1;
	}
	private void addLeafObjectToList(String value, String label,UTreeNodeForm f,  List<ListOptionInfo> retList){
		IdNameObjectI obj;
		String label1;
		String value1;
		obj = (IdNameObjectI)f.getObj();
		value1 = value;
		label1 = label;
		if(value1.length() != 0)
			value1 += "-";
		if(label1.length() != 0)
			label1 += "-";
		value1 +=  obj.getId();
		label1 +=  obj.getName();
		List sonList = f.getSonList();
		if(sonList == null || sonList.size() ==0) {
			retList.add(new ListOptionInfo(value1, label1));
		}else {
			UTreeNodeForm sf;
			for(int i= 0; i < sonList.size();i++){
				sf = (UTreeNodeForm)sonList.get(i);
				addLeafObjectToList(value1,label1,sf, retList);
			}
		}
	}
	private List<ListOptionInfo> getLeftObjectList(Object data){
		List list = null;
		if(data instanceof UTreeNodeForm) {
			UTreeNodeForm tForm = (UTreeNodeForm)data;
			list = (List) tForm.getSonList();
		}else {
			list = (List) data;
		}
		if(list == null ||list.size()== 0)
			return null;
		List<ListOptionInfo> retList = new ArrayList<ListOptionInfo>();
		UTreeNodeForm f;
		for(int i = 0; i < list.size();i++) {
			f = (UTreeNodeForm)list.get(i);
			addLeafObjectToList("","",f, retList);
		}
		return retList;
	}
	
	public void updateAddedDatas() {
		// TODO Auto-generated method stub
		if (filter != null) {
			Object data =  filter.getAddedData();
			if(uTree!= null) {
				uTree.updateAddedDatas(data);
			}
			if(filter == null && uList != null) {
				Object o = getLeftObjectList(data);
				uList.updateAddedDatas(o);
			}
		}else if(filter1 != null && uList != null){
			uList.updateAddedDatas(filter1.getAddedData());
		}
	}

}
