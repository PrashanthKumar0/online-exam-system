package org.example.Controllers;

import java.net.URL;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.event.Event;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.RadioButton;

import org.example.Server.Client;
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
        System.out.println(StudentLoginController.questionsRaw);
        Scanner scanner = new Scanner(StudentLoginController.questionsRaw);
        questionBank = QuestionParser.parseQuestion(scanner, true);
        System.out.println("NUM Q : " + questionBank.getQuestions().size());
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

                option_radio.setOnAction((action) -> {
                    try {
                        // order must be same 😢
                        // message is handled in ClientHandler in the same order
                        // (+/-) OptionID    + says selected - says removed
                        // StudentID
                        // testID
                        if (option_radio.selectedProperty().get()) {
                            System.out.println("Select : #(" + option.getId() + ")  " + option.getOption());
                            Client.sendMessage("+" + option.getId());
                        } else {
                            Client.sendMessage("-" + option.getId());
                        }
                        Client.sendMessage(StudentLoginController.studentID); // order is important
                        Client.sendMessage(StudentLoginController.testID); // order is important
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }

            question_box.getChildren().addLast(new Separator());
            question_box.setPadding(new Insets(20.0, 40.0, 20.0, 40.0));
            body_main.getChildren().addLast(question_box);
            // -----------
            question_number++;
        }

    }

}
