package cn.edu.sdu.uims.component.complex.jfree;

import java.awt.Color
;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import cn.edu.sdu.uims.component.complex.form.UCharDataFormI;

public class UBarChartPanel extends UChartPanel {

	@Override
	public JFreeChart getChartObjectByDataList(List dataList) {
		// TODO Auto-generated method stub
		Calendar calendar = GregorianCalendar.getInstance();
		int year=calendar.get(Calendar.YEAR);
		
		JFreeChart chart = ChartFactory.createBarChart(getTitle(),// 标题
			    "X轴",// x轴
			    "Y轴",// y轴
			    getDataSet(dataList),// 数据
			    PlotOrientation.VERTICAL,// 定位，VERTICAL：垂直
			    false,// 是否显示图例注释(对于简单的柱状图必须是false)
			    false,// 是否生成工具
			    false);// 是否生成URL链接
		 // 周围的背景色
		  chart.setBackgroundPaint(Color.white);
		  // 设置字体，否则会显示乱码
		  Font font = new Font("宋体", 10, 20);
		  TextTitle title = chart.getTitle();
		  // 设置标题字体
		  title.setFont(font);
		  // 得到一个参考
		  CategoryPlot plot = (CategoryPlot) chart.getPlot();
		  // 生成图片的背景色
		  plot.setBackgroundPaint(Color.white);
		  // 行线的颜色
		  plot.setRangeGridlinePaint(Color.BLACK);
		  // 刻度字体
		  plot.getDomainAxis().setTickLabelFont(font);
		  // X轴名称字体
		  plot.getDomainAxis().setLabelFont(font);

		  // LayeredBarRenderer lbr = new LayeredBarRenderer();//(BarRenderer)类：
		  // //void setSeriesBarWidth(int series,double width)
		  // 设定每个分类的宽度（注意设置不要使某分类被覆盖）
		  // lbr.setSeriesBarWidth(1,0.1);

		  // 设置显示整数
		  NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();

		  rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		  // 设置上部空白
		  rangeAxis.setUpperMargin(0.15);
		  // 设置y轴名称字体
		  rangeAxis.setLabelFont(font);

		  CategoryItemRenderer renderer = plot.getRenderer();
		  renderer
		    .setItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		  // renderer.setDrawOutlines(true);//是否折线数据点根据不同数据使用不同的形状
		  // renderer.setSeriesShapesVisible(0, true);
		  renderer.setSeriesItemLabelsVisible(0, Boolean.TRUE);

		  CategoryAxis domainAxis = plot.getDomainAxis();
		  domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);// 倾斜45度

		  BarRenderer renderer1 = new BarRenderer();// 设置柱子的相关属性
		  // 设置柱子宽度
		  // renderer1.setMaximumBarWidth(0.9);
		  // renderer1.setMaximumBarWidth(0.10000000000000001D); //宽度
		  // 设置柱子高度
		  renderer1.setMinimumBarLength(0.5);
		  // 设置柱子边框颜色
		  // renderer1.setBaseOutlinePaint(Color.BLACK);
		  // 设置柱子边框可见
		  // renderer1.setDrawBarOutline(true);
		  // 设置每个地区所包含的平行柱的之间距离，数值越大则间隔越大，图片大小一定的情况下会影响柱子的宽度，可以为负数
		  renderer1.setItemMargin(0.1);
		  // 是否显示阴影
		  renderer1.setShadowVisible(false);
		  // 阴影颜色
		  // renderer1.setShadowPaint(Color.white);
		  plot.setRenderer(renderer1);
		  plot.setBackgroundAlpha((float) 0.5); // 数据区的背景透明度（0.0～1.0）
		  // 设置柱的透明度
		  // plot.setForegroundAlpha(1.0f);
		  // 设置图形的宽度
		  CategoryAxis caxis = plot.getDomainAxis();
		  // 设置图形右边的空白
		  // caxis.setUpperMargin(0.2);
		  // 设置左边的空白
		  // caxis.setLowerMargin(0.2);
		
		// 将柱状图显示在图像界面上
		  return chart;
	}
	
	public  CategoryDataset  getDataSet(List dataList) {
		  DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(int i = 0; i < dataList.size();i++) {
			UCharDataFormI form = (UCharDataFormI) dataList
					.get(i);
			  dataset.addValue(form.getValue(), form.getRowKey(), form.getColKey());
		}
	  return dataset;
	}
	public Dimension getCanvasSize(){
		return new Dimension(1000,800);
	}

}
