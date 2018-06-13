package cn.edu.sdu.commonplatform.handler;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ListSelectionEvent;

import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import cn.edu.sdu.common.form.UNewsFormIdI;
import cn.edu.sdu.common.util.CommonTool;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.component.list.UList;
import cn.edu.sdu.uims.component.method.GetFile;
import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.handler.impl.UInnerHandler;
import cn.edu.sdu.uims.util.UimsUtils;

public class NewsInfoInfoHandler extends UInnerHandler {
	public void processActionEvent(Object o, String cmd){
		ActionEvent e = (ActionEvent)o;
		if(e.getActionCommand().equals("uploadHtml")){
			uploadHtml();
		}else if(e.getActionCommand().equals("URL")) {
			viewNews();
		}else 
			super.processActionEvent(o, cmd);
	}
	public void processMouseEvent(Object o, String cmd){
		MouseEvent e = (MouseEvent)o;		
		if(cmd.equals("clicked")) {
			viewNews();
		}
	}
	
	public void uploadHtml() {
		File file = GetFile.getDirName();
		if (file == null)
			return;
		if(!file.isDirectory()) {
			UimsUtils.messageBoxInfo("请选择目录！");
			return;
		}
		UNewsFormIdI f = (UNewsFormIdI)this.getForm();
		RmiRequest request = new RmiRequest();
		RmiResponse response = new RmiResponse();
		request.add("id", f.getId());
		request.add("file", file);
		request.setCmd("newsUploadNewsFile");
		response = UimsUtils.requestProcesser(request);
		if(response.getErrorMsg() != null) {
			UimsUtils.messageBoxInfo(response.getErrorMsg());
		}else {
			String URL = (String) response.get("URL");
//			int index = URL.indexOf("news");
//			if(index != -1) {
//				URL = URL.substring(index);
//				f.setURL(URL);
//			}			
			formToComponent(f);
		}
	}
	
	public void viewNews() {
	
		UNewsFormIdI f = (UNewsFormIdI)this.getForm();
		String URL = f.getURL();
		if(URL == null || URL.equals("")) {
			return;
		}
		URL = "http://" + URL;
		 
		try {
			java.net.URI uri = java.net.URI.create(URL); 
			java.awt.Desktop dp = java.awt.Desktop.getDesktop(); 
			if(dp.isSupported(java.awt.Desktop.Action.BROWSE)) { 
				dp.browse(uri);// 获取系统默认浏览器打开链接 
			}
		} catch(IOException e) {
			e.printStackTrace();
		}  	
	}
	
	public void processListSelectionEvent(Object obj, String cmd) {
		ListSelectionEvent e = (ListSelectionEvent) obj;
		UList uList = (UList) e.getSource();
		String path = (String) uList.getSelectedValue();
		if (path == null)
			return;
		int ret = UimsUtils.messageBoxChoice(this.component.getAWTComponent(),"确认要删除选中的附件吗！");
		if(ret != UimsUtils.YES_OPTION)
			return;
		UComponentI com = this.component.getSubComponent("attachList");
		FilterI filter = com.getFilter();
		Object o[] = (Object[]) filter.getAddedData();
		if (o == null || o.length == 0)
			return;
		String str;
		List list;
		if (o.length == 1)
			list = null;
		else {
			list = new ArrayList();
			for (int i = 0; i < o.length; i++) {
				str = (String) o[i];
				if (path.equals(str)) {
					continue;
				}
				list.add(str);
			}
		}
		FilterI f = com.getFilter();
		f.setAddedData(list);
		com.updateAddedDatas();
	}

}
