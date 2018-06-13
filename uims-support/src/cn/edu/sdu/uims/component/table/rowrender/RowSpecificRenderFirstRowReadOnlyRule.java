package cn.edu.sdu.uims.component.table.rowrender;

import javax.swing.JTable;

public class RowSpecificRenderFirstRowReadOnlyRule extends
		RowSpecificRenderBaseRule {
	@Override
	public boolean isEditSpecific(JTable table, int row, int col, Object data) {
		// TODO Auto-generated method stub
		if(row == 0) {
			return true;
		}
		return false;
	}

}
