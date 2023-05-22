package com.example.softwareeng;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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
    GroupTaskUserDB groupConn = new GroupTaskUserDB();
    ArrayList<Tasks> taskAndDaysArrayList = new ArrayList<>();
    int groupIDForMission7 = 0;

    void SettingGroupID() {
        int groupIDforTasks = 1;
        CreateTaskController.groupSelectedID = groupIDforTasks;
        CreateTaskDB.groupID = groupIDforTasks;
        CreatingUserViewController.groupIDforUserIDInsert = groupIDforTasks;
        taskViewController.groupSelectedID = groupIDforTasks;
        groupIDForMission7 = groupIDforTasks;
        DisplayingTasks(groupIDforTasks);
    }
    void DisplayingGroups() {
        Label label1;
        ArrayList<Group> groupArrayList = new ArrayList<>(groupConn.GetGroups());
        for (int p = 0; p < groupArrayList.size(); p++) {
            Group groupName = (groupArrayList.get(p));
            label1 = new Label(groupName.getName());

            label1.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    int idOfGroup = groupName.getId();
                    CreateTaskController.groupSelectedID = idOfGroup;
                    CreateTaskDB.groupID = idOfGroup;
                    CreatingUserViewController.groupIDforUserIDInsert = idOfGroup;
                    taskViewController.groupSelectedID = idOfGroup;
                    groupIDForMission7 = idOfGroup;
                    DisplayingTasks(groupName.getId());
                }
            });
            groupsVbox.getChildren().add(label1);
            Separator sep = new Separator();
            groupsVbox.getChildren().add(sep);
        }
    }
    void DisplayingTasks(int groupIdForTasks) {
        vboxForTasks.getChildren().clear();
        ArrayList<Tasks> taskArrayList = new ArrayList<>(groupConn.GetTasks(groupIdForTasks));
        for (int i = 0; i < taskArrayList.size(); i++) {
            HBox hboxesForCheckboxes = new HBox(10);
            hboxesForCheckboxes.setAlignment(Pos.CENTER_LEFT);
            vboxForTasks.getChildren().add(hboxesForCheckboxes);
            Separator sep = new Separator();
            vboxForTasks.getChildren().add(sep);
            Tasks task = taskArrayList.get(i);
            Label label = new Label(task.getTaskName());
            hboxesForCheckboxes.getChildren().add(label);
            Region region = new Region();
            hboxesForCheckboxes.getChildren().add(region);
            HBox.setHgrow(region,Priority.ALWAYS);
            AddingCheckboxesForDays(hboxesForCheckboxes);
            addingDelete(hboxesForCheckboxes);
            buttonModification(hboxesForCheckboxes);
        }
    }
    void buttonModification(HBox hBox){
        Button buttonForModify = new Button("Update");
        buttonForModify.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ObservableList<Node> node = hBox.getChildren();
                Label label = (Label)node.get(0);
                taskViewController.taskName = label.getText();
                Stage stage = (Stage)scrollPaneTasks.getScene().getWindow();
                stage.close();
                FXMLLoader fxmlLoader = new FXMLLoader(GroupTaskUserScreen.class.getResource("taskModify-view.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Stage stageForTasksList = new Stage();
                stageForTasksList.setTitle("Modify");
                stageForTasksList.setScene(scene);
                stageForTasksList.show();
                stageForTasksList.setOnCloseRequest(new EventHandler<WindowEvent>() {
                    @Override
                    public void handle(WindowEvent windowEvent) {
                        FXMLLoader fxmlLoader = new FXMLLoader(GroupTaskUserScreen.class.getResource("groupScreen-view.fxml"));
                        String cssGroup = this.getClass().getResource("groupScreen-css.css").toExternalForm();
                        Scene scene = null;
                        try {
                            scene = new Scene(fxmlLoader.load());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        stage.setTitle("Hello!");
                        stage.setScene(scene);
                        stage.show();
                        DisplayingTasks(groupIDForMission7);
                    }
                });
            }
            });
        hBox.getChildren().add(buttonForModify);
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
                    task.setWeeklyDaily(0);
                    taskAndDaysArrayList.add(task);
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
                        task.setWeeklyDaily(i);
                        taskAndDaysArrayList.add(task);
                    }
                }
                else if(toggleGroup.getSelectedToggle() == Ignore){
                    for (int i = taskAndDaysArrayList.size()-1; i >= 0; i--) {
                        Tasks task = taskAndDaysArrayList.get(i);
                        if(task.getTaskName().equals(l.getText())){
                            taskAndDaysArrayList.remove(task);
                        }
                    }
                }}});
        hBox.getChildren().add(Weekly);
        hBox.getChildren().add(Daily);
        hBox.getChildren().add(Ignore);

    }
    void addingDelete(HBox hbox){
        ObservableList<Node> node = hbox.getChildren();
        Label label = (Label)node.get(0);
        Button button = new Button();
        button.setText("Delete");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                groupConn.deletingTasks(label.getText());
                DisplayingTasks(groupIDForMission7);
            }
        });
        hbox.getChildren().add(button);

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
                taskForUser.setWeeklyDaily(task.getWeeklyDaily());
                int estimatedTime = jobDB.gettingEstimatedTime(user.getUserID(),task.getTaskID());
                taskForUser.setEstimatedTime(estimatedTime);
                tasksArrayList.add(taskForUser);
            }
            user.setTaskList(tasksArrayList);
        }
    JobSorting jobSorting = new JobSorting(jobDB);
        jobSorting.sortTimes(userList);
        Alert alert = new Alert(Alert.AlertType.INFORMATION,"The tasks have been submitted", ButtonType.CLOSE);
        alert.showAndWait();
    }
    @FXML
    void makingTaskAdding() throws IOException {
        Stage stage = (Stage)scrollPaneTasks.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(CreateTaskController.class.getResource("createTask-view.fxml"));
        String css = this.getClass().getResource("createTask.css").toExternalForm();
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(css);
        Stage stageForTask = new Stage();
        stageForTask.setTitle("Hello!");
        stageForTask.setScene(scene);
        stageForTask.show();
        stageForTask.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                FXMLLoader fxmlLoader = new FXMLLoader(GroupTaskUserScreen.class.getResource("groupScreen-view.fxml"));
                String cssGroup = this.getClass().getResource("groupScreen-css.css").toExternalForm();
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                scene.getStylesheets().add(cssGroup);
                stage.setTitle("Hello!");
                stage.setScene(scene);
                stage.show();
            }
        });
    }

    @FXML
    void addingUser() throws IOException {
        Stage stage = (Stage)scrollPaneTasks.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(CreateTaskController.class.getResource("creatingUser-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stageForUser = new Stage();
        stageForUser.setTitle("Hello!");
        stageForUser.setScene(scene);
        stageForUser.show();
        stageForUser.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                FXMLLoader fxmlLoader = new FXMLLoader(GroupTaskUserScreen.class.getResource("groupScreen-view.fxml"));
                String cssGroup = this.getClass().getResource("groupScreen-css.css").toExternalForm();
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                scene.getStylesheets().add(cssGroup);
                stage.setTitle("Hello!");
                stage.setScene(scene);
                stage.show();
            }
        });

    }

    @FXML
    void addingGroup() throws IOException {
        Stage stage = (Stage)scrollPaneTasks.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(CreateTaskController.class.getResource("creatingGroup-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stageForGroups = new Stage();
        stageForGroups.setTitle("Hello!");
        stageForGroups.setScene(scene);
        stageForGroups.show();
        stageForGroups.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                FXMLLoader fxmlLoader = new FXMLLoader(GroupTaskUserScreen.class.getResource("groupScreen-view.fxml"));
                String cssGroup = this.getClass().getResource("groupScreen-css.css").toExternalForm();
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                scene.getStylesheets().add(cssGroup);
                stage.setTitle("Home Screen");
                stage.setScene(scene);
                stage.show();
            }
        });
    }
    @FXML
    void openingTaskList() throws IOException {
        Stage stage = (Stage)scrollPaneTasks.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(GroupTaskUserScreen.class.getResource("taskListScreen-view.fxml"));
        String cssGroup = this.getClass().getResource("taskListScreen-css.css").toExternalForm();
        Scene scene = new Scene(fxmlLoader.load());
        Stage stageForTasksList = new Stage();
        stageForTasksList.setTitle("Hello!");
        stageForTasksList.setScene(scene);
        stageForTasksList.show();
        stageForTasksList.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                FXMLLoader fxmlLoader = new FXMLLoader(GroupTaskUserScreen.class.getResource("groupScreen-view.fxml"));
                String cssGroup = this.getClass().getResource("groupScreen-css.css").toExternalForm();
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                scene.getStylesheets().add(cssGroup);
                stage.setTitle("Hello!");
                stage.setScene(scene);
                stage.show();
            }
        });
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SettingGroupID();
        DisplayingGroups();
        DisplayingTasks(groupIDForMission7);
    }
}

//You have an arraylist that you make with every group, once you press submit the arraylist gets pushed
//to the job sorting algorthm which sorts the jobs and sends it through to the users