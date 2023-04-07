package com.example.softwareeng;

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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLOutput;
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

    ArrayList<TaskAndDays> taskAndDaysArrayList = new ArrayList<>();
    void SettingGroupID() {
        int groupIDforTasks = 1;
        CreateTaskController.groupSelectedID = groupIDforTasks;
        CreateTaskDB.groupID = groupIDforTasks;
        CreatingUserViewController.groupIDforUserIDInsert = groupIDforTasks;
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
        ArrayList<CheckBox> choiceOfDaysList = new ArrayList<CheckBox>();
        for (DayOfWeek day : DayOfWeek.values()) {
            ObservableList<Node> node = hBox.getChildren();
            Label l = (Label)node.get(0);
            String stringOfDay = day.toString();
            CheckBox choiceOfDays = new CheckBox(day.getDisplayName(TextStyle.SHORT, Locale.ENGLISH));
            choiceOfDaysList.add(choiceOfDays);
            hBox.getChildren().add(choiceOfDays);
            TaskAndDays taskAndDays = new TaskAndDays(l.getText(),stringOfDay,day.getValue(),false);
        EventHandler<ActionEvent> eventHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (choiceOfDays.isSelected()) {
                    taskAndDays.setSelectedOrNot(true);
                    System.out.println(taskAndDays.isSelectedOrNot());
                    taskAndDaysArrayList.add(taskAndDays);
                } else {
                    taskAndDays.setSelectedOrNot(false);
                    taskAndDaysArrayList.remove(taskAndDays);
                    System.out.println(taskAndDays.isSelectedOrNot());
                    System.out.println(taskAndDaysArrayList.size());
                }
            }
        };
            choiceOfDays.setOnAction(eventHandler);
        }
    }
    @FXML
    void submittingTasks(){
        //Connect this to mission 7.
        taskAndDaysArrayList.get(0);
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