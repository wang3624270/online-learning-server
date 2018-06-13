package cn.edu.sdu.uims.graph.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import cn.edu.sdu.uims.trans.UFRect;

public class GElementPolyline3D extends GElementPolyline {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GElementPolyline3D(UFRect rec, String label, String label2,
			String label3, String orientation) {
		super(rec, label, label2, label3, orientation);
		// TODO Auto-generated constructor stub
	}

	protected JFreeChart createChart(DefaultCategoryDataset dataset) {
		PlotOrientation plotOrientation = PlotOrientation.VERTICAL;
		if (orientation.equals("vertical")) {
			plotOrientation = PlotOrientation.VERTICAL;
		} else if (orientation.equals("horizontal")) {
			plotOrientation = PlotOrientation.HORIZONTAL;
		}
		JFreeChart chart = ChartFactory.createLineChart3D(label, xLabel,
				yLabel, dataset, plotOrientation, true, false, false);

		return chart;
	}
	public void read(DataInputStream in) throws IOException {
		super.read(in);
	}

	public void write(DataOutputStream out) throws IOException {
		super.write(out);
	}

}
