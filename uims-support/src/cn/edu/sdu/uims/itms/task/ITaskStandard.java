package cn.edu.sdu.uims.itms.task;

import java.util.EventObject;
import java.util.HashMap;
import java.util.StringTokenizer;

import cn.edu.sdu.uims.hcims.UserTask;
import cn.edu.sdu.uims.itms.ItmsCommonPara;
import cn.edu.sdu.uims.itms.ItmsConstants;
import cn.edu.sdu.uims.itms.cursor.CursorDrawControl;
import cn.edu.sdu.uims.itms.cursor.ICursorDataBase;
import cn.edu.sdu.uims.itms.cursor.ICursorDataPoints;
import cn.edu.sdu.uims.itms.def.IStatusDiagramTemplate;
import cn.edu.sdu.uims.itms.def.IStatusTemplate;
import cn.edu.sdu.uims.itms.status.IStatusBase;
import cn.edu.sdu.uims.trans.UFPoint;

public class ITaskStandard extends ITaskBase {
	private HashMap statusMap;
	private IStatusBase startStatus, currentStatus;
	// private ICursorDataBase curCursorData; // 修改图像的点
	private boolean needDrawCursor;

	public ITaskStandard() {
		statusMap = new HashMap();
		needDrawCursor = false;
	}

	public void init(IStatusDiagramTemplate temp) {
		IStatusTemplate st;
		IStatusBase status;
		if (temp == null)
			return;
		try {
			int size = temp.statusList.size();
			int i = 0;
			for (i = 0; i < size; i++) {
				st = (IStatusTemplate) temp.statusList.get(i);
				status = (IStatusBase) Class.forName(
						ItmsConstants.DEFAULY_CLASSNAME_STATUS).newInstance();
				status.setName(st.name);
				statusMap.put(st.name, status);
				if (st.esc) {
					IStatusBase escStatus = null;
					if (!statusMap
							.containsKey(ItmsConstants.STATUS_TYPE_STRING_END_ESC)) {
						// 创建esc状态, 如果已经存在则不再创建
						IStatusTemplate tmpTemplate = new IStatusTemplate();
						tmpTemplate.type = ItmsConstants.STATUS_TYPE_INT_END_ESC;
						tmpTemplate.transList = null;

						escStatus = (IStatusBase) Class.forName(
								ItmsConstants.DEFAULY_CLASSNAME_STATUS)
								.newInstance();
						escStatus
								.setName(ItmsConstants.STATUS_TYPE_STRING_END_ESC);
						statusMap.put(ItmsConstants.STATUS_TYPE_STRING_END_ESC,
								escStatus);
						escStatus.init(tmpTemplate, null);
					}

					// 创建边
					escStatus = (IStatusBase) statusMap
							.get(ItmsConstants.STATUS_TYPE_STRING_END_ESC);
					ITranserEdge trans = new ITranserEdge();
					trans.setAction(null);
					trans.setToStatus(escStatus);
					trans.setEventor(null);
					status.transMap.put(ItmsConstants.EVENT_TYPE_ESC_KEY_DOWN,
							trans);
				}
				if (st.userSwitch != null) {
					IStatusBase switchStatus = null;
					if (!statusMap
							.containsKey(ItmsConstants.STATUS_TYPE_STRING_END_USER_SWITCH)) {
						// 创建userSwitch状态, 如果已经存在则不再创建
						IStatusTemplate tmpTemplate = new IStatusTemplate();
						tmpTemplate.type = ItmsConstants.STATUS_TYPE_INT_END_USER_SWITCH;
						tmpTemplate.transList = null;

						switchStatus = (IStatusBase) Class.forName(
								ItmsConstants.DEFAULY_CLASSNAME_STATUS)
								.newInstance();
						switchStatus
								.setName(ItmsConstants.STATUS_TYPE_STRING_END_USER_SWITCH);
						statusMap
								.put(
										ItmsConstants.STATUS_TYPE_STRING_END_USER_SWITCH,
										switchStatus);
						switchStatus.init(tmpTemplate, null);
					}
					// 创建边
					switchStatus = (IStatusBase) statusMap
							.get(ItmsConstants.STATUS_TYPE_STRING_END_USER_SWITCH);

					StringTokenizer tkz = new StringTokenizer(st.userSwitch,
							"|");

					ITranserEdge trans = null;
					while (tkz.hasMoreTokens()) {
						trans = new ITranserEdge();
						trans.setAction(null);
						trans.setToStatus(switchStatus);
						trans.setEventor(null);
						status.transMap.put(tkz.nextToken(), trans);
					}

				}
				if (st.type == ItmsConstants.STATUS_TYPE_INT_START) {
					startStatus = status;
				}
			}
			for (i = 0; i < size; i++) {
				st = (IStatusTemplate) temp.statusList.get(i);
				status = (IStatusBase) statusMap.get(st.name);
				status.init(st, statusMap);
			}
		} catch (Exception e) {

		}

	}

	public int processEvent(String eventName, EventObject e) {
		if (currentStatus == null)
			return ItmsConstants.EVENT_PROCESS_RETURN_STATUS_ERROR;
		CursorDrawControl cursorDrawControl = CursorDrawControl.getInstance();
		ICursorDataBase cursorData = cursorDrawControl.getCursorData();
		if (currentStatus.getTransMapAction() != null) {
			String newEvtName = UserTask.getInstance().getTransformState(e,
					currentStatus.getTransMapAction());
			if(newEvtName != null){
				eventName = newEvtName;
			}
		}
		currentStatus = currentStatus.processEvent(eventName, e);
		if (currentStatus.type == ItmsConstants.STATUS_TYPE_INT_END) {
			// 正常结束,记录最后一个点
			int len = 0;
			UFPoint ps[] = null;
			ps = cursorData.getPoints();
			if (ps == null || ps.length <= 0) {
				// 没点，不记录
				return ItmsConstants.EVENT_PROCESS_RETURN_STATUS_END;
			}
			len = ps.length;
			ICursorDataPoints dps = new ICursorDataPoints();
			dps.copy(cursorData);
			ItmsCommonPara.cursorPointsSequence.add(dps);
			ItmsCommonPara.commonPoint = ps[len - 1];
			if (owner != null) {
				if (owner.lastPointIndex >= 0 && owner.lastPointIndex < len) {
					ItmsCommonPara.commonPoint = ps[owner.lastPointIndex];
				}
			}
			return ItmsConstants.EVENT_PROCESS_RETURN_STATUS_END;
		} else if (currentStatus.type == ItmsConstants.STATUS_TYPE_INT_NORMAL) {
			return ItmsConstants.EVENT_PROCESS_RETURN_STATUS_NORMAL;
		} else if (currentStatus.type == ItmsConstants.STATUS_TYPE_INT_START) {
			return ItmsConstants.EVENT_PROCESS_RETURN_STATUS_NORMAL;
		} else if (currentStatus.type == ItmsConstants.STATUS_TYPE_INT_END_ESC) {
			// esc 结束 不加入ItmsCommonPara.cursorPointsSequence
			return ItmsConstants.EVENT_PROCESS_RETURN_STATUS_ESC;
		} else if (currentStatus.type == ItmsConstants.STATUS_TYPE_INT_END_USER_SWITCH) {
			// 用户切换 结束
			int len = 0;
			UFPoint ps[] = null;
			ps = cursorData.getPoints();
			len = ps.length;
			if (len <= 0) {
				// 没点，不记录
				ItmsCommonPara.userSwitchEvent = eventName;// 赋值给公共变量
				// 用户切换子任务的事件名字
				return ItmsConstants.EVENT_PROCESS_RETURN_STATUS_END_USER_SWITCH;
			}

			// ICursorDataPoints dps = new ICursorDataPoints();
			// 用户切换的时候，不把还没执行完的点加入到数组中
			// dps.copy(curCursorDataToImg);
			// ItmsCommonPara.cursorPointsSequence.add(dps);

			ItmsCommonPara.commonPoint = ps[len - 1];
			if (owner != null) {
				if (owner.lastPointIndex >= 0 && owner.lastPointIndex < len) {
					ItmsCommonPara.commonPoint = ps[owner.lastPointIndex];
				}
			}

			ItmsCommonPara.userSwitchEvent = eventName;// 赋值给公共变量 用户切换子任务的事件名字
			return ItmsConstants.EVENT_PROCESS_RETURN_STATUS_END_USER_SWITCH;
		} else if (currentStatus.type == ItmsConstants.STATUS_TYPE_INT_TERMINATE_TASK) {
			// 退出到最外去修改DIB
			int len = 0;
			UFPoint ps[] = null;
			ps = cursorData.getPoints();
			len = ps.length;
			if (len <= 0) {
				// 没点，不记录
				return ItmsConstants.EVENT_PROCESS_RETURN_STATUS_TERMINATE_TASK;
			}

			ICursorDataPoints dps = new ICursorDataPoints();
			dps.copy(cursorData);
			ItmsCommonPara.cursorPointsSequence.add(dps);

			ItmsCommonPara.commonPoint = ps[len - 1];
			if (owner != null) {
				if (owner.lastPointIndex >= 0 && owner.lastPointIndex < len) {
					ItmsCommonPara.commonPoint = ps[owner.lastPointIndex];
				}
			}
			return ItmsConstants.EVENT_PROCESS_RETURN_STATUS_TERMINATE_TASK;
		}
		return ItmsConstants.EVENT_PROCESS_RETURN_STATUS_ERROR;
	}

	public void start(String enterStatusFlag, UFPoint firstP) {
		CursorDrawControl cursorDrawControl = CursorDrawControl.getInstance();
		cursorDrawControl.clearCursorDataContent();

		currentStatus = startStatus;

		if (enterStatusFlag != null) {
			if (statusMap.containsKey(enterStatusFlag)) {
				// 修改了当前的状态
				currentStatus = (IStatusBase) statusMap.get(enterStatusFlag);
				if (firstP != null) {
					cursorDrawControl.addCursorDataPoint(firstP);
				}
			}
		}
	}

	public void setParaData(Object data) {
		// curCursorData = (ICursorDataBase) data;
	}

	public void setCurrentPoint(UFPoint p) {
		CursorDrawControl cursorDrawControl = CursorDrawControl.getInstance();
		cursorDrawControl.setCursorDataPoint(p);
	}
}
