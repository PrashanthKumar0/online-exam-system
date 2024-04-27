package org.example.Controllers;

import javafx.fxml.FXML;
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AdminLoginController {
    @FXML
    private TextField admin_email;

    @FXML
    private PasswordField admin_password;

    @FXML
    public void login(ActionEvent event){
        System.out.println(
            "Email : " + admin_email.getText() + " \n"+
            "Password : " + admin_password.getText() + " \n"
        );
    }

    @FXML
    void back(Event event) throws Exception {
        System.out.println("BACK");
        new SceneHelper().changeScene("/login_scene.fxml",event);
    }
}
