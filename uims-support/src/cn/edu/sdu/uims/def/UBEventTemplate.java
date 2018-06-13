package cn.edu.sdu.uims.def;

import java.io.Serializable;

public class UBEventTemplate implements Serializable{

	public String []denpends ;//事件所依赖的form变量
	public String[] getDenpends() {
		return denpends;
	}
	public void setDenpends(String[] denpends) {
		this.denpends = denpends;
	}
	public String[] getTargets() {
		return targets;
	}
	public void setTargets(String[] targets) {
		this.targets = targets;
	}
	public String getPropertyName() {
		return propertyName;
	}
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getColspan() {
		return colspan;
	}
	public void setColspan(String colspan) {
		this.colspan = colspan;
	}
	public String getToPage() {
		return toPage;
	}
	public void setToPage(String toPage) {
		this.toPage = toPage;
	}
	public String getFormShortName() {
		return formShortName;
	}
	public void setFormShortName(String formShortName) {
		this.formShortName = formShortName;
	}
	public String getEventSource() {
		return eventSource;
	}
	public void setEventSource(String eventSource) {
		this.eventSource = eventSource;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String[] targets ;//局部刷新的对象
	public String propertyName;//对应与哪个控件
	public String action = "" ;//处理事件.do
	public String colspan = "" ;//跨的列数
	public String toPage = "" ;//目标跳转页面
	public String formShortName = "" ;//对应的form 默认没有
	public String eventSource = "" ;//事件的来源
	public String eventType = "" ;//事件类型
}
