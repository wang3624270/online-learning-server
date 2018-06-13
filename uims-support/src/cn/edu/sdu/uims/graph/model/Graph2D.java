package cn.edu.sdu.uims.graph.model;

import java.awt.Graphics;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.dom4j.Element;

import cn.edu.sdu.uims.graph.GraphConstants;
import cn.edu.sdu.uims.graph.form.Graph2DDataForm;
import cn.edu.sdu.uims.graph.model.data.Graph2DDataProcessorI;
import cn.edu.sdu.uims.graph.model.drag.DrageDataI;
import cn.edu.sdu.uims.graph.view.ControlParameter;
import cn.edu.sdu.uims.graph.view.ViewParameter;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.trans.UFPoint;
import cn.edu.sdu.uims.trans.UMatrix;
import cn.edu.sdu.uims.trans.UPoint;

public class Graph2D extends GElement implements DrageDataI, Graph2DI {
	public String elementAddedFormClassName = null;
	public List<GElement> elementList;
	public UMatrix currentMatrix;
	public boolean isModify;
	public GraphModelI graphModelI = null;

	private static final long serialVersionUID = 1423322247514835371L;
	
	public Graph2D() {
		name = "000";
		elementList = new ArrayList<GElement>();
		currentMatrix = new UMatrix();
	}

	public Graph2D(String name) {
		this();
		this.name = name;
	}

	public Graph2D(GraphModelI graphModelI) {
		this();
		this.graphModelI = graphModelI;
	}

	public Graph2D(GraphModelI graphModelI, String name) {
		this();
		this.graphModelI = graphModelI;
		this.name = name;
	}

	public void drawDo(Graphics dc, ViewParameter p, ControlParameter c,
			Object data, UPoint shiftPoint) {
		int size, i;
		GElement g;
		Graph2DDataForm dataForm = null;
		Object[] elementDatas = null;
		size = (int) elementList.size();
		if (data != null && (data instanceof Graph2DDataForm)) {
			dataForm = (Graph2DDataForm) data;
			elementDatas = dataForm.getElementDatas();
		}
		for (i = 0; i < size; i++) {
			g = (GElement) elementList.get(i);
			if (elementDatas != null && i < elementDatas.length) {
				g.draw(dc, p, c, elementDatas[i], shiftPoint);
			} else {
				g.draw(dc, p, c, data, shiftPoint);
			}
		}
	}

	public int getElementCount() {
		return elementList.size();
	}

	public void addElement(GElement e) {
		elementList.add(e);
		isModify = true;
	}

	public GElement getElement(int index) {

		return (GElement) elementList.get(index);
	}

	public void resetBox() {
		int size, i;
		GElement g;
		size = (int) elementList.size();
		for (i = 0; i < size; i++) {
			g = (GElement) elementList.get(i);
			g.resetBox();
			if (i == 0) {
				box = g.getBox();
			} else {
				box.Union(g.getBox());
			}
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UMatrix getCurrentMatrix() {
		return currentMatrix;
	}

	public void setCurrentMatrix(UMatrix currentMatrix) {
		this.currentMatrix = currentMatrix;
	}

	public boolean isSelected(UFPoint p) {

		return false;
	}

	public List getElementList() {
		return elementList;
	}

	public void setElementList(List elementList) {
		this.elementList = elementList;
	}

	public void clearAllElement() {
		elementList.clear();
	}

	public void reSetXmlContent() {
	}

	public GElement newGraphElement(String type, String className) {
		GElement gElement = null;
		if (className == null) {
			className = UFactory.getModelSession().getDefaultClassByType(type);
		}
		if (className == null)
			return null;
		gElement = null;
		try {
			gElement = (GElement) Class.forName(className).newInstance();
			gElement.setParent(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gElement;
	}

	public void makeAddedData() {
		int i;
		GElement gElement;
		for (i = 0; i < elementList.size(); i++) {
			gElement = (GElement) elementList.get(i);
			gElement.makeAddedData(this);
		}
	}

	public List getSelectElementsByPoint(UFPoint fp) {
		List list = new ArrayList();
		GElement e;
		for (int i = 0; i < elementList.size(); i++) {
			e = (GElement) elementList.get(i);
			if (e.testPointOnElement(fp) != GraphConstants.GRAPH_SELECT_STATUS_NO) {
				list.add(e);
			}
		}
		return list;
	}

	public void setSelectElementsStatusByPoint(GElement ge, UFPoint fp) {
		int i = ge.testPointOnElement(fp);
		ge.setSelectStatus(i);
	}

	public Object getInnerObjectByPoint(UFPoint fp) {
		GElement e;
		Object ro = null;
		for (int i = 0; i < elementList.size(); i++) {
			e = (GElement) elementList.get(i);
			ro = e.getInnerObjectByPoint(fp);
			if (ro != null)
				return ro;
		}
		return ro;
	}

	public Graph2DI getGraph2DByName(String name) {
		if (graphModelI == null)
			return null;
		else
			return graphModelI.getGraph2DByName(name);
	}

	public String getElementAddedFormClassName() {
		return elementAddedFormClassName;
	}

	public void setElementAddedFormClassName(String elementAddedFormClassName) {
		this.elementAddedFormClassName = elementAddedFormClassName;
	}

	/**
	 * 添加element的className
	 */

	/**
	 * 添加新的element需要写入到xml中
	 * 
	 * @param type
	 * @param map
	 * @return
	 */

	public GElement insertNewElement(String type, HashMap map) {
		GElement ge = null;
		Graph2DDataProcessorI p = (Graph2DDataProcessorI) dataProcessor;
		if (p != null)
			ge = p.insertNewElement(type, map);

		// elementList.add(ge);
		return ge;
	}

	public void deleteElement(int index) {
		if (index < 0 || index >= elementList.size())
			return;
		GElement gElement = (GElement) elementList.get(index);
		if (dataProcessor != null) {
			Graph2DDataProcessorI p = (Graph2DDataProcessorI) dataProcessor;
			p.deleteElement(gElement);
		}
		elementList.remove(index);
		isModify = true;
	}

	public void deleteElement(GElement ge) {
		if (ge == null)
			return;
		if (dataProcessor != null) {
			Graph2DDataProcessorI p = (Graph2DDataProcessorI) dataProcessor;
			p.deleteElement(ge);
		}
		elementList.remove(ge);
		isModify = true;
	}

	public void modifyElement(int index, HashMap map) {
		if (index < 0 || index >= elementList.size())
			return;
		GElement ge = (GElement) elementList.get(index);
		ge.setParent(this);
		if (dataProcessor != null) {
			Graph2DDataProcessorI p = (Graph2DDataProcessorI) dataProcessor;
			p.modifyElement(ge, map);
		}
		elementList.set(index, ge);
	}

	public void modifyElement(GElement ge, HashMap map) {
		int index = elementList.indexOf(ge);
		modifyElement(index, map);
	}

	public GraphModelI getGraphModelI() {
		return graphModelI;
	}

	public void setGraphModelI(GraphModel2D graphModelI) {
		this.graphModelI = graphModelI;
	}

	public void drageDataDraw(Graphics gc, ViewParameter mp, UFPoint p) {
		// TODO Auto-generated method stub

	}

	public SelectedData getSelectedDataByPoint(UFPoint fp) {
		GElement e;
		SelectedData selectedData = null;
		int type;
		for (int i = 0; i < elementList.size(); i++) {
			e = (GElement) elementList.get(i);
			type = e.testPointOnElement(fp);
			if (type != GraphConstants.GRAPH_SELECT_STATUS_NO) {
				if (selectedData == null)
					selectedData = new SelectedData();
				if (selectedData.getSelectedElement() == null
						|| e.box.w < selectedData.getSelectedElement().box.w) {
					selectedData.setSelectedElement(e);
					selectedData.setSelectedType(type);
				}
			}
		}
		return selectedData;
	}

	public void read(DataInputStream in) throws IOException {
		super.read(in);
		elementAddedFormClassName = readString(in);
		int len, i;
		len = in.readInt();
		if (len == 0) {
			elementList = null;
		} else {
			elementList = new ArrayList<GElement>();
			for (i = 0; i < len; i++) {
				elementList.add(readGElement(in));
			}
		}
	}

	public void write(DataOutputStream out) throws IOException {
		super.write(out);
		writeString(out, elementAddedFormClassName);
		if (elementList == null)
			out.writeInt(0);
		else {
			out.writeInt(elementList.size());
			for (int i = 0; i < elementList.size(); i++) {
				writeGElement(out, elementList.get(i));
			}
		}
	}

	public float[] getCellRect(float w[], float h[], int c, int r, int cn,
			int rn, float sx, float sy) {
		float ret[] = new float[4];
		int i;
		for (i = 0; i < c; i++)
			ret[0] += w[i] * sx;
		for (i = c; i < c + cn; i++)
			ret[2] += w[i] * sx;
		for (i = 0; i < r; i++)
			ret[1] += h[i] * sy;
		for (i = r; i < r + rn; i++)
			ret[3] += h[i] * sy;
		return ret;
	}

	public GElement getGElementByName(String name) {
		GElement ge;
		for(int i = 0; i <elementList.size();i++) {
			ge = elementList.get(i);
			if(ge == null || ge.name == null)
				continue;
			if( ge.name.endsWith(name))
				return ge; 
		}
		return null;
	}
	public void exportElementToDoc(Element ge) {
		Element ee;
		ge.addAttribute("name", name);
		if(elementList !=null) {
			for(int i = 0; i  < elementList.size();i++){
				 ee = ge.addElement("element");
				elementList.get(i).exportElementToDoc(ee);
			}
		}	
	}
	public void clearModifyMark(){
		this.isModify = false;
		GElement ge;
		for(int i = 0; i <elementList.size();i++) {
			ge = elementList.get(i);
			if(ge != null)
				ge.isModify = false;
		}		
	}
	
}