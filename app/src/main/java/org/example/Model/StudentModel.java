package org.example.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.example.Database.SQLDatabaseWrapper;

public class StudentModel {
    private String roll;
    private String name;
    private String email;

    // not part of database
    private boolean is_selected = false;

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
