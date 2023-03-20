package com.example.softwareeng;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CreateTaskDB {
    private DBConnection database;

    public CreateTaskDB(){
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
        }
        catch (Exception e){
            System.out.println("An error occurred while putting value of column into countOfColumn");
        }
        return finalAnswer;
    }
    void InsertIntoTask(int groupID,String taskName,String usersName,int estimatedTime){
        String taskIDSQL = new String("Select MAX(taskID) from tasks");
        ResultSet storingTaskID = database.RunSQLQuery(taskIDSQL);
        int taskID = 0;
        try {
            taskID = storingTaskID.getInt(1) + 1;
            System.out.println(taskID);
        }
        catch (Exception e){
            System.out.println("Error occured while getting sql data");
        }
        System.out.println("Is this running");
        String sqlString = new String("INSERT INTO tasks values ('"+taskID+"','"+groupID+"','"+taskName+"');");
        database.RunSQL(sqlString);
        System.out.println("Is this running 2");
        String userCount = new String("Select userID from users where name = '"+usersName+"';");
        ResultSet usersIDRetrival = database.RunSQLQuery(userCount);
        int usersIDRetrivalValue = 0;
        try {
             usersIDRetrivalValue = usersIDRetrival.getInt(1);
        }
        catch (Exception e){
            System.out.println("Issue with the usersIDRetrival");
        }
        String insertingIntoTasksAndUsers = new String("INSERT INTO taskAndUsers values ('"+groupID+"','"+usersIDRetrivalValue+"','"+taskID+"','"+estimatedTime+"')");
        database.RunSQL(sqlString);
    }
    public int GetTimeEstimates(String username) {
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
    }
}
