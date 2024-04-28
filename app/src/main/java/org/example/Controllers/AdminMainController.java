package org.example.Controllers;

import java.io.File;
import javafx.fxml.FXML;
import java.util.Scanner;
import javafx.event.Event;
import javafx.stage.Stage;
import java.util.ArrayList;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.application.Application;

import org.example.Structs.Option;
import org.example.Structs.Question;
import org.example.Helpers.SceneHelper;
import org.example.Structs.QuestionBank;
import org.example.Helpers.QuestionParser;

public class AdminMainController extends Application {

    Stage stage;
    FileChooser filechooser = new FileChooser();
    QuestionBank question_bank = new QuestionBank();

    @FXML
    VBox main_body;

    @FXML
    private Button test_overview_btn;

    @FXML
    private Button upload_question_btn;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
    }

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
            updateQuestionDom();
        }
        // System.out.println("on upload question");
    }

    private void updateQuestionDom() {
        main_body.getChildren().clear();
        ArrayList<Question> questions = question_bank.getQuestions();
        int question_number = 1;
        for (Question question : questions) {
            VBox question_box = new VBox();
            question_box.maxHeight(-1);
            String question_str = String.format("Q%d) %s", question_number, question.getQuestion());

            Text question_tArea = new Text(question_str);
            // question_tArea.maxHeight(1000.0);

            question_box.getChildren().addFirst(question_tArea);

            ArrayList<Option> options = question.getOptions();

            for (Option option : options) {
                RadioButton option_radio = new RadioButton();
                int num_lines = option.getOption().split("\n").length;
                option_radio.setMinHeight(num_lines * 2.0 * (option_radio.fontProperty().get().getSize()));
                option_radio.setText(option.getOption());
                option_radio.setSelected(option.isCorrect());
                question_box.getChildren().addLast(option_radio);
            }
            question_box.getChildren().addLast(new Separator());
            question_box.setPadding(new Insets(20.0, 40.0, 20.0, 40.0));
            main_body.getChildren().addLast(question_box);

            // -----------
            question_number++;
        }

        // Save Button
        HBox buttonContainer = new HBox();
        buttonContainer.setSpacing(50.0);
        buttonContainer.setCenterShape(true);
        buttonContainer.setPadding(new Insets(20.0));

        TextField questionSetDescription = new TextField();
        questionSetDescription.prefWidth(300.0);

        Button button = new Button("Save Question Set");
        questionSetDescription.setPromptText("Question Set Description");

        buttonContainer.getChildren().addFirst(questionSetDescription);
        buttonContainer.getChildren().addLast(button);

        main_body.getChildren().addLast(buttonContainer);
    }

    private void openFile(File file) throws Exception {
        Scanner scanner = new Scanner(file);
        question_bank = QuestionParser.parseQuestion(scanner);
        // question_bank.print();
        scanner.close();
    }
}
