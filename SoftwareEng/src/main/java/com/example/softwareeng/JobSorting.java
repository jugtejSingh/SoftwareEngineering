package com.example.softwareeng;


import java.math.BigDecimal;
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
        for (int i = sizeof; i >= 0; i--) {
            Users lowestTime = usersArrayList.get(0);
            for (int p = 0; p < usersArrayList.size(); p++) {
                if (lowestTime.getDivisionOfWork() > usersArrayList.get(p).getDivisionOfWork()) {
                    lowestTime = usersArrayList.get(p);
                }
            }
            Tasks highestTask = lowestTime.getTaskList().get(0);
            int highestTaskint = 0;
            for (int k = sizeof; k >= 0; k--) {
                if (highestTask.getEstimatedTime() < lowestTime.getTaskList().get(k).getEstimatedTime()) {
                    highestTask = lowestTime.getTaskList().get(k);
                    highestTaskint = k;
                }
            }
            float temp = (float) (lowestTime.getDivisionOfWork() + highestTask.getEstimatedTime());
            lowestTime.setDivisionOfWork(temp);
            highestTask.setUserName(lowestTime.userName);
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


