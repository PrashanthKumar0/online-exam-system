package org.example.Controllers;

import javafx.fxml.FXML;
import javafx.event.Event;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import org.example.Helpers.SceneHelper;
import javafx.scene.control.PasswordField;

public class AdminMainController {
    @FXML
    private TextField admin_email;

    @FXML
    private PasswordField admin_password;

    @FXML
    public void login(ActionEvent event) {
        System.out.println(
                "Email : " + admin_email.getText() + " \n" +
                        "Password : " + admin_password.getText() + " \n");
    }

    @FXML
    void back(Event event) throws Exception {
        System.out.println("BACK");
        new SceneHelper().changeScene(IScenesInfo.main_scene, event);
    }
}
