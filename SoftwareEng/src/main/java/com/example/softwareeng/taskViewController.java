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

public class taskViewController implements Initializable {
    public TextField timeEstimate;
    static int groupSelectedID = 1;
    public Button inputTasks;
    GroupTaskUserDB groupDB;
    CreateTaskDB taskDB;
    @FXML
    TextField taskNameField;
    int taskID;
   static String taskName;
   @FXML
   ChoiceBox choiceBox;

    HashMap<String,Integer> mapForChoiceBox;

    int lastNumberForHashMap = 0;

    public taskViewController() {
        groupDB = new GroupTaskUserDB();
        taskDB = new CreateTaskDB();
    }
    String AddingUsersToChoice(){
        ArrayList<Users> users = new ArrayList<>(taskDB.GetUsers());
        Alert alert;
        if(users.size() == 0){
            alert = new Alert(Alert.AlertType.INFORMATION, "There are no users in this group", ButtonType.CLOSE);
        }
        for (int i = 0; i < users.size(); i++) {
            Users newUser = users.get(i);
            choiceBox.setValue(users.get(0).getUserName());
            choiceBox.getItems().add(newUser.getUserName());
        }
        return users.get(0).getUserName();
    }
    @FXML
    void UpdatingTasksAndEstimates() {
        System.out.println(taskID);
        System.out.println(taskName);
        String newTask = taskNameField.getText();
        boolean testing = taskDB.UpdateTasks(taskID,newTask);
        System.out.println(testing);
        mapForChoiceBox.put((String) choiceBox.getItems().get((Integer) lastNumberForHashMap), Integer.parseInt(timeEstimate.getText()));
        for (String names : mapForChoiceBox.keySet()) {
            int userID = taskDB.GettingUserIds(names);
            taskDB.UpdateTaskAndUsers(userID, mapForChoiceBox.get(names), taskID, taskName);
        }
        Alert alert;
        if(testing){
            alert = new Alert(Alert.AlertType.INFORMATION, "The task has been added", ButtonType.CLOSE);
        }else{
            alert = new Alert(Alert.AlertType.INFORMATION, "The task could not be added, Try again later.", ButtonType.CLOSE);
        }
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        taskNameField.setText(taskName);
        taskID = groupDB.gettingTaskID(taskName);
        String firstUser = AddingUsersToChoice();
        mapForChoiceBox = new HashMap<>();
        ArrayList<String> arraylistOfNames = new ArrayList<>(taskDB.GetUserNames());
        for (String x: arraylistOfNames) {
            int userID = taskDB.GettingUserIds(x);
            int timeEstimatePerUser = taskDB.GettingEstimateTime(userID,taskID);
            mapForChoiceBox.put(x,timeEstimatePerUser);
            System.out.println("TimePerUserEstimate");
            System.out.println(mapForChoiceBox.get(x));
        }
        int userID = taskDB.GettingUserIds(firstUser);
        int timeEstimateForFirst = taskDB.GettingEstimateTime(userID,taskID);
        timeEstimate.setText(Integer.toString(timeEstimateForFirst));
                choiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                try{ int estimatedTime = Integer.parseInt(timeEstimate.getText());
                    mapForChoiceBox.put((String) choiceBox.getItems().get((Integer)number), estimatedTime);
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
