package cn.edu.sdu.uims.graph.model;

import java.awt.Color;
import java.awt.Graphics;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Element;

import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.reportdog.UColor;
import cn.edu.sdu.uims.graph.GraphConstants;
import cn.edu.sdu.uims.graph.form.AddedAttributeForm;
import cn.edu.sdu.uims.graph.model.data.ElementDataProcessorI;
import cn.edu.sdu.uims.graph.view.ControlParameter;
import cn.edu.sdu.uims.graph.view.ViewParameter;
import cn.edu.sdu.uims.graph.view.WVTrans;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.trans.UFPoint;
import cn.edu.sdu.uims.trans.UFRect;
import cn.edu.sdu.uims.trans.UPoint;
import cn.edu.sdu.uims.trans.URect;

public class GElement implements Serializable, GElementI {
	public String id;
	public String name = "";
	public UFRect box = new UFRect();
	public String colorName;
	public String strokeName;
	public int selectStatus;
	public String attributeName;
	public int index;
	public Object parent;
	public ElementDataProcessorI dataProcessor = null;
	public List listNote = null;
	public int maxNoteNum = 0;
	public Object dataObject;
	public boolean isModify;

	public AddedAttributeForm addedAttributeForm;

	public AddedAttributeForm getAddedAttributeForm() {
		return addedAttributeForm;
	}

	public void setAddedAttributeForm(AddedAttributeForm addedAttributeForm) {
		this.addedAttributeForm = addedAttributeForm;
	}

	public String getId() {
		return id;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UFRect getBox() {
		return box;
	}

	public void setBox(UFRect box1) {
		box = box1;
	}

	public void draw(Graphics dc, ViewParameter p, ControlParameter c,
			Object par, UPoint shiftPoint) {
		Color oldColor = dc.getColor();
		UColor uColor;
		boolean bColorChangeed = false;
		if (colorName != null) {
			uColor = UFactory.getModelSession().getColorByName(colorName);
			dc.setColor(uColor.color);
			bColorChangeed = true;
		}
		if (selectStatus != GraphConstants.GRAPH_SELECT_STATUS_NO) {
			uColor = UFactory.getModelSession().getColorByName(
					GraphConstants.GRAPH_COLOR_SELECTION);
			dc.setColor(uColor.color);
			bColorChangeed = true;
		}
		// 绘制note
		// if(c.isEdit) {
		// uColor = UFactory.getModelSession().getColorByName(
		// GraphConstants.GRAPH_COLOR_GRAY);
		// dc.setColor(uColor.color);
		// bColorChangeed = true;
		// URect ur;
		// ur = p.m.logicToView(box);
		// // dc.drawRect(ur.x+shiftPoint.x, ur.y+shiftPoint.y, ur.w, ur.h);
		// }
		drawDo(dc, p, c, par, shiftPoint);
		if (bColorChangeed)
			dc.setColor(oldColor);
	}

	public void drawDo(Graphics dc, ViewParameter p, ControlParameter c,
			Object par, UPoint shiftPoint) {
	}

	public boolean inRect(UFPoint p, UFRect r) {
		if (p.x >= r.x && p.x < r.x + r.w && p.y >= r.y && p.y <= r.y + r.h)
			return true;
		else
			return false;
	}

	public void SetPageSize(int pagesizes) {
	}

	public void resetBox() {

	}

	public void setXY(int x, int y) {

	}

	public int getSelectStatus() {
		return selectStatus;
	}

	public void setSelectStatus(int selectStatus) {
		this.selectStatus = selectStatus;
	}

	public boolean isSelected(UFPoint p) {
		return false;
	}

	public boolean isSelected(UFRect p) {
		return false;
	}

	public String getColorName() {
		return colorName;
	}

	public void setColorName(String colorName) {
		this.colorName = colorName;
	}

	public String getStrokeName() {
		return strokeName;
	}

	public void setStrokeName(String strokeName) {
		this.strokeName = strokeName;
	}

	public boolean isBordered() {
		return false;
	}

	public void setBordered(boolean bordered) {
	}

	public Object getAttributeObject(Object data) {
		if (data == null)
			return null;
		if (!(data instanceof UFormI))
			return data;
		Object obj = null;
		Method method = null;
		String methodName = null;
		if (attributeName != null) {
			UFormI fi = (UFormI) data;
			if (index < 0) {
				obj = fi.getAttributeObject(attributeName);
			} else {
				obj = fi.getAttributeObject(attributeName, index);
			}
		}
		return obj;
	}

	public void makeAddedData(Graph2D g2d) {

	}

	public int testPointOnElement(UFPoint fp) {
		return GraphConstants.GRAPH_SELECT_STATUS_NO;
	}

	public Object getParent() {
		return parent;
	}

	public void setParent(Object parent) {
		this.parent = parent;
	}

	public ElementDataProcessorI getDataProcessor() {
		return dataProcessor;
	}

	public void setDataProcessor(ElementDataProcessorI dataProcessor) {
		this.dataProcessor = dataProcessor;
	}

	public Object getInnerObjectByPoint(UFPoint fp) {
		return null;
	}

	public List getListNote() {
		return listNote;
	}

	public void setListNode(List listNote) {
		this.listNote = listNote;
	}

	public Integer getMaxNoteNum() {
		return maxNoteNum;
	}

	public void setMaxNoteNum(Integer maxNoteNum) {
		this.maxNoteNum = maxNoteNum;
	}

	public void init() {

	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public UFPoint getCenterPoint() {
		return null;
	}

	public UFPoint getCrossingPoint(UFPoint point1, UFPoint point2) {
		return null;
	}

	public ArrayList getDragPoints(int selectStatus) {
		return null;
	}

	public void read(DataInputStream in) throws IOException {
		int len, i;
		id = readString(in);
		name = readString(in);
		readUFRect(in, box);
		colorName = readString(in);
		strokeName = readString(in);
		selectStatus = in.readInt();
		attributeName = readString(in);
		index = in.readInt();
		len = in.readInt();
		maxNoteNum = in.readInt();
	}

	public void write(DataOutputStream out) throws IOException {
		writeString(out, id);
		writeString(out, name);
		writeUFRect(out, box);
		writeString(out, colorName);
		writeString(out, strokeName);
		out.writeInt(selectStatus);
		writeString(out, attributeName);
		out.writeInt(index);
		out.writeInt(maxNoteNum);
	}

	public String readString(DataInputStream in) throws IOException {
		String s;
		int len = in.readInt();
		if (len == 0)
			return null;
		byte b[] = new byte[len];
		in.read(b);
		s = new String(b);
		return s;
	}

	public void writeString(DataOutputStream out, String s) throws IOException {
		if (s == null)
			out.writeInt(0);
		else {
			byte[] b = s.getBytes();
			out.writeInt(b.length);
			out.write(b);
		}
	}

	public void writeURect(DataOutputStream out, URect rect) throws IOException {
		out.writeInt(rect.x);
		out.writeInt(rect.y);
		out.writeInt(rect.w);
		out.writeInt(rect.h);
	}

	public void readURect(DataInputStream in, URect rect) throws IOException {
		rect.x = in.readInt();
		rect.y = in.readInt();
		rect.w = in.readInt();
		rect.h = in.readInt();
	}

	public void writeUFRect(DataOutputStream out, UFRect rect)
			throws IOException {
		out.writeDouble(rect.x);
		out.writeDouble(rect.y);
		out.writeDouble(rect.w);
		out.writeDouble(rect.h);
	}

	public void readUFRect(DataInputStream in, UFRect rect) throws IOException {
		rect.x = in.readDouble();
		rect.y = in.readDouble();
		rect.w = in.readDouble();
		rect.h = in.readDouble();
	}

	public void writeUFPoint(DataOutputStream out, UFPoint fp)
			throws IOException {
		out.writeDouble(fp.x);
		out.writeDouble(fp.y);
	}

	public void readUFPoint(DataInputStream in, UFPoint fp) throws IOException {
		fp.x = in.readDouble();
		fp.y = in.readDouble();
	}

	public void writeWVTrans(DataOutputStream out, WVTrans wv)
			throws IOException {

		out.writeDouble(wv.wx0);
		out.writeDouble(wv.wx1);
		out.writeDouble(wv.wy0);
		out.writeDouble(wv.wy1);
		out.writeDouble(wv.vx0);
		out.writeDouble(wv.vx1);
		out.writeDouble(wv.vy0);
		out.writeDouble(wv.vy1);

	}

	public void readWVTrans(DataInputStream in, WVTrans wv) throws IOException {
		wv.wx0 = in.readDouble();
		wv.wx1 = in.readDouble();
		wv.wy0 = in.readDouble();
		wv.wy1 = in.readDouble();
		wv.vx0 = in.readDouble();
		wv.vx1 = in.readDouble();
		wv.vy0 = in.readDouble();
		wv.vy1 = in.readDouble();
	}

	// 张韩add
	public void writeDouble(DataOutputStream out, double value)
			throws IOException {
		out.writeDouble(value);
	}

	// 张韩add
	public void readDouble(DataInputStream in, double value) throws IOException {
		value = in.readDouble();
	}

	/**
	 * 判断是否包含rect
	 * 
	 * @return 若是包含返回true,否则返回false
	 * */
	public boolean contains(UFRect rect) {
		double min_x = this.box.x;
		double max_x = this.box.x + this.box.w;
		double min_y = this.box.y;
		double max_y = this.box.y + this.box.h;
		double x0 = rect.x;
		double y0 = rect.y;
		double x1 = rect.x + rect.w;
		double y1 = rect.y + rect.h;
		if (y1 < min_y || y0 > max_y || x0 > max_x || x1 < min_x)
			return false;
		return true;
	}

	public boolean isPointInPolygon(UFPoint p, GElementPolygon2 polygon) {
		int nCross = 0;
		List nodeLists = polygon.pList;
		int nCounts = nodeLists.size();
		for (int i = 0; i < nCounts; i++) {

			UFPoint p1 = (UFPoint) nodeLists.get(i);
			UFPoint p2 = (UFPoint) nodeLists.get((i + 1) % nCounts);
			// 求解 y=p.y 与 p1,p2 的交点
			if (p1.y == p2.y) // p1,p2 与 y=p0.y平行
				continue;
			if (p.y < Math.min(p1.y, p2.y)) // 交点在p1p2延长线上
				continue;
			if (p.y >= Math.max(p1.y, p2.y)) // 交点在p1p2延长线上
				continue;
			// 求交点的 X 坐标
			double x = (double) (p.y - p1.y) * (double) (p2.x - p1.x)
					/ (double) (p2.y - p1.y) + p1.x;
			if (x > p.x)
				nCross++; // 只统计单边交点
		}
		// 单边交点为偶数，点在多边形之外
		return (nCross % 2 == 1);
	}

	public boolean containsInPolygon(GElementPolygon2 polygon) {
		UFPoint point_0 = new UFPoint(this.box.x, this.box.y);
		UFPoint point_1 = new UFPoint(this.box.x + this.box.w, this.box.y);
		UFPoint point_2 = new UFPoint(this.box.x + this.box.w, this.box.y
				+ this.box.h);
		UFPoint point_3 = new UFPoint(this.box.x, this.box.y + this.box.h);
		if (isPointInPolygon(point_0, polygon)
				|| isPointInPolygon(point_1, polygon)
				|| isPointInPolygon(point_2, polygon)
				|| isPointInPolygon(point_3, polygon))
			return true;
		return false;
	}

	/**
	 * 判断是否包含在rect中
	 * 
	 * @return 若是返回true,否则返回false
	 * */
	public boolean containsInRect(UFRect rect) {
		double min_x = rect.x;
		double max_x = rect.x + rect.w;
		double min_y = rect.y;
		double max_y = rect.y + rect.h;
		double x0 = this.box.x;
		double y0 = this.box.y;
		double x1 = this.box.x + this.box.w;
		double y1 = this.box.y + this.box.h;
		if (y1 < min_y || y0 > max_y || x0 > max_x || x1 < min_x)
			return false;
		return true;
	}

	public GElement readGElement(DataInputStream in) throws IOException {
		GElement element = null;
		String objectName = readString(in);
		if (!objectName.equals("null")) {
			try {
				element = (GElement) Class.forName(objectName).newInstance();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			element.read(in);

		}
		return element;
	}

	public void writeGElement(DataOutputStream out, GElement element)
			throws IOException {
		if (element == null)
			writeString(out, "null");
		else {
			String objectName = element.getClass().getName();
			writeString(out, objectName);
			element.write(out);
		}
	}

	public void exportElementToDoc(Element ge) {
		if (name != null && !name.equals(""))
			ge.addAttribute("name", name);
		if (attributeName != null && !attributeName.equals(""))
			ge.addAttribute("attributeName", attributeName);
		if (colorName != null && !colorName.equals(""))
			ge.addAttribute("color", colorName);
		if (strokeName != null && !strokeName.equals(""))
			ge.addAttribute("stroke", strokeName);

	}

	@Override
	public Object getDataObject() {
		// TODO Auto-generated method stub
		return dataObject;
	}

	@Override
	public void setDataObject(Object dataObject) {
		// TODO Auto-generated method stub
		this.dataObject = dataObject;
	}

	@Override
	public Object getJSonObject(ViewParameter viewParameter) {
		// TODO Auto-generated method stub
		return null;
	}
}
