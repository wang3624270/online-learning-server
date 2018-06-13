package cn.edu.sdu.uims.component.jfx.control;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class JFXRadioButton extends JFXControl{
	
    final ImageView icon = new ImageView();

	public void  initControl(){
		
        final ToggleGroup group = new ToggleGroup();

        RadioButton rb1 = new RadioButton("Home");
        rb1.setToggleGroup(group);
        rb1.setUserData("Home");

        RadioButton rb2 = new RadioButton("Calendar");
        rb2.setToggleGroup(group);
        rb2.setUserData("Calendar");

        RadioButton rb3 = new RadioButton("Contacts");
        rb3.setToggleGroup(group);
        rb3.setUserData("Contacts");

        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
        public void changed(ObservableValue<? extends Toggle> ov,
            Toggle old_toggle, Toggle new_toggle) {
                if (group.getSelectedToggle() != null) {
                    final Image image = new Image(
                    		Thread.currentThread().getContextClassLoader().getResourceAsStream("jfx/"+
                            group.getSelectedToggle().getUserData().toString() + 
                                ".jpg"
                        )
                    );
                icon.setImage(image);
            }                
        }
        });

        HBox hbox = new HBox();
        VBox vbox = new VBox();

        vbox.getChildren().add(rb1);
        vbox.getChildren().add(rb2);
        vbox.getChildren().add(rb3);
        vbox.setSpacing(10);

        hbox.getChildren().add(vbox);
        hbox.getChildren().add(icon);
        hbox.setSpacing(50);
        hbox.setPadding(new Insets(20, 10, 10, 20));

        ((Group) scene.getRoot()).getChildren().add(hbox);

	}

}
