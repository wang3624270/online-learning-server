package cn.edu.sdu.uims.itms.cursor;

import cn.edu.sdu.uims.graph.model.drag.DrageDataI;
import cn.edu.sdu.uims.trans.UFPoint;
import cn.edu.sdu.uims.trans.UMatrix;

public class ICursorDataPoints extends ICursorDataBase {
	private int size, length;
	private UFPoint datas[];
	private DrageDataI drageData;

	public ICursorDataPoints() {
		size = 100;
		length = 0;
		datas = new UFPoint[size];
	}

	public long getLength() {
		return length;
	}

	public void addPoint(UFPoint p) {
		addPoint(length, p);
	}

	public void addPoint(int index, UFPoint p) {
		if (length + 1 > size) {
			enlarg();
		}
		for (int i = length - 1; i > index; i--) {
			datas[i] = datas[i - 1];
		}
		datas[index] = p;
		length++;
	}

	public void enlarg() {
		UFPoint[] data1 = new UFPoint[size + 10];
		size = size + 10;
		for (int i = 0; i < length; i++)
			data1[i] = datas[i];
		datas = data1;
	}

	public UFPoint getPoint(int i) {
		return datas[i];
	}

	public UFPoint [] getPoints() {
		if (length == 0) {
			return null;
		} else {
			UFPoint[] aPoints = new UFPoint[length];
			for (int i = 0; i < length; i++) {
				aPoints[i] = datas[i];
			}
			return aPoints;
		}
	}
	public void removiePoint(int index) {
		for (int i = index; i < length - 1; i++) {
			datas[i] = datas[i + 1];
		}
		length--;
	}

	public void clearContent() {
		size = 100;
		length = 0;
		datas = new UFPoint[size];
		this.clearSavePoint();
		drageData = null;
	}

	public void copy(ICursorDataBase dataPoints) {
		super.copy(dataPoints);

		UFPoint[] outPoints = null;
		outPoints = dataPoints.getPoints();
		if (outPoints != null && outPoints.length > size) {
			// 扩大点数
			UFPoint data1[] = new UFPoint[outPoints.length];
			size = outPoints.length;
			datas = data1;
		}
		length = outPoints.length;
		for (int i = 0; i < length; i++) {
			datas[i] = outPoints[i];
		}
	}

	public DrageDataI getDrageData() {
		return drageData;
	}

	public void setDrageData(DrageDataI drageData) {
		this.drageData = drageData;
	}
	public UFPoint[] getLogicPoints(UMatrix mv){
		if(length <= 0)
			return null;
		UFPoint fps[] = new UFPoint[length];
		for(int i = 0; i < length;i++)
			fps[i] = mv.viewToLogic(datas[i]);
		return  fps;
	}
}
