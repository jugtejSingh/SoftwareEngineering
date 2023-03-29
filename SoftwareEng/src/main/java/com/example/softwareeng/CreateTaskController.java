package com.example.softwareeng;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import java.net.URL;
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
    int lastNumberOfChoiceBox = 0;
    CreateTaskDB db = new CreateTaskDB();
    ArrayList<Integer> listForEstimates = new ArrayList  <Integer>();
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
    void addingTasksAndEstimates() {
        Integer gettingLastEstimate = Integer.parseInt(timeEstimate.getText());
        listForEstimates.add(lastNumberOfChoiceBox,gettingLastEstimate);
        System.out.println(lastNumberOfChoiceBox);
        System.out.println(gettingLastEstimate);


        String tasksName = taskName.getText();
        int taskID = db.InsertIntoTask(groupSelectedID, tasksName);
        ArrayList<Integer> userID = db.gettingUserIds();
        db.InsertIntoTaskAndUsers(userID,groupSelectedID, listForEstimates, taskID);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeEstimate.setText("0");
        AddingUsersToChoice();
        CreateTaskDB createTaskDB = new CreateTaskDB();
        int userCount = createTaskDB.GetMaxUserCount();
        for (int i = 0; i < userCount; i++){
            listForEstimates.add(i,0);
        }
        choiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                listForEstimates.add((int) number, Integer.parseInt(timeEstimate.getText()));
                lastNumberOfChoiceBox = (int) t1;
                //The last value isnt being added
                int EstimatedTime = listForEstimates.get((int) t1);
                timeEstimate.setText(Integer.toString(EstimatedTime));
            }
        });
    }
}
