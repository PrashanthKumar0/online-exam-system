package org.example.Model;

import java.util.UUID;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.example.Database.SQLDatabaseWrapper;

public class QuestionSetModel {
    private String set_id;
    private String desc;
    public static final String tableName = "QuestionSet";

    public static void init() throws Exception {
        SQLDatabaseWrapper
                .getConnection()
                .createStatement()
                .execute("CREATE TABLE IF NOT EXISTS " + tableName + "( "
                        + "    SetID TEXT  PRIMARY KEY,                 "
                        + "    Desc  TEXT NOT NULL                      "
                        + ");                                           " //
                );
    }

    public QuestionSetModel(String desc) throws Exception {
        this.set_id = UUID.randomUUID().toString();
        this.desc = desc;


        Connection cnxn = SQLDatabaseWrapper.getConnection();
        PreparedStatement pstmt = cnxn.prepareStatement(
                "INSERT INTO " + tableName + "(SetID,Desc) VALUES(?,?);");
        pstmt.setString(1, set_id);
        pstmt.setString(2, desc);
        int rowsAffected = pstmt.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Created " + tableName + "#" + set_id + "(" + desc + ")");
        }
    }

    public void setDescription(String desc) {

    }

    public String getSetID() {
        return set_id;
    };

    public String getDescription() {
        return desc;
    }

    public static String getDescription(int set_id) {
        // select from db by set_id
        return "";
    }
}
