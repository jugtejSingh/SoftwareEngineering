package com.example.softwareeng;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GroupTaskUserController implements Initializable {
    @FXML
    VBox vboxForTasks;

    @FXML
    ScrollPane scrollPaneTasks;
    @FXML
    VBox groupsVbox;
    GroupTaskUserDB groupConn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Code to display the groups on the side which can be clicked on
        int groupIDforTasks = 1;
        CreateTaskController.groupSelectedID = groupIDforTasks;
        DisplayingTasks(groupIDforTasks);
        groupConn = new GroupTaskUserDB();
        Label label1;
        ArrayList<Group> groupArrayList = new ArrayList<>(groupConn.GetGroups());
        for (int p = 0; p < groupArrayList.size(); p++) {
            Group groupName = (groupArrayList.get(p));
            label1 = new Label(groupName.getName());
            groupsVbox.getChildren().add(label1);
            label1.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                int idOfGroup = groupName.getId();
                CreateTaskController.groupSelectedID = idOfGroup;
                DisplayingTasks(groupName.getId());
                }
            });


//Code to display the tasks inside the Second VBOX
            scrollPaneTasks.setFitToWidth(true);

        }
    }
    void DisplayingTasks(int groupIdForTasks){
        groupConn = new GroupTaskUserDB();
        vboxForTasks.getChildren().clear();
        ArrayList<Tasks> taskArrayList = new ArrayList<>(groupConn.GetTasks(groupIdForTasks));
        for (int i = 0; i < taskArrayList.size(); i++) {
            HBox hboxesForCheckboxes = new HBox();
            vboxForTasks.getChildren().add(hboxesForCheckboxes);
            Tasks task = taskArrayList.get(i);
            Label label = new Label(task.getTaskName());
            hboxesForCheckboxes.getChildren().add(label);
            CheckBox checkboxes = new CheckBox();
            hboxesForCheckboxes.getChildren().add(checkboxes);
        }
    }@FXML
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
    }
