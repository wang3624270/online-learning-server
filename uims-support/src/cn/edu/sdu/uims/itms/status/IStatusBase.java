package cn.edu.sdu.uims.itms.status;

import java.awt.event.MouseEvent;
import java.util.EventObject;
import java.util.HashMap;

import cn.edu.sdu.uims.hcims.UserTask;
import cn.edu.sdu.uims.itms.ItmsCommonPara;
import cn.edu.sdu.uims.itms.ItmsConstants;
import cn.edu.sdu.uims.itms.cursor.CursorDrawControl;
import cn.edu.sdu.uims.itms.cursor.ICursorDataBase;
import cn.edu.sdu.uims.itms.def.IStatusTemplate;
import cn.edu.sdu.uims.itms.def.ITransferTemplate;
import cn.edu.sdu.uims.itms.eventor.IEventorBase;
import cn.edu.sdu.uims.itms.service.ItmsTaskService;
import cn.edu.sdu.uims.itms.task.ITranserEdge;
import cn.edu.sdu.uims.trans.UFPoint;

public class IStatusBase {
	public HashMap transMap = new HashMap();
	private String statusName;
	private String transMapAction = null;
	public int type;

	public IStatusBase() {
		transMap = new HashMap();
	}

	public void init(IStatusTemplate st, HashMap map) {
		ITransferTemplate tt = null;
		type = st.type;
		ITranserEdge trans = null;
		// wxString typeName;
		if (st.transList == null) {
			return;
		}
		long num = st.transList.size();
		for (int i = 0; i < num; i++) {
			trans = new ITranserEdge();
			tt = (ITransferTemplate) st.transList.get(i);
			trans.setAction(tt.actionName);
			IStatusBase toStatus = (IStatusBase) map.get(tt.nextStatusName);
			if (toStatus == null) {
				if (tt.nextStatusName
						.equals(ItmsConstants.STATUS_TYPE_STRING_TERMINATE_TASK)) {
					// 创建一个结束任务，状态是
					// ItmsConstants::STATUS_TYPE_INT_TERMINATE_TASK
					try {
						toStatus = (IStatusBase) Class.forName(
								ItmsConstants.DEFAULY_CLASSNAME_STATUS)
								.newInstance();
					} catch (Exception e) {
						toStatus = null;
					}
					toStatus.setName(tt.nextStatusName);
					map.put(tt.nextStatusName, toStatus);
					toStatus.type = ItmsConstants.STATUS_TYPE_INT_TERMINATE_TASK;
				}
			}
			trans.setToStatus(toStatus);
			trans.setEventor(ItmsTaskService.getInstance().getEventorObject(
					tt.eventorName));
			transMap.put(tt.eventType, trans);
		}

	}

	public IStatusBase processEvent(String eventName,
			 EventObject e) {
		UFPoint fp = null;
		ITranserEdge trans = (ITranserEdge) transMap.get(eventName);
		CursorDrawControl cursorDrawControl = CursorDrawControl.getInstance();
		ICursorDataBase cursorData = cursorDrawControl.getCursorData();
		if(e instanceof MouseEvent) {
			MouseEvent me = (MouseEvent) e;
			fp = new UFPoint(me.getX(), me.getY());
			boolean isDrawCursor = cursorDrawControl.needDrawCursor();
			if (isDrawCursor ) {
				cursorDrawControl.setCursorDataPoint(fp);
				cursorDrawControl.drawCursor();
			}
		}
		if (trans == null)
			return this;
		IEventorBase ev = trans.getEventor();
		if (ev != null) {
			// 增加一次记录点
			ItmsCommonPara.userTaskTotalPointsNum++;
			ev.process(cursorData, e);
		}
		// 是否有action
		if (trans.getAction() != null) {
			boolean b = UserTask.getInstance().InvokeCurHandlerMethod(e, trans.getAction(), fp);
			if(b)
				return trans.getToStatus();
			else
				return this;
		}
		else 
		return trans.getToStatus();
	}

	public void setName(String name) {
		statusName = name;
	}

	public String getName() {
		return statusName;
	}

	public String getTransMapAction() {
		return transMapAction;
	}

	public void setTransMapAction(String transMapAction) {
		this.transMapAction = transMapAction;
	}

}
