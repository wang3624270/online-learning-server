package cn.edu.sdu.uims.graph.form;

import cn.edu.sdu.uims.graph.model.GraphModelI;
import cn.edu.sdu.uims.itms.form.IFormI;

public interface GraphViewFormI extends IFormI{
	public GraphModelI getCurrentGraphObject();
	public String getGraphInfo();
}
