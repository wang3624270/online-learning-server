package cn.edu.sdu.uims.graph;


public interface GraphConstants {
	int GRAPH_SELECT_STATUS_NO = 0;
	int GRAPH_SELECT_STATUS_VECTOR = 1;
	int GRAPH_SELECT_STATUS_BOUND = 2;
	int GRAPH_SELECT_STATUS_INNER = 3;
	int GRAPH_SELECT_STATUS_OUTER = 4;
	int GRAPH_SELECT_STATUS_VECTOR_LEFTTOP = 11; // 表示选中左上角顶点
	int GRAPH_SELECT_STATUS_VECTOR_RIGHTTOP = 12;// 表示选中左下角顶点
	int GRAPH_SELECT_STATUS_VECTOR_LEFTBOTTOM = 13; // 表示选中右上角顶点
	int GRAPH_SELECT_STATUS_VECTOR_RIGHTBOTTOM = 14; // 表示选中右下角顶点

	String GRAPH_TYPE_BASIC = "B";
	String GRAPH_TYPE_GENERAL = "G";
	String GRAPH_TYPE_COMPLICATED = "C";

	String GRAPH_COLOR_RED = "colorRed";
	String GRAPH_COLOR_BLUE = "colorBlue";
	String GRAPH_COLOR_GREEN = "colorGreen";
	String GRAPH_COLOR_BLACK = "colorBlack";
	String GRAPH_COLOR_WRITE = "colorWhite";
	String GRAPH_COLOR_GRAY = "colorGray";
	String GRAPH_COLOR_SELECTION = "colorRed";
	
	
	float MIN_BOUND = 5;
//	List points = new ArrayList(); // 存放原element的点的信息
}
