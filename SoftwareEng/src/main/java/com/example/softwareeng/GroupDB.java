package com.example.softwareeng;

public class GroupDB {
	private DBConnection database;
	
	public GroupDB(){
		database = new DBConnection();
		database.Connect("SoftwareEngineering\\TaskManagerDB.sqlite");
	}
	
	
	/*
	* Adds a new student to the DBConnection database
	* Sends error string to System.out if the DBConnection reports a failure
	*
	* @param  sm  A StudentMark object with the name and mark

	public void AddStudent(Booking sm) {
		String sqlString = new String("INSERT INTO studentMark VALUES('");
		sqlString = sqlString + sm.getName()+"', ";
		sqlString = sqlString+   Integer.toString(sm.getMark());
		sqlString = sqlString+  ");";
		
		boolean success = database.RunSQL(sqlString);
		
		if(!success) {
			System.out.println("Failed to run query: "+sqlString);
		}
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
