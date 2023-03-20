package com.example.softwareeng;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CreateTaskController implements Initializable {
    @FXML
    TextField taskName;
    @FXML
    ChoiceBox<String> choiceBox;
    @FXML
    TextField timeEstimate;
    static int groupSelectedID = 0;
    String usersName = "";
    int gettingEstimate = 0;

    CreateTaskDB db = new CreateTaskDB();
//Initializer for data in the choice boxes
    void AddingUsersToChoice(){
        ArrayList<Users> users = new ArrayList<>(db.GetUsers());
        for (int i = 0; i < users.size(); i++) {
            Users newUser = users.get(i);
            choiceBox.setValue(users.get(0).getUserName());
            choiceBox.getItems().add(newUser.getUserName());
        }
    }
    //Make the groupID static everytime they click it, send it to the controller class
    @FXML
    //Event handler for the button
    void addingTasksAndEstimates(){
        gettingEstimate = Integer.parseInt(timeEstimate.getText());
        String tasksName = taskName.getText();
        int groupID = groupSelectedID;
        System.out.println(gettingEstimate + " "+ groupID);
        db.InsertIntoTask(groupSelectedID,tasksName,choiceBox.getValue(),gettingEstimate);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeEstimate.setText("0");
        AddingUsersToChoice();
        CreateTaskDB createTaskDB = new CreateTaskDB();
        choiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                String nameForEstimate = choiceBox.getItems().get((Integer)t1);
                int EstimatedTime = createTaskDB.GetTimeEstimates(nameForEstimate);
                timeEstimate.setText(Integer.toString(EstimatedTime));
            }
        });
    }
}
