package cn.edu.sdu.uims.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.edu.sdu.common.form.FormI;
import cn.edu.sdu.uims.component.event.UEventObject;
import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.def.UDialogTemplate;
import cn.edu.sdu.uims.def.UElementTemplate;
import cn.edu.sdu.uims.def.UGroupElementTemplate;
import cn.edu.sdu.uims.def.UMenuPanelMapTemplate;
import cn.edu.sdu.uims.def.UPanelTemplate;
import cn.edu.sdu.uims.flex.FHashMap;
import cn.edu.sdu.uims.flex.FHashMapUtil;
import cn.edu.sdu.uims.flex.FNameObjectPar;
import cn.edu.sdu.uims.handler.UHandlerI;
import cn.edu.sdu.uims.handler.impl.UHandler;

public class UHandlerSession implements UHandlerSessionI {

	private String retMarkArray [] = {"data", "panel","font", "color", "border", "filter",
			"table", "buttonBar"};
	protected HashMap<String, HashMap> handlerMap = null;;

	public String getClientSessionId() {
		return null;
	}

	public UHandlerSession() {
		initHandlerMap();
	}

	public void initHandlerMap() {
	}
	public int getIndexFromMarkString(String mark){
		for(int i = 0; i < retMarkArray.length;i++) {
			if(retMarkArray[i].equals(mark)){
				return i;
			}
		}
		return -1;
	}
	public HashMap getHandlerMap(String prifex, String sessionId) {
		HashMap map = handlerMap.get(prifex);
		return (HashMap) map.get(sessionId);
	}

	public UHandlerI getHandlerObject(String prifex, String seesionId,
			String handlerId) {
		if (handlerId == null)
			return null;
		HashMap map = getHandlerMap(prifex, seesionId);
		if (map == null)
			return null;
		UHandlerI hi = (UHandlerI) map.get(handlerId);
		return hi;
	}

	public void putHandlerObject(String prifex, String seesionId,
			String handlerId, UHandlerI hi) {
		if (handlerId == null || hi == null)
			return;
		HashMap map1 = getHandlerMap(prifex, seesionId);
		if (map1 == null) {
			map1 = new HashMap();
			HashMap map = handlerMap.get(prifex);
			map.put(seesionId, map1);
		}
		map1.put(handlerId, hi);
	}

	public UHandlerI newHandlerInstance(String prifex, String sessionId,
			String handlerId, String className, String componentName,
			UHandlerI parent, String dataFormClassName,
			Boolean clearSessionHandler) {
		if (handlerId == null || className == null)
			return null;
		if (clearSessionHandler != null && clearSessionHandler.booleanValue())
			removeAllSessionHandler(prifex, sessionId);
		UHandler handler = null;
		FormI formI = null;
		try {
			handler = (UHandler) Class.forName(className).newInstance();
			handler.setId(handlerId);
			handler.setPrifex(prifex);
			handler.setSessionId(sessionId);
			handler.setComponentName(componentName);
			handler.setParent(parent);
			if(parent != null){
				parent.addSubHandler(handler);
			}
			if (dataFormClassName != null) {
				formI = (FormI) Class.forName(dataFormClassName).newInstance();
				handler.setForm(formI);
			}
			putHandlerObject(prifex, sessionId, handlerId, handler);
			return handler;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	public FormI getDataFormFromHandler(String prifex, String sessionId,
			String handlerId) {
		// TODO Auto-generated method stub
		UHandlerI handler = this.getHandlerObject(prifex, sessionId, handlerId);
		if (handler == null)
			return null;
		return handler.getForm();
	}

	public FormI newFormInstance(String prifex, String sessionId,
			String handlerId, String className) {
		UHandlerI handler = this.getHandlerObject(prifex, sessionId, handlerId);
		if (handler == null)
			return null;
		try {
			FormI form = (FormI) Class.forName(className).newInstance();
			handler.setForm(form);
			return form;
		} catch (Exception e) {
			return null;
		}
	}

	public List sendhandlerProcess(String prifex, String sessionId, String id,
			String methodName, UEventObject event, String actionCmd) {
		UHandlerI handler = this.getHandlerObject(prifex, sessionId, id);
		if (handler == null)
			return null;
		try {
			Method method;
			List RmiResponse = null;
			if (methodName.substring(0, 2).equals("do")) {
				method = handler.getClass().getMethod(methodName);
				method.invoke(handler);
				return null;
			} else {
				method = handler.getClass().getMethod(methodName,
						UEventObject.class, String.class);
				RmiResponse = (List) method.invoke(handler, event, actionCmd);
				return RmiResponse;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	public void removeHandlerObject(String prifex, String sessionId, String id) {
		HashMap map = getHandlerMap(prifex, sessionId);
		if (map == null)
			return;
		map.remove(id);
	}

	public void sendFormHandler(String prifex, String sessionId, String id,
			FormI form) {
		UHandlerI handler = this.getHandlerObject(prifex, sessionId, id);
		handler.setForm(form);
	}

	public void setHandlerOutFormData(String prifex, String sessionId, String id) {
		UHandlerI handler = this.getHandlerObject(prifex, sessionId, id);
		handler.setOutFormData();
	}

	public void removeAllSessionHandler(String prifex, String sessionId) {
		HashMap map = this.getHandlerMap(prifex, sessionId);
		if(map == null)
			return;
		map.clear();
	}

	public List getinitDataFromHandler(String prifex, String sessionId,
			String handlerId, HashMap map) {
		// TODO Auto-generated method stub
		UHandlerI handler = this.getHandlerObject(prifex, sessionId, handlerId);
		if (handler == null)
			return null;
		handler.setParameters(map);
		return handler.getinitDataFromHandler();
	}

	public FormI getDataFormFromHandlerFHashMap(String prifex,
			String sessionId, String id) {
		// TODO Auto-generated method stub
		FormI formI = getDataFormFromHandler(prifex, sessionId, id);
		if (formI == null)
			return null;
		return formI;
	}


	public List getinitDataFromHandlerFHashMap(String prifex, String sessionId,
			String id, FHashMap map) {
		HashMap parMap = FHashMapUtil.fHashMapToHashMap(map);
		List retList = getinitDataFromHandler(prifex, sessionId, id, parMap);
		// TODO Auto-generated method stub
		return retList;
	}

	public List sendhandlerProcessFHashMap(String prifex, String sessionId,
			String id, String methodName, UEventObject event, String actionCmd) {
		// TODO Auto-generated method stub
		List list = sendhandlerProcess(prifex, sessionId, id, methodName,
				event, actionCmd);
		return list;
	}

	public List sendHandlerRequestData(String prifex, String sessionId,
			String handlerId, HashMap map) {
		UHandlerI handler = this.getHandlerObject(prifex, sessionId, handlerId);
		if (handler == null)
			return null;
		if (map == null)
			return null;
		return handler.sendHandlerRequestData(map);
	}

	public List sendHandlerRequestDataFHashMap(String prifex, String sessionId,
			String handlerId, FHashMap map) {
		// TODO Auto-generated method stub
		HashMap parMap = FHashMapUtil.fHashMapToHashMap(map);
		List list = sendHandlerRequestData(prifex, sessionId, handlerId, parMap);
		return list;
	}

	public List initPanelServerData(String prifex, String sessionId, String menuName) {
		String name = menuName;
		String templateName;
		HashMap paras = null;
		Object o = UFactory.getModelSession().getTemplate(
				UConstants.MAPKEY_PANEL_FORM, name);
		UMenuPanelMapTemplate mt = null;
		if (o == null) {
			o = UFactory.getModelSession().getTemplate(UConstants.MAPKEY_MENUMAP,name);
			if (o != null) {
				mt = (UMenuPanelMapTemplate) o;
				paras = FHashMapUtil.fHashMapToHashMap(mt.parameterMap);
				templateName = mt.templateName;
			} else {
				templateName = name;
			}
		}
		else {
			templateName = name;			
		}
		if(templateName == null)
			return null;
		ArrayList<FNameObjectPar> retList = new ArrayList<FNameObjectPar>();
		FNameObjectPar datas = null;
		for (int i = 0; i < retMarkArray.length; i++) {
			datas = new FNameObjectPar();
			datas.name = retMarkArray[i];
			datas.ob = null;
			retList.add(datas);
		}
		UPanelTemplate panelTemplate = getPanelTemplate(templateName, retList);
		if(panelTemplate != null) {
			UHandlerI handler = initHandlerObject(prifex, sessionId, menuName, panelTemplate,
				panelTemplate.name, null,paras,new Boolean(true));
			ArrayList dataList = new ArrayList();
			getinitDataFromHandler(handler,dataList );
			retList.get(0).ob = dataList;
		}
		return retList;
	}
	public List initDialogServerData(String prifex, String sessionId, String templateName) {
		String name = templateName;
		Object o = UFactory.getModelSession().getTemplate(
				UConstants.MAPKEY_DIALOG, name);
		if(o == null)
			return null;
		ArrayList<FNameObjectPar> retList = new ArrayList<FNameObjectPar>();
		FNameObjectPar datas = null;
		for (int i = 0; i < retMarkArray.length; i++) {
			datas = new FNameObjectPar();
			datas.name = retMarkArray[i];
			datas.ob = null;
			retList.add(datas);
		}
		UPanelTemplate panelTemplate = getPanelTemplate(templateName, retList);
		if(panelTemplate != null) {
			UHandlerI handler = initHandlerObject(prifex, sessionId, templateName, panelTemplate,
				panelTemplate.name, null,null,new Boolean(false));
			ArrayList dataList = new ArrayList();
			getinitDataFromHandler(handler,dataList );
			retList.get(0).ob = dataList;
		}
		return retList;
	}
	public List initFlexDialogServerData(String prifex, String sessionId, String templateName)
	{
		String name = templateName;
		UDialogTemplate o = (UDialogTemplate)UFactory.getModelSession().getTemplate(
				UConstants.MAPKEY_DIALOG, name);
		if(o == null)
			return null;
		ArrayList<FNameObjectPar> retList = new ArrayList<FNameObjectPar>();
		FNameObjectPar datas = null;
		for (int i = 0; i < retMarkArray.length; i++) {
			datas = new FNameObjectPar();
			datas.name = retMarkArray[i];
			datas.ob = null;
			retList.add(datas);
		}
		
		UDialogTemplate dialogTemplate = this.getDialogTemplate(templateName, retList);
		
		if(dialogTemplate!=null)
		{
			UHandlerI handler = initHandlerObject(prifex, sessionId, templateName, dialogTemplate,
					dialogTemplate.name, null,null,new Boolean(false));
			ArrayList dataList = new ArrayList();
			getinitDataFromHandler(handler,dataList );
			retList.get(0).ob = dataList;

		}
		
		return retList;
	}
	public void getinitDataFromHandler(UHandlerI handler,  List retList){
		List list = handler.getinitDataFromHandler();
		if(list != null) {
			retList.add(list);
		}
		UHandlerI [] a= handler.getAllSubHandler();
		if(a == null)
			return;
		for(int i = 0;i < a.length;i++) {
			getinitDataFromHandler(a[i],retList);
		}
	}

	public void addTemplateToList(FNameObjectPar par, String name, Object o) {
		int i;
		ArrayList<FNameObjectPar> list = (ArrayList<FNameObjectPar>) par.ob;
		FNameObjectPar temp, temp1;
		if (list == null) {
			list = new ArrayList<FNameObjectPar>();
			par.ob = list;
		}
		for (i = 0; i < list.size(); i++) {
			temp = (FNameObjectPar) list.get(i);
			if (temp.name.equals(name))
				return;
		}
		temp = new FNameObjectPar();
		temp.name = name;
		temp.ob = o;
		list.add(temp);
	}

	public UPanelTemplate getPanelTemplate(String templateName,
			ArrayList<FNameObjectPar> retList) {
		UModelSessionI modelSession = UFactory.getModelSession();
		UPanelTemplate panelTemplate;
		panelTemplate = (UPanelTemplate) modelSession.getTemplate(
				UConstants.MAPKEY_PANEL_FORM, templateName);
		FNameObjectPar par = retList.get(1);
		addTemplateToList(par, templateName, panelTemplate);
		if(panelTemplate!=null)
			addGroupTemplateToList(panelTemplate.groupElementTemplate, retList);
		return panelTemplate;
	}

	public UDialogTemplate getDialogTemplate(String templateName,
			ArrayList<FNameObjectPar> retList) {
		UModelSessionI modelSession = UFactory.getModelSession();
		UDialogTemplate dialogTemplate;
		dialogTemplate = (UDialogTemplate) modelSession.getTemplate(
				UConstants.MAPKEY_DIALOG, templateName);
		FNameObjectPar par = retList.get(1);
		addTemplateToList(par, templateName, dialogTemplate);
		if(dialogTemplate!=null)
			addGroupTemplateToList(dialogTemplate.groupElementTemplate, retList);
		return dialogTemplate;
	}

	public void addGroupTemplateToList(UGroupElementTemplate gc,
			ArrayList<FNameObjectPar> retList) {
		int i;
		UModelSessionI modelSession = UFactory.getModelSession();
		FNameObjectPar par;
		UElementTemplate elt;
		for (i = 0; i < gc.componentNum; i++) {
			elt = (UElementTemplate)gc.componentTemplates.get(i);			
			if (elt instanceof UGroupElementTemplate) {
				addGroupTemplateToList(
						(UGroupElementTemplate) elt,
						retList);
			} 
			if(elt.templateName != null) {
				if(elt.type == 291 || elt.type == 2911) {
					getPanelTemplate(elt.templateName, retList);
				} else if(elt.type == 241){
					addTemplateToList(retList.get(6), elt.templateName,
						modelSession.getTemplate(
								UConstants.MAPKEY_DATA_TABLE_FORM,
								elt.templateName));
				}
				else if(elt.type == 274){
					addTemplateToList(retList.get(7), elt.templateName,
							modelSession.getTemplate(
									UConstants.MAPKEY_GRAPHBUTTONBAR,
									elt.templateName));					
				}
			}
			if (elt.fontName != null) {
				addTemplateToList(
						retList.get(2),
						elt.fontName,
						modelSession
								.getFontByName(elt.fontName));
			}
			if (elt.colorName != null) {
				addTemplateToList(
						retList.get(3),
						elt.fontName,
						modelSession
								.getColorByName(elt.colorName));
			}
			if (elt.borderName != null) {
				addTemplateToList(
						retList.get(4),
						elt.borderName,
						modelSession
								.getBorderByName(elt.borderName));
			}
			if (elt.filter != null) {
				addTemplateToList(
						retList.get(5),
						elt.filter,
						modelSession
								.getFilterTemplateByName(elt.filter));
			}
			if (elt.filter1 != null) {
				addTemplateToList(
						retList.get(5),
						elt.filter,
						modelSession
								.getFilterTemplateByName(elt.filter1));
			}
		}
	}

	public UHandlerI initHandlerObject(String prifex, String sessionId,
			String menuName, UPanelTemplate panelTemplate,
			String componentName, UHandlerI parent ,HashMap map,Boolean isClear) {
		UHandlerI handler = null;
		if (panelTemplate.handlerClassName == null)
			return null;
		handler = newHandlerInstance(prifex, sessionId, menuName + "."
				+ panelTemplate.name, panelTemplate.handlerClassName,
				componentName, parent, panelTemplate.dataFormClassName,
				isClear);
		if (handler == null)
			return null;
		handler.setParameters(map);
		initHandlerObjectGroup(prifex, sessionId, menuName,
				panelTemplate.groupElementTemplate, handler);
		return handler;
	}

	public void initHandlerObjectGroup(String prifex, String sessionId,
			String menuName, UGroupElementTemplate groupElementTemplate,
			UHandlerI parent) {
		{
			UElementTemplate elt;
			for (int i = 0; i < groupElementTemplate.componentNum; i++) {
				elt = (UElementTemplate)groupElementTemplate.componentTemplates.get(i);
				if (elt instanceof UGroupElementTemplate) {
					initHandlerObjectGroup(
							prifex,
							sessionId,
							menuName,
							(UGroupElementTemplate) elt,
							parent);
				} else if (elt.type == 291 || elt.type == 2911
						&& elt.templateName != null) {
					UPanelTemplate panelTemplate = (UPanelTemplate) UFactory
							.getModelSession()
							.getTemplate(
									UConstants.MAPKEY_PANEL_FORM,
									elt.templateName);
					HashMap paras = FHashMapUtil.fHashMapToHashMap(elt.parameters);
					initHandlerObject(prifex, sessionId, menuName,
							panelTemplate, elt.name, parent,paras,false);
				}
			}
		}
	}
	

	public List initPanelServerDataFHashMap(String prifex, String sessionId,
			String menuName) {
		List retList = initPanelServerData(prifex,sessionId, menuName);
		return retList;
		// TODO Auto-generated method stub
	}
	public List initDialogServerDataFHashMap(String prifex, String sessionId,
			String menuName) {
		List retList = initDialogServerData(prifex,sessionId, menuName);
		return retList;
		// TODO Auto-generated method stub
	}

}
