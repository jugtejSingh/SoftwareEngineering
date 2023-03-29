package com.example.softwareeng;

import java.sql.ResultSet;
import java.util.ArrayList;

public class CreateTaskDB {
    private DBConnection database;

    public CreateTaskDB() {
        database = new DBConnection();
        database.Connect("TaskManagerDB.sqlite");
    }

    public ArrayList<Users> GetUsers() {
        String sqlString = new String("SELECT userID, name from users;");
        ResultSet users = database.RunSQLQuery(sqlString);
        ArrayList<Users> finalAnswer = new ArrayList<Users>();
        try {
            while (users.next()) {
                Users userObject = new Users();
                userObject.setUserID(users.getInt(1));
                userObject.setUserName(users.getString(2));
                finalAnswer.add(userObject);
            }
        } catch (Exception e) {
            System.out.println("An error occurred while putting value of column into countOfColumn");
        }
        return finalAnswer;
    }


    int InsertIntoTask(int groupID, String taskName) {
        String taskIDSQL = new String("Select MAX(taskID) from tasks");
        ResultSet storingTaskID = database.RunSQLQuery(taskIDSQL);
        int taskID = 0;
        try {
            taskID = storingTaskID.getInt(1) + 1;
        } catch (Exception e) {
            System.out.println("Error occured while getting sql data");
        }
        String sqlString = new String("INSERT INTO tasks(taskID,groupID,taskName) values('" + taskID + "','" + groupID + "','" + taskName + "');");
        database.RunSQL(sqlString);
        return taskID;
    }

    ArrayList<Integer> gettingUserIds() {
        String userCount = new String("Select userID from users;");
        ResultSet usersIDRetrival = database.RunSQLQuery(userCount);
        ArrayList<Integer> usersID = new ArrayList<>();
        try {
            while (usersIDRetrival.next())
                usersID.add(usersIDRetrival.getInt(1));
        } catch (Exception e) {
            System.out.println("Issue with the usersIDRetrival");
        }
        return usersID;
    }

    void InsertIntoTaskAndUsers(ArrayList<Integer> userIDs, int groupID, ArrayList<Integer> estimatedTime, int taskID) {
        for (int i = 0; i < userIDs.size(); i++) {
            String insertingIntoTasksAndUsers = new String("INSERT INTO taskAndUsers values ('" + groupID + "','" + userIDs.get(i) + "','" + taskID + "','" + estimatedTime.get(i) + "')");
            database.RunSQL(insertingIntoTasksAndUsers);
        }
    }
  /*  public int GetTimeEstimates(String username) {
        String sqlString = new String("SELECT estimatedTime from taskAndUsers join users  on taskAndUsers.userID = users.userID where users.name == '"+username+"' ");
        ResultSet time = database.RunSQLQuery(sqlString);
        int timeEstimate = 0;
        try {
            while (time.next()) {
                System.out.println("getting value");
                timeEstimate = time.getInt(1);
            }
        }
        catch (Exception e){
            System.out.println("An error occurred while putting value of column into countOfColumn " + e.getMessage());
        }
        return timeEstimate;
    } */

       public int GetMaxUserCount(){
            String sqlString = new String("SELECT MAX(userID) from users");
            ResultSet users = database.RunSQLQuery(sqlString);
            int usersMax = 0;
            try {
                while (users.next()) {
                    usersMax = users.getInt(1);
                }
            } catch (Exception e) {
                System.out.println("An error occurred while putting value of column into countOfColumn " + e.getMessage());
            }
            return usersMax;
        }
    }
