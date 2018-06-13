package cn.edu.sdu.uims.graph.model;

import cn.edu.sdu.uims.graph.view.ViewParameter;

public interface GElementI {
	String getId();
	Object getDataObject();
	void setDataObject(Object dataObject);
	Object getJSonObject(ViewParameter ViewParameter);
}
