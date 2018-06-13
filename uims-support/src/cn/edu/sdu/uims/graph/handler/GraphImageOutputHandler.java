package cn.edu.sdu.uims.graph.handler;

import cn.edu.sdu.common.reportdog.UPaperTemplate;
import cn.edu.sdu.uims.graph.view.GraphViewI;
import cn.edu.sdu.uims.graph.view.UViewImage;

public class GraphImageOutputHandler extends GraphPrintHandler {

	public GraphViewI getGraphView(UPaperTemplate paperTemplate) {
		return new UViewImage(paperTemplate,null);
	}
}