package cn.edu.sdu.uims.graph.model;

import java.awt.Graphics;
import java.util.List;

import cn.edu.sdu.uims.graph.view.ControlParameter;
import cn.edu.sdu.uims.graph.view.ViewParameter;
import cn.edu.sdu.uims.trans.UFPoint;
import cn.edu.sdu.uims.trans.UPoint;

public interface Graph2DI {
	void draw(Graphics dc, ViewParameter p, ControlParameter c,Object data, UPoint shiftPoint);
	List getElementList();
	void addElement(GElement e);
	List getSelectElementsByPoint(UFPoint fp);
	Object getInnerObjectByPoint(UFPoint fp);
	void clearAllElement();
}
