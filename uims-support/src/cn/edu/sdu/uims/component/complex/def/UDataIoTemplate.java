package cn.edu.sdu.uims.component.complex.def;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Element;

import cn.edu.sdu.uims.def.DataIoStruct;
import cn.edu.sdu.uims.def.UElementTemplate;

public class UDataIoTemplate extends UElementTemplate{
	public List <DataIoStruct> modelList;
	public String title;
	public void getSelfAttribute(Element e1){
		name = e1.attributeValue("name");
		title =  e1.attributeValue("title");
		Iterator it = e1.elementIterator("model");
		Element e2;
		DataIoStruct io;
		String str;
		while(it.hasNext()) {
			e2 = (Element)it.next();
			if(modelList == null) {
				modelList = new ArrayList<DataIoStruct>();
			}
			io = new DataIoStruct();
			io.setLabel(e2.attributeValue("label"));
			io.setModelName(e2.attributeValue("name"));
			io.setIoType(e2.attributeValue("ioType"));
			io.setSuffix(e2.attributeValue("suffix"));
			io.setParas(e2.attributeValue("paras"));
			modelList.add(io);			
		}
	}	
	
}
