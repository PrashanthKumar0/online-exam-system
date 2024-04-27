package org.example.Controllers;

import javafx.fxml.FXML;
import javafx.event.Event;
import org.example.Helpers.SceneHelper;

public class MainSceneController {

    @FXML
    public void onAdminLogin(Event event) throws Exception {
        new SceneHelper().changeScene(IScenesInfo.admin_main_scene, event);
    }
    
    @FXML
    public void onStudentLogin(Event event) throws Exception {
        new SceneHelper().changeScene(IScenesInfo.student_login_scene, event);
    }
}
