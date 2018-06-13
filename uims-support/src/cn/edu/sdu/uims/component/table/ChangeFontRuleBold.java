package cn.edu.sdu.uims.component.table;

import javax.swing.JTable;

public class ChangeFontRuleBold implements ChangeRenderRule {
	boolean  boldMarks[];
	public ChangeFontRuleBold(){
	}
	public ChangeFontRuleBold(boolean marks[]){
		boldMarks = marks;
	}
	@Override
	public boolean ruleToChangeRender(JTable table, int row, int col) {
		// TODO Auto-generated method stub
		return boldMarks[row];
	}
	public boolean[] getBoldMarks() {
		return boldMarks;
	}
	public void setBoldMarks(boolean[] boldMarks) {
		this.boldMarks = boldMarks;
	}

}
