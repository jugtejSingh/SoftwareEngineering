package com.example.softwareeng;

import java.util.ArrayList;

public class Users {
    int userID;
    String userName;
    int estimateTime;
    int backlog;
    int divisionOfWork;

    public int getDivisionOfWork() {
        return divisionOfWork;
    }

    public void setDivisionOfWork(int divisionOfWork) {
        this.divisionOfWork = divisionOfWork;
    }

    ArrayList<Tasks> taskList = new ArrayList<Tasks>();

    public ArrayList<Tasks> getTaskList() {
        return taskList;
    }

    public void setTaskList(ArrayList<Tasks> taskList) {
        this.taskList = taskList;
    }

    public int getBacklog() {
        return backlog;
    }

    public void setBacklog(int backlog) {
        this.backlog = backlog;
    }

    public int getEstimateTime() {
        return estimateTime;
    }

    public void setEstimateTime(int estimateTime) {
        this.estimateTime = estimateTime;
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
