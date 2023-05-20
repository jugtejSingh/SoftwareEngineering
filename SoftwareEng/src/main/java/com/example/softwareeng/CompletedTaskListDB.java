package com.example.softwareeng;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class CompletedTaskListDB {
    private DBConnection database;
    private LocalDate currentWeekStart;
    private LocalDate currentWeekEnd;
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

    //create database connection and set current week start and end
    public CompletedTaskListDB() {
        database = new DBConnection();
        database.Connect("TaskManagerDB.sqlite");
        this.currentWeekStart=getCurrentWeekStart();
        this.currentWeekEnd=getCurrentWeekEnd();
    }

    public ObservableList<CompletedTask> GetCompletedTasks(LocalDate startDate, LocalDate endDate) {
        currentWeekStart = startDate;
        currentWeekEnd = endDate;

        String sqlString = new String("select TaskName, groupName, userName, completedDate from completedTasks " +
               "where DATE(completedDate) >=  '" + currentWeekStart +
                "' and DATE(completedDate) <= '" + currentWeekEnd + "'");
        ResultSet CompletedTask = database.RunSQLQuery(sqlString);
        ObservableList<CompletedTask> CompletedTaskList = FXCollections.observableArrayList();
        try {
            while (CompletedTask.next()) {
                //store column data in variables
                String taskName = CompletedTask.getString(1);
                String groupName = CompletedTask.getString(2);
                String user = CompletedTask.getString(3);
                String completedDate = CompletedTask.getString(4);

                //create new CompletedTask passing through variables
                CompletedTask task = new CompletedTask(taskName, groupName, user, completedDate);

                task.setTaskName(CompletedTask.getString(1));
                task.setGroupName(CompletedTask.getString(2));
                task.setUser(CompletedTask.getString(3));
                task.setCompletedDate(CompletedTask.getString(4));

                //add task to taskList
                CompletedTaskList.add(task);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return CompletedTaskList;
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
}
