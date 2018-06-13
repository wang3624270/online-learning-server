package cn.edu.sdu.uims.graph.form;

import cn.edu.sdu.common.form.UForm;

public class GraphHeadForm extends UForm{
	protected Integer graphId;
	protected String graphNum;
	protected String graphName;
	protected String graphType;
	private String xmlContent;
	public Integer getGraphId() {
		return graphId;
	}
	public void setGraphId(Integer graphId) {
		this.graphId = graphId;
	}
	public String getGraphNum() {
		return graphNum;
	}
	public void setGraphNum(String graphNum) {
		this.graphNum = graphNum;
	}
	public String getGraphName() {
		return graphName;
	}
	public void setGraphName(String graphName) {
		this.graphName = graphName;
	}
	public String getGraphType() {
		return graphType;
	}
	public void setGraphType(String graphType) {
		this.graphType = graphType;
	}
	public String toString(){
		return graphName;
	}
	public String getXmlContent() {
		return xmlContent;
	}
	public void setXmlContent(String xmlContent) {
		this.xmlContent = xmlContent;
	}
}
