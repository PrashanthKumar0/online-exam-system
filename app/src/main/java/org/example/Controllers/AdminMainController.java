package org.example.Controllers;

import java.io.File;
import javafx.fxml.FXML;
import java.util.Scanner;
import javafx.stage.Stage;
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.scene.control.Button;
import javafx.application.Application;

import org.example.Helpers.QuestionParser;
import org.example.Helpers.SceneHelper;
import org.example.Structs.QuestionBank;

public class AdminMainController extends Application {

    Stage stage;
    FileChooser filechooser = new FileChooser();
    QuestionBank question_bank = new QuestionBank();

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
    }

    @FXML
    private Button test_overview_btn;

    @FXML
    private Button upload_question_btn;

    @FXML
    void back(Event event) throws Exception {
        new SceneHelper().changeScene(IScenesInfo.main_scene, event);
    }

    @FXML
    void onEditStudents(ActionEvent event) {
        System.out.println("on edit students");
    }

    @FXML
    void onTestOverview(ActionEvent event) {
        System.out.println("on test overview");
    }

    @FXML
    void onUploadQuestion(ActionEvent event) throws Exception {
        File file = filechooser.showOpenDialog(stage);
        if (file != null) {
            openFile(file);
        }
        // System.out.println("on upload question");
    }

    private void openFile(File file) throws Exception {
        Scanner scanner = new Scanner(file);
        question_bank = QuestionParser.parseQuestion(scanner);
        question_bank.print();
        scanner.close();
    }
}
