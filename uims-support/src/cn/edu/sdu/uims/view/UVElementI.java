package cn.edu.sdu.uims.view;

import java.awt.Graphics;
import java.util.HashMap;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.uims.base.UComponentI;
import cn.edu.sdu.uims.trans.UMatrix;
import cn.edu.sdu.uims.trans.UPoint;
import cn.edu.sdu.uims.trans.URect;

public interface UVElementI  {
	void draw(Graphics g, UMatrix m);
	void setParameterData(HashMap map, UFormI form, UComponentI father);
	int getSelectStatus(UPoint p);
	void drawBox(Graphics g, UMatrix m);
	void shift(int dx, int dy);
	URect getBox();
}
