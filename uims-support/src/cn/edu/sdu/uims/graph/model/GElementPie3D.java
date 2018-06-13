package cn.edu.sdu.uims.graph.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import cn.edu.sdu.uims.trans.UFRect;

public class GElementPie3D extends GElementPie {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public GElementPie3D(UFRect rec, String label) {
		super(rec, label);
		// TODO Auto-generated constructor stub
	}

	protected JFreeChart createChart(DefaultPieDataset dataset) {
		JFreeChart chart = ChartFactory.createPieChart3D(label, dataset, true,
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
