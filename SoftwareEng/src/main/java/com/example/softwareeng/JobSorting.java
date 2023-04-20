package com.example.softwareeng;

import java.util.ArrayList;

public class JobSorting {
    void sortTimes(ArrayList<Users> usersArrayList){
        for (Users user:usersArrayList){
            int total = 0;
            for (Tasks task:user.getTaskList()) {
                total = total + task.getEstimatedTime();
                task.setTotalTime(total);
            }
            for (Tasks task:user.getTaskList()) {
                task.setEstimatedTime(task.getEstimatedTime()/total);
            }
            System.out.println(total);
            user.setDivisionOfWork(user.getBacklog());
            }
        }
    }

