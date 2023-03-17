package com.example.softwareeng;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GroupScreenController implements Initializable {
    @FXML
    VBox vboxForTasks;

    @FXML
    ScrollPane scrollPaneTasks;
    @FXML
    VBox groupsVbox;
    GroupDB groupConn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Code to display the groups on the side which can be clicked on
        int groupIDforTasks = 1;
        DisplayingTasks(groupIDforTasks);
        groupConn = new GroupDB();
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
                DisplayingTasks(groupName.getId());
                    System.out.println(idOfGroup);
                }
            });


//Code to display the tasks inside the Second VBOX
            scrollPaneTasks.setFitToWidth(true);

        }
    }
    void DisplayingTasks(int groupIdForTasks){
        groupConn = new GroupDB();
        vboxForTasks.getChildren().clear();
        ArrayList<Tasks> taskArrayList = new ArrayList<>(groupConn.GetTasks(groupIdForTasks));
        for (int i = 0; i < taskArrayList.size(); i++) {
            Tasks task = taskArrayList.get(i);
            Label label = new Label(task.getTaskName());
            label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    System.out.println(task.getTaskID());
                }
            });
            vboxForTasks.getChildren().add(label);
        }
    }
}
