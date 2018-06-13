package cn.edu.sdu.uims.component.dialog;

import java.util.HashMap;

import javax.swing.JDialog;

public class SuperDialog extends JDialog {

	protected HashMap authcontrol;

	public HashMap getAuthcontrol() {
		return authcontrol;
	}

	public void setAuthcontrol(HashMap authcontrol) {
		this.authcontrol = authcontrol;
	}
}
