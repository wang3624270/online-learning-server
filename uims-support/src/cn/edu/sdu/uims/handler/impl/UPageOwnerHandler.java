package cn.edu.sdu.uims.handler.impl;

public class UPageOwnerHandler extends UToolHandler{
	public void initAddedData(){
		initAllAddedData();
	}
/*	
	public void doExport(){
		UPanelI p = this.component.getCurrentDisplayPagePanel(); 
		if(p != null){
			UHandlerI h = p.getHandler();
			if(h!= null || h instanceof UToolHandler) {
				((UToolHandler)h).doExport();
			}
		}
	}
*/	
}
