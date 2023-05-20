package com.example.softwareeng;

import javafx.scene.control.CheckBox;

public class CompletedTask {
    private String taskName;
    private String groupName;
    private String user;
    private String completedDate;

    public CompletedTask(String taskName, String groupName, String user, String completedDate){
        this.taskName = taskName;
        this.groupName = groupName;
        this.user = user;
        this.completedDate = completedDate;
    }

    public String getTaskName() {
        return taskName;
    }
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }

    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCompletedDate() {
        return completedDate;
    }
    public void setCompletedDate(String completedDate) {
        this.completedDate = completedDate;
    }

}
