package cn.edu.sdu.uims.hcims;

import java.awt.Graphics;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.EventObject;
import java.util.List;

import cn.edu.sdu.uims.base.UPointCursor;
import cn.edu.sdu.uims.graph.handler.GraphInteractionHandler;
import cn.edu.sdu.uims.graph.view.UCanvasI;
import cn.edu.sdu.uims.graph.view.ViewParameter;
import cn.edu.sdu.uims.hcims.def.UserTaskTemplate;
import cn.edu.sdu.uims.itms.ItmsCommonPara;
import cn.edu.sdu.uims.itms.ItmsConstants;
import cn.edu.sdu.uims.itms.cursor.CursorDrawControl;
import cn.edu.sdu.uims.itms.cursor.ICursorDataBase;
import cn.edu.sdu.uims.itms.cursor.ICursorDataPoints;
import cn.edu.sdu.uims.itms.event.ItmsEvent;
import cn.edu.sdu.uims.itms.form.IFormI;
import cn.edu.sdu.uims.itms.service.ItmsTaskService;
import cn.edu.sdu.uims.itms.task.ITaskBase;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.trans.UFPoint;

public class UserTask {
	private UCanvasI canvas;
	private ITaskBase currentTask;
	private String methodName;
	private UserTaskTemplate currentTemplate;
	private boolean isCurrentTaskRepeat;
	private GraphInteractionHandler currentHandler;
	private static UserTask instance = null;
	//还有一大堆要定义的参数
	private IFormI form;
	public void setMethodName(String name){
		methodName = name;
	}
	public void setTemplate(UserTaskTemplate temp){
		currentTemplate = temp;
	}
	public void setCurrentTaskRepeat(boolean isRepeat){
		isCurrentTaskRepeat = isRepeat;
	}
	public void setCurrentHandler(GraphInteractionHandler handler){
		currentHandler = handler;
	}

	public void setHciTask(ITaskBase task)
	{
		if (currentTask != null){
			isCurrentTaskRepeat = true;
		}
		currentTask = task;
	}

	public void setCurrentCurssor(){
		if(currentTemplate == null || canvas == null)
			return;
		CursorDrawControl curDrawControl = CursorDrawControl.getInstance();
		curDrawControl.setDrawMode(currentTemplate.cursorDrawMode);
		UPointCursor pointCursor = (UPointCursor)UFactory.getModelSession().getPointCursorByName(currentTemplate.pointCursorName);
		curDrawControl.setPointCursor(pointCursor);
		curDrawControl.setCanvas(canvas);
		curDrawControl.setCurrentTask(currentTask);
		curDrawControl.start();
	}
	public void doUserTask()
	{
		if (currentTask != null){
			ItmsCommonPara.cursorPointsSequence.clear();
			//清空一次userTask中的记录的所有点
			ItmsCommonPara.userTaskTotalPointsNum = 0;
			currentTask.setParaData(form);
			setCurrentCurssor();
			currentTask.start(null,null);
		}
	}
	
	
	public int processEvent(String eventName, EventObject e)
	{
		if (currentTask != null)
		{
			//PointForm *form = new PointForm();
			long ret = currentTask.processEvent(eventName,e);
	 		if (ret == ItmsConstants.EVENT_PROCESS_RETURN_STATUS_END || ret == ItmsConstants.EVENT_PROCESS_RETURN_STATUS_TERMINATE_TASK)
	 		{
				//转到handler的method的方法
				if (currentHandler != null)
				{
					try {
						ItmsEvent ie = new ItmsEvent(e.getSource(),form, viewToLogic(ItmsCommonPara.cursorPointsSequence),ItmsConstants.TRANS_ACTION_TYPE_END_TO_MODIFY_GRAPH_DATA);
						if(currentHandler !=null && currentHandler.getClass().getMethod(methodName,ItmsEvent.class) != null)
							currentHandler.getClass().getMethod(methodName,ItmsEvent.class).invoke(currentHandler,ie);
					} catch (IllegalArgumentException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SecurityException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InvocationTargetException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (NoSuchMethodException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
//					wxLogDebug("go modify dib");
				}
				//修改完DIB清空cursorPointsSequence
				ItmsCommonPara.cursorPointsSequence.clear();
				//清空一次userTask中的记录的所有点
				ItmsCommonPara.userTaskTotalPointsNum = 0;

				//((HciHandler*)((UComponent*)e.getSource()).getHandler()).InvokeMethod(methodName,form);
				//判断是否这个任务是否要重复，如果要重复，自动重新start，否则设置当前currentTask设置为null
				if (isCurrentTaskRepeat)
				{
					if (form != null)
					{
						form.clearForUse();
					}
					
					currentTask.start(null,null);
				}
				else
				{
					ItmsTaskService.getInstance().setCurrentTaskName("");
					currentTask = null;
					isCurrentTaskRepeat = true;
				}
			}
			else if (ret == ItmsConstants.EVENT_PROCESS_RETURN_STATUS_ESC)
			{
				ItmsCommonPara.cursorPointsSequence.clear();

				//清空一次userTask中的记录的所有点
				ItmsCommonPara.userTaskTotalPointsNum = 0;

				//刷新屏幕
				if (currentHandler != null)
				{
//					currentHandler.getComponent().getComponent().Refresh(false);  临时注释应该有问题
				}
				//不做业务，但是
				//判断是否这个任务是否要重复，如果要重复，自动重新start，否则设置当前currentTask设置为null
				if (isCurrentTaskRepeat)
				{
					currentTask.start(null,null);
				}
				else
				{
					ItmsTaskService.getInstance().setCurrentTaskName("");
					currentTask = null;
					isCurrentTaskRepeat = true;
				}
			}
			else if (ret == ItmsConstants.EVENT_PROCESS_RETURN_STATUS_END_USER_SWITCH)
			{
				ItmsCommonPara.cursorPointsSequence.clear();

				//清空一次userTask中的记录的所有点
				ItmsCommonPara.userTaskTotalPointsNum = 0;

				//到这里就不处理了，但是要把用户切换子任务的事件名字清空
				ItmsCommonPara.userSwitchEvent = "";
			}
		}

		return 0;
	}
	public String getTransformState(EventObject  e,String transMapAction){
		if (currentHandler != null){
			try {
				ItmsEvent ie = new ItmsEvent(e.getSource(),form, viewToLogic(ItmsCommonPara.cursorPointsSequence),transMapAction);
				return currentHandler.getTransformState(ie);
			} catch (IllegalArgumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return null;
			} catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				return null;
			} 
		}
		else{
			return null;
		}
		
	}
	public boolean InvokeCurHandlerMethod(EventObject  e,String atcionType, UFPoint fp)
	{
		if (currentHandler != null)
		{
			try {
				ItmsEvent ie = new ItmsEvent(e.getSource(),form, viewToLogic(ItmsCommonPara.cursorPointsSequence),atcionType, viewToLogic(fp));
				Object ret = currentHandler.getClass().getMethod(methodName,ItmsEvent.class).invoke(currentHandler,ie);
				boolean bt = true;
				if(ret instanceof Boolean) {
					bt = ((Boolean)ret).booleanValue();
				}
				return bt;
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return true;
	}
	public void setCurrentPoint(UFPoint p)
	{
		if (currentTask != null)
		{
			currentTask.setCurrentPoint(p);
		}
	}

	public void drawCursor(Graphics dc)
	{
		if (currentTask != null)
		{
			//画已经结束的段
			int size = ItmsCommonPara.cursorPointsSequence.size();
			for ( int i = 0;i < size;i++)
			{
				ICursorDataBase dataBase = (ICursorDataBase)ItmsCommonPara.cursorPointsSequence.get(i);
				if (dataBase != null)
				{
					if (dataBase.cursor != null)
					{
//						dataBase.cursor.drawExists(dc,dataBase);
					}
				}	
			}
			//画正在运行任务的段
			currentTask.drawCursor(dc);
		}
	}

	public void reset()
	{
		if (currentTask != null)
		{
			currentTask = null;
			currentTemplate = null;

			methodName = "";//method设置为空
			isCurrentTaskRepeat = true;
		}

		if (form != null)
		{
			form = null;
		}
		ItmsCommonPara.cursorPointsSequence.clear();
		//清空一次userTask中的记录的所有点
		ItmsCommonPara.userTaskTotalPointsNum = 0;
	}

	public String getName()
	{
		return currentTemplate == null ? null : currentTemplate.name;
	}
	public void setFieldForm(IFormI f)
	{
		if (this.form != null)
		{
		}
		form = f;

		ItmsCommonPara.cursorPointsSequence.clear();
		//清空一次userTask中的记录的所有点
		ItmsCommonPara.userTaskTotalPointsNum = 0;
	}
	
	public static UserTask getInstance(){
		if(instance == null)
			instance = new UserTask();
		return instance;
	}
	public GraphInteractionHandler getCurrentHandler() {
		return currentHandler;
	}
	public UCanvasI getCanvas() {
		return canvas;
	}
	public void setCanvas(UCanvasI canvas) {
		this.canvas = canvas;
		this.canvas.setUserTask(this);
	}
	public List viewToLogic(List list) {
		if(canvas == null || list == null || list.size() == 0)
			return list;
		ViewParameter vp = canvas.getViewParameter(); 
		if(vp == null)
			return list;
		ICursorDataPoints ip;
		List retList = new ArrayList(list.size());
			for(int i = 0; i < list.size();i++) { 
				if(list.get(i) instanceof  ICursorDataPoints) {
					ip = (ICursorDataPoints)list.get(i);
					retList.add(ip.getLogicPoints(vp.mv));
				}else
					retList.add(vp.mv.viewToLogic((UFPoint)list.get(i)));
			}
			return retList;
	}
	public UFPoint viewToLogic(UFPoint p) {
		if(canvas == null)
			return p;
		else
			return canvas.getViewParameter().mv.viewToLogic(p);
	}
}
