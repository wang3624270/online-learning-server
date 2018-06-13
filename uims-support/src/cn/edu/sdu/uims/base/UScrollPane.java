package cn.edu.sdu.uims.base;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JScrollPane;

import cn.edu.sdu.uims.constant.UConstants;

public class UScrollPane extends JScrollPane {
	private boolean canScrolling = true;
	public UScrollPane() {
		super();
		// TODO Auto-generated constructor stub
		getVerticalScrollBar().setPreferredSize(
				new Dimension(UConstants.SCROLL_WIDTH, UConstants.SCROLL_WIDTH));
		getHorizontalScrollBar().setPreferredSize(
				new Dimension(UConstants.SCROLL_WIDTH, UConstants.SCROLL_WIDTH));
		setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	}

	public UScrollPane(Component view, int vsbPolicy, int hsbPolicy) {
		super(view, vsbPolicy, hsbPolicy);
		// TODO Auto-generated constructor stub
		getVerticalScrollBar().setPreferredSize(
				new Dimension(UConstants.SCROLL_WIDTH, UConstants.SCROLL_WIDTH));
		getHorizontalScrollBar().setPreferredSize(
				new Dimension(UConstants.SCROLL_WIDTH, UConstants.SCROLL_WIDTH));
	}

	public UScrollPane(Component view) {
		super(view);
		// TODO Auto-generated constructor stub
		getVerticalScrollBar().setPreferredSize(
				new Dimension(UConstants.SCROLL_WIDTH, UConstants.SCROLL_WIDTH));
		getHorizontalScrollBar().setPreferredSize(
				new Dimension(UConstants.SCROLL_WIDTH, UConstants.SCROLL_WIDTH));
	}

	public UScrollPane(int vsbPolicy, int hsbPolicy) {
		super(vsbPolicy, hsbPolicy); 
		// TODO Auto-generated constructor stub
		getVerticalScrollBar().setPreferredSize(
				new Dimension(UConstants.SCROLL_WIDTH, UConstants.SCROLL_WIDTH));
		getHorizontalScrollBar().setPreferredSize(
				new Dimension(UConstants.SCROLL_WIDTH, UConstants.SCROLL_WIDTH));
	}

}
