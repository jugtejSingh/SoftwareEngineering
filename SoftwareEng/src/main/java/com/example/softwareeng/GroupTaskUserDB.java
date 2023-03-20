package com.example.softwareeng;

import java.sql.ResultSet;
import java.util.ArrayList;

public class GroupTaskUserDB {
	private DBConnection database;
	
	public GroupTaskUserDB(){
		database = new DBConnection();
		database.Connect("TaskManagerDB.sqlite");
	}
	/*
	* Adds a new student to the DBConnection database
	* Sends error string to System.out if the DBConnection reports a failure
	*
	* @param  sm  A StudentMark object with the name and mark
*/
	public ArrayList<Group> GetGroups() {
		String sqlString = new String("SELECT groupName, groupID from groups;");
		ResultSet groups = database.RunSQLQuery(sqlString);
		ArrayList<Group> answer = new ArrayList<Group>();
		try {
			while (groups.next()) {
				Group newGroup = new Group();
				newGroup.setName(groups.getString(1));
				newGroup.setId(groups.getInt(2));
				answer.add(newGroup);
			}
		}
		catch (Exception e){
			System.out.println("An error occurred while putting value of column into countOfColumn");
		}
		return answer;
	}
	public ArrayList<Tasks> GetTasks(int groupIDNumber) {
		String sqlString = new String("SELECT taskID, groupID, taskName from tasks WHERE groupid = '"+groupIDNumber+"' ;");
		ResultSet tasks = database.RunSQLQuery(sqlString);
		ArrayList<Tasks> answer = new ArrayList<Tasks>();
		try {
			while (tasks.next()) {
				Tasks taskObject = new Tasks();
				taskObject.setTaskID(tasks.getInt(1));
				taskObject.setGroupID(tasks.getInt(2));
				taskObject.setTaskName(tasks.getString(3));
				answer.add(taskObject);
			}
		}
		catch (Exception e){
			System.out.println("An error occurred while putting value of column into countOfColumn");
		}
		return answer;
	}
	
	/**
	* Deletes a  student from the DBConnection database
	* Sends error string to System.out if the DBConnection reports a failure
	* Fails silently if the student does not exist
	* @param  studentName  The name of the student to delete

	public void DeleteStudent(String studentName) {
		// Should probably add a message if the student does not exist.
		
		String sqlString = new String("DELETE FROM studentMark WHERE studentName = '"+studentName+"';");
		boolean success = database.RunSQL(sqlString);
		if(!success) {
			System.out.println("Failed to run query: "+sqlString);
		}
	}
	
	/**
	* Changes the mark of a student in the DBConnection database
	* Sends error string to System.out if the DBConnection reports a failure
	* fails silently if the student does not exist
	* @param  sm  A StudentMark object with the name and mark

	public void UpdateMark(String studentName, int mark) {
		// Should probably add a message if the student does not exist
		String sqlString = new String("UPDATE studentMark SET mark="+ mark +" WHERE studentName='"+studentName+"';");

		boolean success = database.RunSQL(sqlString);
		
		if(!success) {
			System.out.println("Failed to run query: "+sqlString);
		}
	}
	
	/**
	* Returns an array list of all students in the database
	* Sends error string to System.out if the DBConnection reports a failure
	* @return  An arraylist containing StudentMark objects for all students in the database

	public ArrayList<StudentMark> GetAllStudents(){
		
		String sqlString = new String("SELECT studentName, mark FROM studentMark;");
		ResultSet studentList = database.RunSQLQuery(sqlString);
		ArrayList<StudentMark> answer = new ArrayList<StudentMark>();
		
		try {
			while(studentList.next()) {
				StudentMark newStudent = new StudentMark();
				newStudent.setName(studentList.getString(1));
				newStudent.setMark(studentList.getInt(2));	
				answer.add(newStudent);
			}
		} catch (SQLException e) {
			System.out.println("Failed to process query in GetAllStudents()");
			System.out.println("SQL attempted: "+sqlString);	
			System.out.println("Error: "+e.getErrorCode());
			System.out.println("Message: "+e.getMessage());			
			e.printStackTrace();
		}
		return answer;
	}
	*/
}
