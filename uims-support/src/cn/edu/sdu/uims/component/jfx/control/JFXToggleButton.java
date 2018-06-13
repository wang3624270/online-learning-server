package cn.edu.sdu.uims.component.jfx.control;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class JFXToggleButton extends JFXControl{
	   ImageView image = new ImageView();
	   Rectangle rect = new Rectangle(145, 50);
	   private static final Label label = new Label ("Priority:");

	public void  initControl(){
        rect.setFill(Color.WHITE);
        rect.setStroke(Color.DARKGRAY);
        rect.setStrokeWidth(2);

        final ToggleGroup group = new ToggleGroup();

        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov,
                Toggle toggle, Toggle new_toggle) {
                    if (new_toggle == null)
                        rect.setFill(Color.WHITE);
                    else
                        rect.setFill(
                            (Color) group.getSelectedToggle().getUserData()
                        );
            }
        });

        ToggleButton tb1 = new ToggleButton("Minor");
        tb1.setToggleGroup(group);
        tb1.setUserData(Color.LIGHTGREEN);
        tb1.setSelected(true);
        tb1.setStyle("-fx-base: lightgreen;");

        ToggleButton tb2 = new ToggleButton("Major");
        tb2.setToggleGroup(group);
        tb2.setUserData(Color.LIGHTBLUE);
        tb2.setStyle("-fx-base: lightblue;");

        ToggleButton tb3 = new ToggleButton("Critical");
        tb3.setToggleGroup(group);
        tb3.setUserData(Color.SALMON);
        tb3.setStyle("-fx-base: salmon;");

        HBox hbox = new HBox();

        hbox.getChildren().add(tb1);
        hbox.getChildren().add(tb2);
        hbox.getChildren().add(tb3);

        rect.setArcHeight(10);
        rect.setArcWidth(10);

        VBox vbox = new VBox();

        vbox.getChildren().add(label);
        vbox.getChildren().add(hbox);
        vbox.getChildren().add(rect);
        vbox.setPadding(new Insets(20, 10, 10, 20));

        ((Group) scene.getRoot()).getChildren().add(vbox);

	}
	

}
