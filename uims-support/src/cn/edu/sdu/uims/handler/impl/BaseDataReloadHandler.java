package cn.edu.sdu.uims.handler.impl;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import cn.edu.sdu.uims.util.UimsUtils;



public class BaseDataReloadHandler extends UFormHandler {

	public void processActionEvent(Object o, String cmd) {
		ActionEvent e = (ActionEvent) o;
		String acd = e.getActionCommand();
		if(acd.equals("dictionaryLoad")){
			RmiRequest request = new RmiRequest();
			request.setCmd("initDataDictionary");
			RmiResponse respond = UimsUtils.requestProcesser(request);
			if (respond.getErrorMsg() == null) {
				JOptionPane.showMessageDialog(null, "数据字典载成功！");				
			}									
		}else if(acd.equals("menuLoad")){
			RmiRequest request = new RmiRequest();
			request.setCmd("csClearRolyMenu");
			RmiResponse respond = UimsUtils.requestProcesser(request);
			if (respond.getErrorMsg() == null) {
				JOptionPane.showMessageDialog(null, "角色菜单加载成功！");				
			}						
		}else if(acd.equals("modelLoad")){
			RmiRequest request = new RmiRequest();
			request.setCmd("reloadModleTemplate");
			RmiResponse respond = UimsUtils.requestProcesser(request);
			if (respond.getErrorMsg() == null) {
				JOptionPane.showMessageDialog(null, "角色菜单加载成功！");				
			}				
		}else if(acd.equals("commonDataLoad")) {
			RmiRequest request = new RmiRequest();
			request.setCmd("commonBaseDataQueryClearCommonQueryBufferMap");
			RmiResponse respond = UimsUtils.requestProcesser(request);
			if (respond.getErrorMsg() == null) {
				JOptionPane.showMessageDialog(null, "公共数据加载成功！");				
			}				
			
		}
	}
}
