package cn.edu.sdu.uims.component.event;

public interface GraphInteractionListener extends UEventListener {
	public void commandSelected(GraphInteractionEvent e);
	public void itemStatusChanged(GraphInteractionEvent e);
	public void checkSatatusSelected(GraphInteractionEvent e );

}
