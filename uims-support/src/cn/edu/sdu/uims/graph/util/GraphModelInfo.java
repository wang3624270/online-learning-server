package cn.edu.sdu.uims.graph.util;

import java.util.Date;

import cn.edu.sdu.uims.graph.model.GraphModelI;

public class GraphModelInfo {
	private Integer graphId;
	private GraphModelI  graphModel;
	private Date timeStamep;
	public Integer getGraphId() {
		return graphId;
	}
	public void setGraphId(Integer graphId) {
		this.graphId = graphId;
	}
	public GraphModelI getGraphModel() {
		return graphModel;
	}
	public void setGraphModel(GraphModelI graphModel) {
		this.graphModel = graphModel;
	}
	public Date getTimeStamep() {
		return timeStamep;
	}
	public void setTimeStamep(Date timeStamep) {
		this.timeStamep = timeStamep;
	}
	
}
