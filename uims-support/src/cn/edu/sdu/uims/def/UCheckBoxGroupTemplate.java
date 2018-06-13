package cn.edu.sdu.uims.def;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.dom4j.Element;

public class UCheckBoxGroupTemplate extends UElementTemplate {
	public int row = 1, column = 1;
	public int rowHeight = 25, columnWidth = 120;
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
	public boolean[] getChecks() {
		return checks;
	}
	public void setChecks(boolean[] checks) {
		this.checks = checks;
	}
	public boolean checks[];
	public void getSelfAttribute(Element e) {
		String str;
		str  = e.attributeValue("row");
		if(str != null)
			row=Integer.parseInt(str);
		str  = e.attributeValue("column");
		if(str != null)
			column=Integer.parseInt(str);
		str  = e.attributeValue("rowHeight");
		if(str != null)
			rowHeight=Integer.parseInt(str);
		str  = e.attributeValue("columnWidth");
		if(str != null)
			columnWidth=Integer.parseInt(str);
	}	
	public void read(DataInputStream in) throws IOException {
		super.read(in);
		row = in.readInt();
		column = in.readInt();
		int len, i;
		len = in.readInt();
		if(len == 0)
			checks = null;
		else {
			checks = new boolean[len];
			for(i =0; i <len;i++)
				checks[i] = in.readBoolean();
		}
	}
	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		out.writeInt(row);
		out.writeInt(column);
		if(checks == null)
			out.writeInt(0);
		else {
			out.writeInt(checks.length);
			for(int i = 0; i < checks.length;i++)
				out.writeBoolean(checks[i]);
		}
	}
}
