package com.example.softwareeng;

import java.sql.ResultSet;
import java.util.ArrayList;

public class GroupTaskUserDB {
	private DBConnection database;

	public GroupTaskUserDB() {
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
}