package cn.edu.sdu.uims.component.event;

import java.util.EventObject;

public class GroupComponentEvent extends EventObject {

	
	private int selectIndex;
	private Object selectCom;
	private String paras;

	/**
	 * 
	 */
	
	public GroupComponentEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}

	public GroupComponentEvent(Object source, int selectIndex, Object selectCom) {
		super(source);
		this.selectIndex = selectIndex;
		this.selectCom =selectCom;
		this.paras  = paras;
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 1L;

	public int getSelectIndex() {
		return selectIndex;
	}

	public void setSelectIndex(int selectIndex) {
		this.selectIndex = selectIndex;
	}

	public Object getSelectCom() {
		return selectCom;
	}

	public void setSelectCom(Object selectCom) {
		this.selectCom = selectCom;
	}

	public String getParas() {
		return paras;
	}

	public void setParas(String paras) {
		this.paras = paras;
	}

}
