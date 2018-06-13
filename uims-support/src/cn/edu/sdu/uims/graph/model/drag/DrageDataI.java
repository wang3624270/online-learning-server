package cn.edu.sdu.uims.graph.model.drag;

import java.awt.Graphics;

import cn.edu.sdu.uims.graph.view.ViewParameter;
import cn.edu.sdu.uims.trans.UFPoint;

public interface DrageDataI {
	void drageDataDraw(Graphics gc, ViewParameter mp, UFPoint p);
}
