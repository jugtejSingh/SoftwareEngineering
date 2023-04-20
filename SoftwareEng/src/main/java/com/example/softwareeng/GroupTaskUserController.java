package com.example.softwareeng;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

public class GroupTaskUserController implements Initializable {
    @FXML
    VBox vboxForTasks;
    @FXML
    ScrollPane scrollPaneTasks;
    @FXML
    VBox groupsVbox;
    GroupTaskUserDB groupConn;
    ArrayList<Tasks> taskAndDaysArrayList = new ArrayList<>();
    int groupIDForMission7 = 0;
    void SettingGroupID() {
        int groupIDforTasks = 1;
        CreateTaskController.groupSelectedID = groupIDforTasks;
        CreateTaskDB.groupID = groupIDforTasks;
        CreatingUserViewController.groupIDforUserIDInsert = groupIDforTasks;
        groupIDForMission7 = groupIDforTasks;
        DisplayingTasks(groupIDforTasks);
    }
    void DisplayingGroups() {
        groupConn = new GroupTaskUserDB();
        Label label1;
        ArrayList<Group> groupArrayList = new ArrayList<>(groupConn.GetGroups());
        for (int p = 0; p < groupArrayList.size(); p++) {
            Group groupName = (groupArrayList.get(p));
            label1 = new Label(groupName.getName());
            groupsVbox.getChildren().add(label1);
            Separator sep = new Separator();
            groupsVbox.getChildren().add(sep);
            label1.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    int idOfGroup = groupName.getId();
                    CreateTaskController.groupSelectedID = idOfGroup;
                    CreateTaskDB.groupID = idOfGroup;
                    CreatingUserViewController.groupIDforUserIDInsert = idOfGroup;
                    groupIDForMission7 = idOfGroup;
                    DisplayingTasks(groupName.getId());
                }
            });
        }
    }
    void DisplayingTasks(int groupIdForTasks) {
        groupConn = new GroupTaskUserDB();
        vboxForTasks.getChildren().clear();
        ArrayList<Tasks> taskArrayList = new ArrayList<>(groupConn.GetTasks(groupIdForTasks));
        for (int i = 0; i < taskArrayList.size(); i++) {
            HBox hboxesForCheckboxes = new HBox();
            vboxForTasks.getChildren().add(hboxesForCheckboxes);
            Separator sep = new Separator();
            vboxForTasks.getChildren().add(sep);
            Tasks task = taskArrayList.get(i);
            Label label = new Label(task.getTaskName());
            hboxesForCheckboxes.getChildren().add(label);
            AddingCheckboxesForDays(hboxesForCheckboxes);
        }
    }
    void AddingCheckboxesForDays(HBox hBox) {
        ToggleGroup toggleGroup = new ToggleGroup();
        RadioButton Weekly = new RadioButton("Weekly");
        RadioButton Daily = new RadioButton("Daily");
        RadioButton Ignore = new RadioButton("Ignore");
        Weekly.setToggleGroup(toggleGroup);
        Daily.setToggleGroup(toggleGroup);
        Ignore.setToggleGroup(toggleGroup);
        Ignore.setSelected(true);
        ObservableList<Node> node = hBox.getChildren();
        Label l = (Label)node.get(0);
        int taskID = groupConn.gettingTaskID(l.getText());
        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {
                if (toggleGroup.getSelectedToggle() == Weekly) {
                    for (int i = taskAndDaysArrayList.size()-1; i >= 0; i--) {
                        Tasks task = taskAndDaysArrayList.get(i);
                        if(task.getTaskName().equals(l.getText())){
                            taskAndDaysArrayList.remove(task);
                        }
                    }
                    Tasks task = new Tasks();
                    task.setTaskID(taskID);
                    task.setTaskName(l.getText());
                    task.setGroupID(groupIDForMission7);
                    task.setWeeklyorDaily(0);
                    taskAndDaysArrayList.add(task);
                    System.out.println("Weekly");
                } else if(toggleGroup.getSelectedToggle() == Daily) {
                    for (int i = taskAndDaysArrayList.size()-1; i >= 0; i--)
                    {
                        Tasks task = taskAndDaysArrayList.get(i);
                        if(task.getTaskName().equals(l.getText())){
                            taskAndDaysArrayList.remove(task);
                        }
                    }
                    for (int i = 1; i < 8; i++) {
                        Tasks task = new Tasks();
                        task.setTaskID(taskID);
                        task.setTaskName(l.getText());
                        task.setGroupID(groupIDForMission7);
                        task.setWeeklyorDaily(i);
                        taskAndDaysArrayList.add(task);
                        System.out.println("Daily");
                    }
                }
                else if(toggleGroup.getSelectedToggle() == Ignore){
                    for (int i = taskAndDaysArrayList.size()-1; i >= 0; i--) {
                        Tasks task = taskAndDaysArrayList.get(i);
                        System.out.println(taskAndDaysArrayList.size());
                        if(task.getTaskName().equals(l.getText())){
                            taskAndDaysArrayList.remove(task);
                            System.out.println("Ignored");
                        }
                    }
                }}});
        hBox.getChildren().add(Weekly);
        hBox.getChildren().add(Daily);
        hBox.getChildren().add(Ignore);
    }
ArrayList<Tasks> gettingTheTaskArrayList(int groupIDForMission7){
        ArrayList<Tasks> tasksArrayList = new ArrayList<>();
    for (Tasks task: taskAndDaysArrayList){
        if(task.getGroupID() == groupIDForMission7){
            tasksArrayList.add(task);
        }
    }
    return tasksArrayList;
}
    @FXML
    void submittingTasks(){
        ArrayList<Tasks> taskList = gettingTheTaskArrayList(groupIDForMission7);
        JobSortingDB jobDB = new JobSortingDB();
        ArrayList<Users> userList = jobDB.getUsers(groupIDForMission7);
        for (Users user:userList) {
            ArrayList<Tasks> tasksArrayList = new ArrayList<>();
            for (Tasks task: taskList) {
                Tasks taskForUser = new Tasks();
                taskForUser.setGroupID(task.getGroupID());
                taskForUser.setTaskID(task.getTaskID());
                taskForUser.setTaskName(task.getTaskName());
                task.setWeeklyorDaily(task.getWeeklyorDaily());
                int estimatedTime = jobDB.gettingEstimatedTime(user.getUserID(),task.getTaskID());
                taskForUser.setEstimatedTime(estimatedTime);
                tasksArrayList.add(taskForUser);
            }
            user.setTaskList(tasksArrayList);
        }
    JobSorting jobSorting = new JobSorting();
        jobSorting.sortTimes(userList);
    }
    @FXML
    void makingTaskAdding() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CreateTaskScreen.class.getResource("createTask-view.fxml"));
        String css = this.getClass().getResource("createTask.css").toExternalForm();
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(css);
        Stage stage = new Stage();
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void addingUser() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CreateTaskScreen.class.getResource("creatingUser-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    void addingGroup() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(CreateTaskScreen.class.getResource("creatingGroup-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SettingGroupID();
        DisplayingGroups();
        scrollPaneTasks.setFitToWidth(true);
        groupsVbox.setFillWidth(true);
    }
}

//You have an arraylist that you make with every group, once you press submit the arraylist gets pushed
//to the job sorting algorthm which sorts the jobs and sends it through to the users