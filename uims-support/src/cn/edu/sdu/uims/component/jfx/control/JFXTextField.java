package cn.edu.sdu.uims.component.jfx.control;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class JFXTextField extends JFXControl{
	public void  initControl(){
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(5);
        grid.setHgap(5);

        scene.setRoot(grid);

        final TextField name = new TextField();
        name.setPromptText("Enter your first name.");
        name.setPrefColumnCount(10);
        name.getText();
        GridPane.setConstraints(name, 0, 0);
        grid.getChildren().add(name);

        final TextField lastName = new TextField();
        lastName.setPromptText("Enter your last name.");
        GridPane.setConstraints(lastName, 0, 1);
        grid.getChildren().add(lastName);

        final TextField comment = new TextField();
        comment.setPrefColumnCount(15);
        comment.setPromptText("Enter your comment.");
        GridPane.setConstraints(comment, 0, 2);
        grid.getChildren().add(comment);

        Button submit = new Button("Submit");
        GridPane.setConstraints(submit, 1, 0);
        grid.getChildren().add(submit);

        Button clear = new Button("Clear");
        GridPane.setConstraints(clear, 1, 1);
        grid.getChildren().add(clear);

        final Label label = new Label();
        GridPane.setConstraints(label, 0, 3);
        GridPane.setColumnSpan(label, 2);
        grid.getChildren().add(label);

        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (
                    (comment.getText() != null && !comment.getText().isEmpty())
                ) {
                    label.setText(name.getText() + " " +
                        lastName.getText() + ", "
                        + "thank you for your comment!");
                } else {
                    label.setText("You have not left a comment.");
                }
            }
        });

        clear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                name.clear();
                lastName.clear();
                comment.clear();
                label.setText(null);
            }
        });

	}

}
