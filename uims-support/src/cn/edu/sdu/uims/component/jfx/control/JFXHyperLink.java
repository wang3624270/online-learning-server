package cn.edu.sdu.uims.component.jfx.control;

import cn.edu.sdu.uims.util.UimsUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Hyperlink;

public class JFXHyperLink extends JFXControl {
	public void initControl() {
		Hyperlink link = new Hyperlink();
		link.setText(elementTemplate.text);
		link.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				openUrl();
			}
		});
		((Group) scene.getRoot()).getChildren().add(link);
		control = link;
	}
	public void setData(Object o){
		Hyperlink link = (Hyperlink) control;		
		if(o == null) {
			link.setText("");
		}else {
			link.setText(link.toString());
		}
	}
	public Object getData() {
		Hyperlink link = (Hyperlink) control;		
		return link.getText();
	}
	public void openUrl() {
		Hyperlink link = (Hyperlink) control;
		UimsUtils.openURl(link.getText());
	}
	
}
