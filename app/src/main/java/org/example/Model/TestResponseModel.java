package org.example.Model;

import java.util.UUID;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.example.Database.SQLDatabaseWrapper;

public class TestResponseModel {
    private String id;
    private String test_id;
    private String option_id;
    private String student_id;

    public static final String tableName = "TestResponse";

    public static void init() throws Exception {
        //! NOTE: TestID can be deduced from OptionID but we keep it here for simplicity
        SQLDatabaseWrapper
                .getConnection()
                .createStatement()
                .execute(
                        "CREATE TABLE IF NOT EXISTS " + tableName + " (  " +
                        "   ID TEXT NOT NULL PRIMARY KEY,                " +
                        "   TestID TEXT NOT NULL,                        " +
                        "   OptionID TEXT NOT NULL,                      " +
                        "   StudentID TEXT NOT NULL                      " +
                        ");                                              "   //
                );
    }

    public TestResponseModel(String student_id, String option_id, String test_id) throws Exception {

        this.test_id = test_id;
        this.option_id = option_id;
        this.student_id = student_id;
        this.id = UUID.randomUUID().toString();

        Connection cnxn = SQLDatabaseWrapper.getConnection();
        PreparedStatement stmt = cnxn.prepareStatement(
            "INSERT INTO " + tableName + "(ID,TestID,OptionID,StudentID) VALUES(?,?,?,?);" //
        );

        stmt.setString(1, this.id);
        stmt.setString(2, this.test_id);
        stmt.setString(3, this.option_id);
        stmt.setString(4, this.student_id);

        if (stmt.executeUpdate() > 1) {
            System.out.println("CREATED : " + tableName + "(#" + this.id +", TestID:" + this.test_id+ ", OptionID : " + this.option_id + ", StudentID" + this.student_id + ");");
        }
    }


    public static void remove(String studentID, String OptionID, String testID) throws Exception {
        Connection cnxn = SQLDatabaseWrapper.getConnection();
        PreparedStatement stmt = cnxn.prepareStatement(
            "DELETE FROM " + tableName + " WHERE TestID = ? AND OptionID = ? AND StudentID = ?"
        );

        stmt.setString(1, testID);
        stmt.setString(2, OptionID);
        stmt.setString(3, studentID);

        if(stmt.executeUpdate() > 0) {
            System.out.println("REMOVED OPTION " + OptionID + " FROM STUDENT  " + studentID +" In test " + testID);
        }
    }

    public static int getMarks(String studentID, String testID) throws Exception {
        int marks = 0;
        Connection cnxn = SQLDatabaseWrapper.getConnection();
        PreparedStatement pstmt = cnxn.prepareStatement(
            "SELECT OptionID FROM "+ tableName + " WHERE TestID = ? AND StudentID = ?;"
        );
        pstmt.setString(1, testID);
        pstmt.setString(2, studentID);

        ResultSet res = pstmt.executeQuery();
        
        while(res.next()){
            String optionID = res.getString("OptionID");
            OptionModel option = OptionModel.fromOptionID(optionID);
            if(option.isCorrect()) {
                marks++;
            }
        }


        return marks;
    }

    public String getOptionId() {
        return option_id;
    }
    public String getStudentID() {
        return student_id;
    }
}
