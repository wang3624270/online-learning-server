package cn.edu.sdu.uims.component.table.rowrender;

import javax.swing.JTable;

import cn.edu.sdu.uims.form.UCellLockedDataI;

public class RowSpecificRenderCellLockedRule extends RowSpecificRenderBaseRule {
	public boolean isColorSpecific(JTable table, int row, int col, Object data) {
		// TODO Auto-generated method stub
		if(data == null || !(data instanceof UCellLockedDataI))
			return false;
		UCellLockedDataI w = (UCellLockedDataI)data;
		return w.isLocked(col);
	}
	
	/*	@Override

	public boolean isEditSpecific(JTable table, int row, int col, Object data) {
		// TODO Auto-generated method stub
		if(data == null || !(data instanceof UCellLockedDataI))
			return false;
		UCellLockedDataI w = (UCellLockedDataI)data;
		return w.isLocked(col);
	}*/
}
