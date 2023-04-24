package com.example.softwareeng;

import java.time.LocalDateTime;

public class Tasks {
    private int taskID;
    private int groupID;
    private String taskName;
    int WeeklyDaily;
    float estimatedTime;
    int totalTime;
    String userName;
    LocalDateTime timeTillCompletition;

    public LocalDateTime getTimeTillCompletition() {
        return timeTillCompletition;
    }

    public void setTimeTillCompletition(LocalDateTime timeTillCompletition) {
        this.timeTillCompletition = timeTillCompletition;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    public float getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(float estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public int getWeeklyDaily() {
        return WeeklyDaily;
    }

    public void setWeeklyDaily(int weeklyDaily) {
        WeeklyDaily = weeklyDaily;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int groupID) {
        this.groupID = groupID;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
