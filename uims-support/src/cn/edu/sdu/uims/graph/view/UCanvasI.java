package cn.edu.sdu.uims.graph.view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;

import cn.edu.sdu.uims.hcims.UserTask;


public interface UCanvasI {
	Graphics getGraphics();
	void setCursor(Cursor cursor);
	void setUserTask(UserTask userTask);
	ViewParameter getViewParameter();
	Dimension getGraphViewSize();
}
