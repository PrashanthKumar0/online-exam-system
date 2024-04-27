package org.example;

import javafx.stage.Stage;
import javafx.application.Application;
import org.example.Helpers.SceneHelper;
import org.example.Controllers.IScenesInfo;

public class App extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        new SceneHelper().changeScene(IScenesInfo.main_scene, stage);
    }

    public void run() {
        launch();
    }
}