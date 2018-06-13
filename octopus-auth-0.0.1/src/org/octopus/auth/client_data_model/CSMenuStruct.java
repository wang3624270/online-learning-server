package org.octopus.auth.client_data_model;

import org.sdu.rmi.ReturnToClientStruct;
 

public class CSMenuStruct extends ReturnToClientStruct {
	private Object rootMenu;

	public Object getRootMenu() {
		return rootMenu;
	}

	public void setRootMenu(Object rootMenu) {
		this.rootMenu = rootMenu;
	}

}