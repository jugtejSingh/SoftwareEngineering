package com.example.softwareeng;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    Button button;
    @FXML
    VBox vbox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (int i = 0;i <10; i++){
            String p = Integer.toString(i);
            button = new Button();
            button.setText(p);
            vbox.getChildren().add(button);
        }
    }
}