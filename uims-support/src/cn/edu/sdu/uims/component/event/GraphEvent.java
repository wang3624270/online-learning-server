package cn.edu.sdu.uims.component.event;

import java.util.EventObject;

public class GraphEvent extends EventObject {

	private int selectIndex = 0-1;
	public GraphEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}
	public GraphEvent(Object source, int selectIndex) {
		this(source);
		this.selectIndex = selectIndex;
	}
	public int getSelectIndex(){
		return selectIndex;
	}
}
