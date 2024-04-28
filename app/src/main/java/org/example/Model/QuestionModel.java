package org.example.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.UUID;
import org.example.Database.SQLDatabaseWrapper;

public class QuestionModel {
    private String q_id;
    private String set_id;
    private String desc;
    public static final String tableName = "Question";

    public static void init() throws Exception {
        SQLDatabaseWrapper
                .getConnection()
                .createStatement()
                .execute("CREATE TABLE IF NOT EXISTS " + tableName + "( "
                        + "    QID   TEXT NOT NULL PRIMARY KEY,         "
                        + "    SetID TEXT NOT NULL,                     "
                        + "    Desc  TEXT NOT NULL                      "
                        // +" FORIEGN KEY (SetID) REFERENCES QuestionSet(SetID) "+
                        + ");                                           " //
                );
    }

    public QuestionModel(String set_id, String desc) throws Exception {
        this.q_id = UUID.randomUUID().toString();
        this.set_id = set_id;
        this.desc = desc;

        Connection cnxn = SQLDatabaseWrapper.getConnection();
        PreparedStatement pstmt = cnxn.prepareStatement(
                "INSERT INTO " + tableName + "(QID, SetID, Desc) VALUES(?, ?, ?);");
        pstmt.setString(1, q_id);
        pstmt.setString(2, set_id);
        pstmt.setString(3, desc);
        int rowsAffected = pstmt.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("CREATED : " + tableName + "#" + q_id + "(" + set_id + ", " + desc + ")");
        }
    }

    public void setDescription(String desc) throws Exception {
        Connection cnxn = SQLDatabaseWrapper.getConnection();
        PreparedStatement pstmt = cnxn.prepareStatement(
                "Update " + tableName + " SET Desc = ? WHERE QID = ?;");
        pstmt.setString(1, desc);
        pstmt.setString(2, q_id);
        int affectedRows = pstmt.executeUpdate();
        if (affectedRows > 0) {
            System.out.println("Update : " + tableName + "#" + q_id + "(Desc = " + desc + ")");
        }
    }

    public String getQID() {
        return q_id;
    }

    public void setSetID(int set_id) {

    }

    public String getDescription() {
        return desc;
    }

    public String getSetID() {
        return set_id;
    }

    public static String getDescription(int set_id) {
        // select from db by set_id
        return "";
    }
}
