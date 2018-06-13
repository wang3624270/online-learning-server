package cn.edu.sdu.uims.itms.cursor;

import java.awt.Graphics;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import cn.edu.sdu.uims.base.UPointCursor;
import cn.edu.sdu.uims.graph.model.drag.DrageDataI;
import cn.edu.sdu.uims.graph.view.UCanvasI;
import cn.edu.sdu.uims.itms.ItmsConstants;
import cn.edu.sdu.uims.itms.status.IStatusBase;
import cn.edu.sdu.uims.itms.task.ITaskBase;
import cn.edu.sdu.uims.trans.UFPoint;

public class CursorDrawControl {

	private static CursorDrawControl instance = null;
	private UCanvasI canvas;
	private UPointCursor pointCursor;
	private ITaskBase currentTask;
	private ICursorDataBase cursorData = null;
	private int drawMode = ItmsConstants.CURSOR_DRAW_MODE_DEFAULT;
	private boolean isCursorOn = false;
	private ICursorBase drawCursor;
	private List svaeCursorList;

	public static CursorDrawControl getInstance() {
		if (instance == null)
			instance = new CursorDrawControl();
		return instance;
	}

	public CursorDrawControl() {
		svaeCursorList = new ArrayList();
	}

	public int getDrawMode() {
		return drawMode;
	}

	public void setDrawMode(int drawMode) {
		this.drawMode = drawMode;
	}

	public UCanvasI getCanvas() {
		return canvas;
	}

	public void setCanvas(UCanvasI canvas) {
		this.canvas = canvas;
	}

	public UPointCursor getPointCursor() {
		return pointCursor;
	}

	public void setPointCursor(UPointCursor pointCursor) {
		this.pointCursor = pointCursor;
	}

	public void start() {
		if (canvas == null)
			return;
		if (pointCursor != null)
			canvas.setCursor(pointCursor.cursor);
		isCursorOn = false;
	}

	public ITaskBase getCurrentTask() {
		return currentTask;
	}

	public void setCurrentTask(ITaskBase currentTask) {
		this.currentTask = currentTask;
	}

	public ICursorDataBase getCursorData() {
		return cursorData;
	}

	public void setCursorData(ICursorDataBase cursorData) {
		this.cursorData = cursorData;
	}

	public boolean isCursorOn() {
		return isCursorOn;
	}

	public void setCursorOn(boolean isCursorOn) {
		this.isCursorOn = isCursorOn;
	}

	public ICursorBase getDrawCursor() {
		return drawCursor;
	}

	public void setDrawCursor(ICursorBase drawCursor) {
		this.drawCursor = drawCursor;
	}

	public void drawCursor() {
		drawCursor(null);
	}

	public boolean needDrawCursor() {
		if (drawCursor == null)
			return false;
		else {
			return true;
		}
	}

	public void drawCursor(String methodName) {
		if (drawCursor == null || canvas == null || cursorData == null)
			return;
		if (methodName == null || methodName.equals("")) {
			drawCursor.draw(canvas, cursorData);
		} else {
			Method method;
			try {
				method = drawCursor.getClass().getMethod(methodName,
						Graphics.class, ICursorDataBase.class,IStatusBase.class);
				method.invoke(drawCursor, canvas, cursorData);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}

	public void startNewCursor(ICursorBase drawCursor,
			ICursorDataBase cursorData) {
		this.drawCursor = drawCursor;
		this.cursorData = cursorData;
	}

	public void clearCursorDataContent() {
		if (cursorData != null)
			cursorData.clearContent();
	}

	public void addCursorDataPoint(UFPoint up) {
		if (cursorData != null) {
			cursorData.addPoint(up);
		}
	}

	public void setCursorDataPoint(UFPoint p) {
		if (cursorData != null) {
			cursorData.setCurrentPoint(p);
		}
	}
	public DrageDataI getDrageData() {
		if(cursorData !=  null)
			return cursorData.getDrageData();
		else 
			return null;
	}
	public void setDrageData(DrageDataI drageData) {
		if(cursorData != null)
			cursorData.setDrageData(drageData);
	}

}
