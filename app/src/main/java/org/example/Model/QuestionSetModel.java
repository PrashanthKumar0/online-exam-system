package org.example.Model;

import java.util.ArrayList;
import java.util.UUID;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

    // its intentanally private constructor
    // no external script should access this constructor
    // see getAllQuestionSets() to understand
    private QuestionSetModel() {
    }

    public static ArrayList<QuestionSetModel> getAllQuestionSets() throws Exception {
        ArrayList<QuestionSetModel> all_question_sets = new ArrayList<QuestionSetModel>();

        Connection cnxn = SQLDatabaseWrapper.getConnection();
        PreparedStatement pstmt = cnxn.prepareStatement("SELECT SetID, Desc FROM " + tableName);
        ResultSet res = pstmt.executeQuery();

        while (res.next()) {
            String SetID = res.getString("SetID");
            String Desc = res.getString("Desc");
            QuestionSetModel qs = new QuestionSetModel();
            qs.set_id = SetID;
            qs.desc = Desc;
            all_question_sets.add(qs);
        };

        return all_question_sets;
    }

    public void setDescription(String desc) {

    }

    public String getSetID() {
        return set_id;
    };

    public String getDescription() {
        return desc;
    }

    public static String getDescription(String set_id) throws Exception{
        Connection cnxn = SQLDatabaseWrapper.getConnection();
        PreparedStatement pstmt = cnxn.prepareStatement(
            "SELECT Desc FROM " + tableName+ " WHERE SetID = ?;"
        );
        pstmt.setString(1, set_id);
        ResultSet res = pstmt.executeQuery();

        if(res.next()){
            return res.getString("Desc");
        }
        
        return "[ERR::INVALID_SET_SELECTED_TRY_AGAIN]";
    }
}
