package cn.edu.sdu.uims.def;

import org.dom4j.Element;

import cn.edu.sdu.common.reportdog.UTemplate;

public class UListSectionTemplat extends UTemplate {
		public String dispName;
		public String filterCode;
		public void getAttribute(Element e) {
			dispName= e.attributeValue("dispName");
			filterCode = e.attributeValue("filterCode");
		}
}
