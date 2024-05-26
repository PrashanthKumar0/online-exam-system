package org.example.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.UUID;

import org.example.Database.SQLDatabaseWrapper;
import org.example.Structs.EOptionType;
import org.example.Structs.Option;

public class OptionModel {
    private String QID;
    private String desc;
    private String OptID;
    private boolean is_correct_option;
    public static String tableName = "Option";

    public static void init() throws Exception {
        SQLDatabaseWrapper
                .getConnection()
                .createStatement()
                .execute("CREATE TABLE IF NOT EXISTS " + tableName + " ("
                        + "  OptID TEXT NOT NULL PRIMARY KEY,           "
                        + "  QID TEXT NOT NULL,                         "
                        + "  Desc TEXT NOT NULL,                        "
                        + "  IsCorrect BOOLEAN NOT NULL                 "
                        + ");                                           " //
                );
    }

    public static ArrayList<Option> getAllOptions(String QID) throws Exception {
        ArrayList<Option> options = new ArrayList<Option>();

        Connection cnxn = SQLDatabaseWrapper.getConnection();
        PreparedStatement pstmt = cnxn.prepareStatement(
            "SELECT Desc , OptID, IsCorrect FROM " + tableName + " WHERE QID = ?"
        );

        pstmt.setString(1, QID);

        ResultSet res = pstmt.executeQuery();

        while (res.next()){
            String option_str = res.getString("Desc");
            String OptID = res.getString("OptID");
            boolean isCorrect = res.getBoolean("IsCorrect");
            EOptionType option_type = isCorrect ? EOptionType.CORRECT : EOptionType.INCORRECT;
            Option option = new Option(option_str, option_type);
            option.setId(OptID);
            options.add(option);
        }

        return options;
    }

    public OptionModel(String q_id, String desc, boolean is_correct_option) throws Exception {
        this.OptID = UUID.randomUUID().toString();
        this.QID = q_id;
        this.desc = desc;
        this.is_correct_option = is_correct_option;

        Connection cnxn = SQLDatabaseWrapper.getConnection();
        PreparedStatement pstmt = cnxn.prepareStatement(
                "INSERT INTO " + tableName + "(OptID, QID, Desc, IsCorrect) VALUES(?, ?, ?, ?);");

        pstmt.setString(1, this.OptID);
        pstmt.setString(2, this.QID);
        pstmt.setString(3, this.desc);
        pstmt.setBoolean(4, this.is_correct_option);

        int rowsAffected = pstmt.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("INSERTED : " + tableName + "#" + this.OptID + "(" + this.QID + ", " + this.desc + ")");
        }
        // pstmt.
    }

    private OptionModel() {}

    public static OptionModel fromOptionID(String optionID) throws Exception {
        OptionModel opt = new OptionModel();
        Connection cnxn = SQLDatabaseWrapper.getConnection();
        PreparedStatement pstmt = cnxn.prepareStatement(
            "SELECT IsCorrect FROM "+ tableName + " WHERE OptionID = ?"
        );
        pstmt.setString(1, optionID);
        ResultSet res = pstmt.executeQuery();
        while(res.next()) {
            opt.is_correct_option = res.getBoolean("IsCorrect");
        }
        return opt;
    }

    public boolean isCorrect() {
        return is_correct_option;
    }

    public String getOptID() {
        return OptID;
    }
    public String getDesc() {
        return desc;
    }
    public String getQID() {
        return QID;
    }
}
