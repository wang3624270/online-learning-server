package cn.edu.sdu.uims.graph.handler;

import java.util.ArrayList;
import java.util.List;

import cn.edu.sdu.uims.component.event.GraphInteractionEvent;
import cn.edu.sdu.uims.graph.GraphConstants;
import cn.edu.sdu.uims.graph.form.GraphViewForm;
import cn.edu.sdu.uims.graph.model.GraphModelI;
import cn.edu.sdu.uims.graph.model.SelectedData;
import cn.edu.sdu.uims.graph.model.drag.DrageDataI;
import cn.edu.sdu.uims.graph.model.drag.DrageDataRectChangePoint;
import cn.edu.sdu.uims.graph.model.drag.DrageDataSelectElements;
import cn.edu.sdu.uims.itms.cursor.CursorDrawControl;
import cn.edu.sdu.uims.itms.cursor.ICursorDataBase;
import cn.edu.sdu.uims.itms.event.ItmsEvent;
import cn.edu.sdu.uims.trans.UFPoint;

public class GraphInteractionHandler extends GraphHandler implements
		GraphInteractionHandlerI {

	public void processGraphInteractionEvent(Object o, String cmd) {
		System.out.println(o.toString() + cmd);
	}

	public void processCheCeckSatatusSelected(GraphInteractionEvent e) {
		// TODO Auto-generated method stub
		GraphViewForm form = (GraphViewForm) dataForm;
		GraphModelI gm2d = form.getCurrentGraphObject();
		if (gm2d == null)
			return;
		int index = e.getStatusItemNo();
		if (index >= 0) {
			gm2d.setLayerDisplayStatus(index, e.isStatusValue());
		}
	}

	public String getTransformState(ItmsEvent ie) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean selectDrageElement(ItmsEvent ie) {
		GraphViewForm form = (GraphViewForm) dataForm;
		GraphModelI gm2d = form.getCurrentGraphObject();
		if (gm2d == null)
			return false;
		UFPoint fp = ie.getPoint();
		if (fp == null)
			return false;
		List list = gm2d.getSelectElementByPoint(fp);
		if (list == null)
			return false;
		DrageDataI drageData = new DrageDataSelectElements(list, fp);
		CursorDrawControl cursorDrawControl = CursorDrawControl.getInstance();
		ICursorDataBase cursorData = cursorDrawControl.getCursorData();
		cursorData.setDrageData(drageData);
		return true;
	}

	public boolean selectDrageRectPart(ItmsEvent ie) {
		GraphViewForm form = (GraphViewForm) dataForm;
		GraphModelI gm2d = form.getCurrentGraphObject();
		if (gm2d == null)
			return false;
		UFPoint fp = ie.getPoint();
		if (fp == null)
			return false;
		SelectedData selectedData = gm2d.getSelectedDataByPoint(fp);
		if (selectedData == null)
			return false;
		DrageDataI drageData = null;
		if (selectedData.getSelectedType() == GraphConstants.GRAPH_SELECT_STATUS_INNER) {
			ArrayList list = new ArrayList();
			list.add(selectedData.getSelectedElement());
			drageData = new DrageDataSelectElements(list, fp);
			CursorDrawControl cursorDrawControl = CursorDrawControl.getInstance();
			ICursorDataBase cursorData = cursorDrawControl.getCursorData();
			cursorData.setDrageData(drageData);
		} else if (selectedData.getSelectedType() == GraphConstants.GRAPH_SELECT_STATUS_VECTOR_LEFTTOP
				|| selectedData.getSelectedType() == GraphConstants.GRAPH_SELECT_STATUS_VECTOR_RIGHTTOP
				|| selectedData.getSelectedType() == GraphConstants.GRAPH_SELECT_STATUS_VECTOR_RIGHTBOTTOM
				|| selectedData.getSelectedType() == GraphConstants.GRAPH_SELECT_STATUS_VECTOR_LEFTBOTTOM) {
			drageData = new DrageDataRectChangePoint(fp, selectedData
					.getSelectedType());
		}
		if (drageData != null) {
			CursorDrawControl cursorDrawControl = CursorDrawControl
					.getInstance();
			ICursorDataBase cursorData = cursorDrawControl.getCursorData();
			cursorData.setDrageData(drageData);
			return true;
		} else {
			return false;
		}
	}

}
