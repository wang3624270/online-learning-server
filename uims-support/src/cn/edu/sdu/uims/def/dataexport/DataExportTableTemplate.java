package cn.edu.sdu.uims.def.dataexport;

import org.dom4j.Element;

import cn.edu.sdu.uims.def.BaseTemplate;
import cn.edu.sdu.uims.util.UimsUtils;

public class DataExportTableTemplate extends BaseTemplate {
	private String anonym;
	private String clause;
	
	public String getClause() {
		return clause;
	}
	public void setClause(String clause) {
		this.clause = clause;
	}
	public String getAnonym() {
		return anonym;
	}
	public void setAnonym(String anonym) {
		this.anonym = anonym;
	}
	public void getAttribute(Element e) {
		super.getAttribute(e);
		anonym = e.attributeValue("anonym");
		clause = UimsUtils.getMultiRowString(e, "clause");
	}
	
}
