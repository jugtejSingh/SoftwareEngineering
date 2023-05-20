package com.example.softwareeng;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TaskListController extends Application implements Initializable {

    @FXML
    private TableView taskListTable;
    @FXML
    private Button exit;
    @FXML
    private Label date;
    public LocalDate startDate;
    public LocalDate endDate;
    private Stage stage;
    private Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GroupTaskUserScreen.class.getResource("taskListScreen-view.fxml"));
        String cssGroup = this.getClass().getResource("taskListScreen-css.css").toExternalForm();
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(cssGroup);
        stage.setTitle("TaskList");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb){
        TableColumn taskName = new TableColumn("Task");
        TableColumn groupName = new TableColumn("Group");
        TableColumn user = new TableColumn("Assigned to");
        TableColumn completeBy = new TableColumn("Complete by");
        TableColumn tempTaskID = new TableColumn("Task ID");
        taskListTable.getColumns().addAll(taskName,groupName,user,completeBy,tempTaskID);

        TaskListDB taskListDB = new TaskListDB();
        startDate = taskListDB.calculateStartDate();
        endDate = taskListDB.calculateEndDate();
        ObservableList taskList = taskListDB.GetTasks(startDate, endDate);

        int taskListSize = taskList.size();

        taskListDB.calculateSunday(taskListSize);

        //Put date of current week above the table
        date.setText(String.valueOf(startDate));

        taskName.setCellValueFactory(new PropertyValueFactory<TempTask,String>("taskName"));
        groupName.setCellValueFactory(new PropertyValueFactory<TempTask,String>("groupName"));
        user.setCellValueFactory(new PropertyValueFactory<TempTask,String>("user"));
        completeBy.setCellValueFactory(new PropertyValueFactory<TempTask,String>("completeBy"));
        tempTaskID.setCellValueFactory(new PropertyValueFactory<TempTask,Integer>("tempTaskID"));

        taskListTable.setItems(taskList);
    }

    public LocalDate getStartDate()
    {
        return startDate;
    }

    public void setStartDate(LocalDate startDate)
    {
        this.startDate = startDate;
    }

    public LocalDate getEndDate()
    {
        return endDate;
    }

    public void setEndDate(LocalDate endDate)
    {
        this.endDate = endDate;
    }
    public void previousWeek(ActionEvent event)
    {
        //get start date and subtract 1 week
        LocalDate previousStart = getStartDate();
        previousStart = previousStart.minusWeeks(1);
        setStartDate(previousStart);

        //get end date and subtract 1 week
        LocalDate previousEnd = getEndDate();
        previousEnd = previousEnd.minusWeeks(1);
        setEndDate(previousEnd);

        date.setText(String.valueOf(previousStart));
        //update tableview
        updateTable(previousStart, previousEnd);
    }
    public void upcomingWeek(ActionEvent event)
    {
        //get start date and add 1 week
        LocalDate nextStart = getStartDate();
        nextStart = nextStart.plusWeeks(1);
        setStartDate(nextStart);

        //get end date and add 1 week
        LocalDate nextEnd = getEndDate();
        nextEnd = nextEnd.plusWeeks(1);
        setEndDate(nextEnd);

        date.setText(String.valueOf(nextStart));

        //update tableview
        updateTable(nextStart, nextEnd);
    }
    public void updateTable(LocalDate startDate, LocalDate endDate)
    {
        taskListTable.getColumns().clear();

        TableColumn taskName = new TableColumn("Task");
        TableColumn groupName = new TableColumn("Group");
        TableColumn user = new TableColumn("Assigned to");
        TableColumn completeBy = new TableColumn("Complete by");
        TableColumn tempTaskID = new TableColumn("Task ID");
        taskListTable.getColumns().addAll(taskName,groupName,user,completeBy,tempTaskID);

        TaskListDB taskListDB = new TaskListDB();

        ObservableList taskList = taskListDB.GetTasks(startDate, endDate);

        taskName.setCellValueFactory(new PropertyValueFactory<TempTask,String>("taskName"));
        groupName.setCellValueFactory(new PropertyValueFactory<TempTask,String>("groupName"));
        user.setCellValueFactory(new PropertyValueFactory<TempTask,String>("user"));
        completeBy.setCellValueFactory(new PropertyValueFactory<TempTask,String>("completeBy"));

        tempTaskID.setCellValueFactory(new PropertyValueFactory<TempTask,Integer>("tempTaskID"));

        taskListTable.setItems(taskList);
    }

    public void taskComplete(ActionEvent event)
    {
        if (taskListTable.getSelectionModel().getSelectedItem() != null) {
            TempTask tempTask = (TempTask) taskListTable.getSelectionModel().getSelectedItem();

            int tempTaskID = tempTask.getTempTaskID();
            String taskName = tempTask.getTaskName();
            String userName = tempTask.getUser();
            String groupName = tempTask.getGroupName();
            LocalDate completedDate = LocalDate.now();

            TaskListDB taskListDB = new TaskListDB();

            taskListDB.insertCompletedTask(tempTaskID, taskName, userName, groupName, completedDate);
            taskListDB.deleteTempTask(tempTaskID);

            //refresh table after delete
            updateTable(startDate, endDate);
        }
    }

    public void viewCompletedTasks(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("completedTaskListScreen-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    //exit the window
    public void exit(ActionEvent event) throws IOException{
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }
}
