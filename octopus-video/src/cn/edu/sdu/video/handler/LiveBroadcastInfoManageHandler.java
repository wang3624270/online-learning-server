package cn.edu.sdu.video.handler;

import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.event.ListSelectionEvent;

import org.sdu.rmi.RmiKeyConstants;
import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import com.video.form.LiveBroadcastInfoForm;

import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.component.complex.avsdk.main.java.com.qiniu.pili.Client;
import cn.edu.sdu.uims.component.complex.avsdk.main.java.com.qiniu.pili.Hub;
import cn.edu.sdu.uims.component.complex.avsdk.main.java.com.qiniu.pili.PiliException;
import cn.edu.sdu.uims.component.complex.avsdk.main.java.com.qiniu.pili.Stream;
import cn.edu.sdu.uims.component.list.UList;
import cn.edu.sdu.uims.filter.FilterI;
import cn.edu.sdu.uims.handler.impl.UFormHandler;
import cn.edu.sdu.uims.util.UimsUtils;
import cn.edu.sdu.video.form.VideoQueryForm;
import jdk.nashorn.internal.ir.RuntimeNode.Request;

public class LiveBroadcastInfoManageHandler extends UFormHandler {
	static String accessKey = "2M63A85U1GpU37_hxw6zmCYt7ia0YPIEpOjLeJt5";
	static String secretKey = "n02lUl9NEAyT22aE2JpkYy49rSFTAe-obnZt4G7O";
	private Integer tag = null;
	public void processActionEvent(Object o, String cmd){
		ActionEvent e = (ActionEvent) o;
		String amd = e.getActionCommand();
		if(amd.equals("query")) {
			doQuery();
		}else if(amd.equals("doNew")) {
			doNew();
		}else if(amd.equals("dSave")) {
			doSave();
		}else if(amd.equals("doDelete")) {
			doDelete();
		}else if(amd.equals("doPause")) {
			doPause();
		}else if(amd.equals("doStart")) {
			doStart();
		}else if(amd.equals("doBroadcast")) {
			doBroadcast();
		}
	}
	public void processListSelectionEvent(Object o, String cmd){
		ListSelectionEvent e = (ListSelectionEvent)o;
		UList uList = (UList)e.getSource();
		if(uList == null || uList.getSelectedValue() == null)
			return;
		ListOptionInfo info = (ListOptionInfo)uList.getSelectedValue();
		Integer liveId = new Integer(info.getValue());
		RmiRequest request = new RmiRequest();
	    request.add("liveId", liveId);
	    request.setCmd("videoGetBroadcastIdByLiveId");
	    RmiResponse respond = UimsUtils.requestProcesser(request);
	    if(respond.getErrorMsg() != null) {
	    	UimsUtils.messageBoxInfo(respond.getErrorMsg());
	    }
	    Integer broadcastId = (Integer)respond.get("broadcastId");
		UFormHandler sh = (UFormHandler)this.getSubComponentHandler("infoPanel");
		LiveBroadcastInfoForm f = (LiveBroadcastInfoForm)sh.getForm();
		if(f.getId()!= null && f.getId().equals(broadcastId)) {
			return;
		}
		updateVideoInfoPanel(broadcastId);
	}
	public void doQuery(){
		List list = null;
		RmiRequest request= new RmiRequest();
		VideoQueryForm qForm = (VideoQueryForm)this.component.getSubComponent("queryPanel").getData();
		if(qForm.getLiveState() == null) {
			UimsUtils.messageBoxInfo("请在'状态'复选框中选择相应的状态后再进行查询！");
			return;
		}
		request.add(RmiKeyConstants.KEY_FORM,qForm);
		request.setCmd("videoGetLiveBroadcastInfoOptionListByQueryForm");
		RmiResponse respond = UimsUtils.requestProcesser(request);
		if(respond.getErrorMsg() == null) {
			list =  (List)respond.get(RmiKeyConstants.KEY_FORMLIST);
		}
		UComponentI com = this.component.getSubComponent("videoList");
		FilterI f = com.getFilter();
		f.setAddedData(list);
		com.updateAddedDatas();
	}
	
	public void updateVideoInfoPanel(Integer broadcastId) {
		LiveBroadcastInfoForm f = null;
		if(broadcastId != null) {
			RmiRequest request= new RmiRequest();
			request.add("broadcastId",broadcastId);
			request.setCmd("videoGetLiveBroadcastInfoFormById");
			RmiResponse respond = UimsUtils.requestProcesser(request);
			if(respond.getErrorMsg() == null) {
				f =  (LiveBroadcastInfoForm)respond.get(RmiKeyConstants.KEY_FORM);
			}
		}
		if(f == null)
			f = new LiveBroadcastInfoForm();
		UFormHandler sh = (UFormHandler)this.getSubComponentHandler("infoPanel");
		sh.formToComponent(f);		
	}
	public void doNew(){
		LiveBroadcastInfoForm f = new LiveBroadcastInfoForm();
		UFormHandler sh = (UFormHandler)this.getSubComponentHandler("infoPanel");
		sh.formToComponent(f);			
	}
	public void doSave(){
		UFormHandler sh = (UFormHandler)this.getSubComponentHandler("infoPanel");
		sh.componentToForm();	
		LiveBroadcastInfoForm f= (LiveBroadcastInfoForm)sh.getForm();
		RmiRequest request= new RmiRequest();
		request.add(RmiKeyConstants.KEY_FORM,sh.getForm());
		request.setCmd("videoSaveOrUpdateLiveBroadcastInfo");
		RmiResponse respond = UimsUtils.requestProcesser(request);
		if(respond.getErrorMsg() == null) {
			Integer broadcastId = (Integer)respond.get("broadcastId");
			if(broadcastId!= null) {
				f.setId(broadcastId);
				doQuery();
			}
			UimsUtils.messageBoxInfo("保存成功！");
		}
		else {
			UimsUtils.messageBoxInfo(respond.getErrorMsg());			
		}
	}
	public void doDelete(){
		UFormHandler sh = (UFormHandler)this.getSubComponentHandler("infoPanel");
		LiveBroadcastInfoForm f= (LiveBroadcastInfoForm)sh.getForm();
		Integer broadcastId = f.getId();
		String hubName = (String)this.component.getSubComponent("infoPanel")
				.getSubComponent("hubName").getData();
		String streamName = (String)this.component.getSubComponent("infoPanel")
				.getSubComponent("streamName").getData();
		if(broadcastId == null) {
			UimsUtils.messageBoxInfo("无法下架一个空直播，请选中直播后再进行操作！");
			return;
		}
		int ret = UimsUtils.messageBoxChoice("你确定要下架当前的直播？");
		if(ret != UimsUtils.YES_OPTION)
			return;
		RmiRequest request= new RmiRequest();
		request.add("broadcastId",broadcastId);
		request.setCmd("videoDeleteLiveBroadcastInfo");
		RmiResponse respond = UimsUtils.requestProcesser(request);
		if(respond.getErrorMsg() == null) {
			UimsUtils.messageBoxInfo("直播已下架！");
			Client cli = new Client(accessKey,secretKey);
			Hub hub = cli.newHub(hubName);
			//Stream str = null;
			Integer time = -1; //永久禁播
			Stream str = null;
			try {
				str = hub.get(streamName);
				str.disable(time);
			} catch (PiliException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			doQuery();
		}
		else {
			UimsUtils.messageBoxInfo(respond.getErrorMsg());			
		}		
	}
	
	public void doPause() {
		tag = 2;
		Integer choice = UimsUtils.messageBoxChoice("你确定要暂停该直播？");
		if(choice != 0) {
			return;
		}
		UFormHandler sh = (UFormHandler)this.getSubComponentHandler("infoPanel");
		LiveBroadcastInfoForm f= (LiveBroadcastInfoForm)sh.getForm();
		Integer broadcastId = f.getId();
		this.setLiveBroadcastLiveState(broadcastId, tag);
		String hubName = (String)this.component.getSubComponent("infoPanel")
				.getSubComponent("hubName").getData();
		String streamName = (String)this.component.getSubComponent("infoPanel")
				.getSubComponent("streamName").getData();
		Integer time = 30;//永久禁播是-1，单位是分钟。
		//初始化client
		Client cli = new Client(accessKey,secretKey);
		//初始化Hub
		Hub hub = cli.newHub(hubName);
		Stream str = null;
		try {
			//str.disable(time);
			str = hub.get(streamName);
			str.disable(time);
		} catch (PiliException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.printf("keyA=%s 禁用: %s\n", streamName, str.toJson());
	}
	
	public void doStart() {
		tag = 1;
		Integer choice = UimsUtils.messageBoxChoice("你确定要开始该直播？");
		if(choice != 0) {
			return;
		}
		String hubName = (String)this.component.getSubComponent("infoPanel")
				.getSubComponent("hubName").getData();
		String streamName = (String)this.component.getSubComponent("infoPanel")
				.getSubComponent("streamName").getData();
		Client cli = new Client(accessKey,secretKey);
		Hub hub = cli.newHub(hubName);
		Stream str = null;
		try {
			//str.enable();
			str = hub.get(streamName);
			str.enable();
		} catch (PiliException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.printf("keyA=%s 启用: %s\n", streamName, str.toJson());
	}
	
	public void doBroadcast() {
		
	}
	
	public void setLiveBroadcastLiveState(Integer id,Integer tag) {
		RmiRequest request = new RmiRequest();
		request.add("id", id);
		request.add("tag", tag);
		request.setCmd("liveSetBroadcastInfoLiveState");
		RmiResponse respond = UimsUtils.requestProcesser(request);
		if(respond.getErrorMsg() != null) {
			UimsUtils.messageBoxInfo(respond.getErrorMsg());
		}
	}

}
