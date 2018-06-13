package cn.edu.sdu.uims.view;

import java.awt.Graphics;
import java.awt.Image;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.def.UChartTemplate;
import cn.edu.sdu.uims.service.UFactory;
import cn.edu.sdu.uims.trans.UMatrix;
import cn.edu.sdu.uims.trans.URect;

public class UVChart extends UVElement {
	private Image img = null;
	private UChartTemplate chartTemplate = null;
	private DefaultCategoryDataset dataset = null;
	private JFreeChart chart = null;

	public UVChart() {
	}

	public UVChart(UChartTemplate template) {
		super();
		chartTemplate = template;
	}

	public void draw(Graphics g, UMatrix m) {
		super.draw(g, m);
		if (chartTemplate == null)
			return;
		URect rs = new URect(x, y, w, h);
		URect rd;
		rd = m.logicToView(rs);
		if (chartTemplate.type == UConstants.CHART_HISTOGRAM)
			drawHistogram(g, rd);
		else if (chartTemplate.type == UConstants.CHART_CELL) {
			drawCellTable(g, rd);
		}
	}

	public void setTemplate(Object Template) {
		chartTemplate = (UChartTemplate) Template;
	}

	private void drawCellTable(Graphics g, URect r) {
		int titleHeight =0, noteHeight = 0;
		double xt, yt,dh, dw;
		int i,j;
		int col = chartTemplate.label[0].length;
		int row = chartTemplate.label[1].length;		
		if(chartTemplate.dimension == 2){
			if(chartTemplate.title != null){
				titleHeight = chartTemplate.title.h;
				UVUtil.drawStringBox(g,chartTemplate.title.content,
						UFactory.getModelSession().getColorByName(chartTemplate.title.frontColorName), 
						UFactory.getModelSession().getFontByName(chartTemplate.title.fontName), r.x, r.y, w, titleHeight, chartTemplate.title.horizontalAlignment,chartTemplate.title.verticalAlignment,false);
			}
			if(chartTemplate.note != null){
				noteHeight = chartTemplate.note.h;
				UVUtil.drawStringBox(g,chartTemplate.note.content ,
						UFactory.getModelSession().getColorByName(chartTemplate.note.frontColorName), 
						UFactory.getModelSession().getFontByName(chartTemplate.note.fontName), r.x, r.y, w, titleHeight, chartTemplate.note.horizontalAlignment,chartTemplate.note.verticalAlignment,false);
			}
			dh = (double)(r.h - titleHeight-noteHeight)/(row+1);
			dw = (double)(r.w)/(col+1);
			xt = r.x;
			yt = r.y + titleHeight;
			for(i = 0; i< col;i++){
				g.drawLine((int)(xt+(i+1)*dw), (int)yt,(int)(xt+(i+1)*dw) , (int)(yt + (row+1)*dh));
			}
			for(j = 0; j< row+1;j++){
				g.drawLine((int)(r.x), (int)(yt+j*dh),(int)(r.x+r.w) , (int)(yt + j*dh));
			}
			g.drawLine((int)xt,(int)yt, (int)(xt+dw),(int)( yt+dh));
			for(i =0;i < col;i++){
				UVUtil.drawStringBox(g, chartTemplate.label[0][i], color, font,(int)(xt+dw + i*dw),(int)yt,(int)dw,(int)dh,horizontalAlignment, verticalAlignment,false);
			}
			for(j =0;j < row;j++){
				UVUtil.drawStringBox(g, chartTemplate.label[1][j], color, font, (int)xt, (int)(yt+dh+j*dh), (int)dw,(int)dh,horizontalAlignment, verticalAlignment,false);
			}
			xt += dw;
			yt += dh;
			for(j = 0; j < row; j++){
				for(i = 0; i < col; i++){
					UVUtil.drawStringBox(g, chartTemplate.data[j*col+i]+"", color, font,(int)(xt+i*dw), (int)(yt+j*dh), (int)dw,(int)dh,horizontalAlignment, verticalAlignment,false);
				}
			}
		}
	}

	private void drawStringBox(Graphics g, String str, int xt, int yt, int dw,
			int dh) {
	}

	private void drawHistogram(Graphics g, URect rd) {
		if (chart == null)
			return;
		Image img = chart.createBufferedImage(rd.w, rd.h);
		g.drawImage(img, rd.x, rd.y, rd.w, rd.h, null);
	}

	private void initHistogramChart() {
		if (chartTemplate == null
				|| chartTemplate.type != UConstants.CHART_HISTOGRAM)
			return;
		int i, j, k;
		dataset = new DefaultCategoryDataset();
		if (chartTemplate.dimension == 2) {
			for (i = 0; i < chartTemplate.label[0].length; i++) {
				for (j = 0; j < chartTemplate.label[1].length; j++) {
					k = i * chartTemplate.label[1].length + j;
					if (k < chartTemplate.data.length)
						dataset.addValue(chartTemplate.data[k],
								chartTemplate.label[0][i],
								chartTemplate.label[1][j]);
				}
			}
			chart = ChartFactory.createBarChart(chartTemplate.title.content,
					"", "", dataset, PlotOrientation.VERTICAL, true, true,
					false);
		}
	}


	public Object getData() {
		return chartTemplate.data;
	}

	public void setData(Object obj) {
		if (obj == null)
			return;
		int data[] = (int[]) obj;
		int i;
		for (i = 0; i < data.length; i++)
			if (i < chartTemplate.data.length)
				chartTemplate.data[i] = data[i];
		initHistogramChart();
	}
}
