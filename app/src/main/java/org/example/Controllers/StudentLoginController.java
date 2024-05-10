package org.example.Controllers;

import javafx.fxml.FXML;
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

import org.example.Helpers.SceneHelper;
import org.example.Server.Client;

public class StudentLoginController {
    public static Client client;
    private String questionsRaw;

    @FXML
    TextField roll_number;

    @FXML
    TextField server_address;

    @FXML
    TextField test_id;

    @FXML
    void proceedForTest(ActionEvent event) throws Exception {
        if (!fetchQuestions()) {
            // show Alert box
            System.out.println("Failed Connecting");
            return;
        }
        System.out.println("Connected");

    }

    boolean fetchQuestions() throws Exception {
        String testID = test_id.getText();
        String studentID = roll_number.getText();
        String hostString = server_address.getText();
        client = new Client(hostString);

        String res = client.getQuestionsRaw(studentID, testID);

        if (res == null) {
            return false;
        }

        System.out.println("QUESTIONS : \n" + res);

        this.questionsRaw = res;

        return true;
    }

    @FXML
    void back(Event event) throws Exception {
        new SceneHelper().changeScene(IScenesInfo.main_scene, event);
    }

}
