package cn.edu.sdu.uims.graph.model.data.xml;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import cn.edu.sdu.common.reportdog.UPaperTemplate;
import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.graph.model.Graph2D;
import cn.edu.sdu.uims.graph.model.GraphLayer;
import cn.edu.sdu.uims.graph.model.GraphModel2D;
import cn.edu.sdu.uims.graph.model.GraphModelI;
import cn.edu.sdu.uims.graph.model.data.GraphModelDataProcessor;
import cn.edu.sdu.uims.service.UFactory;

public class XmlGraphModelDataProcessor extends GraphModelDataProcessor {
	private Document xmlDoc = null;

	public XmlGraphModelDataProcessor(GraphModel2D g2d) {
		super(g2d);
	}
	public XmlGraphModelDataProcessor(GraphModelI g2d) {
		if(g2d instanceof GraphModel2D) {
			this.graphModel2D = (GraphModel2D)g2d; 

		}
	}

	public void getInitDefaultGraphData() {
		String content = this.getDefaultXmlContent();
		setGraphData(content);
	}

	public String getDefaultXmlContent() {
		String name = "000001";
		if (graphModel2D != null)
			name = graphModel2D.getName();
		String xmlContent;
		xmlContent = "";
		xmlContent += "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
		xmlContent += "<graphmodeltemplate>";
		xmlContent += "<graph_model name=\"" + name + "\"  page = \"A4\"";
		xmlContent += "	width =\"595.27559\" height = \"841.88976\" dpi = \"72\">";
		xmlContent += "	<graph2d  name=\"000001\"  dpi=\"72\" >";
		xmlContent += "	</graph2d >";
		xmlContent += "	<layer x=\"0\" y=\"0\" w=\"595.27559\" h=\"841.88976\" dpi=\"72\" disp = \"true\"";
		xmlContent += "			graph2dName = \"000001\"/>";
		xmlContent += "	</graph_model>";
		xmlContent += "</graphmodeltemplate>";
		return xmlContent;
	}

	public void makeObject() {
		if (xmlDoc == null)
			return;
		setObject(xmlDoc.getRootElement());
	}

	public void setGraphData(Object object) {
		String content;
		if (object == null || !(object instanceof String))
			content = this.getDefaultXmlContent();
		else
			content = (String) object;
		xmlDoc = null;
		try {
			xmlDoc = DocumentHelper.parseText(content);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		makeObject();
	}

	public void setXmlDoc(Document xmlDoc) {
		this.xmlDoc = xmlDoc;
		makeObject();
	}
	
	public Object getGraphData() {
		// TODO Auto-generated method stub
		String content = null;
		if (xmlDoc == null)
			return content;
		StringWriter out = new StringWriter();
		try {
			xmlDoc.write(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		out.flush();
		StringBuffer sBuffer = out.getBuffer();
		return sBuffer.toString();
	}

	public Document getXmlDoc() {
		return xmlDoc;
	}


	public void setObject(Element root) {
		String str;
		HashMap<String, Graph2D> graph2DMap = graphModel2D.getGraph2DMap();
		Element element, layer;
		element = root.element("graph_model");
		graphModel2D.setName(element.attributeValue("name"));
		root.element("");
		String pageType = "A4";
		if (element.attributeValue("page") != null
				&& !element.attributeValue("page").equals("")) {
			pageType = element.attributeValue("page");
		}
		graphModel2D.setPaperTemplate((UPaperTemplate) UFactory.getModelSession()
				.getTemplate(UConstants.MAPKEY_PAPER, pageType));
		str = element.attributeValue("width");
		if (str != null)
			graphModel2D.setGraphWidth(Double.parseDouble(str));
		str = element.attributeValue("height");
		if (str != null)
			graphModel2D.setGraphHeight(Double.parseDouble(str));
		str = element.attributeValue("dpi");
		if (str != null)
			graphModel2D.setGraphDpi(Double.parseDouble(str));
		Graph2D graph2d;
		String graph2DName;
		Iterator graph2Dite = element.elementIterator("graph2d");
		while (graph2Dite.hasNext()) {
			Element graph2DElement = (Element) graph2Dite.next();
			graph2DName = graph2DElement.attributeValue("name");
			graph2d = loadGraph2DFormModel(graph2DName, graph2DElement);
			graph2d.setParent(graphModel2D);
			graph2DMap.put(graph2DName, graph2d);
		}
		Iterator it1 = element.elementIterator("layer");
		GraphLayer graphLayer;
		String g2dName;
		XmlElementDataProcessorLayer ldProcessorLayer;
		while (it1.hasNext()) {
			layer = (Element) it1.next();
			graphLayer = new GraphLayer();

			ldProcessorLayer = new XmlElementDataProcessorLayer();
			ldProcessorLayer.setElement(layer);
			ldProcessorLayer.setGElement(graphLayer);
			graphLayer.setDataProcessor(ldProcessorLayer);
			str = layer.attributeValue("x");
			if (str != null) {
				graphLayer.setX(Double.parseDouble(str));
			}
			str = layer.attributeValue("y");
			if (str != null) {
				graphLayer.setY(Double.parseDouble(str));
			}
			str = layer.attributeValue("w");
			if (str != null) {
				graphLayer.setW(Double.parseDouble(str));
			}
			str = layer.attributeValue("h");
			if (str != null) {
				graphLayer.setH(Double.parseDouble(str));
			}
			str = layer.attributeValue("dpi");
			if (str != null) {
				graphLayer.setDpi(Double.parseDouble(str));
			}
			str = layer.attributeValue("display");
			if (str == null || str.equals("true")) {
				graphLayer.setVisible(true);
			} else {
				graphLayer.setVisible(false);
			}
			g2dName = layer.attributeValue("graph2dName");
			graphLayer.setGraph2DName(g2dName);
			graphLayer.setParent(graphModel2D);
			graphModel2D.addLayer(graphLayer);
		}
		graphModel2D.setGraphDataProcessor(this);
		graphModel2D.makeWVTrans();
	}

	public Graph2D loadGraph2DFormModel(String name, Element root) {
		Graph2D graph2D = null;
		if (root != null) {
			graph2D = new Graph2D(graphModel2D, name);
			XmlGraph2DDataProcessor pDl = new XmlGraph2DDataProcessor();
			pDl.setElement(root);
			pDl.setGElement(graph2D);
			graph2D.setDataProcessor(pDl);
			pDl.setObject();
		}
		return graph2D;
	}

	/**
	 * 添加新的graph2d
	 * 
	 * @param graph2DName
	 *            名字
	 */
	public Graph2D insertNewGraph2D(String graph2DName) {
		if (graphModel2D != null) {
			if (xmlDoc != null) {
				Element root = xmlDoc.getRootElement();
				if (root != null) {
					Graph2D g2d = new Graph2D(graphModel2D, graph2DName);
					Element element0 = root.element("graph_model");
					if (element0 != null) {
						Element element = element0.addElement("graph2d");
						if (element != null) {
							element.addAttribute("name", graph2DName);
							XmlGraph2DDataProcessor xmlGraph2DDataProcessor = (XmlGraph2DDataProcessor) g2d
									.getDataProcessor();
							if (xmlGraph2DDataProcessor == null)
								xmlGraph2DDataProcessor = new XmlGraph2DDataProcessor();
							xmlGraph2DDataProcessor.setElement(element);
							xmlGraph2DDataProcessor.setGElement(g2d);
							g2d.setDataProcessor(xmlGraph2DDataProcessor);
							graphModel2D.addGraph2D(graph2DName, g2d);
							return g2d;
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * 添加新的图层
	 * 
	 * @param g2d
	 *            图层对应的graph2d
	 */
	public void insertNewLayer(Graph2D g2d, String layerName) {
		if (graphModel2D != null) {
			if (xmlDoc != null) {
				Element root = xmlDoc.getRootElement();
				if (root != null) {
					Element element0 = root.element("graph_model");
					if (element0 != null) {
						GraphLayer graphLayer = new GraphLayer();
						graphLayer.setName(layerName);
						graphLayer.setGraph2DName(g2d.getName());
						Element element = element0.addElement("layer");
						if (element != null) {
							XmlElementDataProcessorLayer xmlLayerProcessor = (XmlElementDataProcessorLayer) graphLayer
									.getDataProcessor();
							if (xmlLayerProcessor == null)
								xmlLayerProcessor = new XmlElementDataProcessorLayer();
							xmlLayerProcessor.setGElement(graphLayer);
							xmlLayerProcessor.setElement(element);
							xmlLayerProcessor.setAttributeValueToData(null);
							graphModel2D.addLayer(graphLayer);
						}
					}
				}
			}
		}
	}

	public void updateLayer(int index, HashMap map) {
		if (graphModel2D != null) {
			if (graphModel2D.getLayerList() != null) {
				GraphLayer layer = graphModel2D.getGraphLayer(index);
				XmlElementDataProcessorLayer xmlLayerProcessor = (XmlElementDataProcessorLayer) layer
						.getDataProcessor();
				if (xmlLayerProcessor != null) {
					xmlLayerProcessor.setAttributeValueToData(map);
					xmlLayerProcessor.getAttributeFromData();
				}
			}
		}	
	}

}
