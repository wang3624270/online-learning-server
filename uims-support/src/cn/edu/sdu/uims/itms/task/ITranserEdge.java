package cn.edu.sdu.uims.itms.task;

import cn.edu.sdu.uims.itms.eventor.IEventorBase;
import cn.edu.sdu.uims.itms.status.IStatusBase;

public class ITranserEdge {
	private	IStatusBase toStatus;
	private String action;
	private IEventorBase eventor;
	public IStatusBase getToStatus(){
		return toStatus;
	}
	public void setToStatus(IStatusBase s){
		toStatus = s;
	}
	public String getAction(){
		return action;
	}
	public void setAction(String act){
		action = act;
	}
	public IEventorBase  getEventor(){
		return eventor;
	}
	public void setEventor(IEventorBase eventor1){
		eventor = eventor1;
	}
}
