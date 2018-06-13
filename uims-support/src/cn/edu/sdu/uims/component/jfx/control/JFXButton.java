package cn.edu.sdu.uims.component.jfx.control;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class JFXButton extends JFXControl{
    private static final Color color = Color.web("#464646");
    Button button3 = new Button("Decline");
    DropShadow shadow = new DropShadow();
    Label label = new Label();
	
		public void  initControl(){
			
	        label.setFont(Font.font("Times New Roman", 22));
	        label.setTextFill(color);

	        Image imageDecline = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("jfx/not.png"));
	        Image imageAccept = new Image(Thread.currentThread().getContextClassLoader().getResourceAsStream("jfx/ok.png"));

	        VBox vbox = new VBox();
	        vbox.setLayoutX(20);
	        vbox.setLayoutY(20);
	        HBox hbox1 = new HBox();
	        HBox hbox2 = new HBox();

	        Button button1 = new Button("Accept", new ImageView(imageAccept));
	        button1.setStyle("-fx-font: 22 arial; -fx-base: #b6e7c9;");
	        button1.setOnAction(new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	                label.setText("Accepted");
	            }
	        });


	        Button button2 = new Button("Accept");
	        button2.setOnAction(new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	                label.setText("Accepted");
	            }
	        });

	        button3.setOnAction(new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	                 label.setText("Declined");
	            }
	        });

	        button3.addEventHandler(MouseEvent.MOUSE_ENTERED,
	                new EventHandler<MouseEvent>() {
	            @Override public void handle(MouseEvent e) {
	                button3.setEffect(shadow);
	            }
	        });

	        button3.addEventHandler(MouseEvent.MOUSE_EXITED,
	                new EventHandler<MouseEvent>() {
	            @Override public void handle(MouseEvent e) {
	                button3.setEffect(null);
	            }
	        });


	        hbox1.getChildren().add(button2);
	        hbox1.getChildren().add(button3);
	        hbox1.getChildren().add(label);
	        hbox1.setSpacing(10);
	        hbox1.setAlignment(Pos.BOTTOM_CENTER);

	        Button button4 = new Button();
	        button4.setGraphic(new ImageView(imageAccept));
	        button4.setOnAction(new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	                label.setText("Accepted");
	            }
	        });


	        Button button5 = new Button();
	        button5.setGraphic(new ImageView(imageDecline));
	        button5.setOnAction(new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	                label.setText("Declined");
	            }
	        });

	        hbox2.getChildren().add(button4);
	        hbox2.getChildren().add(button5);
	        hbox2.setSpacing(25);

	        vbox.getChildren().add(button1);
	        vbox.getChildren().add(hbox1);
	        vbox.getChildren().add(hbox2);
	        vbox.setSpacing(10);
	        ((Group)scene.getRoot()).getChildren().add(vbox);
		}

}
