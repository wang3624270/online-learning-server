package cn.edu.sdu.uims.graph.filter;

import cn.edu.sdu.uims.filter.UFilter;

public class GraphFilter  extends UFilter{
	private	int left, right, top, bottom;
	private int ySpace;
	public GraphFilter(){
		left = 50;
		right = 50;
		top = 50;
		bottom = 50;
		ySpace = 40;
	}

public void init(String parameter){
	}
}
