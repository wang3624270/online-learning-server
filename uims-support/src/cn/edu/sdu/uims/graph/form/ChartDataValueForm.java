package cn.edu.sdu.uims.graph.form;

import cn.edu.sdu.common.form.UForm;

public class ChartDataValueForm extends UForm{
	private String col;
	private String row;
	private double value;
	public String getCol() {
		return col;
	}
	public void setCol(String col) {
		this.col = col;
	}
	public String getRow() {
		return row;
	}
	public void setRow(String row) {
		this.row = row;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	public ChartDataValueForm(String row, String col, double value){
		this.row = row;
		this.col = col;
		this.value = value;
	}
}
