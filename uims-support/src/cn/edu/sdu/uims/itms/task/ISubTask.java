package cn.edu.sdu.uims.itms.task;

import cn.edu.sdu.uims.itms.cursor.CursorDrawControl;
import cn.edu.sdu.uims.itms.cursor.ICursorBase;
import cn.edu.sdu.uims.itms.cursor.ICursorDataBase;
import cn.edu.sdu.uims.itms.form.IForm;
import cn.edu.sdu.uims.trans.UFPoint;

public class ISubTask extends ITaskBase{
	private ICursorBase cursor;
	private ICursorDataBase cursorData;
	private String dataMember;
	private ITaskBase taskObject;
	private String subname;
	private int executeTimes =1;//总共要执行的次数
	//multiExeStartStatus表示多次执行时非第一次进入的初始状态,nextSubStartStatus表示如果有下一个子任务则它的初始状态
	public String multiExeStartStatus,nextSubStartStatus;

	public int leavingTimes =1;//当前剩余执行的次数
	public int lastPointIndex =-1;//当任务切换或者点顺序执行的时候，下个子任务需要的最后一个点在这次任务点集中的位置,默认-1 是最后一个点
	
	public void setName(String name){
		subname = name;
	}
	public String getName(){
		return subname;
	}
	public void setExcuteTimes(int times){
		executeTimes = times;
	}
	public int  getExcuteTimes(){
		return executeTimes;
	}
	void restoreLevingTimes(){leavingTimes = executeTimes;}//还原原有的剩余次数

	public void setDataMembet(String str){
		this.dataMember = str;
	}
	public String getDataMember(){
		return this.dataMember;
	}

	public ICursorBase getCursor(){
		return this.cursor;
	}
	public void setCursor(ICursorBase cursor1){
		this.cursor =cursor1;
	}

	public void setTaskObject(ITaskBase taskObject1){
		this.taskObject = taskObject1;
	}
	public ITaskBase  getTaskObject(){
		return this.taskObject;
	}
	public ICursorDataBase getCursorData(IForm dataForm){
		Object  o = dataForm.getFieldData(dataMember);
		if(o == null)
			return  null;
		if(o instanceof ICursorDataBase ) {
			return (ICursorDataBase )o;
		}
		else 
			return null;
	}

	public void start(String enterStatusFlag ,UFPoint firstP)
	{
		CursorDrawControl.getInstance().startNewCursor(cursor, cursorData);
		taskObject.start(enterStatusFlag,firstP);
	}
	public ICursorDataBase getCursorData() {
		return cursorData;
	}
	public void setCursorData(ICursorDataBase cursorData) {
		this.cursorData = cursorData;
	}
}
