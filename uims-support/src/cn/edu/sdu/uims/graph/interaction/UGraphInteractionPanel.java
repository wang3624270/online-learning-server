package cn.edu.sdu.uims.graph.interaction;

import java.util.HashMap;
import java.util.Iterator;

import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UPanel;
import cn.edu.sdu.uims.component.panel.UFPanel;
import cn.edu.sdu.uims.graph.handler.GraphInteractionHandler;
import cn.edu.sdu.uims.graph.view.UGraphView;
import cn.edu.sdu.uims.hcims.UserTask;
import cn.edu.sdu.uims.hcims.def.UserTaskTemplate;
import cn.edu.sdu.uims.itms.form.IForm;
import cn.edu.sdu.uims.itms.service.ItmsTaskService;
import cn.edu.sdu.uims.service.UFactory;

public class UGraphInteractionPanel extends UFPanel {

	private HashMap userTaskMap = null;
	private UserTask userTask = null;
	private UGraphView graphView = null;

	private int innerWidth, innerHeight;

	public UGraphInteractionPanel() {
		eventAdaptor = new GraphInteractionProcessor(this);
	}

	public void initInteraction() {
		if (panelTemplate == null || panelTemplate.userTaskGroupName == null)
			return;
		userTaskMap = (HashMap) UFactory.getModelSession().getUserTaskMap(panelTemplate.userTaskGroupName);
		if(panelTemplate.defaultUserTaskName == null || panelTemplate.defaultMethodName == null)
			return ;
		initUserTask(panelTemplate.defaultUserTaskName,panelTemplate.defaultMethodName);
	}

	public void initUserTask(String userTaskName, String methodName) {
		if (userTaskMap == null)
			return;
		UserTaskTemplate tmp = (UserTaskTemplate) userTaskMap.get(userTaskName);
		if (tmp == null)
			return;
		UGraphView graphView = getGraphView();
		if (graphView == null)
			return;
		userTask = UserTask.getInstance();
		userTask.setCanvas(graphView.getCanvas());
		userTask.setTemplate(tmp);

		// 下面根据template找到交互Task
		userTask.setHciTask(ItmsTaskService.getInstance().getTaskByName(
				tmp.hciTaskName));
		ItmsTaskService.getInstance().setCurrentTaskName(tmp.hciTaskName);
		userTask.setCurrentTaskRepeat(tmp.isRepeat);
		userTask.setMethodName(methodName);
		IForm form = null;
		try {
			form = (IForm) Class.forName(tmp.dataForm).newInstance();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (form != null)
			userTask.setFieldForm(form);
		userTask.setCurrentHandler((GraphInteractionHandler) this.getHandler());
		userTask.doUserTask();
	}

	/*
	 * public void processHcimsEvent(EventObject e, String eventName, String
	 * cmd) { if (hcimsProcesor != null) { hcimsProcesor.processEvent(e,
	 * eventName, cmd); } }
	 */

	public UserTask getUserTask() {
		return userTask;
	}

	public void setUserTask(UserTask userTask) {
		this.userTask = userTask;
	}

	public UGraphView getGraphView() {
		
		
		UComponentI com;
		String name;
		Iterator it = getNameIterator();
		while (it.hasNext()) {
			name = (String) it.next();
			com = (UComponentI) componentMap.get(name);
			if (com instanceof UGraphView) {
				graphView = (UGraphView) com;
				return graphView;
			}
		}
		if(parent !=null){
			UPanel panel = ((UPanel)parent).getParent();
			getGraphView(panel.getComponentMap());
		}
		return graphView;
	}
	
	public void getGraphView(HashMap map){
		UComponentI com;
		String name;
		Iterator it = map.keySet().iterator();
		while (it.hasNext()) { 
			name = (String) it.next();
			System.out.println(name);
			com = (UComponentI) map.get(name);
			if (com instanceof UGraphView) {
				graphView = (UGraphView) com;
			}
			UComponentI com1 = (UComponentI)map.get(name);
			if(com1 instanceof UPanel){
				UPanel panel = (UPanel)com1;
				if(panel!=null)
					this.getGraphView(panel.getComponentMap());
			}
		
		}
	}

	public void scaleGraph(boolean isEnlarge) {
		UGraphView graphView = getGraphView();
		if (graphView != null)
			graphView.scaleGraph(isEnlarge);
	}

}
