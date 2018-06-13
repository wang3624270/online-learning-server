package cn.edu.sdu.uims.graph.model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cn.edu.sdu.uims.def.UTableTemplate;
import cn.edu.sdu.uims.graph.view.ControlParameter;
import cn.edu.sdu.uims.graph.view.ViewParameter;
import cn.edu.sdu.uims.trans.UFPoint;
import cn.edu.sdu.uims.trans.UPoint;

public class GElementTable extends GElement {
	public UTableTemplate atbleTemplate;
	public GElementTableCell [] cells = null;
	public UFPoint po = new UFPoint();

	public void makeCells(){
	}
	public void drawDo(Graphics dc, ViewParameter p, ControlParameter c,
			Object par,UPoint shiftPoint) {
		if(cells == null) {
			makeCells();
		}
		if(cells == null)
			return;
		Graphics2D dc2d = (Graphics2D) dc;
		for(int i = 0; i < cells.length;i++) {
			cells[i].draw(dc, p, c, par,shiftPoint);
		}
	}
	public void read(DataInputStream in) throws IOException {
		super.read(in);
	}

	public void write(DataOutputStream out) throws IOException {
		super.write(out);
	}
}
