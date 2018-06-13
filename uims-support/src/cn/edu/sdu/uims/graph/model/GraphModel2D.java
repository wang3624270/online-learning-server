package cn.edu.sdu.uims.graph.model;

import java.awt.Graphics;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.dom4j.Element;

import cn.edu.sdu.common.form.ListOptionInfo;
import cn.edu.sdu.common.form.UFormI;
import cn.edu.sdu.common.reportdog.UPaperTemplate;
import cn.edu.sdu.common.reportdog.UTemplate;
import cn.edu.sdu.uims.base.CheckObject;
import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.def.UCheckBoxGroupTemplate;
import cn.edu.sdu.uims.flex.FHashMap;
import cn.edu.sdu.uims.flex.FNameObjectPar;
import cn.edu.sdu.uims.graph.form.Graph2DDataForm;
import cn.edu.sdu.uims.graph.form.GraphDataForm;
import cn.edu.sdu.uims.graph.model.data.GraphModelDataProcessorI;
import cn.edu.sdu.uims.graph.model.drag.DrageDataI;
import cn.edu.sdu.uims.graph.view.GraphViewParasI;
import cn.edu.sdu.uims.graph.view.WVTrans;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.trans.UFPoint;

public class GraphModel2D extends GElement implements GraphModelI {
	private String num;
	private GraphDataForm graphDataForm = null;
	private UPaperTemplate paperTemplate = null;
	private double graphWidth = 1, graphHeight = 1, graphDpi = 1;
	private List<GraphLayer> layerList = new ArrayList<GraphLayer>();
	private int currentLayerNo = 0;
	private double scaleXY = 1.0;
	private WVTrans wv = new WVTrans();
	private HashMap<String, Graph2D> graph2DMap = new HashMap<String, Graph2D>();
	private List selectedElementList = null;
	private GraphModelDataProcessorI graphDataProcessor = null;
	private Date timestamp;

	public GraphModel2D() {
	}

	public void makeWVTrans() {
		wv.setWindows(0, 0, graphWidth, graphHeight);
		wv.setViewport(0, 0, this.getImageWidth(), this.getImageHeight());
		wv.makeWVMatrix();
	}

	public WVTrans getWindowViewport() {
		return wv;
	}

	public double getImageWidth() {
		if (paperTemplate != null) {
			if (paperTemplate.orientation != 1)
				return paperTemplate.width * scaleXY;
			else
				return paperTemplate.height * scaleXY;
		} else {
			return this.graphWidth * scaleXY;
		}
	}

	public double getImageHeight() {
		if (paperTemplate != null) {
			if (paperTemplate.orientation != 1)
				return paperTemplate.height * scaleXY;
			else
				return paperTemplate.width * scaleXY;
		} else
			return this.graphHeight * scaleXY;
	}

	public double getImageDpi() {
		if (paperTemplate != null) {
			return paperTemplate.dpi;
		} else
			return this.graphDpi;
	}

	public int getLayerSize() {
		return layerList.size();
	}

	public UPaperTemplate getPaperTemplate() {
		return paperTemplate;
	}

	public void setPaperTemplate(UPaperTemplate paperTemplate) {
		this.paperTemplate = paperTemplate;
	}

	public List getLayerList() {
		return layerList;
	}

	public void setLayerList(List layerList) {
		this.layerList = layerList;
	}

	public GraphLayer getGraphLayer(int pos) {
		if (layerList == null || pos < 0 || pos >= layerList.size())
			return null;
		else {
			return (GraphLayer) layerList.get(pos);
		}
	}

	public double getGraphWidth() {
		return graphWidth;
	}

	public void setGraphWidth(double graphWidth) {
		this.graphWidth = graphWidth;
	}

	public double getGraphHeight() {
		return graphHeight;
	}

	public void setGraphHeight(double graphHeight) {
		this.graphHeight = graphHeight;
	}

	public double getGraphDpi() {
		return graphDpi;
	}

	public void setGraphDpi(double graphDpi) {
		this.graphDpi = graphDpi;
	}

	public void draw(Graphics dc, GraphViewParasI para) {
		int size, i;
		GraphLayer layer;
		Object control;
		GraphDataForm graphDataForm = (GraphDataForm) para.getData();
		UFormI formI = null;
		size = (int) layerList.size();
		for (i = 0; i < size; i++) {
			layer = (GraphLayer) layerList.get(i);
			if (graphDataForm != null
					&& graphDataForm.getLayerList().size() > i) {
				formI = (UFormI) graphDataForm.getLayerList().get(i);
				if (graphDataForm.getControlList() != null
						&& i < graphDataForm.getControlList().size()) {
					control = graphDataForm.getControlList().get(i);
					para.getControlParameter().shiftMap = (HashMap) control;
				}

			} else
				formI = null;
			if (layer.isVisible() && getGraph2D(layer) != null) {
				getGraph2D(layer)
						.draw(dc,
								layer.changeViewParameter(para
										.getViewParameter()),
								para.getControlParameter(), formI,
								para.getShiftPoint());
			}
		}
	}

	public void drawLayer(Graphics dc, GraphViewParasI para, int index) {
		int size;
		GraphLayer layer;
		size = (int) layerList.size();
		if (index < 0 || index >= size)
			return;
		layer = (GraphLayer) layerList.get(index);
		Object formI = null;
		Object control;
		para.getControlParameter().shiftMap = null;
		if (para.getData() != null && para.getData() instanceof GraphDataForm) {
			GraphDataForm graphDataForm = (GraphDataForm) para.getData();
			if (graphDataForm.getLayerList().size() > index) {
				formI = (UFormI) graphDataForm.getLayerList().get(index);
				if (graphDataForm.getControlList() != null
						&& graphDataForm.getControlList().size() > index) {
					control = graphDataForm.getControlList().get(index);
					para.getControlParameter().shiftMap = (HashMap) control;
				}
			} else {
				formI = null;
			}
		} else {
			formI = para.getData();
		}
		if (layer.isVisible() && getGraph2D(layer) != null) {
			getGraph2D(layer).draw(dc,
					layer.changeViewParameter(para.getViewParameter()),
					para.getControlParameter(), formI, para.getShiftPoint());
		}
	}

	public Graph2D getGraph2D(GraphLayer layer) {
		return getGraph2DByName(layer.getGraph2DName());
	}

	public Graph2D getGraph2DByName(String name) {
		return graph2DMap.get(name);
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public void reSetXmlContent() {

	}

	public GraphDataForm getGraphDataForm() {
		return graphDataForm;
	}

	public void setGraphDataForm(GraphDataForm graphDataForm) {
		this.graphDataForm = graphDataForm;
	}

	public void makeGraphDataForm() {
		// if (graphDataForm == null) {
		graphDataForm = new GraphDataForm();
		List layerDataList;
		layerDataList = new ArrayList();
		for (int i = 0; i < layerList.size(); i++) {
			layerDataList.add(null);
		}
		graphDataForm.setLayerList(layerDataList);
		layerDataList = new ArrayList();
		for (int i = 0; i < layerList.size(); i++) {
			layerDataList.add(null);
		}
		graphDataForm.setControlList(layerDataList);
		// }
	}

	public void setLayerDataForm(int index, UFormI form) {
		if (graphDataForm == null)
			return;
		List layerDataList = graphDataForm.getLayerList();
		if (layerDataList != null && layerDataList.size() > index) {
			layerDataList.set(index, form);
		}
	}

	public void setLayerControlData(int index, Object form) {
		if (graphDataForm == null)
			return;
		List layerDataList = graphDataForm.getControlList();
		if (layerDataList != null && layerDataList.size() > index) {
			layerDataList.set(index, form);
		}
	}

	public Graph2D getGraph2DObject(int index) {
		if (layerList == null || layerList.size() <= index)
			return null;
		GraphLayer layer = (GraphLayer) layerList.get(index);
		if (layer == null)
			return null;

		return (Graph2D) getGraph2D(layer);

	}

	public List getLayerElementList(int index) {
		List elementList = null;
		if (layerList == null || layerList.size() <= index)
			return null;
		GraphLayer layer = (GraphLayer) layerList.get(index);
		if (layer == null)
			return null;
		Graph2D g2d = getGraph2D(layer);
		if (g2d != null) {
			elementList = g2d.getElementList();
		}
		return elementList;

	}

	public Graph2DDataForm getLayerDataForm(int index) {
		if (graphDataForm == null)
			return null;
		List list = graphDataForm.getLayerList();
		if (list == null)
			return null;
		return (Graph2DDataForm) list.get(index);
	}

	public UCheckBoxGroupTemplate getLayersVisibleStatus() {
		UCheckBoxGroupTemplate template = null;
		if (layerList == null)
			return null;
		GraphLayer layer;
		template = new UCheckBoxGroupTemplate();
		template.row = layerList.size();
		template.column = 1;
		ListOptionInfo info;
		template.addedDatas = new ArrayList();
		template.checks = new boolean[layerList.size()];
		for (int i = 0; i < layerList.size(); i++) {
			layer = (GraphLayer) layerList.get(i);
			template.checks[i] = layer.isVisible();
			info = new ListOptionInfo(i + 1 + "", layer.getName());
			template.addedDatas.add(info);
		}
		return template;
	}

	public List getLayersVisibleStatusList() {
		if (layerList == null)
			return null;
		GraphLayer layer;
		ListOptionInfo info;
		List list = new ArrayList();
		for (int i = 0; i < layerList.size(); i++) {
			layer = (GraphLayer) layerList.get(i);
			info = new ListOptionInfo(i + 1 + "", layer.getName());
			list.add(new CheckObject(layer.isVisible(), info));
		}
		return list;
	}

	public List getLayersInfoList() {
		List list = null;
		if (layerList == null)
			return null;
		list = new ArrayList();
		GraphLayer layer;
		ListOptionInfo info;
		for (int i = 0; i < layerList.size(); i++) {
			layer = (GraphLayer) layerList.get(i);
			info = new ListOptionInfo(i + "", layer.getName());
			list.add(info);
		}
		return list;
	}

	public Boolean getLayerDisplayStatus(int index) {
		Boolean rets[] = null;
		if (layerList == null || index < 0 || index >= layerList.size())
			return false;
		GraphLayer layer;
		layer = (GraphLayer) layerList.get(index);
		return layer.isVisible();
	}

	public void setLayersDisplayStatus(Boolean[] data) {
		if (layerList == null)
			return;
		int len = data.length;
		if (len < layerList.size())
			len = layerList.size();
		GraphLayer layer;
		for (int i = 0; i < len; i++) {
			layer = (GraphLayer) layerList.get(i);
			layer.setVisible(data[i]);
		}
	}

	public void setLayerDisplayStatus(int index, Boolean bDisp) {
		if (layerList == null || index < 0 || index >= layerList.size())
			return;
		GraphLayer layer;
		layer = (GraphLayer) layerList.get(index);
		layer.setVisible(bDisp);
	}

	public int getCurrentLayerNo() {
		return currentLayerNo;
	}

	public void setCurrentLayerNo(int currentLayerNo) {
		this.currentLayerNo = currentLayerNo;
	}

	public String getLayerName(int index) {
		if (index < 0)
			return null;
		else {
			GraphLayer layer;
			layer = (GraphLayer) layerList.get(index);
			return layer.getName();
		}
	}

	public double getScaleXY() {
		return scaleXY;
	}

	public void setScaleXY(double scaleXY) {
		this.scaleXY = scaleXY;
		makeWVTrans();
	}

	public void scaleGraph(boolean isEnlarge) {
		if (isEnlarge) {
			scaleXY *= 2.0;
		} else {
			scaleXY /= 2.0;
		}
		makeWVTrans();
	}

	public HashMap<String, Graph2D> getGraph2DMap() {
		return graph2DMap;
	}

	public void setGraph2DMap(HashMap<String, Graph2D> graph2DMap) {
		this.graph2DMap = graph2DMap;
	}

	public void addGraph2D(String name, Graph2D graph2DObject) {
		// TODO Auto-generated method stub
		graph2DMap.put(name, graph2DObject);
	}

	public SelectedData getSelectedDataByPoint(UFPoint fp) {
		int i;
		GraphLayer layer;
		Graph2D g2d;
		SelectedData selectedData = null;
		for (i = 0; i < layerList.size(); i++) {
			layer = (GraphLayer) layerList.get(i);
			if (layer.isVisible()) {
				g2d = getGraph2D(layer);
				selectedData = g2d.getSelectedDataByPoint(fp);
				if (selectedData != null)
					return selectedData;
			}
		}
		return selectedData;
	}

	public Object getInnerObjectByPoint(UFPoint fp) {
		GraphLayer layer = (GraphLayer) layerList.get(currentLayerNo);
		return getGraph2D(layer);
	}

	public List getSelectElementList() {
		return selectedElementList;
	}

	public void addLayer(GraphLayer layer) {
		layerList.add(layer);
	}

	public void setGraphData(Object object) {
		if (this.graphDataProcessor != null) {
			if (object == null) {
				graphDataProcessor.getInitDefaultGraphData();
			} else {
				graphDataProcessor.setGraphData(object);
			}
		}
	}

	public Object getGraphData() {
		if (this.graphDataProcessor != null) {
			return graphDataProcessor.getGraphData();
		} else {
			return null;
		}
	}

	public void makeObject() {
		if (graphDataProcessor != null) {
			graphDataProcessor.makeObject();
		}
	}

	public GraphModelDataProcessorI getGraphDataProcessor() {
		return graphDataProcessor;
	}

	public void setGraphDataProcessor(
			GraphModelDataProcessorI graphDataProcessor) {
		this.graphDataProcessor = graphDataProcessor;
	}

	public void setDeaultGraphSize() {
		graphWidth = 595.27559f;
		graphHeight = 841.88976f;
		graphDpi = 72f;

	}

	public void getInitDefaultGraph() {

		setPaperTemplate((UPaperTemplate) UFactory.getModelSession()
				.getTemplate(UConstants.MAPKEY_PAPER, "A4"));
		setDeaultGraphSize();
		Graph2D g2d = new Graph2D(this);
		g2d.setName(name);
		GraphLayer layer = new GraphLayer();
		if (this.graphDataProcessor != null)
			this.graphDataProcessor.insertNewLayer(g2d, name);
		layer.setGraph2DName(name);
		layerList.add(layer);
		this.graph2DMap.put(name, g2d);
	}

	public Graph2D getCurrentGraph2D() {
		GraphLayer layer = (GraphLayer) layerList.get(currentLayerNo);
		if (layer == null)
			return null;
		else {
			return getGraph2D(layer);
		}
	}

	public void makeGraphObject(HashMap para) {

	}

	FHashMap getGraph2DFMap() {
		FHashMap fMap = new FHashMap();
		Iterator it = graph2DMap.keySet().iterator();
		String name;
		FNameObjectPar par;
		while (it.hasNext()) {
			name = (String) it.next();
			fMap.putData(name, graph2DMap.get(name));
		}
		return fMap;
	}

	public DrageDataI getDrageData() {
		// TODO Auto-generated method stub
		return null;
	}

	public List getSelectElementByPoint(UFPoint fp) {
		int i;
		GraphLayer layer;
		Graph2D g2d;
		List list;
		selectedElementList = null;
		for (i = 0; i < layerList.size(); i++) {
			layer = (GraphLayer) layerList.get(i);
			if (layer.isVisible() && layer.isCanSelected()) {
				g2d = getGraph2D(layer);
				list = g2d.getSelectElementsByPoint(fp);
				if (list != null && list.size() > 0) {
					if (selectedElementList == null) {
						selectedElementList = new ArrayList();
					}
					selectedElementList.addAll(list);
				}
			}
		}
		return selectedElementList;
	}

	public void read(DataInputStream in) throws IOException {
		// TODO Auto-generated method stub
		int len, i;
		UTemplate ts = new UTemplate();
		paperTemplate = (UPaperTemplate) ts.readTemplate(in);
		graphWidth = in.readDouble();
		graphHeight = in.readDouble();
		graphDpi = in.readDouble();
		len = in.readInt();
		for (i = 0; i < len; i++) {
			layerList.add((GraphLayer) readGElement(in));
		}
		currentLayerNo = in.readInt();
		scaleXY = in.readDouble();
		readWVTrans(in, wv);
		len = in.readInt();
		Graph2D g2d;
		String nameString;
		for (i = 0; i < len; i++) {
			nameString = readString(in);
			g2d = (Graph2D) readGElement(in);
			g2d.setGraphModelI(this);
			graph2DMap.put(nameString, g2d);
		}
	}

	public void write(DataOutputStream out) throws IOException {
		// TODO Auto-generated method stub
		int i;
		UTemplate ts = new UTemplate();
		ts.writeTemplate(out, paperTemplate);
		out.writeDouble(graphWidth);
		out.writeDouble(graphHeight);
		out.writeDouble(graphDpi);
		out.writeInt(layerList.size());
		for (i = 0; i < layerList.size(); i++) {
			writeGElement(out, layerList.get(i));
		}
		out.writeInt(currentLayerNo);
		out.writeDouble(scaleXY);
		writeWVTrans(out, wv);
		out.writeInt(graph2DMap.size());
		Iterator it = graph2DMap.keySet().iterator();
		String key;
		while (it.hasNext()) {
			key = (String) it.next();
			writeString(out, key);
			writeGElement(out, graph2DMap.get(key));
		}
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public void setGraphSizeByPaperTemplate(UPaperTemplate paper) {
		// TODO Auto-generated method stub
		if (paper == null) {
			setDeaultGraphSize();
			return;
		}
		graphDpi = 72f;
		double w = paper.width - paper.left - paper.right;
		double h = paper.height - paper.top - paper.bottom;
		if (paper.orientation == 1) {
			graphWidth = h;
			graphHeight = w;
		} else {
			graphWidth = h;
			graphHeight = w;
		}
	}

	public GElement getGElementByName(String name, int layerNo) {
		if (layerList == null)
			return null;
		GraphLayer layer = layerList.get(layerNo);
		if (layer == null)
			return null;
		Graph2D g2d = graph2DMap.get(layer.getGraph2DName());
		if (g2d == null)
			return null;
		return g2d.getGElementByName(name);
	}

	@Override
	public void exportElementToDoc(Element ge) {
		// TODO Auto-generated method stub
		int i;
		String key;
		Graph2D g2d;
		Element ee;
		ge.addAttribute("page", paperTemplate.getName());
		ge.addAttribute("width", "" + graphWidth);
		ge.addAttribute("height", "" + graphHeight);
		ge.addAttribute("dpi", "" + graphDpi);
		ge.addAttribute("name", name);
		if (graph2DMap != null) {
			Iterator ie = graph2DMap.keySet().iterator();
			while (ie.hasNext()) {
				key = (String) ie.next();
				g2d = graph2DMap.get(key);
				ee = ge.addElement("graph2d");
				g2d.exportElementToDoc(ee);
			}
		}
		if (layerList != null) {
			for (i = 0; i < layerList.size(); i++) {
				ee = ge.addElement("layer");
				layerList.get(i).exportElementToDoc(ee);
			}
		}
	}

	@Override
	public Graph2DI getGraph2D(GraphLayerI layer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void resetViewParameter() {
		// TODO Auto-generated method stub
		// if(this.paperTemplate == null) {
		// if(graphWidth < 1000)
		// scaleXY = 1000 /graphWidth;
		// else
		// scaleXY = 1;
		// }else
		scaleXY = 1.0;
		makeWVTrans();
	}

	@Override
	public void setLayerVisible(String name, boolean visible) {
		// TODO Auto-generated method stub
		int index = Integer.parseInt(name) - 1;
		layerList.get(index).setVisible(visible);
	}

	@Override
	public boolean getLayerVisible(String name) {
		// TODO Auto-generated method stub
		int index = Integer.parseInt(name) - 1;
		return layerList.get(index).isVisible();
	}

	@Override
	public void setGraphId(Integer graphId) {
		// TODO Auto-generated method stub

	}

	@Override
	public Integer getGraphId() {
		// TODO Auto-generated method stub
		return null;
	}


	public void addLayer(Graph2D g2d, String name) {
		graph2DMap.put(g2d.getName(), g2d);
		GraphLayer layer = new GraphLayer();
		layer.setColorName(g2d.getColorName());
		layer.setName(name);
		layer.setGraph2DName(g2d.getName());
		layer.setVisible(true);
		layerList.add(layer);
	}

	public boolean isModify() {
		Set set = graph2DMap.keySet();
		if (set == null)
			return false;
		Iterator ie = set.iterator();
		if (ie == null)
			return false;
		String key;
		Graph2D g2d;
		while (ie.hasNext()) {
			key = (String) ie.next();
			g2d = graph2DMap.get(key);
			if (g2d != null && g2d.isModify)
				return true;
		}
		return false;
	}

	public void clearModifyMark() {
		Set set = graph2DMap.keySet();
		if (set == null)
			return;
		Iterator ie = set.iterator();
		if (ie == null)
			return;
		String key;
		Graph2D g2d;
		while (ie.hasNext()) {
			key = (String) ie.next();
			g2d = graph2DMap.get(key);
			if (g2d != null)
				g2d.clearModifyMark();
		}
	}

	public void setLayerCanSelected(int index, boolean canSelected) {
		if (layerList == null || index < 0 || index >= layerList.size())
			return;
		layerList.get(index).setCanSelected(canSelected);
	}
//	public void resetRealGraphSize(){
//		int size, i;
//		UFRect re = new UFRect(); 
//		GraphLayer layer;
//		Object control;
//		UFormI formI = null;
//		size = (int) layerList.size();
//		UFRect r;
//		Graph2D g2d;
//		for (i = 0; i < size; i++) {
//			layer = (GraphLayer) layerList.get(i);
//			g2d = getGraph2D(layer);
//			g2d.resetBox();
//			r = g2d.getBox();
//			re.Union(r);
//		}
//		this.graphWidth = re.w;
//		this.graphHeight = re.h;
//	}

	@Override
	public Object outJSONObject(int imageWidth, int imageHeight) {
		// TODO Auto-generated method stub
		return null;
	}
}
