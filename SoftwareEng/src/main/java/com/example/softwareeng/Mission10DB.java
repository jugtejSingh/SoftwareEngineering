package com.example.softwareeng;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Mission10DB {
    DBConnection database;

    public Mission10DB() {
        database = ConnectionFile.database;
    }

    public ArrayList<Ranks> getRanksForMission10(){
        String sqlString = new String("SELECT rank, name, points, streak FROM leaderboard");
        ResultSet id = database.RunSQLQuery(sqlString);
        ArrayList<Ranks> rankList = new ArrayList<>();
        try {

            while (id.next()) {
                Ranks rank = new Ranks();
                rank.setRank(id.getInt("rank"));
                rank.setName(id.getString("name"));
                rank.setPoints(id.getInt("points"));
                rank.setStreak(id.getInt("streak"));
                rankList.add(rank);
            }
        }catch (Exception e){
            System.out.println("Error retriving from ranks");
        }
        return rankList;
    }
    public ArrayList<Users> getListOfUsersInTempTask() {
        String sqlString = new String("SELECT userName from tempTasks group by userName");
        ResultSet id = database.RunSQLQuery(sqlString);
        ArrayList<Users> userNames = new ArrayList<>();
        try {
            while (id.next()) {
                Users user = new Users();
                user.setUserName(id.getString("userName"));
                userNames.add(user);
            }
        }catch (Exception e){
            System.out.println("Error occured when retrieving data from mission10");
        }
            ArrayList userList = getList(userNames);
            return userList;
        }

    public ArrayList<Users> getList(ArrayList<Users> userList){
        for (int i = 0; i <userList.size();i++) {
            String sqlString1 = new String("select COUNT(userName) from tempTasks where userName = '" + userList.get(i).getUserName() + "'");
            ResultSet TempTasks = database.RunSQLQuery(sqlString1);
            try {
                    userList.get(i).setTempTaskCount(TempTasks.getInt(1));
            }catch (Exception e){
                System.out.println("Error occured when getting tempTask count");}
        }
        return userList;
    }
}
