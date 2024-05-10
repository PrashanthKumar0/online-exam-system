package org.example.Model;

import java.util.UUID;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.example.Database.SQLDatabaseWrapper;

public class TestCandidatesModel {
    private String id;
    private String test_id;
    private String student_id;

    public static final String tableName = "TestCandidate";

    public static void init() throws Exception {
        SQLDatabaseWrapper
                .getConnection()
                .createStatement()
                .execute(
                        "CREATE TABLE IF NOT EXISTS " + tableName + " (  " +
                        "   ID TEXT NOT NULL PRIMARY KEY,                " +
                        "   TestID TEXT NOT NULL,                        " +
                        "   StudentID TEXT NOT NULL                      " +
                        ");                                              "   //
                );
    }

    public TestCandidatesModel(String test_id, String student_id) throws Exception {

        this.test_id = test_id;
        this.student_id = student_id;
        this.id = UUID.randomUUID().toString();

        Connection cnxn = SQLDatabaseWrapper.getConnection();
        PreparedStatement stmt = cnxn.prepareStatement(
            "INSERT INTO " + tableName + "(ID,TestID,StudentID) VALUES(?,?,?);" //
        );

        stmt.setString(1, this.id);
        stmt.setString(2, this.test_id);
        stmt.setString(3, this.student_id);

        if (stmt.executeUpdate() > 1) {
            System.out.println("CREATED : " + tableName + "(#" + this.id + ", TestID : " + this.test_id + ", StudentID" + this.student_id + ");");
        }
    }

    public static boolean canStudentGiveTest(String testID, String studentID) throws Exception {
        Connection cnxn = SQLDatabaseWrapper.getConnection();
        PreparedStatement pstmt = cnxn.prepareStatement(
            "SELECT ID FROM "+tableName+" WHERE TestID = ? AND StudentID = ?;"
        );

        pstmt.setString(1, testID);
        pstmt.setString(2, studentID);
        
        ResultSet res = pstmt.executeQuery();
        if(res.next()){
            return true;
        }
        return false;
    }


    public String getTestID() {
        return test_id;
    }

    public String getStudentID() {
        return student_id;
    }
}
