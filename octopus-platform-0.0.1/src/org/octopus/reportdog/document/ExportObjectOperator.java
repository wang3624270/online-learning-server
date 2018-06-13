package org.octopus.reportdog.document;

import java.util.HashMap;

public class ExportObjectOperator {
	private String docName;// 文档名
	private Object docTarget;// 文档对象

	private Object docOperator;
	public HashMap paraMap = new HashMap();// 相关参数

	public ExportObjectOperator() {
		paraMap.put("paperType", "A4");
		paraMap.put("isOrientation", "false");
	}

	public void setDocTarget(Object docTarget) {
		this.docTarget = docTarget;
	}

	public Object getDocTarget() {
		return this.docTarget;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocOperator(Object docOperator) {
		this.docOperator = docOperator;
	}

	public Object getDocOperator() {
		return docOperator;
	}

}