package com.example.softwareeng;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

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
    static int groupSelectedID = 1;
    CreateTaskDB db = new CreateTaskDB();
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
    void addingTasksAndEstimates() throws InterruptedException {
        mapForChoiceBox.put(choiceBox.getItems().get((Integer) lastNumberForHashMap), Integer.parseInt(timeEstimate.getText()));
        boolean wentThrough = false;
            String tasksName = taskName.getText();
            int taskID = db.InsertIntoTask(groupSelectedID, tasksName);
            for (String names : mapForChoiceBox.keySet()) {
                int userID = db.GettingUserIds(names);
                wentThrough = db.InsertIntoTaskAndUsers(userID, groupSelectedID, mapForChoiceBox.get(names), taskID);
            }
        Alert alert;
        if(wentThrough){
            alert = new Alert(Alert.AlertType.INFORMATION, "The user has been added", ButtonType.CLOSE);
        }else{
            alert = new Alert(Alert.AlertType.INFORMATION, "The user could not be added, Try again later.", ButtonType.CLOSE);
        }
        alert.showAndWait();
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
                try{ int estimatedTime = Integer.parseInt(timeEstimate.getText());
                    mapForChoiceBox.put(choiceBox.getItems().get((Integer)number), estimatedTime);
                timeEstimate.setText(String.valueOf(mapForChoiceBox.get(choiceBox.getItems().get((Integer)t1))));
                lastNumberForHashMap = (int) t1;
                }catch(Exception e){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION,"The value added is not an integer", ButtonType.CLOSE);
                    alert.showAndWait();
                }}
            }
        );
        for (String x: arraylistOfNames) {
            System.out.println(mapForChoiceBox.get(x));
        }
    }
}
