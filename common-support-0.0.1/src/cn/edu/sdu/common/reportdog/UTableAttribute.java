package cn.edu.sdu.common.reportdog;

import java.io.Serializable;

public class UTableAttribute implements Serializable {

	public int colNum;
	public int rowNum;
	public float width[];
	public int horizontalAlignment;
	public float spacingBefore;
	public float spacingAfter;
	public boolean showTable = true;
}
