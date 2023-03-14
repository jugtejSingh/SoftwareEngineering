package com.example.softwareeng;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class CreateTaskController implements Initializable {
    @FXML
    TextField firstName;
    @FXML
    TextField lastName;
    @FXML
    AnchorPane ParentanchorPane;

    @FXML
    VBox labelVbox;
    GroupDB group = new GroupDB();
    public TextField textField;

    @FXML
    public void enteringFirstLast(Event e) {
        String first = firstName.getText();
        String last = lastName.getText();
        group.AddStudent(first, last);
        System.out.println("The values inputted were " + first + " " + last + "");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GroupDB db = new GroupDB();
        int numberOfRows = db.GetCount();
        for (int i = 0; i < numberOfRows; i++) {
            System.out.println(i);
            String string = Integer.toString(i);
            textField = new TextField(string);
            labelVbox.getChildren().add(textField);
        }
    }
}
