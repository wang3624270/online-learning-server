package cn.edu.sdu.uims.component.table.rowrender;

import javax.swing.JTable;

import org.springframework.stereotype.Service;

import cn.edu.sdu.uims.form.UWarnnigDataI;

public class RowSpecificRenderWarningColorRule extends
		RowSpecificRenderBaseRule {
	@Override
	public boolean isColorSpecific(JTable table, int row, int col, Object data) {
		// TODO Auto-generated method stub
		if(data == null || !(data instanceof UWarnnigDataI))
			return false;
		UWarnnigDataI w = (UWarnnigDataI)data;
		return w.isWarnning();
	}

}
