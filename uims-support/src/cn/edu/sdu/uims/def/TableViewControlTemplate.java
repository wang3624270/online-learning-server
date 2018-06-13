package cn.edu.sdu.uims.def;

import java.io.Serializable;

public class TableViewControlTemplate implements Serializable {
	public static final int CONTROL_TYPE_MULTI_VIEW = 1; 
	public static final int CONTROL_TYPE_COLUMN_CONTROL = 2; 
	public static final int VIEW_TYPE_DATA = 0; 
	public static final int VIEW_TYPE_HISTOGRAM = 1; //Histogram 直方图 
	public static final int VIEW_TYPE_PIE =2;
	public static final int VIEW_TYPE_CUSTOM = 3;
	public int  type = CONTROL_TYPE_MULTI_VIEW;
	public int width = 80;
	public int rowHeight = 24;
	public int views[];
	public int dims[];
	public String customViewName;
	public String customViewLabel;
}
