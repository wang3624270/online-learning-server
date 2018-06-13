package cn.edu.sdu.uims.graph.model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import cn.edu.sdu.uims.graph.form.GraphChartDataForm;
import cn.edu.sdu.uims.graph.view.ControlParameter;
import cn.edu.sdu.uims.graph.view.ViewParameter;
import cn.edu.sdu.uims.trans.UFPoint;
import cn.edu.sdu.uims.trans.UFRect;
import cn.edu.sdu.uims.trans.UPoint;

public class GElementHistogram extends GElement2D {

	private static final long serialVersionUID = 1L;

	public String label = "";

	public String xLabel = "";

	public String yLabel = "";

	public String orientation;

	public CategoryDataset dataset;

	public GElementHistogram(UFRect rec, String label, String xLabel,
			String yLabel, String orientation) {
		this.box = rec;
		this.label = label;
		this.xLabel = xLabel;
		this.yLabel = yLabel;
		this.orientation = orientation;
	}

	public void drawDo(Graphics dc, ViewParameter p, ControlParameter c,
			Object par,UPoint shiftPoint) {
		Object a[][] = { { 1, 2, 3, 4 }, { 5, 6, 7, 8 } };
		par = a;
		if (par != null) {
			UFPoint po = new UFPoint();
			po.x = box.x;
			po.y = box.y;
			UFPoint pi = new UFPoint();
			pi.x = box.x + box.w;
			pi.y = box.y + box.h;
			UPoint p1, p2;
			p1 = p.m.logicToView(po);
			p2 = p.m.logicToView(pi);
			if (dataset != null) {
				JFreeChart chart = createChart(createDataset(par));
				if (chart == null) {
					return;
				}
				Graphics2D g2 = (Graphics2D) dc.create();
				Rectangle2D chartArea = new Rectangle2D.Double(p1.x, p1.y, p2.x
						- p1.x, p2.y - p1.y);
				chart.draw(g2, chartArea);
				g2.dispose();
			}
		}
	}

	private DefaultCategoryDataset createDataset(Object par) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();		
		if(par == null)
			return dataset;
		int i, j;
		if(par instanceof GraphChartDataForm) {
			GraphChartDataForm form = (GraphChartDataForm)par;
			String [] row = form.getRow();
			String col[] = form.getCol();
			double[][] data = form.getData();
			for(i = 0; i < row.length; i++) {
				for (j = 0;  j < col.length; j++) {
					dataset.setValue(data[i][j],row[i], col[j]);
				}
			}
		}
		else {
			Object[][] data= (Object[][])par;
			Object[] rowData;
			for (i = 0; i < data.length; i++) {
				rowData = data[i];
				dataset.setValue(Double.valueOf(String.valueOf(rowData[0])),
						String.valueOf(rowData[1]), String.valueOf(rowData[2]));
			}
		}
		return dataset;
	}

	protected JFreeChart createChart(DefaultCategoryDataset dataset) {
		PlotOrientation plotOrientation = PlotOrientation.VERTICAL;
		if (orientation.equals("vertical")) {
			plotOrientation = PlotOrientation.VERTICAL;
		} else if (orientation.equals("horizontal")) {
			plotOrientation = PlotOrientation.HORIZONTAL;
		}
		JFreeChart chart = ChartFactory.createBarChart(label, xLabel, yLabel,
				dataset, plotOrientation, true, false, false);

		return chart;
	}

	public CategoryDataset getDataset() {
		return dataset;
	}

	public void setDataset(CategoryDataset dataset) {
		this.dataset = dataset;
	}
	public void read(DataInputStream in) throws IOException {
		super.read(in);
	}

	public void write(DataOutputStream out) throws IOException {
		super.write(out);
	}

}
