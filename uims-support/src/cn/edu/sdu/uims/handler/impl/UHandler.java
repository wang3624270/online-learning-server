package cn.edu.sdu.uims.handler.impl;

import java.awt.TrayIcon;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.sdu.rmi.RmiRequest;
import org.sdu.rmi.RmiResponse;

import cn.edu.sdu.common.form.FormI;
import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.component.dialog.UDialogPanel;
import cn.edu.sdu.uims.graph.model.GraphModelI;
import cn.edu.sdu.uims.handler.UHandlerI;

public class UHandler implements UHandlerI {
	private String sessionId;
	private String prifex;
	private String id;
	private UHandlerI parent;
	private ArrayList<UHandlerI> sHandlerList = null;
	private String componentName;
	protected FormI dataForm;
	protected Boolean trayFlag=true;
	protected TrayIcon trayIcon;
	

	public UHandler() {
	}


	public FormI getForm() {
		return dataForm;
	}
	public void initFormData() {

	}
	public void setForm(FormI form) {
		dataForm = form;
	}
	public Field[] getFormFields(){
		if(dataForm == null)
			return null;
		return dataForm.getClass().getFields();
	}

	public void setInitData(UDialogPanel p) {
		
	}


	public UHandlerI[] getAllSubHandler() {
		// TODO Auto-generated method stub
		if(sHandlerList == null)
			return null;
		UHandlerI [] a= new UHandlerI[sHandlerList.size()];
		for(int i = 0; i<a.length;i++){
			a[i] = sHandlerList.get(i);
		}
		return a;
	}



	public HashMap getParameters() {
		// TODO Auto-generated method stub
		return null;
	}


	public void setParameters(HashMap paras) {
		// TODO Auto-generated method stub
		
	}


	public void initAddedData() {
		// TODO Auto-generated method stub
		
	}



	public UComponentI getComponentByActionCommandString(String str) {
		// TODO Auto-generated method stub
		return null;
	}



	public void setOutFormData() {
		// TODO Auto-generated method stub
		
	}	
	public RmiResponse request(RmiRequest request){
		return null;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public UHandlerI getParent() {
		return parent;
	}


	public void setParent(UHandlerI parent) {
		this.parent = parent;
	}


	public String getComponentName() {
		return componentName;
	}


	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}

	public UHandlerI getParentHandler(){
		return parent;
	}
	public void addSubHandler(UHandlerI handler){
		if(sHandlerList == null) {
			sHandlerList = new ArrayList<UHandlerI>();
		}
		sHandlerList.add(handler);
	}
	public UHandlerI getSubComponentHandler(String name){
		int i;
		UHandlerI sh;
		for(i = 0; i< sHandlerList.size();i++) {
			sh = sHandlerList.get(i);
			if(sh.getComponentName().equals(name)){
				return sh;
			}
		}
		return null;
	}



	public List getinitDataFromHandler() {
		// TODO Auto-generated method stub
//		setParameters(params);
		initFormData();
		initAddedData();
		return null;
	}
	public void sendFormHandler(UFormI form){
	}
	public void setHandlerOutFormData(){
	}


	public String getSessionId() {
		return sessionId;
	}


	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}


	public String getPrifex() {
		return prifex;
	}


	public void setPrifex(String prifex) {
		this.prifex = prifex;
	}



	public List processActionEventFromSubHandler(String actionCommand) {
		// TODO Auto-generated method stub
		return null;
	}



	public List sendHandlerRequestData(HashMap map) {
		// TODO Auto-generated method stub
		return null;
	}



	public GraphModelI getGraphModel2DObject(String name) {
		// TODO Auto-generated method stub
		return null;
	}
	public void doOwnerCommand(UHandler innerHander){
		
	}


	@Override
	public void setDisplayPanel() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void start() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public HashMap getBusinessParaMap(String name) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public int[] getSelectedIndices(String name) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Object getSelectedValue(String name) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void resetPanelData() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public HashMap getUserDataHashMap() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List getInitAddedDataListByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List getRowAddedDataListByName(int col,Object data, String name) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List getNextComItemList(String name, String[] code) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void refreshComponentData() {
		// TODO Auto-generated method stub
		
	}


	public void initAllAddedData(){
		initAddedData();
		UHandlerI []hs =  getAllSubHandler();
		if(hs != null && hs.length != 0) {
			for(int i = 0; i < hs.length;i++) {
				if(hs[i]!= null) {
					hs[i].initAddedData();
				}
			}
		}
	}


	@Override
	public List getInitAddedDataList(String comName) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void componentProcessFished(String comName) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void initAllFormData() {
		// TODO Auto-generated method stub
		this.initFormData();
		UHandlerI[] sh = this.getAllSubHandler();
		if(sh == null || sh.length == 0)
			return;
		for(int i = 0; i < sh.length;i++) {
			sh[i].initAllFormData();
		}
	}


	@Override
	public void windowGainedFocus() {
		// TODO Auto-generated method stub
		
	}

	
}
