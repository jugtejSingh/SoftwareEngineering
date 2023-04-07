package com.example.softwareeng;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public class CreateTaskDB {
    private DBConnection database;

    static int groupID = 0;

    public CreateTaskDB() {
        database = new DBConnection();
        database.Connect("TaskManagerDB.sqlite");
    }

    public ArrayList<Users> GetUsers() {
        String sqlString = new String("SELECT userID, name from users where groupID = '" + groupID + "';");
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

    int gettingUserIds(String username) {
        String userCount = new String("Select userID from users where name = '" + username + "';");
        ResultSet usersIDRetrival = database.RunSQLQuery(userCount);
        int usersID = 0;
        try {
            while (usersIDRetrival.next())
                usersID = usersIDRetrival.getInt(1);
        } catch (Exception e) {
            System.out.println("Issue with the usersIDRetrival");
        }
        return usersID;
    }

    void InsertIntoTaskAndUsers(int userIDs, int groupID, int estimatedTime, int taskID) {
        String insertingIntoTasksAndUsers = new String("INSERT INTO taskAndUsers values ('" + groupID + "','" + userIDs + "','" + taskID + "','" + estimatedTime + "')");
        database.RunSQL(insertingIntoTasksAndUsers);
    }

    public int GetMaxUserCount() {
        String sqlString = new String("SELECT COUNT(userID) from users where groupID = '" + groupID + "'");
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
    public int GetMaxUserID() {
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

    public ArrayList<String> GetUserNames() {
        String sqlString = new String("SELECT name from users where groupID = '" + groupID + "'");
        ResultSet users = database.RunSQLQuery(sqlString);
        ArrayList<String> listOfUserNames = new ArrayList<String>();
        try {
            while (users.next()) {
                listOfUserNames.add(users.getString(1));
            }
        } catch (Exception e) {
            System.out.println("An error occurred while putting value of column into countOfColumn " + e.getMessage());
        }
        return listOfUserNames;
    }

    void InsertingUsers(int groupID, String userName) {
        int userID = GetMaxUserID() + 1;
        String insertingIntoUsers = new String("insert into users(userID,name,groupID)values('" + userID + "','" + userName + "','" + groupID + "')");
        boolean value = database.RunSQL(insertingIntoUsers);
        if(!value){
            System.out.println("There was an issue inserting users in InsertingUsers");
        }
    }

    public void DeleteUser(String userName) {
        // Should probably add a message if the student does not exist.
        String sqlStringforID = new String("Select userID FROM users WHERE name = '" + userName + "';");
        ResultSet users = database.RunSQLQuery(sqlStringforID);
        int userID = 0;
        try {
            userID = Integer.parseInt(users.getString(1));
        } catch (Exception e) {
            System.out.println("Error occured in deleteUser");
        }
        String sqlString = new String("DELETE FROM users WHERE name = '" + userName + "';");
        boolean success = database.RunSQL(sqlString);
        String removingFromTaskandUsers = new String("DELETE FROM taskAndUsers where userID ='" + userID + "' ");
        if (!success) {
            System.out.println("Failed to run query: " + sqlString);
        }
    }
}


