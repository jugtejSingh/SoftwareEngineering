package com.example.softwareeng;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

//GroupTaskUserdb for reference
public class TaskListDB {
    private DBConnection database;

    private LocalDate currentWeekStart;
    private LocalDate currentWeekEnd;

    public TaskListDB() {
        database = new DBConnection();
        database.Connect("TaskManagerDB.sqlite");
        this.currentWeekStart=getCurrentWeekStart();
        this.currentWeekEnd=getCurrentWeekEnd();
    }

    public LocalDate getCurrentWeekStart()
    {
        return currentWeekStart;
    }

    public void setCurrentWeekStart()
    {
        this.currentWeekStart=currentWeekStart;
    }

    public LocalDate getCurrentWeekEnd()
    {
        return currentWeekEnd;
    }

    public void setCurrentWeekEnd()
    {
        this.currentWeekEnd=currentWeekEnd;
    }

    public ObservableList<TempTask> GetTasks(LocalDate startDate, LocalDate endDate) {
        currentWeekStart = startDate;
        currentWeekEnd = endDate;

        String sqlString = new String("select TaskName, groupName, userName, dateCompletedBy, TempTaskID from tempTasks " +
                "WHERE DATE(dateCompletedBy) >=  '" + currentWeekStart +
                "' and DATE(dateCompletedBy) <= '" + currentWeekEnd + "'" +
                "ORDER BY dateCompletedBy ASC");
        ResultSet TempTasks = database.RunSQLQuery(sqlString);
        ObservableList<TempTask> TaskList = FXCollections.observableArrayList();
        try {
            while (TempTasks.next()) {
                //store column data in variables
                String taskName = TempTasks.getString(1);
                String groupName = TempTasks.getString(2);
                String user = TempTasks.getString(3);
                String dateCompleteBy = TempTasks.getString(4);
                int tempTaskID = TempTasks.getInt(5);


                //create new TempTask passing through variables
                TempTask task = new TempTask(taskName, groupName, user, dateCompleteBy);

                task.setTaskName(TempTasks.getString(1));

                task.setGroupName(TempTasks.getString(2));

                task.setUser(TempTasks.getString(3));

                //retrieving date which is varchar and splitting to only show in format
                //yyyy-MM-DD
                String longDate = TempTasks.getString(4);
                String date = longDate.split("T")[0];
                task.setCompleteBy(date);

                task.setTaskID(TempTasks.getInt(5));

                TaskList.add(task);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return TaskList;
    }

    public LocalDate calculateStartDate()
    {
        LocalDate date = LocalDate.now();

        currentWeekStart = date;
        while (currentWeekStart.getDayOfWeek() != DayOfWeek.MONDAY) {
            currentWeekStart = currentWeekStart.minusDays(1);
        }
        return currentWeekStart;
    }

    public LocalDate calculateEndDate()
    {
        LocalDate date = LocalDate.now();

        currentWeekEnd = date;
        while (currentWeekEnd.getDayOfWeek() != DayOfWeek.SUNDAY) {
            currentWeekEnd = currentWeekEnd.plusDays(1);
        }
        return currentWeekEnd;
    }

    public LocalDate calculateSunday(int taskListSize) {
        LocalDate date = LocalDate.now();

        //currentWeekEnd = date;
        if (date.getDayOfWeek() == DayOfWeek.SUNDAY && taskListSize > 0) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("End of Week Task Alert");
            alert.setHeaderText(null);
            alert.setContentText("Incomplete tasks");
            alert.showAndWait();
        }
        return date;
    }

    //Method to update task status to true
    public void insertCompletedTask(int tempTaskID, String taskName, String userName, String groupName, LocalDate completedDate )
    {
/*        String sqlString = new String("select TaskName, groupName, userName, dateCompletedBy, TempTaskID from tempTasks " +
                "WHERE DATE(dateCompletedBy) >=  '" + currentWeekStart +
                "' and DATE(dateCompletedBy) <= '" + currentWeekEnd + "'");
        ResultSet TempTasks = database.RunSQLQuery(sqlString);*/

        String sqlString = new String("INSERT INTO completedTasks(TempTaskID,taskName,userName,groupName,completedDate) values('" + tempTaskID + "','" + taskName + "','" + userName + "','" + groupName + "','" + completedDate + "');");
        database.RunSQL(sqlString);
    }

    public void deleteTempTask(int tempTaskID){
        String sqlString = new String("delete from tempTasks where TempTaskID = "+ tempTaskID);
        database.RunSQL(sqlString);
    }
}
