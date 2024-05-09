package org.example.Model;

import java.util.UUID;
import java.sql.Connection;
import java.sql.PreparedStatement;

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

    public String getOptionId() {
        return option_id;
    }
    public String getStudentID() {
        return student_id;
    }
}
