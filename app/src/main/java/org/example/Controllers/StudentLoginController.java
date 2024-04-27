package org.example.Controllers;

import javafx.fxml.FXML;
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class StudentLoginController {
    @FXML
    TextField jnu_email_id;

    @FXML
    TextField test_access_code;

    @FXML
    void proceedForTest(ActionEvent event) {
        
    }

    @FXML
    void back(Event event) throws Exception {
        new SceneHelper().changeScene("/login_scene.fxml", event);
    }
}
