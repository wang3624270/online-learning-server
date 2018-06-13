package cn.edu.sdu.uims.component.panel;


public class UFPanelContent extends SuperPanel{
	private UFPanel owner;

	public void setOwner(UFPanel owner) {
		this.owner = owner;
	}

	public UFPanel getOwner() {
		return owner;
	}

	public String[] getToolActions() {
		return owner.getToolActions();
	}

	public void setToolActions(String[] acts) {
	}

	public void onClose(){
		if(owner != null)
			owner.onClose();
	}	
	public void setShellBounds(int x, int y, int w, int h) {
		// TODO Auto-generated method stub
	}
	public void doBeforePanelClosed() {
		// TODO Auto-generated method stub
		if(owner != null)
			owner.doBeforePanelClosed();
	}
	
}

