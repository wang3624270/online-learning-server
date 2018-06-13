package cn.edu.sdu.uims.service;


public class UFactory {

	private int uimsType = 0;
	static private UModelSessionI modelSessionI = null;
	static private UModelSessionI serverModelSessionI = null;
	static private UHandlerSessionI handlerSessionI = null;
	private UFactory(){
	}
	public int getUimsType() {
		return uimsType;
	}
	public void setUimsType(int uimsType) {
		this.uimsType = uimsType;
	}
	public static UModelSessionI getModelSession() {
		return modelSessionI;
	}
	public static void setModelSession(UModelSessionI modelSessionI) {
		UFactory.modelSessionI = modelSessionI;
	}
	public static void setServerModelSession(UModelSessionI modelSessionI) {
		UFactory.serverModelSessionI = modelSessionI;
	}
	public static UModelSessionI getServerModelSession() {
		return serverModelSessionI;
	}

	public static UHandlerSessionI getHandlerSession() {
		if(handlerSessionI == null)
			handlerSessionI = new UHandlerSession();
		return handlerSessionI;
	}
	public static void setHandlerSession(UHandlerSessionI handlerSessionI) {
		UFactory.handlerSessionI = handlerSessionI;
	}

}