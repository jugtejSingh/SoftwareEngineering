package com.example.softwareeng;

import java.util.ArrayList;

public class Users {
    int userID;
    String userName;
    float backlog;
    float divisionOfWork;

    int numberOfTask;

    int tempTaskCount;

    public int getTempTaskCount() {
        return tempTaskCount;
    }

    public void setTempTaskCount(int tempTaskCount) {
        this.tempTaskCount = tempTaskCount;
    }

    public int getNumberOfTask() {
        return numberOfTask;
    }

    public int ge() {
        return numberOfTask;
    }

    public void setNumberOfTask(int amountOfTime) {
        this.numberOfTask = amountOfTime;
    }


    public void setBacklog(float backlog) {
        this.backlog = backlog;
    }

    public float getDivisionOfWork() {
        return divisionOfWork;
    }

    public void setDivisionOfWork(float divisionOfWork) {
        this.divisionOfWork = divisionOfWork;
    }

    ArrayList<Tasks> taskList = new ArrayList<Tasks>();

    public ArrayList<Tasks> getTaskList() {
        return taskList;
    }

    public void setTaskList(ArrayList<Tasks> taskList) {
        this.taskList = taskList;
    }

    public float getBacklog() {
        return backlog;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
