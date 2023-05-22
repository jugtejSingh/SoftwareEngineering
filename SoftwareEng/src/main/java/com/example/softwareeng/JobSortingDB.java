package com.example.softwareeng;

import java.sql.ResultSet;
import java.util.ArrayList;


public class JobSortingDB {
    private DBConnection database;

    public JobSortingDB() {
        database = ConnectionFile.database;
    }
    public ArrayList<Users> getUsers(int groupID){
        String sqlString = new String("SELECT name, userID from users where groupID = '"+groupID+"';");
        ResultSet users = database.RunSQLQuery(sqlString);
        ArrayList<Users> userList = new ArrayList<Users>();
        try{
        while(users.next()){
            Users user = new Users();
            user.setUserName(users.getString(1));
            user.setUserID(users.getInt(2));
            userList.add(user);
        }
    }catch (Exception e){
            System.out.println("There was an issue in gettingUsers");
        }
        return userList;
    }
    public int gettingEstimatedTime(int userID,int taskID) {
        String sqlString = new String("SELECT estimatedTime from taskAndUsers join users on users.userID = taskAndUsers.userID where taskAndUsers.taskID = '"+taskID+"' AND users.userID = '"+userID+"';");
        ResultSet groups = database.RunSQLQuery(sqlString);
        int estimatedTime = 0;
        try {
            estimatedTime = groups.getInt(1);
        } catch (Exception e) {
            System.out.println("An error occurred while putting value of column into countOfColumn");
        }
        return estimatedTime;
    }

    public void addingToTempTable(Tasks task) {
    int tempID = getMaxID();
    String groupName = getGroupName(task.getGroupID());
        String sqlString = new String("INSERT INTO tempTasks values('"+tempID+"','"+task.getTaskName()+"','"+task.getTaskID()+"','"+task.getUserName()+"','"+task.getTimeTillCompletition()+"','"+groupName+"');");
        try {
            database.RunSQL(sqlString);
        } catch (Exception e) {
            System.out.println("An error occured with addingToTable");
        }
    }

public int getMaxID(){
    String sqlString = new String("SELECT MAX(tempTaskID) from tempTasks;");
    ResultSet id = database.RunSQLQuery(sqlString);
    int maxID = 0;
    try {
        maxID = id.getInt(1) +1;
    } catch (Exception e) {
        System.out.println("An error occurred while getting the max ID");
    }
    return maxID;
}
    public String getGroupName(int groupID){
        String sqlString = new String("SELECT groupName from groups where groupID = '"+groupID+"';");
        ResultSet id = database.RunSQLQuery(sqlString);
        String nameOfID = "";
        try {
            nameOfID = id.getString(1);
        } catch (Exception e) {
            System.out.println("An error occurred while getting the groupName");
        }
        return nameOfID;
    }
    public void insertBacklog(int userID, float backlog){
            String sqlString = new String("UPDATE  users  SET backLog = '"+backlog+"' where userID = '"+userID+"';");
            try {
                database.RunSQL(sqlString);
            } catch (Exception e) {
                System.out.println("An error occured with addingToTable");
            }
    }
    public float getBacklog(int userID){
        String sqlString = new String("SELECT backLog from users where userID = '"+userID+"';");
        ResultSet id = database.RunSQLQuery(sqlString);
        float backlog = 0;
        try {
            backlog = id.getFloat(1);
        } catch (Exception e) {
            System.out.println("An error occurred while getting the groupName");
        }
        return backlog;
    }

}
        /* get the group number
        from the group number find all the tasks and users associated with it.
        once you get all of them, you make a list of users and each user has an arraylist of a new class that stores taskID, taskName and estimated time of the uder
        You can use this to make it so each user has all it tasks and estimated allocated and you can compare which is the lowest for whom
         */
