package cn.edu.sdu.uims.itms.cursor;

import cn.edu.sdu.common.form.UForm;
import cn.edu.sdu.uims.graph.model.drag.DrageDataI;
import cn.edu.sdu.uims.trans.UFPoint;

public class ICursorDataBase extends UForm {
	public UFPoint currentPoint = new UFPoint(0,0);
	public UFPoint savePoint = new UFPoint(-1,-1);
	public String type;
	public ICursorBase cursor;

	public UFPoint getCurrentPoint() {
		return currentPoint;
	}

	public void setCurrentPoint(UFPoint p) {
		currentPoint.x = p.x;
		currentPoint.y = p.y;
	}

	public UFPoint[] getPoints() {
		return null;

	}
	public void clearContent(){
		
	}
	public void addPoint(UFPoint p){
		
	}

	public void copy(ICursorDataBase dataPoints) {
		currentPoint = dataPoints.getCurrentPoint();
		type = dataPoints.type;
		cursor = dataPoints.cursor;
	}

	public UFPoint getSavePoint() {
		return savePoint;
	}

	public void setSavePoint(UFPoint savePoint) {
		this.savePoint = savePoint;
	}
	public void saveCurrentPoint(){
		savePoint.x = currentPoint.x;
		savePoint.y = currentPoint.y;
	}
	public void clearSavePoint(){
		savePoint.x = 0-1;
		savePoint.y = 0-1;
	}
	public DrageDataI getDrageData() {
		return null;
	}
	public void setDrageData(DrageDataI drageData) {
	}
}
