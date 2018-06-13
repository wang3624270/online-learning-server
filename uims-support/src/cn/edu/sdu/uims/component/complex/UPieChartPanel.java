package cn.edu.sdu.uims.component.complex;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.RenderingHints;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.urls.StandardPieURLGenerator;
import org.jfree.data.general.DefaultPieDataset;

import cn.edu.sdu.uims.component.complex.form.UCharDataFormI;
import cn.edu.sdu.uims.component.complex.jfree.UChartPanel;

public class UPieChartPanel extends UChartPanel {

	public JFreeChart getChartObjectByDataList(List dataList) {

		// TODO Auto-generated method stub
		JFreeChart pieChart = ChartFactory.createPieChart3D(getTitle(),getDataSet(dataList), true, true, false);
		// 设置Title的字体(有些版本会出现乱码)
		pieChart.getTitle().setFont((new Font("宋体", Font.CENTER_BASELINE, 20)));
		// 背景色
		pieChart.setBackgroundPaint(Color.white);
		// RenderingHints做文字渲染参数的修改，VALUE_TEXT_ANTIALIAS_OFF表示将文字的抗锯齿关闭.
		pieChart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		// 设置图例说明Legend上的文字(//设置底部的字体)
		pieChart.getLegend().setItemFont(
				new Font("宋体", Font.CENTER_BASELINE, 15));
		// 取得饼图的绘图(plot)对象
		PiePlot3D piePlot = (PiePlot3D) pieChart.getPlot();
		setBase(piePlot);
		setSection(piePlot);
		setLabel(piePlot);
		setNoDataMessage(piePlot);
		setNullAndZeroValue(piePlot);
		return pieChart;
		// 将饼图显示在图像界面上
	}

	// 对绘图对象的基本设置
	public static void setBase(PiePlot piePlot) {

		piePlot.setBaseSectionOutlinePaint(Color.BLACK);// 图形边框颜色
		piePlot.setBaseSectionOutlineStroke(new BasicStroke(0.1f));// 图形边框粗细
		piePlot.setForegroundAlpha(0.65f); // 指定图片的透明度(0.0-1.0)

		piePlot.setCircular(false);// 设置饼图为正圆还是椭圆，默认为正圆（true）
		piePlot.setStartAngle(75);// 设置第一个 饼块section 的开始位置，默认是12点钟方向

		piePlot.setToolTipGenerator(new StandardPieToolTipGenerator()); // 设置鼠标悬停提示
		piePlot.setExplodePercent(1, 0.1D);// 设置突出显示的数据块
		piePlot.setURLGenerator(new StandardPieURLGenerator());
	}

	// 创建饼图数据对象
	public String getRowColKey(String rowKey, String colKey){
		String str = rowKey;
		if(str != null)
			str += colKey;
		else
			str = colKey;
		if(str == null)
			str = "";
		return str;
	}

	public DefaultPieDataset getDataSet(List dataList) {
		DefaultPieDataset pieDataset = new DefaultPieDataset();
		for (int i = 0; i < dataList.size(); i++) {
			UCharDataFormI form = (UCharDataFormI) dataList
					.get(i);
			pieDataset.setValue(getRowColKey(form.getRowKey(), form.getColKey()), form.getValue());
		}
		return pieDataset;
	}

	// 设置扇区饼颜色
	public static void setSection(PiePlot pieplot) {
		// 如果不设置，它会自己设置
		// pieplot.setSectionPaint("淘宝网", new Color(160, 160, 255));

		pieplot.setExplodePercent(5, 0.2D);// 设置扇区分离显示
		pieplot.setSectionOutlinesVisible(false);// 设置扇区边框不可见
	}

	// 对饼图标签的设置
	public static void setLabel(PiePlot pieplot) {

		// 设置扇区标签显示格式：自定义方式，{0} 表示选项， {1} 表示数值， {2} 表示所占比,小数点后一位
		pieplot.setLabelGenerator(new StandardPieSectionLabelGenerator(
				"{0}:{2}({1}平方米)", NumberFormat.getNumberInstance(),
				new DecimalFormat("0.00%")));
		// 设置扇区标签颜色
		pieplot.setLabelBackgroundPaint(new Color(220, 220, 220));
		pieplot.setLabelFont((new Font("宋体", Font.PLAIN, 12)));

	}

	// 无数据的处理
	public static void setNoDataMessage(PiePlot pieplot) {
		// 设置没有数据时显示的信息
		pieplot.setNoDataMessage("没有相应的数据");
		// 设置没有数据时显示的信息的字体
		pieplot.setNoDataMessageFont(new Font("宋体", Font.BOLD, 14));
		// 设置没有数据时显示的信息的颜色
		pieplot.setNoDataMessagePaint(Color.red);
	}

	// 设置是否忽略0和null值
	public static void setNullAndZeroValue(PiePlot piePlot) {
		piePlot.setIgnoreNullValues(true);
		// piePlot.setIgnoreZeroValues(true);
	}
	public Dimension getCanvasSize(){
		return new Dimension(1000,1500);
	}

}
