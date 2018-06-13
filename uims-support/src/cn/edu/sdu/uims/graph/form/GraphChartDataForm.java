package cn.edu.sdu.uims.graph.form;

import cn.edu.sdu.common.form.UForm;

public class GraphChartDataForm extends UForm {
	private String []row;
	private String []col;
	private double [][]data;
	public String[] getRow() {
		return row;
	}
	public void setRow(String[] row) {
		this.row = row;
	}
	public String[] getCol() {
		return col;
	}
	public void setCol(String[] col) {
		this.col = col;
	}
	public double[][] getData() {
		return data;
	}
	public void setData(double[][] data) {
		this.data = data;
	}
}
