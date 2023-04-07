package com.example.softwareeng;

public class TaskAndDays {
    private String nameOfTask;
    private String day;

    private boolean selectedOrNot;
    private int valueOfDay;

    public TaskAndDays(String nameOfTask, String day, int valueOfDay, boolean selectedOrNot) {
        this.nameOfTask = nameOfTask;
        this.day = day;
        this.selectedOrNot = selectedOrNot;
        this.valueOfDay = valueOfDay;
    }

    public boolean isSelectedOrNot() {
        return selectedOrNot;
    }

    public void setSelectedOrNot(boolean selectedOrNot) {
        this.selectedOrNot = selectedOrNot;
    }

    public int getValueOfDay() {
        return valueOfDay;
    }

    public void setValueOfDay(int valueOfDay) {
        this.valueOfDay = valueOfDay;
    }

    public String getNameOfTask() {
        return nameOfTask;

    }

    public void setNameOfTask(String nameOfTask) {
        this.nameOfTask = nameOfTask;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
