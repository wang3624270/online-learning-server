package cn.edu.sdu.uims.itms.event;

import java.util.EventObject;
import java.util.List;

import cn.edu.sdu.uims.itms.form.IFormI;
import cn.edu.sdu.uims.trans.UFPoint;

public class ItmsEvent extends EventObject {
	private IFormI paraForm;
	private List  dataList;
	private String  actionType;
	private UFPoint point;
	public ItmsEvent() {
		super(null);
	}
	public ItmsEvent(Object sourece) {
		super(null);
	}
	public ItmsEvent(IFormI paraForm, List dataList,String actionType){
		this(null,paraForm,dataList,actionType,null);
	}
	
	public ItmsEvent(Object source, IFormI paraForm, List dataList,String actionType){
		this(source,paraForm,dataList,actionType, null);
	}
	public ItmsEvent(Object source, IFormI paraForm, List dataList,String actionType, UFPoint fp){
		super(source);
		this.paraForm = paraForm;
		this.dataList = dataList;
		this.actionType = actionType;
		this.point = fp;
	}
	
	public Object getSource() {
		return source;
	}
	public void setSource(Object source) {
		this.source = source;
	}
	public IFormI getParaForm() {
		return paraForm;
	}
	public void setParaForm(IFormI paraForm) {
		this.paraForm = paraForm;
	}
	public List getDataList() {
		return dataList;
	}
	public void setDataList(List dataList) {
		this.dataList = dataList;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	public UFPoint getPoint() {
		return point;
	}
	public void setPoint(UFPoint point) {
		this.point = point;
	}
	
}
