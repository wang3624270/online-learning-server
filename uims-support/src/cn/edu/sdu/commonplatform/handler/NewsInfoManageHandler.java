package cn.edu.sdu.commonplatform.handler;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import javax.imageio.ImageIO;

import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;



import cn.edu.sdu.common.form.InfoPersonInfoForm;
import cn.edu.sdu.common.form.PersonSetI;
import cn.edu.sdu.common.form.UNewsFormIdI;
import cn.edu.sdu.uims.UimsFactory;
import cn.edu.sdu.uims.component.dialog.UDialogPanel;
import cn.edu.sdu.uims.component.method.GetFile;
import cn.edu.sdu.uims.component.table.UTablePanel;
import cn.edu.sdu.uims.form.impl.UTableForm;
import cn.edu.sdu.uims.handler.impl.UFormHandler;
import cn.edu.sdu.uims.util.UimsUtils;

public class NewsInfoManageHandler extends UFormHandler {
	public static final String ATTACHMENT_FOLDER_GROUPNEWS = "attach/news";
	public static final String FSWJLX_GROUPNEWS = "22";


	public List getGroupNewsTypeList(){
		return UimsFactory.getClientDataDictionaryI().getComboxListByCode("XWLXM");
	}
	
	public void doQuery() {
		RmiRequest request = new RmiRequest();
		request.add(RmiKeyConstants.KEY_FORM, this.component.getSubComponent("queryPanel").getData());
		request.setCmd("newsGetNewsInfoFormListByQueryForm");
		RmiResponse respond = UimsUtils.requestProcesser(request);
  		List list = null;
  		if(respond.getErrorMsg()==null) {
  			list=(List)respond.get(RmiKeyConstants.KEY_FORMLIST);
  		}
		UFormHandler sh = (UFormHandler)this.getSubComponentHandler("editPanel");
		UTableForm f = (UTableForm)sh.getForm();
		f.setItemsByList(list);
		sh.formToComponent();		
}
	
	public void start(){
		doQuery();
	}
	public UNewsFormIdI getNewsInfoForm(){
		return (UNewsFormIdI) this.getSubComponentHandler("editPanel").getSubComponentHandler("infoPanel").getForm();
	}
	public void processMouseEvent(Object o, String cmd){
		MouseEvent e = (MouseEvent)o;		
		if(cmd.equals("clicked")) {
			getSelectedNewsFormInfo();
		}
	}
	public void processActionEvent(Object o, String cmd){
		ActionEvent e =(ActionEvent)o;
		String amd = e.getActionCommand();
		if(amd.equals("query")){
			doQuery();
		}else if(amd.equals("personSearch")){
			personSearch();
		}else if(amd.equals("createButton")){
			doNew();
		}else if(amd.equals("saveButton")){
			doSave();
		}else if(amd.equals("deleteButton")){
			doDelete();
		}else if(amd.equals("uploadImage")){
			uploadImage();
		}else if(amd.equals("uploadHtml")){
			uploadHtml();
		}
	}
	/*public void getSelectedNewsFormInfo(){
		UTablePanel p = (UTablePanel)this.component.getSubComponent("editPanel").getSubComponent("newsTable");
		int indexs[] = p.getSelectedIndices();
		if(indexs == null || indexs.length == 0)
			return;
		UFormHandler h = (UFormHandler)this.getSubComponentHandler("editPanel");
		UTableForm tForm = (UTableForm)h.getForm();
		Object []items = tForm.getItems();
		if(items == null || items.length == 0)
			return ;
		UNewsFormIdI f= (UNewsFormIdI) items[indexs[0]];
		h = (UFormHandler)this.getSubComponentHandler("editPanel").getSubComponentHandler("infoPanel");
		h.formToComponent(f);
	}*/
	
	public void getSelectedNewsFormInfo() {
		UTablePanel p = (UTablePanel)this.component.getSubComponent("editPanel").getSubComponent("newsTable");
		List list = p.getSelectRowsList();
		if(list == null || list.size() == 0)
			return;
		UNewsFormIdI f = null ;
		for(int i = 0; i < list.size(); i++) {
			f = (UNewsFormIdI)list.get(i);
		}
		UFormHandler h = (UFormHandler)this.getSubComponentHandler("editPanel").getSubComponentHandler("infoPanel");
		h.formToComponent(f);
	}


	public Object doNew(){
		UFormHandler h = (UFormHandler)this.getSubComponentHandler("editPanel").getSubComponentHandler("infoPanel");
		h.formToComponent(UimsFactory.getClientMainI().creatNewsInfoForm());
		return null;
	}
	public void doSave(){
		UNewsFormIdI nForm = getNewsInfoForm();
//		NewsInfoInfoHandler sh = (NewsInfoInfoHandler)this.getSubComponentHandler("editPanel").getSubComponentHandler("infoPanel");
//		List attachList = sh.getAttachObjectList();
		RmiRequest request=new RmiRequest();
		request.add(RmiKeyConstants.KEY_FORM, nForm);
		request.setCmd("newsSaveOrUpdateNewsInfo");
		RmiResponse respond = UimsUtils.requestProcesser(request);
		if(respond.getErrorMsg() != null) {
			UimsUtils.messageBoxInfo(respond.getErrorMsg());
			return;
		}else {
			UimsUtils.messageBoxInfo("保存成功！");			
		}
		Integer newsId =(Integer)respond.get("newsId");
/*		if(nForm.getId() == null) 
			return;*/
		nForm.setId(newsId);
		doQuery();
	}
	public void doDelete(){
		UNewsFormIdI nForm = getNewsInfoForm();
		RmiRequest request=new RmiRequest();
		request.add("newsId", nForm.getId());
		request.setCmd("newsDeleteNewsInfo");
		RmiResponse respond = UimsUtils.requestProcesser(request);
		if(respond.getErrorMsg()!=null) {
			UimsUtils.messageBoxInfo(respond.getErrorMsg());
			return;
		}else {
			UimsUtils.messageBoxInfo("删除成功！");			
			doQuery();
		}
	}
	public void personSearch(){
		UDialogPanel dlg = (UDialogPanel)this.startDialog("commonplatformPersonChooseDialog", null);
		if(!dlg.getReturnValue().equals(dlg.RETURN_OK))
			return;
		UTableForm tForm = (UTableForm)dlg.getDialogForm();
		List list = tForm.getSelectItemList();
		if(list == null || list.size() == 0)
			return;
		InfoPersonInfoForm pf = (InfoPersonInfoForm)list.get(0);
		UFormHandler sh = (UFormHandler)this.getSubComponentHandler("editPanel").getSubComponentHandler("infoPanel");
		PersonSetI f = (PersonSetI)sh.getForm();
		sh.componentToForm();
		f.setPersonId(pf.getPersonId());
		f.setPerNum(pf.getPerNum());
		sh.formToComponent();
	}
	
	public void uploadImage(){
		File file = GetFile.getOpenFile();
		if(file == null) {
			return;
		}
		UFormHandler sh = (UFormHandler)this.getSubComponentHandler("editPanel").getSubComponentHandler("infoPanel");
		UNewsFormIdI f = (UNewsFormIdI)sh.getForm();
		Integer attachId = f.getAttachId();
		String newsNum = f.getNewsNum();
		if(newsNum == null || newsNum.equals("")) {
			UimsUtils.messageBoxInfo("新闻编号为空，请核对数据后再做操作！");
			return;
		}
		Integer newsId = f.getId();
		byte[] data = null;
 		try {
			BufferedImage img = ImageIO.read(new FileInputStream(file));
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ImageIO.write(img, "png", out);
			out.flush();
			data = out.toByteArray();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
 		if(data == null) {
 			return;
 		}
 		RmiRequest request = new RmiRequest();
 		RmiResponse response;
 		request.add("newsId", newsId);
 		request.add("attachId", attachId);
 		request.add("newsNum", newsNum);
 		request.add("data", data);
 		request.setCmd("newsUpLoadImageFile");
		response = UimsUtils.requestProcesser(request);
		if(response.getErrorMsg() != null) {
			UimsUtils.messageBoxChoice(response.getErrorMsg());
		}else {
			Integer backAttachId = (Integer)response.get("attachId");
			f.setAttachId(backAttachId);
			sh.formToComponent(f);
			UimsUtils.messageBoxInfo("上传成功！");
		}
 		
	}
	
	public void uploadHtml(){
		
	}
}
