package cn.edu.sdu.uims.graph.view;

import java.awt.BorderLayout;

import cn.edu.sdu.uims.component.label.ULabelMultiLine;
import cn.edu.sdu.uims.def.UElementTemplate;
import cn.edu.sdu.uims.def.UGraphViewTemplate;
import cn.edu.sdu.uims.def.ULabelMultiLineTemplate;
import cn.edu.sdu.uims.graph.form.GraphViewFormI;
import cn.edu.sdu.uims.graph.model.GraphModelI;
import cn.edu.sdu.uims.trans.UFPoint;

public class UGraphView extends UGraphViewSuper {
	protected String graphInfo = ""; 
	protected GraphModelI currentGraph = null;
	private ULabelMultiLine infoLabel = null;
	private UGraphViewTemplate graphViewTemplate;
	private boolean graphShiftAction = false;
	private UFPoint graphShiftPoint0, graphShiftPoint1;
	public UGraphView() {
		super();
		canSelectObject = true;
	}
	
	public GraphModelI getCurrentGraph() {
		return currentGraph;
	}

	public void setCurrentGraph(GraphModelI graphModelI) {
		this.currentGraph = graphModelI;
	}
	
	public void updateInfoView(){
		
	}
	
	public void setElementTemplate(UElementTemplate elementTemplate) {
		// TODO Auto-generated method stub
		this.elementTemplate = elementTemplate;
		this.graphViewTemplate = (UGraphViewTemplate)elementTemplate;
	}
	
	public void addInfoPanel(){
		if(graphViewTemplate == null)
			return;
		if(graphViewTemplate.infoLayout == null)
			return;
		ULabelMultiLineTemplate lTemplate = new ULabelMultiLineTemplate();
		lTemplate.charMax = graphViewTemplate.infoCharMax;
		lTemplate.fixHeight = false;
		infoLabel = new ULabelMultiLine();
		infoLabel.setTemplate(lTemplate);
		infoLabel.initContents();		
		if(graphViewTemplate.infoLayout.equals("left")) {
			add(infoLabel.getAWTComponent(),BorderLayout.WEST);
		}else if(graphViewTemplate.infoLayout.equals("right")) {
			add(infoLabel.getAWTComponent(),BorderLayout.EAST);
		}else if(graphViewTemplate.infoLayout.equals("top")) {
			add(infoLabel.getAWTComponent(),BorderLayout.NORTH);
		}else if(graphViewTemplate.infoLayout.equals("bottom")) {
			add(infoLabel.getAWTComponent(),BorderLayout.SOUTH);
		}
	}
	public void updateInfoPanel(){
		if(infoLabel == null)
			return;
		if(graphInfo == null)
			infoLabel.setData("楼层信息");
		else
			infoLabel.setData(graphInfo);
	}
	
	public void setData(Object obj) {
		// TODO Auto-generated method stub
		if (obj == null) {
			currentGraph = null;
			graphInfo = null;
		}else if( obj instanceof GraphModelI) {
			currentGraph = (GraphModelI) obj;
			graphInfo = null;
		}else if(obj instanceof GraphViewFormI){
			GraphViewFormI f = (GraphViewFormI)obj;
			currentGraph = f.getCurrentGraphObject();
			graphInfo = f.getGraphInfo();
		}
		updateInfoPanel();
		resetWindowViewPort();
	}


	public boolean isGraphShiftAction() {
		return graphShiftAction;
	}

	public void setGraphShiftAction(boolean graphShiftAction) {
		this.graphShiftAction = graphShiftAction;
	}


}
