package cn.edu.sdu.uims.graph.util;

import java.util.List;

import cn.edu.sdu.common.reportdog.UPaperTemplate;
import cn.edu.sdu.uims.graph.GraphElementCreatorI;
import cn.edu.sdu.uims.graph.model.GElementText;
import cn.edu.sdu.uims.graph.model.Graph2D;
import cn.edu.sdu.uims.graph.model.GraphLayer;
import cn.edu.sdu.uims.graph.model.GraphModel2D;
import cn.edu.sdu.uims.graph.model.GraphModelI;
import cn.edu.sdu.uims.trans.UFPoint;

public class GraphModelUtils {
	public static GraphModelI makeGraphModel2DByRegionDataList(List dataList) {
		if (dataList == null || dataList.size() == 0)
			return null;
		GraphModel2D gm2d = null;
		List<GraphLayer> layerList = null;
		Graph2D g2d = null;
		GraphLayer layer = null;
		UPaperTemplate paperTemplate = null;
		int i;
		GraphElementCreatorI gc;
		GElementText gt;

		gm2d = new GraphModel2D();
		gm2d.setName("gm0");
		paperTemplate = new UPaperTemplate();
		gm2d.setPaperTemplate(paperTemplate);
		g2d = new Graph2D(gm2d);
		g2d.setName("g0");
		layer = new GraphLayer();
		layer.setGraph2DName("g0");
		gm2d.getLayerList().add(layer);
		gm2d.getGraph2DMap().put("g0", g2d);
		double minx = Integer.MAX_VALUE, miny = Integer.MAX_VALUE, maxx = Integer.MIN_VALUE, maxy = Integer.MIN_VALUE;
		for (i = 0; i < dataList.size(); i++) {
			gc = (GraphElementCreatorI) dataList.get(i);
			gt = new GElementText();
			gt.dataObject = gc;
			gt.box = gc.getBox();
			if (gt.box.x < minx) {
				minx = gt.box.x;
			}
			if (gt.box.y < miny) {
				miny = gt.box.y;
			}
			if (gt.box.x + gt.box.w > maxx) {
				maxx = gt.box.x + gt.box.w;
			}
			if (gt.box.y + gt.box.h > maxy) {
				maxy = gt.box.y + gt.box.h;
			}
			gt.po = new UFPoint(gt.box.x, gt.box.y);
			gt.text = gc.getDispContent();
			gt.colorName = gc.getColorName();
			gt.fontName = gc.getFontName();
			g2d.elementList.add(gt);
		}
		maxx += minx;
		maxy += maxy;
		gm2d.setGraphWidth(maxx);
		gm2d.setGraphHeight(maxy);
		paperTemplate.width = maxx;
		paperTemplate.height = maxy;
		paperTemplate.left = 0;
		paperTemplate.top = 0;
		paperTemplate.right = 0;
		paperTemplate.bottom = 0;
		return gm2d;
	}

	public static GraphModel2D newGraphModel2D(){
		GraphModel2D g = new GraphModel2D();
		return g;
	}
	
	public static void multMatrix(double a[][], double b[][],double c[][]) {
		int i,j,k;
		for (i = 0; i < 3; i++) {
			for (j = 0; j < 3; j++) {
				c[i][j] = 0;
				for (k = 0; k < 3; k++)
					c[i][j] += a[i][k] * b[k][j];
			}
		}
	}
	public static double [][] setStandardGraphModel(GraphModel2D g, double minx, double miny, double maxx, double maxy) {
		double width = maxx-minx;
		double height = maxy - miny;
		double scale = 841.88976 / width;
		height = height * scale;
		
		double m0[][] = new double[][] { { 1, 0, -minx }, { 0, 1, -miny }, {0,0,1} };
		double m1[][] = new double[][] { { scale, 0, 0,}, { 0, -scale, 0, },{0,0,1} };
		double m2[][] = new double[][] { { 1, 0, 0 }, { 0, 1,height,},{0,0,0,1} };
		double m[][] = new double[3][3];
		multMatrix(m1, m0, m);
		multMatrix(m2, m, m0);
		g.setGraphDpi(72);
		g.setGraphWidth(841.88976);
		g.setGraphHeight(height);
		return m0;
	}
	
	public static GraphModel2D createGraphMode2DByImageData(String url) {
		GraphModel2D g = newGraphModel2D();
		Graph2D g2d = new Graph2D("ground");
		g.addLayer(g2d, "底图");
		
		return g;
	}
	
	
}
