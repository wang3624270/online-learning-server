package cn.edu.sdu.uims.graph.handler;

import java.util.HashMap;

import cn.edu.sdu.uims.graph.form.GraphViewForm;
import cn.edu.sdu.uims.service.UFactory;

public class GraphViewHandler extends GraphHandler {
	public void initFormData() {
		HashMap map = this.getParameters();
		if (map == null)
			return;
		String name;
		name = (String) map.get("name");
		GraphViewForm form = (GraphViewForm) dataForm;
		if (form == null)
			return;
		form.setCurrentGraphObject(UFactory.getModelSession().getGraphModel2DObject(name));
	}
	public void processGraphInteractionEvent(Object o, String cmd){
		System.out.println(o.toString() + cmd);
	}

}
