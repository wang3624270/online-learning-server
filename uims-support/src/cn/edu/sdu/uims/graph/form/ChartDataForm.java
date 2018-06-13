package cn.edu.sdu.uims.graph.form;

import java.util.List;

import cn.edu.sdu.common.form.UForm;

public class ChartDataForm extends UForm{
	private List<ChartDataValueForm> dataList;
	private int rowNum;
	public List<ChartDataValueForm> getDataList() {
		return dataList;
	}
	public void setDataList(List<ChartDataValueForm> dataList) {
		this.dataList = dataList;
	}
	public int getRowNum() {
		return rowNum;
	}
	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}
}
