package cn.edu.sdu.uims.component.table.rowrender;

import javax.swing.JTable;


public class RowSpecificRenderBaseRule implements RowSpecificRenderI{

	@Override
	public boolean isColorSpecific(JTable table, int row, int col, Object data) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEditSpecific(JTable table, int row, int col, Object data) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isFontSpecific(JTable table, int row, int col, Object data) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getCellColorName(JTable table, int row, int col, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCellFontName(JTable table, int row, int col, Object data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isBackgroundColorSpecific(JTable table, int row, int col,
			Object data) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getCellBackgroundCodeName(JTable table, int row, int col,
			Object data) {
		// TODO Auto-generated method stub
		return null;
	}

}
