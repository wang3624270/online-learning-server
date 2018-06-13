package cn.edu.sdu.uims.def;

import java.util.ArrayList;

import cn.edu.sdu.common.reportdog.UTemplate;


public class UGraphButtonBarTemplate extends UTemplate {
	public int row = 1, column =1;
	public ArrayList items;   //UButtonTemplate
	public int layoutStyle = 1;
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}
	public ArrayList getItems() {
		return items;
	}
	public void setItems(ArrayList items) {
		this.items = items;
	}
	public int getLayoutStyle() {
		return layoutStyle;
	}
	public void setLayoutStyle(int layoutStyle) {
		this.layoutStyle = layoutStyle;
	}
}
