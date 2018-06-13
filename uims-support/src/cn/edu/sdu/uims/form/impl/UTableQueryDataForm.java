package cn.edu.sdu.uims.form.impl;

import java.util.HashMap;
import java.util.List;

import cn.edu.sdu.common.form.UForm;
import cn.edu.sdu.uims.handler.impl.UPageActionHandlerI;

public class UTableQueryDataForm extends UForm {
	private UPageActionHandlerI handler = null;
	private int total;
	private int start;
	private int pageNum =40;
	private List<Integer>idList;
	private List<UForm> dataList;
	private HashMap parasMap;
	
	public HashMap getParasMap() {
		return parasMap;
	}
	public void setParasMap(HashMap parasMap) {
		this.parasMap = parasMap;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public List<UForm> getDataList() {
		return dataList;
	}
	public void setDataList(List<UForm> dataList) {
		this.dataList = dataList;
	}
	public List<Integer> getIdList() {
		return idList;
	}
	public void setIdList(List<Integer> idList) {
		this.idList = idList;
	}
	public UPageActionHandlerI getHandler() {
		return handler;
	}
	public void setHandler(UPageActionHandlerI handler) {
		this.handler = handler;
	}
}
