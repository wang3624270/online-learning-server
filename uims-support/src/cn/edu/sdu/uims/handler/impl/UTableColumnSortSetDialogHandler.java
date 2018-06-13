package cn.edu.sdu.uims.handler.impl;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.uims.component.table.UTablePanel;
import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.form.impl.UTableColumnSortItemForm;
import cn.edu.sdu.uims.form.impl.UTableForm;

public class UTableColumnSortSetDialogHandler extends UDialogHandler {
	public void initAddedData() {
		String str = this.getParameter("itemStr");
		if(str ==null || str.length()==0)
			return ;
		StringTokenizer sz = new StringTokenizer(str,";");
		int i =0;
		List list = new ArrayList();
		while(sz.hasMoreTokens()) {
			list.add(new ListOptionInfo(""+i, sz.nextToken()));
			i++;
		}
		UTablePanel t = (UTablePanel)(this.component.getSubComponent("dataTable"));
		FilterI  f = t.getColumnFilter("col");
		f.setAddedData(list);
		t.updateColumnAddedDatas("col");
	}
	public void processActionEvent(Object o, String cmd){
		ActionEvent e = (ActionEvent)o;
		String acm = e.getActionCommand();
		if(acm.equals("addRow")) {
			addRow();
		}else if(acm.equals("deleteRow")) {
			deleteRow();
		}else {
			super.processActionEvent(o, cmd);
		}
	}
	public void addRow(){
		this.componentToForm();
		UTableForm tForm = (UTableForm)dataForm;
		UTableColumnSortItemForm f= new UTableColumnSortItemForm();
		tForm.addItem(f);
		this.formToComponent();
	}
	public void deleteRow(){
		UTablePanel t = (UTablePanel)(this.component.getSubComponent("dataTable"));
		int index[] = t.getSelectedIndices();
		if(index ==null || index.length == 0)
			return;
		UTableForm tForm = (UTableForm)dataForm;
		tForm.removeObjects(index);
		this.formToComponent();	
	}
}
