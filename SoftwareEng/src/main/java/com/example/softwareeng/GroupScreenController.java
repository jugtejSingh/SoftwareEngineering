package com.example.softwareeng;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class GroupScreenController implements Initializable {
    @FXML
    VBox vboxForTasks;

    @FXML
    ScrollPane scrollPaneTasks;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scrollPaneTasks.setFitToWidth(true);
        for (int i = 0; i < 50; i++){
            String x = Integer.toString(i);
             Label label = new Label(x);
            label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    System.out.println("Hello" + x);
                }
            });
            vboxForTasks.getChildren().add(label);
        }
    }
}
