package org.example.Controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class LoginSceneController {
    @FXML
    Button admin_login_button;

    @FXML
    public void onAdminLogin(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/admin_login_scene.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
