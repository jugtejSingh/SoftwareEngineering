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
import java.util.HashMap;
import java.util.ResourceBundle;

public class CreateTaskController implements Initializable {
    @FXML
    TextField taskName;
    @FXML
    ChoiceBox<String> choiceBox;
    @FXML
    TextField timeEstimate;
    static int groupSelectedID = 0;
    CreateTaskDB db = new CreateTaskDB();
    ArrayList<Integer> listForEstimates = new ArrayList<Integer>();
    HashMap<String,Integer> mapForChoiceBox;
    int lastNumberForHashMap = 0;
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
        mapForChoiceBox.put(choiceBox.getItems().get((Integer)lastNumberForHashMap), Integer.parseInt(timeEstimate.getText()));
        String tasksName = taskName.getText();
        int taskID = db.InsertIntoTask(groupSelectedID, tasksName);
        for (String names: mapForChoiceBox.keySet()){
            int userID = db.gettingUserIds(names);
            db.InsertIntoTaskAndUsers(userID,groupSelectedID, mapForChoiceBox.get(names), taskID);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mapForChoiceBox = new HashMap<String,Integer>();
        ArrayList<String> arraylistOfNames = new ArrayList<>(db.GetUserNames());
        for (String x: arraylistOfNames) {
            mapForChoiceBox.put(x,0);
            System.out.println(mapForChoiceBox.get(x));
        }
        timeEstimate.setText("0");
        AddingUsersToChoice();
        choiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                    mapForChoiceBox.put(choiceBox.getItems().get((Integer)number), Integer.parseInt(timeEstimate.getText()));
                timeEstimate.setText(String.valueOf(mapForChoiceBox.get(choiceBox.getItems().get((Integer)t1))));
                lastNumberForHashMap = (int) t1;
                }
            }
        );
        for (String x: arraylistOfNames) {
            System.out.println(mapForChoiceBox.get(x));
        }
    }
}
