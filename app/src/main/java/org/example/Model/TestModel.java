package org.example.Model;

import java.util.UUID;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.example.Database.SQLDatabaseWrapper;

public class TestModel {
    private String test_id;
    private String question_set_id;
    private boolean is_active;
    public static final String tableName = "Test";

    public static void init() throws Exception {
        SQLDatabaseWrapper
            .getConnection()
            .createStatement()
            .execute(
                    "CREATE TABLE IF NOT EXISTS " + tableName + " (  " +  //
                    "   ID TEXT NOT NULL PRIMARY KEY,                " +  //
                    "   QuestionSetID TEXT NOT NULL,                 " +  //
                    "   IsActive BOOLEAN NOT NULL DEFAULT FALSE      " +  //
                    ");                                              "    //
            );
    }

    private TestModel(){}

    public TestModel(String question_set_id) throws Exception {
        this.test_id = UUID.randomUUID().toString();
        this.question_set_id = question_set_id;

        Connection cnxn = SQLDatabaseWrapper.getConnection();
        PreparedStatement stmt = cnxn.prepareStatement(
            "INSERT INTO " + tableName + "(ID,QuestionSetID) VALUES(?,?);" //
        );

        stmt.setString(1, this.test_id);
        stmt.setString(2, this.question_set_id);

        if (stmt.executeUpdate() > 1) {
            System.out.println("CREATED : " + tableName + "(#" + this.test_id + ")");
        }
    }

    public String getTestID() {
        return test_id;
    }

    public String getQuestionSetId() {
        return question_set_id;
    }

    public boolean isActive(){
        return this.is_active;
    }

    public static TestModel getTest(String test_id) throws Exception{
        TestModel model = new TestModel();
        model.test_id = test_id;

        Connection cnxn = SQLDatabaseWrapper.getConnection();
        PreparedStatement pstmt= cnxn.prepareStatement(
            "SELECT QuestionSetID, IsActive FROM " + tableName + " WHERE ID = ?;"
        );
        
        pstmt.setString(1, test_id);
        ResultSet res = pstmt.executeQuery();

        if(res.next()) {
            model.question_set_id = res.getString("QuestionSetID");
            model.is_active = res.getBoolean("IsActive");
        } else {
            return null;
        }

        return model;
    }

    public boolean activateTest() throws Exception{
        Connection cnxn = SQLDatabaseWrapper.getConnection();
        PreparedStatement stmt = cnxn.prepareStatement(
            "UPDATE "+tableName+" SET IsActive = TRUE WHERE ID = ?;"
        );

        stmt.setString(1, test_id);

        return stmt.executeUpdate() > 1;
    }
    public boolean deActivateTest() throws Exception{
        Connection cnxn = SQLDatabaseWrapper.getConnection();
        PreparedStatement stmt = cnxn.prepareStatement(
            "UPDATE "+tableName+" SET IsActive = FALSE WHERE ID = ?;"
        );

        stmt.setString(1, test_id);

        return stmt.executeUpdate() > 1;
    }
}
