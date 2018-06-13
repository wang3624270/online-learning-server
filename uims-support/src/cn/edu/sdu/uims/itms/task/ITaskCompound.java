package cn.edu.sdu.uims.itms.task;

import java.awt.Graphics;
import java.util.EventObject;
import java.util.HashMap;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.uims.itms.ItmsCommonPara;
import cn.edu.sdu.uims.itms.ItmsConstants;
import cn.edu.sdu.uims.itms.cursor.ICursorDataBase;
import cn.edu.sdu.uims.itms.def.ISubTaskTemplate;
import cn.edu.sdu.uims.itms.def.ITransferTemplate;
import cn.edu.sdu.uims.itms.form.IFormI;
import cn.edu.sdu.uims.itms.service.ItmsTaskService;
import cn.edu.sdu.uims.trans.UFPoint;

public class ITaskCompound extends ITaskBase{
	private ISubTask subTasks[];
	private int subTaskNum;
	private int currentNo;
	private HashMap m_subTaskMap = new HashMap();
	private ISubTask m_selectedTask; //子任务间是select关系时，选中的Task

	private HashMap m_subTaskAndTransMap;//key 是subTask的name  value是  transMap(StringHashMap *transMap  key是trans中的eventType  value是转到的subTask的name (OSting))
	public  ITaskCompound(){
		m_subTaskAndTransMap = new HashMap();
	}

	public void init(){
		if(taskTemplate == null)
			return ;
		ITaskBase taskInSub = null;
		currentNo = 0;
		int i = 0,j;
		ISubTaskTemplate stt = null;
		ItmsTaskService  s = ItmsTaskService.getInstance();
		//dataForm = (IForm *)wxCreateDynamicObject(taskTemplate.dataFormName);
		subTaskNum = taskTemplate.taskList.size();

		subTasks = new ISubTask [subTaskNum];
		for(i=0;i<subTaskNum;i++) 
		{
			stt = (ISubTaskTemplate )taskTemplate.taskList.get(i);
			subTasks[i] = new ISubTask();
			subTasks[i].setName(stt.name);
			subTasks[i].setDataMembet(stt.dataMember);
		
			subTasks[i].setCursor(s.getCursorObject(stt.cursorName));
			subTasks[i].setCursorData(s.getCursorDataObject(stt.cursorDataName));
			if(stt.type == ItmsConstants.TASK_TYPE_INT_TASK) 
			{
				taskInSub = s.getTaskByName(stt.templateName);
				subTasks[i].setTaskObject(taskInSub);
				taskInSub.setOwner(subTasks[i]);
			}
			else if(stt.type == ItmsConstants.TASK_TYPE_INT_DIAGRAM)
			{
				taskInSub = s.getTaskByName(stt.templateName);
				subTasks[i].setTaskObject(taskInSub);
				taskInSub.setOwner(subTasks[i]);
			}
			subTasks[i].setExcuteTimes(stt.executeTimes);
			subTasks[i].multiExeStartStatus = stt.multiExeStartStatus;
			subTasks[i].nextSubStartStatus = stt.nextSubStartStatus;
			subTasks[i].lastPointIndex = stt.lastPointIndex;
			m_subTaskMap.put(stt.name,i);

			if (stt.transList != null)
			{
				//构造任务切换的map
				int size = stt.transList.size();
				if (size > 0)
				{
					ITransferTemplate tt = null;
					HashMap transMap = new HashMap();
					for (j = 0;j < size;j++)
					{
						tt = (ITransferTemplate)stt.transList.get(j);
						transMap.put(tt.eventType,tt.nextStatusName);
					}
					m_subTaskAndTransMap.put(stt.name,transMap);
				}
				
			}
		}
	}
	public int processEvent(String eventName, EventObject  e){
		if(currentNo < 0 || currentNo >= subTaskNum)
			return ItmsConstants.EVENT_PROCESS_RETURN_STATUS_ERROR;

		//需要利用taskTemplate的 statusMapping 当任务切换或者repeat的时候
		ISubTask task = subTasks[currentNo];

		if(task == null)
			return ItmsConstants.EVENT_PROCESS_RETURN_STATUS_ERROR;
		
		int ret = task.getTaskObject().processEvent(eventName,e);
		if (ret == ItmsConstants.EVENT_PROCESS_RETURN_STATUS_END)
		{
			//首先判断子任务剩余要执行的次数
			if (task.getExcuteTimes() == -1)
			{
				//n次执行 重新start当前子任务 还原leavingTimes
				task.restoreLevingTimes();

				//判断subTask的 multiExeStartStatus决定起始状态
				if ((task.multiExeStartStatus !=null) && ItmsCommonPara.userTaskTotalPointsNum > 0)
				{
					//有状态设置
					task.start(task.multiExeStartStatus,ItmsCommonPara.commonPoint);
				}
				else
				{
					task.start(null,null);
				}
				
				return ItmsConstants.EVENT_PROCESS_RETURN_STATUS_NORMAL;
			}
			else
			{
				task.leavingTimes--;
				if (task.leavingTimes == 0)
				{
					//当前子任务执行完成
					if (taskTemplate.opType == ItmsConstants.OPERATE_TYPE_INT_SELECT)
					{
						//选择执行 这个复合任务相当于作完了
						return ItmsConstants.EVENT_PROCESS_RETURN_STATUS_END;
					}
					else
					{
						//顺序执行
						if (currentNo >= subTaskNum - 1)
						{
							//所有的子任务都做完了 这个复合任务相当于作完了
							return ItmsConstants.EVENT_PROCESS_RETURN_STATUS_END;
						}
						else
						{
							//应该继续下一个子任务
							ISubTask nextT = subTasks[currentNo + 1];

							//判断subTask的 nextSubStartStatus决定下一个子任务的起始状态
							if ((task.nextSubStartStatus != null) && ItmsCommonPara.userTaskTotalPointsNum > 0)
							{
								//有状态设置
								nextT.start(task.nextSubStartStatus,ItmsCommonPara.commonPoint);
							}
							else
							{
								nextT.start(null,null);
							}
							
							currentNo++;
							return ItmsConstants.EVENT_PROCESS_RETURN_STATUS_NORMAL;
						}
					}
				}
				else
				{
					//当前子任务还有剩余执行次数 但不还原leavingTimes
					//判断subTask的 multiExeStartStatus决定下一次起始状态
					task.start(task.multiExeStartStatus,ItmsCommonPara.commonPoint);
					return ItmsConstants.EVENT_PROCESS_RETURN_STATUS_NORMAL;
				}

			}	
			
		}	
		else if (ret == ItmsConstants.EVENT_PROCESS_RETURN_STATUS_END_USER_SWITCH)
		{
			//用户任务切换退出，如果找到了相应的切换转换，则向上传的返回值改为EVENT_PROCESS_RETURN_STATUS_END
			
			//将m_selectedTask 设置为新的要切换到的任务，这里不用考虑multiExeStartStatus 和 nextSubStartStatus ，由上一层考虑

			//如果没有找到相应的转换，则继续向上传递EVENT_PROCESS_RETURN_STATUS_END_USER_SWITCH
			if (m_subTaskAndTransMap.containsKey(task.getName()))
			{
				//有trans
				HashMap transMap = (HashMap )m_subTaskAndTransMap.get(task.getName());
				if (transMap.containsKey(ItmsCommonPara.userSwitchEvent))
				{
					String ostr = (String)transMap.get(ItmsCommonPara.userSwitchEvent);//找到要转移到的子任务
					ItmsCommonPara.userSwitchEvent = "";//用完后立刻清空用户切换子任务的事件名字
//					ISubTask oldSubTask = task;

					int tmpNum = (Integer)m_subTaskMap.get(ostr);
					m_selectedTask = subTasks[tmpNum];

//	 				newSubTask.restoreLevingTimes();//新任务还原重启
//	 				newSubTask.start();
	// 
//	 				currentNo = tmpNum;
//	 				oldSubTask.restoreLevingTimes(); //原来任务还原重启
//	 				oldSubTask.start();

					return ItmsConstants.EVENT_PROCESS_RETURN_STATUS_END;
				}
			}
			return ItmsConstants.EVENT_PROCESS_RETURN_STATUS_END_USER_SWITCH;
		}
		else if (ret == ItmsConstants.EVENT_PROCESS_RETURN_STATUS_ESC)
		{
			//esc 退出,退出到UserTask 
			return ItmsConstants.EVENT_PROCESS_RETURN_STATUS_ESC;
		}
		else if (ret == ItmsConstants.EVENT_PROCESS_RETURN_STATUS_TERMINATE_TASK)
		{
			//退出到UsrTask去修改DIB
			return ItmsConstants.EVENT_PROCESS_RETURN_STATUS_TERMINATE_TASK;
		}
		return ret;	
	}
	public void start(String enterStatusFlag,UFPoint firstP)
	{
		currentNo = 0;

		//根据子任务之间的关系决定开始的情况
		if (taskTemplate.opType == ItmsConstants.OPERATE_TYPE_INT_SEQUENCE)
		{
			//顺序执行
			m_selectedTask = null;
		}
		else if (taskTemplate.opType == ItmsConstants.OPERATE_TYPE_INT_SELECT)
		{
			//选择执行
			if (m_selectedTask == null)
			{
				//当第一次执行还没有子任务的时候，默认第一个子任务是select任务
				m_selectedTask = subTasks[0];
			}
			else
			{
				//将当前No设置为m_selectedTask的subTask的No
				currentNo = (Integer)m_subTaskMap.get(m_selectedTask.getName());
			}
		}
		else
		{
			//默认为顺序执行
			m_selectedTask = null;
		}

		//一层一层的初始化
		for (int i = 0;i < subTaskNum;i++)
		{
			ISubTask task = subTasks[i];
			if(task != null)
			{
				task.restoreLevingTimes();
				if (i == currentNo)
				{
					//马上要执行的任务
					if (enterStatusFlag != null)
					{
						//修改了进入时的第一个状态   /临时注释，应该有问题
//						if (taskTemplate.statusMapping.find(enterStatusFlag) != taskTemplate.statusMapping.end())
//						{
//							task.start((String)taskTemplate.statusMapping.get(enterStatusFlag),firstP);
//						}
//						else
//						{
//							task.start();
//						}
					}
					else
					{
						task.start(null,null);
					}
				}
				else
				{
					task.start(null,null);
				}
				
			}
		}
		
	}
	public void setParaData(Object data)
	{
		for (int i = 0;i < subTaskNum;i++)
		{
			ISubTask task = subTasks[i];
			if(task != null)
			{
				
				if(data != null)
				{
					IFormI form = (IFormI )data;
					UFormI data2 = (UFormI )form.getFieldData(task.getDataMember());
					if (data2 instanceof ICursorDataBase)
					{
						((ICursorDataBase)data2).type = task.getName();
						((ICursorDataBase)data2).cursor = task.getCursor();
					}
					task.getTaskObject().setParaData(data2);
				}
				
			}
		}
	}

	public void setCurrentPoint(UFPoint p)
	{
		ISubTask task = subTasks[currentNo];
		if (task != null)
		{
			task.getTaskObject().setCurrentPoint(p);
		}
	}

	public void drawCursor(Graphics dc)
	{
		ISubTask task = subTasks[currentNo];
		if (task != null)
		{
			task.drawCursor(dc);
		}
	}

}
