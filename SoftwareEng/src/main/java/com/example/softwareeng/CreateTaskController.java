package com.example.softwareeng;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CreateTaskController {
    @FXML
    TextField firstName;
    @FXML
    TextField lastName;
    GroupDB group = new GroupDB();
    @FXML
    public void enteringFirstLast(Event e){
        String first = firstName.getText();
        String last = lastName.getText();
        group.AddStudent(first,last);
        System.out.println("The values inputted were " + first + " " + last + "");
    }
}
