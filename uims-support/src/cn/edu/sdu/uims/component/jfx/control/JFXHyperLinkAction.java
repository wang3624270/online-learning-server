package cn.edu.sdu.uims.component.jfx.control;

import cn.edu.sdu.uims.util.UimsUtils;
import javafx.scene.control.Hyperlink;

public class JFXHyperLinkAction extends JFXHyperLink {

	public void openUrl() {
		Hyperlink link = (Hyperlink) control;
		UimsUtils.openURl(link.getText());
	}

}
