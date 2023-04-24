package com.example.softwareeng;

import java.sql.ResultSet;
import java.util.ArrayList;

public class GroupTaskUserDB {
	private DBConnection database;

	public GroupTaskUserDB() {
		database = new DBConnection();
		database.Connect("TaskManagerDB.sqlite");
	}

	void InsertingGroups(String userName) {
		int groupID = GetMaxGroupID() + 1;
		String insertingIntoUsers = new String("INSERT INTO groups(groupID,groupName)VALUES('" + groupID + "','" + userName + "')");
		boolean value = database.RunSQL(insertingIntoUsers);
		if(!value){
			System.out.println("There was an issue inserting users in InsertingUsers");
		}
	}
	public int GetMaxGroupID() {
		String sqlString = new String("SELECT MAX(groupID) from groups");
		ResultSet groups = database.RunSQLQuery(sqlString);
		int groupMax = 0;
		try {
			while (groups.next()) {
				groupMax = groups.getInt(1);
			}
		} catch (Exception e) {
			System.out.println("An error occurred while putting value of column into countOfColumn " + e.getMessage());
		}
		return groupMax;
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
		} catch (Exception e) {
			System.out.println("An error occurred while putting value of column into countOfColumn");
		}
		return answer;
	}

	public ArrayList<Tasks> GetTasks(int groupIDNumber) {
		String sqlString = new String("SELECT taskID, groupID, taskName from tasks WHERE groupid = '" + groupIDNumber + "' ;");
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
		} catch (Exception e) {
			System.out.println("An error occurred while putting value of column into countOfColumn");
		}
		return answer;
	}
	public void DeleteGroup(String groupName) {
		// Should probably add a message if the student does not exist.
		String sqlStringforID = new String("Select groupID FROM groups WHERE groupName = '" + groupName + "';");
		ResultSet groups = database.RunSQLQuery(sqlStringforID);
		int groupID = 0;
		try {
			groupID = Integer.parseInt(groups.getString(1));
		} catch (Exception e) {
			System.out.println("Error occured in deleteUser");
		}
		String sqlString = new String("DELETE FROM groups WHERE groupName = '" + groupName + "';");
		boolean success = database.RunSQL(sqlString);
		String removingFromTaskandUsers = new String("DELETE FROM taskAndUsers where groupID ='" + groupID + "' ");
		if (!success) {
			System.out.println("Failed to run query: " + sqlString);
		}
		String removingFromTasks = new String("DELETE FROM tasks where groupID ='" + groupID + "'");
		if (!success) {
			System.out.println("Failed to run query: " + sqlString);
		}
	}
	public int gettingTaskID(String taskName){
		String sqlString = new String("SELECT taskID from tasks where taskName = '" +taskName+ "' ;");
		ResultSet tasks = database.RunSQLQuery(sqlString);
		int taskID = 0;
		try {
			while (tasks.next()) {
				taskID = tasks.getInt(1);
			}
		} catch (Exception e) {
			System.out.println("An error occurred while putting value of column into countOfColumn");
		}
		return taskID;

	}
}