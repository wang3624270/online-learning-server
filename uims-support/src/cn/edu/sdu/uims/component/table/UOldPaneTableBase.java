package cn.edu.sdu.uims.component.table;

import java.awt.Component;
import java.io.File;
import java.util.ArrayList;

import cn.edu.sdu.uims.base.UTableI;
import cn.edu.sdu.uims.component.method.GetFile;
import cn.edu.sdu.uims.component.panel.UOldPanelBase;

public class UOldPaneTableBase extends UOldPanelBase {
	protected UInnerTable mytable;
	
	/*public SuperTablePanel(UInnerTable table){
		mytable=table;
	}*/
	
	//目前带有table的Panel需要重写的方法，或许应该增加个SuperTablePanel???
	public Component getPrintComponent() {
		return mytable.getTable();
	}

	public void doCopy() {
		mytable.copySecletionToClipboard();
	}

	public void doRefresh() {

	}

	public void doExport() {
		File file = GetFile.getSaveFile("xls");
		if (file == null)
			return;
		mytable.saveTabalDataToExcel(file.getAbsolutePath());
	}
	
	public void doExport(File file) {
		if (file == null)
			return;
		mytable.saveTabalDataToExcel(file.getAbsolutePath());
	}

	public void doSelectAll() {
		mytable.checkAll();
	}

	public void doSelectNone() {
		mytable.clearChecked();
	}

	public void doSelectReverse() {
		mytable.selectReverse();
	}
	
	public UInnerTable getMytable(){
		return this.mytable;
	}
	
	public void initTable(ArrayList datas,UInnerTable mytable){
		
	}

	public UTableI getTableComponent() {
		// TODO Auto-generated method stub
		return null;
	}

}
