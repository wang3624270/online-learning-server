package cn.edu.sdu.uims.handler.impl;

import java.awt.event.ActionEvent;

import cn.edu.sdu.commoncomponent.form.CommonBaseDataQueryForm;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UPanelI;

public class UQueryFormHandler extends UFormHandler {
	public CommonBaseDataQueryForm getQueryForm(ActionEvent e){
		UComponentI  com = (UComponentI) e.getSource();
		if(com instanceof UPanelI ) {
			UPanelI cp = (UPanelI) com;
			com = cp.getCurrentDisplayPageCommonent();
		}
		return  (CommonBaseDataQueryForm) com
					.getData();
	}
}
