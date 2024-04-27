package org.example.Controllers;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;

public class LoginSceneController {

    @FXML
    public void onAdminLogin(ActionEvent event) throws Exception {
        new SceneHelper().changeScene("/admin_login_scene.fxml", event);
    }
    
    @FXML
    public void onStudentLogin(ActionEvent event) throws Exception {
        new SceneHelper().changeScene("/student_login_scene.fxml", event);
    }
}
