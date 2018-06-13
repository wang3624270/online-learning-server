package cn.edu.sdu.uims.graph.view;

import java.awt.Container;
import java.awt.event.MouseEvent;

import cn.edu.sdu.uims.component.event.EventConstants;
import cn.edu.sdu.uims.component.event.UGraphRegionListener;
import cn.edu.sdu.uims.graph.interaction.UGraphInteractionPanel;



public class UGraphViewRegion extends UGraphView {

	private UGraphInteractionPanel graphFrame = null;
	
	private UGraphRegionListener graphRegionListener;
	private String dataFormMember;

	public UGraphInteractionPanel getGraphFrame() {
		// TODO Auto-generated method stub
		return graphFrame;
	}
	public void setGraphFrame(UGraphInteractionPanel frame){
		graphFrame = frame;
	}
	public void initGraphContent() {
		// TODO Auto-generated method stub
		initContents();
	}

	public void addGraphRegionListener(UGraphRegionListener l) {
		// TODO Auto-generated method stub
		graphRegionListener = l;
	}
	public void processHcimsEvent(MouseEvent e,String evtType,String evtCmd){
		if(EventConstants.EVENT_MOUSE.equals(evtType)){
			if(EventConstants.CMD_CLICKED.equals(evtCmd)){
				graphRegionListener.mouseClicked(e);
			}
		}else if(EventConstants.EVENT_MOUSEMOTION.equals(evtType)){
			if(EventConstants.CMD_DRAGGED.equals(evtCmd)){
				graphRegionListener.mouseDragged(e);
			}else if(EventConstants.CMD_MOVED.equals(evtCmd)){
				graphRegionListener.mouseMoved(e);
			}
		}
	}

	public String getDataFormMember() {
		// TODO Auto-generated method stub
		return dataFormMember;
	}

	public void setDataFormMember(String dataFormMember) {
		// TODO Auto-generated method stub
		this.dataFormMember = dataFormMember;
	}
	
	public void setContainer(Container container) {
		// TODO Auto-generated method stub
		
	}
}
