package org.example.Model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.example.Database.SQLDatabaseWrapper;

public class StudentModel {
    private String roll;
    private String name;
    private String email;

    // not part of database
    private boolean is_selected = false;
    public int num_correct_response = -1;

    public static final String tableName = "Student";

    public static void init() throws Exception {
        SQLDatabaseWrapper.getConnection().prepareStatement(
                "CREATE TABLE IF NOT EXISTS " + tableName + "(" +
                        "   Roll TEXT PRIMARY KEY NOT NULL," +
                        "   Name TEXT NOT NULL," +
                        "   Email TEXT NOT NULL" +
                        ");")
                .execute();
    }

    public StudentModel(String roll, String name, String email) throws Exception {
        this.roll = roll;
        this.name = name;
        this.email = email;

        Connection cnxn = SQLDatabaseWrapper.getConnection();
        PreparedStatement stmt = cnxn.prepareStatement(
                "INSERT INTO " + tableName + "(Roll, Name, Email) VALUES(?,?,?);");

        stmt.setString(1, this.roll);
        stmt.setString(2, this.name);
        stmt.setString(3, this.email);

        int rowsAffected = stmt.executeUpdate();

        if (rowsAffected > 0) {
            System.out
                    .println("INSERTED : " + tableName + "(" + this.roll + ", " + this.name + ", " + this.email + ")");
        }
    }

    private StudentModel() {
    }


    public static StudentModel fromStudentID(String roll, String testID) throws Exception {
        StudentModel sm = new StudentModel();
        sm.is_selected = true;
        Connection cnxn = SQLDatabaseWrapper.getConnection();
        PreparedStatement pstmt = cnxn.prepareStatement(
            "SELECT Name, Email FROM " + tableName + " WHERE Roll = ?"
        );

        pstmt.setString(1, roll);

        ResultSet res = pstmt.executeQuery();
        
        if(res.next()){
            String name = res.getString("Name");
            String email = res.getString("Email");
            sm.roll = roll;
            sm.name = name;
            sm.email = email;
            sm.num_correct_response = TestResponseModel.getMarks(roll, testID);
        } else {
            sm = null;
        }

        return sm;
    }

    public int getMarks(){
        return num_correct_response;
    }

    public String getName() {
        return name;
    }

    public void setSelected(boolean val) {
        this.is_selected = val;
    }
    
    public boolean isSelected() {
        return this.is_selected;
    }

    
    public String getEmail() {
        return email;
    }

    public String getRoll() {
        return roll;
    }

    public static ArrayList<StudentModel> getAllStudents() throws Exception {
        ArrayList<StudentModel> students_list = new ArrayList<StudentModel>();

        ResultSet res = SQLDatabaseWrapper
                .getConnection()
                .prepareStatement("SELECT Roll,Name,Email FROM " + tableName)
                .executeQuery();

        while (res.next()) {
            StudentModel student = new StudentModel();
            student.roll = res.getString("Roll");
            student.name = res.getString("Name");
            student.email = res.getString("Email");
            students_list.add(student);
        }

        return students_list;
    }

}
