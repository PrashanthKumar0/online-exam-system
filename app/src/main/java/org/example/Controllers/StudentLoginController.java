package org.example.Controllers;

import javafx.fxml.FXML;
import javafx.event.Event;
import javafx.scene.control.TextField;

import org.example.Server.Client;
import org.example.Helpers.SceneHelper;

public class StudentLoginController {

    public static Client client;
    public static String testID;
    public static String studentID;
    public static String questionsRaw;

    @FXML
    TextField roll_number;

    @FXML
    TextField server_address;

    @FXML
    TextField test_id;

    @FXML
    void proceedForTest(Event event) throws Exception {
        if (!fetchQuestions()) {
            // show Alert box
            System.out.println("Failed Connecting");
            return;
        }
        new SceneHelper().changeScene(IScenesInfo.test_scene, event);
    }

    boolean fetchQuestions() throws Exception {
        testID = test_id.getText();
        studentID = roll_number.getText();
        String hostString = server_address.getText();
        client = new Client(hostString);

        String res = client.getQuestionsRaw(studentID, testID);

        questionsRaw = res;

        if (questionsRaw == null) {
            return false;
        }

        return true;
    }

    @FXML
    void back(Event event) throws Exception {
        new SceneHelper().changeScene(IScenesInfo.main_scene, event);
    }

}
