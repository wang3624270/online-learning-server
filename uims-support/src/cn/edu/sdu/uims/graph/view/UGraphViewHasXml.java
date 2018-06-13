package cn.edu.sdu.uims.graph.view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import cn.edu.sdu.uims.base.UScrollPane;
import cn.edu.sdu.uims.component.USplitPane;
import cn.edu.sdu.uims.component.UTextArea;
import cn.edu.sdu.uims.constant.UConstants;
import cn.edu.sdu.uims.constant.UMethodConstants;
import cn.edu.sdu.uims.graph.model.GraphModelI;
import cn.edu.sdu.uims.trans.UMatrix;

public class UGraphViewHasXml extends UGraphView {

	private UTextArea g2dModelTextArea = new UTextArea();
	protected USplitPane splitPane;
	public void initView(){
		Dimension d = new Dimension(1,1);
		view = new UViewport(this);
		view.initContent();
		mt = new UMatrix();
		splitPane = new USplitPane(USplitPane.VERTICAL_SPLIT);
		splitPane.setDividerLocation(100);
		splitPane.setDividerSize(UConstants.SPLIT_WIDTH);
		scrollPane = new UScrollPane(view);
		splitPane.setTopComponent(g2dModelTextArea.getAWTComponent());
		splitPane.setBottomComponent(scrollPane);
		g2dModelTextArea.setPreferredSize(d);
		scrollPane.setPreferredSize(d);
		setLayout(new BorderLayout());
		add(splitPane,BorderLayout.CENTER);
		setPreferredSize(d);
	}
	public Object getData() {
		return g2dModelTextArea.getData();
	}

	public void setData(Object obj) {
		if (obj != null) {
			if (obj instanceof GraphModelI) {
				currentGraph = (GraphModelI) obj;
				resetWindowViewPort();
				scrollPane.invalidate();
				g2dModelTextArea.setData(UMethodConstants.formatXmlContent((String)currentGraph.getGraphData()));
			}
		}
	}

	public UTextArea getG2dModelTextArea() {
		return g2dModelTextArea;
	}

	public void setG2dModelTextArea(UTextArea g2dModelTextArea) {
		this.g2dModelTextArea = g2dModelTextArea;
	}
	public void resetLastContent(){
//		Dimension size = scrollPane.getSize();
//		size = scrollPane.getPreferredSize();
//		size = new Dimension(400,400);
//		scrollPane.setMinimumSize(size);
//		scrollPane.setMaximumSize(size);
	}

}
