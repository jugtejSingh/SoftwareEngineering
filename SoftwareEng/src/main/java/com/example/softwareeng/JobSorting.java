package com.example.softwareeng;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

public class JobSorting {
    JobSortingDB jobDB;

    public JobSorting(JobSortingDB jobDB) {
        this.jobDB = jobDB;
    }

    void sortTimes(ArrayList<Users> usersArrayList) {
        for (Users user : usersArrayList) {
            float total = 0;
            for (Tasks task : user.getTaskList()) {
                total = total + task.getEstimatedTime();
            }
            for (Tasks task : user.getTaskList()) {
                if (total == 0) {
                    total = 1;
                } else {
                    task.setEstimatedTime((task.getEstimatedTime() / total));
                }
            }
            //Add in getting backlog from the database table
            user.setDivisionOfWork(jobDB.getBacklog(user.getUserID()));
        }
        compareUsers(usersArrayList);
    }

    void compareUsers(ArrayList<Users> usersArrayList) {
        Collections.shuffle(usersArrayList);
        int sizeof = usersArrayList.get(0).getTaskList().size()-1;
        ArrayList<Tasks> finalAllocatedTasks = new ArrayList<>();
        //Checks the person with the lowest backLog.
        int totalTaskTime = sizeof+1;
        for (int i = sizeof; i >= 0; i--) {
            Users lowestTimedUser = usersArrayList.get(0);
            for (int p = 0; p < usersArrayList.size(); p++) {
                if(lowestTimedUser.getNumberOfTask() > totalTaskTime/usersArrayList.size()){
                    usersArrayList.remove(lowestTimedUser);
                    break;
                }
                if (lowestTimedUser.getDivisionOfWork() > usersArrayList.get(p).getDivisionOfWork()) {
                    lowestTimedUser = usersArrayList.get(p);
                }
                lowestTimedUser.setNumberOfTask(lowestTimedUser.getNumberOfTask()+1);
            }//Finds the task which takes the highest time, If 0 the user is skipped for this task
            Tasks highestTask = lowestTimedUser.getTaskList().get(0);
            int highestTaskint = 0;
            for (int k = sizeof; k >= 0; k--) {
                if(lowestTimedUser.getTaskList().get(k).getEstimatedTime() == 0){
                    break;
                }
                else if (highestTask.getEstimatedTime() < lowestTimedUser.getTaskList().get(k).getEstimatedTime()) {
                    highestTask = lowestTimedUser.getTaskList().get(k);
                    highestTaskint = k;
                }
            }
            //Adding the estimated time to division of work which is the accumulation of all the task work the user will conduct
            float temp = (float) (lowestTimedUser.getDivisionOfWork() + highestTask.getEstimatedTime());
            lowestTimedUser.setDivisionOfWork(temp);
            highestTask.setUserName(lowestTimedUser.userName);
            Tasks tempTask =  timeAddition(highestTask);
            finalAllocatedTasks.add(tempTask);
            jobDB.addingToTempTable(tempTask);
            for (int h = 0; h < usersArrayList.size(); h++) {
                usersArrayList.get(h).getTaskList().remove(highestTaskint);
            }
            sizeof--;
        }
        for (Tasks task: finalAllocatedTasks) {
            System.out.println(task.getUserName() + "    " + task.getTaskName() + "    " + task.getEstimatedTime());
        }
        float totalDivision =0;
        for (Users user: usersArrayList) {
            totalDivision = totalDivision + user.getDivisionOfWork();
        }
        for (Users user:usersArrayList) {

            Float average = totalDivision/(float)usersArrayList.size();
            System.out.println(average);
            if (user.getDivisionOfWork() > average){
                float backlog = user.getDivisionOfWork() - average;
                jobDB.insertBacklog(user.userID,backlog);
            }
        }
    }
    Tasks timeAddition(Tasks task){
            LocalDateTime timeNow = LocalDateTime.now();
            if (task.getWeeklyDaily() == 0){
                task.setTimeTillCompletition(timeNow.plusDays(7));
            } else {
                task.setTimeTillCompletition(timeNow.plusDays(task.getWeeklyDaily()));
            }
            return task;
        }
    }


