package cn.edu.sdu.uims.graph.model;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.dom4j.Element;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.CategoryItemEntity;
import org.jfree.chart.entity.EntityCollection;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import cn.edu.sdu.uims.graph.view.ControlParameter;
import cn.edu.sdu.uims.graph.view.ViewParameter;
import cn.edu.sdu.uims.trans.UFPoint;
import cn.edu.sdu.uims.trans.UFRect;
import cn.edu.sdu.uims.trans.UPoint;

public class GElementPolyline extends GElement2D {

	private static final long serialVersionUID = 1L;

	public String label = "";

	public String xLabel = "";

	public String yLabel = "";

	public String orientation = "vertical";

	public Object par;

	private ChartRenderingInfo info;

	private JFreeChart chart;

	private EntityCollection entities = new StandardEntityCollection();

	public GElementPolyline(UFRect rec, String label, String xLabel,
			String yLabel) {
		this.box = rec;
		this.label = label;
		this.xLabel = xLabel;
		this.yLabel = yLabel;
	}

	public GElementPolyline(UFRect rec, String label, String xLabel,
			String yLabel, String orientation) {
		this(rec, label, xLabel, yLabel);
		this.orientation = orientation;

	}

	public GElementPolyline(UFRect rec, String label, String xLabel,
			String yLabel, String orientation, Object par) {
		this(rec, label, xLabel, yLabel);
		if (this.par != null)
			this.par = null;
		this.par = par;
	}

	public void drawDo(Graphics dc, ViewParameter p, ControlParameter c,
			Object obj,UPoint shiftPoint) {
		if (obj == null) {
			Object[][] data = (Object[][]) par;
			UFPoint po = new UFPoint();
			po.x = box.x;
			po.y = box.y;
			UFPoint pi = new UFPoint();
			pi.x = box.x + box.w;
			pi.y = box.y + box.h;
			UPoint p1, p2;
			p1 = p.m.logicToView(po);
			p2 = p.m.logicToView(pi);
			DefaultCategoryDataset dataset = createDataset(data);
			chart = createChart(dataset);
			if (chart == null) {
				return;
			}
			//CategoryPlot plot = chart.getCategoryPlot();
			//CategoryAxis axis = plot.getDomainAxis();
			//axis.setLowerMargin(UConstants.chartOffset);
			Graphics2D g2 = (Graphics2D) dc.create();
			Rectangle2D chartArea = new Rectangle2D.Double(p1.x, p1.y, p2.x
					- p1.x, p2.y - p1.y);
			entities = new StandardEntityCollection();
			info = new ChartRenderingInfo(entities);
			chart.draw(g2, chartArea, info);
			g2.dispose();
		}
	}

	protected DefaultCategoryDataset createDataset(Object[][] data) {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		if (data != null && data.length > 0) {
			int i;
			Object[] rowData;
			for (i = 0; i < data.length;) {
				rowData = data[i];
				
				
//				String ceshi=String.valueOf(rowData[0]);
//				System.out.println(ceshi.indexOf(":"));
//				String arrays1[] = ceshi.split(":");
//                String sss=(String)arrays1[0];
//                String ssw=(String)arrays1[1];
                
                
				if (rowData[0] != null) {
					
					
//					dataset.setValue(
//							Double.valueOf(ssw), String
//									.valueOf(sss),String
//									.valueOf(rowData[1]));
					
					dataset.setValue(
							Double.valueOf(String.valueOf(rowData[0])), String
									.valueOf(rowData[1]),String
									.valueOf(rowData[2]));
					
					i++;
					
					
				} else {
					// 第一个非空
					int j = i + 1;
					for (; i > 0 && j < data.length && data[j][0] == null; j++)
						;
					int num = j - i + 1;
					double distance = Double
							.valueOf(String.valueOf(data[j][0]))
							- Double.valueOf(String.valueOf(data[i - 1][0]));
					double delta = distance / num;
					for (int m = i; m < j; m++) {
						dataset.setValue(Double.valueOf(String
								.valueOf(data[i - 1][0]))
								+ delta * (m - i + 1), String
								.valueOf(data[m][1]), String
								.valueOf(data[m][2]));
					}
					i = j;
				}
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
		JFreeChart chart = ChartFactory.createLineChart(label, xLabel, yLabel,
				dataset, plotOrientation, true, false, false);
		return chart;
	}

	/**
	 * function 获取chart中点的数据
	 */
	public Object getInnerObjectByPoint(UFPoint fp) {
		CategoryItemEntity entity = null;
		if (this.info != null) {
			// EntityCollection entities = this.info.getEntityCollection(); //
			// 获得所有点的信息
			if (entities != null) {
				entity = (CategoryItemEntity) entities.getEntity(fp.x, fp.y); // 根据point获取点
				if (entity != null) {
					if (par != null) {
						Integer index = (Integer) entity.getCategoryIndex(); // 获取point的索引
						if (index != null) {
							Object[][] data = (Object[][]) par; // 获取point的对应的数据
							return data[index];
						}
					}
				}
			}
		}
		return null;
	}
	public void read(DataInputStream in) throws IOException {
		super.read(in);
	}

	public void write(DataOutputStream out) throws IOException {
		super.write(out);
	}
	public void exportElementToDoc(Element ge) {
		// TODO Auto-generated method stub
		ge.addAttribute("", "");
		super.exportElementToDoc(ge);
	}	

}
