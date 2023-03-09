package com.example.softwareeng;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
* Constructs a DBConnection object, not connected to a database 
*/
public class DBConnection {
	private Connection conn;
	public DBConnection(){
		conn = null;
	}
	
	/**
	* Connects to a sqlite3 database object
	* Writes a success message to System.out if the connection was successful
	* Writes an error message to System.out if the connection failed
	* Note I am using boolean to report error rather than exceptions.
	* Exceptions are better - I'm just in a hurry
	* @param  filename  Path and file name to a sqlite3 database
	* @return true if the connection was successful, false otherwise
	*/
	public boolean Connect(String filename)
	{
        try {
            String url = "jdbc:sqlite:"+filename;
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to "+filename+" has been established.");
        } 
        catch (SQLException e) {
            System.out.println("Failed to connect to "+filename);        	
            System.out.println(e.getMessage());
            return false;
        } 
        return true;    
	}

	/**
	* Runs the SQL string on the sqlite object
	* If the SQL fails, prints error data to System.out
	* Note I am using boolean to report error rather than exceptions.
	* Exceptions are better - I'm just in a hurry
	* @param  sql A string containing the SQL instruction. Don't forget the semicolon.
	* @return true if the SQL command was performed correctly. False otherwise 
	*/
	public boolean RunSQL(String sql){
		  if(conn == null){
	            System.out.println("There is no database loaded. Cannot run SQL.");
	            return false;
		  }
		  try {
			Statement sqlStatement = conn.createStatement();
			sqlStatement.execute(sql);
		  }
		  catch(SQLException e){
			System.out.println("Failed to execute "+sql);
	             System.out.println(e.getMessage());
	             return false;
		  }
		  return true;
		}

	
	/**
	* Runs the SQL string on the sqlite object
	* If the SQL fails, prints error data to System.out
	*
	* @param  sql A string containing the SQL instruction. Don't forget the semicolon.
	* @return A resultset from the operation
	*/
	public ResultSet RunSQLQuery(String sql){
		  ResultSet result;
		  if(conn == null){
	           System.out.println("There is no database loaded. Cannot run SQL.");
	           return null;
		  }
		  try {
		    Statement sqlStatement = conn.createStatement();
		    result = sqlStatement.executeQuery(sql);
	        }
		  catch(SQLException e){
		    System.out.println("Failed to execute "+sql);
	           System.out.println(e.getMessage());
	           return null;
	        }
		  return result;
		}



}
