package cn.edu.sdu.uims.component.jfx.control;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

public class JFXChoiceBox extends JFXControl{
    Rectangle rect = new Rectangle(150, 30);
    final Label label = new Label("Hello");

	public void  initControl(){
        label.setFont(Font.font("Arial", 25));
        label.setLayoutX(40);

        final String[] greetings = new String[]{"Hello", "Hola", "Привет", "你好",
            "こんにちは"};
        final ChoiceBox cb = new ChoiceBox(FXCollections.observableArrayList(
            "English", "Español", "Русский", "简体中文", "日本語")
        );
                     
       cb.getSelectionModel().selectedIndexProperty().addListener(new
            ChangeListener<Number>() {
                public void changed(ObservableValue ov,
                    Number value, Number new_value) {
                        label.setText(greetings[new_value.intValue()]);
            }
        });
       
        cb.setTooltip(new Tooltip("Select the language"));
        cb.setValue("English");
        HBox hb = new HBox();
        hb.getChildren().addAll(cb, label);
        hb.setSpacing(30);
        hb.setAlignment(Pos.CENTER);
        hb.setPadding(new Insets(10, 0, 0, 10));

        ((Group) scene.getRoot()).getChildren().add(hb);
       
	}

}
