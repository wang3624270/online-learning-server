package cn.edu.sdu.uims.component.jfx.control;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class JFXHtmlEditor extends JFXControl{
	 private final String INITIAL_TEXT = "Lorem ipsum dolor sit "
	            + "amet, consectetur adipiscing elit. Nam tortor felis, pulvinar "
	            + "in scelerisque cursus, pulvinar at ante. Nulla consequat"
	            + "congue lectus in sodales. Nullam eu est a felis ornare "
	            + "bibendum et nec tellus. Vivamus non metus tempus augue auctor "
	            + "ornare. Duis pulvinar justo ac purus adipiscing pulvinar. "
	            + "Integer congue faucibus dapibus. Integer id nisl ut elit "
	            + "aliquam sagittis gravida eu dolor. Etiam sit amet ipsum "
	            + "sem.";
	public void  initControl(){
        VBox root = new VBox();     
        root.setPadding(new Insets(8, 8, 8, 8));
        root.setSpacing(5);
        root.setAlignment(Pos.BOTTOM_LEFT);
 
        final HTMLEditor htmlEditor = new HTMLEditor();
        htmlEditor.setPrefHeight(245);
        htmlEditor.setHtmlText(INITIAL_TEXT);
        //htmlEditor.setHtmlText("<<p>Text</p><img src='blog.png'/>");
        
        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();
        
     
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.getStyleClass().add("noborder-scroll-pane");
        scrollPane.setStyle("-fx-background-color: white");
        scrollPane.setContent(browser);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(180);
 
        Button showHTMLButton = new Button("Load Content in Browser");
        root.setAlignment(Pos.CENTER);
        showHTMLButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent arg0) {                
                webEngine.loadContent(htmlEditor.getHtmlText());
            }
        });
        
        root.getChildren().addAll(htmlEditor, showHTMLButton, scrollPane);
    	((Group)scene.getRoot()).getChildren().add(root);

	}

}
