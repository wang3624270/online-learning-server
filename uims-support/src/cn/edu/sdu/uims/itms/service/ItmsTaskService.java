package cn.edu.sdu.uims.itms.service;

import java.util.HashMap;

import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.itms.cursor.ICursorBase;
import cn.edu.sdu.uims.itms.cursor.ICursorDataBase;
import cn.edu.sdu.uims.itms.def.ICursorTemplate;
import cn.edu.sdu.uims.itms.def.IEventorTemplate;
import cn.edu.sdu.uims.itms.def.IStatusDiagramTemplate;
import cn.edu.sdu.uims.itms.def.ITaskTemplate;
import cn.edu.sdu.uims.itms.eventor.IEventorBase;
import cn.edu.sdu.uims.itms.status.IStatusBase;
import cn.edu.sdu.uims.itms.task.ITaskBase;
import cn.edu.sdu.uims.itms.task.ITaskCompound;
import cn.edu.sdu.uims.itms.task.ITaskStandard;
import cn.edu.sdu.uims.service.UFactory;

public class ItmsTaskService {
	private HashMap taskMap;
	private String currentTaskName;
	private int currentSubTaskNo;
	private IStatusBase currentStatus;
	private static ItmsTaskService instance = new ItmsTaskService();
	private HashMap cursorMap;
	private HashMap eventorMap;
	
	private ItmsTaskService() {
		cursorMap = new HashMap();
		eventorMap = new HashMap();
		currentTaskName = "";
		currentSubTaskNo = 0-1;
		currentStatus = null;
	}

	public static ItmsTaskService getInstance(){
		return instance;
	}
	public void init(){
	}


	public String getCurrentTaskName(){
		return currentTaskName;
	}
	public void setCurrentTaskName(String name){
		currentTaskName = name;
	}
	public int getCurrentSubTaskNo(){
		return currentSubTaskNo;
	}
	public void setCurrentSubTaskNo(int no){
		currentSubTaskNo = no;
	}
	public IStatusBase getCurrentStatus(){
		return currentStatus;
	}
	public void setCurrentStatus(IStatusBase status){
		currentStatus = status;
	}
	public ITaskBase getTaskByName(String  name){
		ITaskBase task = null;
		ITaskTemplate taskTemplate = null;
			taskTemplate = (ITaskTemplate )UFactory.getModelSession().getTemplate(UConstants.MAPKEY_TASK, name);
			if(taskTemplate != null){
				task = createCompoundTask(taskTemplate);
				//taskMap->put(name,task);
			}
			else {
				IStatusDiagramTemplate sdt = (IStatusDiagramTemplate )UFactory.getModelSession().getTemplate(UConstants.MAPKEY_STATUSDIAGRAM,name);
				if(sdt != null){
					task = createStandardTask(sdt);
					//taskMap->put(name,task);
				}
			}
		return task;
	}
	public ITaskCompound createCompoundTask(ITaskTemplate taskTemplate){
		ITaskCompound  task = null;
		if(taskTemplate == null)
			return null;
		task = new ITaskCompound();
		task.setTemplate(taskTemplate);
		task.init();
		return task;
	}
	public ITaskStandard createStandardTask(IStatusDiagramTemplate temp){
		ITaskStandard  task = null;
		if(temp == null)
			return null;
		task = new ITaskStandard();
		task.init(temp);
		return task;
	}
	public ICursorBase getCursorObject(String name){
		ICursorBase cursor = null;
		String className;
		cursor = (ICursorBase )cursorMap.get(name);
		if(cursor == null) {
			ICursorTemplate temp;
			temp = (ICursorTemplate )UFactory.getModelSession().getTemplate(UConstants.MAPKEY_CURSOR,name);
			if(temp != null){
				try {
					cursor = (ICursorBase )Class.forName(temp.className).newInstance();
				}catch(Exception e){
					cursor = null;
				}
			}
			if (cursor != null)
			{
				cursorMap.put(name,cursor);
			}		
		} 
		return cursor;
	}
	public IEventorBase  getEventorObject(String  name){
		IEventorBase eventor = null;
		eventor = (IEventorBase )eventorMap.get(name);
		if(eventor == null) {
			IEventorTemplate temp;
			temp = (IEventorTemplate )UFactory.getModelSession().getTemplate(UConstants.MAPKEY_EVENTOR,name);
			if(temp != null){
				try{
					eventor = (IEventorBase )Class.forName(temp.className).newInstance();
				}catch (Exception e) {
					eventor = null;
				}
			}
			if (eventor != null)
			{
				eventorMap.put(name,eventor);
			}	
		} 
		return eventor;

	}
	public ICursorDataBase  getCursorDataObject(String  name){
		ICursorDataBase data = null;
		String className = name; 
		if(className == null || className.equals(""))
			className = "cn.edu.sdu.uims.itms.cursor.ICursorDataPoints";
		try{
				data = (ICursorDataBase )Class.forName(className).newInstance();
		}catch (Exception e) {
				data = null;
		} 
		return data;

	}

}
