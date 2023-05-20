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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CompletedTaskListController extends Application implements Initializable {
    @FXML
    private TableView completedTaskListTable;
    @FXML Button exit;
    private Stage stage;
    private Scene scene;
    @FXML
    private Label date;
    public LocalDate startDate;
    public LocalDate endDate;

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

    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(GroupTaskUserScreen.class.getResource("completedTaskListScreen-view.fxml"));
        String cssGroup = this.getClass().getResource("com/example/softwareeng/completedTaskListScreen-css.css").toExternalForm();
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(cssGroup);
        stage.setTitle("CompletedTaskList");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }

    public void initialize(URL url, ResourceBundle rb){
        //create columns
        TableColumn taskName = new TableColumn("Task");
        TableColumn groupName = new TableColumn("Group");
        TableColumn user = new TableColumn("Completed By");
        TableColumn completedDate = new TableColumn("Completed Date");

        //add columns to completedTaskListTable
        completedTaskListTable.getColumns().addAll(taskName,groupName,user,completedDate);

        CompletedTaskListDB completedTaskListDB = new CompletedTaskListDB();

        //get start date and end date of current week
        startDate = completedTaskListDB.calculateStartDate();
        endDate = completedTaskListDB.calculateEndDate();

        //get task data
        ObservableList completedTaskList = completedTaskListDB.GetCompletedTasks(startDate, endDate);

        taskName.setCellValueFactory(new PropertyValueFactory<TempTask,String>("taskName"));
        groupName.setCellValueFactory(new PropertyValueFactory<TempTask,String>("groupName"));
        user.setCellValueFactory(new PropertyValueFactory<TempTask,String>("user"));
        completedDate.setCellValueFactory(new PropertyValueFactory<TempTask,String>("completedDate"));

        //populate table with data
        completedTaskListTable.setItems(completedTaskList);

        //Put date of current week above the table
        date.setText(String.valueOf(startDate));
    }

    public void viewWeeklyToDoList(ActionEvent event) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("taskListScreen-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void exit(ActionEvent event) throws IOException{
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
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

    //when previous or next is clicked, this refreshes the table data
    public void updateTable(LocalDate startDate, LocalDate endDate)
    {
        completedTaskListTable.getColumns().clear();

        TableColumn taskName = new TableColumn("Task");
        TableColumn groupName = new TableColumn("Group");
        TableColumn user = new TableColumn("Completed By");
        TableColumn completedDate = new TableColumn("Completed Date");

        completedTaskListTable.getColumns().addAll(taskName,groupName,user,completedDate);

        CompletedTaskListDB completedTaskListDB = new CompletedTaskListDB();

        ObservableList completedTaskList = completedTaskListDB.GetCompletedTasks(startDate, endDate);

        taskName.setCellValueFactory(new PropertyValueFactory<TempTask,String>("taskName"));
        groupName.setCellValueFactory(new PropertyValueFactory<TempTask,String>("groupName"));
        user.setCellValueFactory(new PropertyValueFactory<TempTask,String>("user"));
        completedDate.setCellValueFactory(new PropertyValueFactory<TempTask,String>("completedDate"));

        completedTaskListTable.setItems(completedTaskList);
    }

}
