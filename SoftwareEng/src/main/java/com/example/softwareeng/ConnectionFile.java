package com.example.softwareeng;

public class ConnectionFile {
     static DBConnection database;
    public ConnectionFile() {
    }
    public static void Connection(){
        database = new DBConnection();
        database.Connect("TaskManagerDB.sqlite");
    }
}
