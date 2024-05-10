package org.example.Controllers;

import java.net.URL;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Separator;

import org.example.Structs.Option;
import org.example.Structs.Question;
import org.example.Structs.QuestionBank;
import org.example.Helpers.QuestionParser;

public class TestSceneController implements Initializable {
    private QuestionBank questionBank;

    @FXML
    private VBox body_main;

    @FXML
    private VBox right_pannel;

    @FXML
    private Label header;

    @FXML
    void submitTest(Event event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Scanner scanner = new Scanner(StudentLoginController.questionsRaw);
        questionBank = QuestionParser.parseQuestion(scanner);

        try {
            renderQuestion();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void renderQuestion() throws Exception {

        ArrayList<Question> questions = questionBank.getQuestions();
        int question_number = 1;
        for (Question question : questions) {
            VBox question_box = new VBox();
            question_box.maxHeight(-1);
            String question_str = String.format("Q%d) %s", question_number, question.getQuestion());

            Text question_tArea = new Text(question_str);

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
            body_main.getChildren().addLast(question_box);
            // -----------
            question_number++;
        }

    }

}
