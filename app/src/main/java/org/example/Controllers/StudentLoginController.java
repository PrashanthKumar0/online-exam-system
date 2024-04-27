package org.example.Controllers;

import javafx.fxml.FXML;
import javafx.event.Event;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.example.Helpers.SceneHelper;
import javafx.scene.control.Alert.AlertType;

public class StudentLoginController {
    @FXML
    TextField jnu_email_id;

    @FXML
    TextField test_access_code;

    @FXML
    void proceedForTest(ActionEvent event) {
        if (!checkInputs())
            return;

    }

    boolean checkInputs() {
        // send access code and email id to server / admin
        //
        Alert a = new Alert(AlertType.ERROR);
        a.setContentText("Invalid Access Code");
        a.show();
        return false;
    }

    @FXML
    void back(Event event) throws Exception {
        new SceneHelper().changeScene(IScenesInfo.main_scene, event);
    }
}
