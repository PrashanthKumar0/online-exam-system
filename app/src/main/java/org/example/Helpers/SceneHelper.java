package org.example.Helpers;

import javafx.scene.Node;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;

public class SceneHelper{
    public void changeScene(String fxml_path, Event event) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource(fxml_path));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void changeScene(String fxml_path, Stage stage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource(fxml_path));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}