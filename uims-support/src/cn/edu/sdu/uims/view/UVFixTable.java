package cn.edu.sdu.uims.view;

import java.awt.Component;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.base.UFixTable;
import cn.edu.sdu.uims.def.UTableTemplate;
import cn.edu.sdu.uims.trans.UMatrix;
import cn.edu.sdu.uims.trans.UMatrixUtil;
import cn.edu.sdu.uims.trans.UPoint;
import cn.edu.sdu.uims.trans.URect;

public class UVFixTable extends UFixTable implements UVElementI {
	
	protected List list = new ArrayList();
	protected int x, y, w,h;

	public UVFixTable() {
		this(null);
	}

	public UVFixTable(UTableTemplate template) {
		super(template);
	}
	public void setShellBounds(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}


	public void draw(Graphics g, UMatrix m) {
		// TODO Auto-generated method stub
		int i;
        UMatrix mt = makeTransMatrix();
        UMatrix ms = new UMatrix();
        UMatrixUtil.multi(m.m, mt.m, ms.m);
		UVElementI com;
		for(i= 0; i< list.size();i++){
			com = (UVElementI)list.get(i);
			com.draw(g, ms);
		}
	}

	public void addComponent(UComponentI com){
		list.add(com);
	}
	public UComponentI getStaticTextObject(String str){
//		UVText laber = new UVText();
//		laber.setText(str);
//		return laber;
		return null;
	}
	public UComponentI getFieldsObject(){
//		UVText laber = new UVText();
//		return laber;
		return null;
	}

	public Component getAWTComponent() {
		return null;
	}
	public UMatrix makeTransMatrix(){
		return new UMatrix(x,y);
	}

	public void setParameterData(HashMap map, UFormI form, UComponentI father) {
		// TODO Auto-generated method stub
		
	}

	public void drawBox(Graphics g, UMatrix m) {
		// TODO Auto-generated method stub
		
	}


	public URect getBox() {
		// TODO Auto-generated method stub
		return null;
	}

	public void shift(int dx, int dy) {
		// TODO Auto-generated method stub
		
	}

	public int getSelectStatus(UPoint p) {
		// TODO Auto-generated method stub
		return -1;
	}

}
