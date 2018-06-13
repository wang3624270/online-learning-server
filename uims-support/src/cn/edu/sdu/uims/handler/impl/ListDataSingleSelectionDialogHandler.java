package cn.edu.sdu.uims.handler.impl;

import javax.swing.event.ListSelectionEvent;

import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.uims.component.dialog.UDialogPanel;
import cn.edu.sdu.uims.component.list.UList;
import cn.edu.sdu.uims.form.impl.UDataListForm;


public class ListDataSingleSelectionDialogHandler extends UDialogHandler {

	public void processListSelectionEvent(Object obj, String cmd){
		ListSelectionEvent e = (ListSelectionEvent)obj;
		UDataListForm f = (UDataListForm)dataForm;
		UList uList = (UList)e.getSource();
		Object o = uList.getSelectedValue();
		if(o == null) 
			return;
		if(o instanceof ListOptionInfo ) {
			ListOptionInfo info = (ListOptionInfo)uList.getSelectedValue();
			if( info.getValue() == null) 
				return;
			f.setValue(info.getValue());
		}else {
			f.setValue(o);			
		}
		UDialogPanel dlg = (UDialogPanel)component;
		dlg.doOk();
	}
}
