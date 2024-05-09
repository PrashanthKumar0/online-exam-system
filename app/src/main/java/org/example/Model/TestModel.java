package org.example.Model;

import java.util.UUID;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.example.Database.SQLDatabaseWrapper;

public class TestModel {
    private String test_id;
    private String question_set_id;
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


    // TODO : implement these methods
    public void activateTest() throws Exception{
        
    }
    public void deActivateTest() throws Exception{

    }
}
