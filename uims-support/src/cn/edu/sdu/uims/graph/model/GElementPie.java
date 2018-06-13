package cn.edu.sdu.uims.graph.model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import cn.edu.sdu.uims.graph.view.ControlParameter;
import cn.edu.sdu.uims.graph.view.ViewParameter;
import cn.edu.sdu.uims.trans.UFPoint;
import cn.edu.sdu.uims.trans.UFRect;
import cn.edu.sdu.uims.trans.UPoint;

public class GElementPie extends GElement2D {

	private static final long serialVersionUID = 1L;

	public String label;

	public DefaultPieDataset dataset;

	public GElementPie(UFRect rec, String label) {
		this.box = rec;
		this.label = label;
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
			// DefaultPieDataset dataset = createDataset(data);

			if (dataset != null) {
				JFreeChart chart = createChart(dataset);
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

	protected DefaultPieDataset createDataset(Object[][] data) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		if (data != null && data.length > 0) {
			int i;
			Object[] rowData;
			;
			for (i = 0; i < data.length; i++) {
				rowData = data[i];
				dataset.setValue(String.valueOf(rowData[1]), Double
						.valueOf(Double.valueOf(String.valueOf(rowData[0]))));
			}
		}
		return dataset;
	}

	protected JFreeChart createChart(DefaultPieDataset dataset) {
		JFreeChart chart = ChartFactory.createPieChart(label, dataset, true,
				false, false);

		return chart;
	}
	public void read(DataInputStream in) throws IOException {
		super.read(in);
	}

	public void write(DataOutputStream out) throws IOException {
		super.write(out);
	}

}
