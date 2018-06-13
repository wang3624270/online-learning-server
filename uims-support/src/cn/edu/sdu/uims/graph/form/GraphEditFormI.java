package cn.edu.sdu.uims.graph.form;

import cn.edu.sdu.uims.graph.model.GraphModelI;

public interface GraphEditFormI extends GraphViewFormI {
	void setModify(boolean b);
	boolean isModify();
	void setCurrentGraphObject(GraphModelI graph);
}
