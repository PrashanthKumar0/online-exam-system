package org.example.Controllers;

import java.io.File;
import java.net.URL;
import java.util.Scanner;
import java.util.ArrayList;
import java.net.ServerSocket;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.event.Event;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.stage.FileChooser;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.fxml.Initializable;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.input.Clipboard;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.application.Application;
import javafx.scene.control.RadioButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.ClipboardContent;
import javafx.scene.control.Alert.AlertType;

import org.example.Structs.Option;
import org.example.Model.TestModel;
import org.example.Structs.Question;
import org.example.Server.ServerMain;
import org.example.Model.OptionModel;
import org.example.Model.StudentModel;
import org.example.Model.QuestionModel;
import org.example.Helpers.SceneHelper;
import org.example.Helpers.ServerHelper;
import org.example.Structs.QuestionBank;
import org.example.Helpers.QuestionParser;
import org.example.Model.QuestionSetModel;
import org.example.Helpers.StudentsManager;
import org.example.Model.TestCandidatesModel;

public class AdminMainController extends Application implements Initializable {

    Stage stage;
    FileChooser filechooser = new FileChooser();
    QuestionBank question_bank = new QuestionBank();
    StudentsManager students_manager = new StudentsManager();
    StudentListRenderer student_list_renderer = new StudentListRenderer(students_manager);

    private static ServerMain serverMain;

    TestModel test;

    @FXML
    VBox main_body;

    @FXML
    VBox right_container;

    @FXML
    private Button test_overview_btn;

    @FXML
    private Button upload_question_btn;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        student_list_renderer.render(right_container);
    }

    @FXML
    void back(Event event) throws Exception {
        new SceneHelper().changeScene(IScenesInfo.main_scene, event);
    }

    @FXML
    void onAddStudents(ActionEvent event) throws Exception {
        File file = filechooser.showOpenDialog(stage);
        if (file != null) {
            Scanner scanner = new Scanner(file);
            students_manager.parseAndSave(scanner);
            // question_bank.print();
            scanner.close();
            student_list_renderer.render(right_container);
        }
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

            main_body.getChildren().clear();
            updateQuestionDom();
            renderSaveQuestionSetControls();
        }
        // System.out.println("on upload question");
    }

    @FXML
    void onShowTestSets(ActionEvent event) throws Exception {
        main_body.getChildren().clear();
        renderTestSetList(main_body);
    }

    // -----------------------
    private static void initializeServer() {
        // spawn a new server thread
        if (serverMain == null) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        ServerSocket serverSocket = new ServerSocket(1000);
                        serverMain = new ServerMain(serverSocket);
                        System.out.println("Server Listening on : " + ServerHelper.getLocalIpAddress() + ":1000");
                        serverMain.run();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            });

            thread.start();
        } // if
    }

    // -----------------------

    private void renderSaveQuestionSetControls() {
        // Save Button
        HBox buttonContainer = new HBox();
        buttonContainer.setSpacing(50.0);
        buttonContainer.setCenterShape(true);
        buttonContainer.setPadding(new Insets(20.0));

        TextField questionSetDescription = new TextField();
        questionSetDescription.setPrefWidth(450.0);

        Button button = new Button("Save Question Set");
        questionSetDescription.setPromptText("Question Set Description");

        buttonContainer.getChildren().addFirst(questionSetDescription);
        buttonContainer.getChildren().addLast(button);

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    saveQuestionSet(questionSetDescription.getText());
                    main_body.getChildren().clear();
                    // SHOW SUCCESS ALERT BOX
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setContentText("Successfully Saved new question set");
                    alert.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        main_body.getChildren().addLast(buttonContainer);
    }

    private void renderTestSetList(VBox body) throws Exception {
        ComboBox<String> dropdown = new ComboBox<String>();
        // dropdown.getItems().add("Hello");
        ArrayList<QuestionSetModel> qs_array = QuestionSetModel.getAllQuestionSets();
        for (QuestionSetModel qs : qs_array) {
            dropdown.getItems().add(qs.getDescription());
        }

        // set onchange listener
        dropdown
                .getSelectionModel()
                .selectedIndexProperty()
                .addListener(new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable, Number oldIdx, Number newIdx) {
                        // System.out.println("Selected :: " + newValue);
                        try {
                            String setID = qs_array.get(newIdx.intValue()).getSetID();
                            question_bank = QuestionBank.fromSetID(setID);

                            main_body.getChildren().clear();
                            updateQuestionDom();

                            // TODO : move in seperate function
                            Button begin_test_button = new Button("Begin Test");
                            body.getChildren().addLast(begin_test_button);

                            begin_test_button.setOnAction((ActionEvent a) -> {
                                beginTest(setID);
                            });
                            // ----------

                            body.getChildren().addFirst(dropdown);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        // dropdown.onchange

        body.getChildren().addFirst(dropdown);

    }

    private void beginTest(String setID) {
        try {

            initializeServer();
            test = new TestModel(setID);
            test.activateTest();
            insertSelectedStudents();
            
            main_body.getChildren().clear();
            renderTestInfo();
            renderTestStudentList();
            renderEndTestButton();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertSelectedStudents() throws Exception {
        ArrayList<StudentModel> students = students_manager.getStudents();
        for(StudentModel student : students) {
            if(student.isSelected()) {
                new TestCandidatesModel(test.getTestID(), student.getRoll());
            }
        } 
    }

    private void renderTestInfo() {
        // Header
        Label test_set_info_header = new Label("Test Set Info");
        test_set_info_header.setCenterShape(true);
        test_set_info_header.setFont(new Font(30.0));
        main_body.getChildren().addLast(test_set_info_header);

        Label test_id_label = new Label(test.getTestID());
        Label question_set_label = new Label("" + test.getQuestionSetId());
        Label server_address_label = new Label("" + ServerHelper.getLocalIpAddress());

        GridPane grid = new GridPane();

        grid.setHgap(10.0);

        grid.addRow(0, new Label("Active Test Set"));
        grid.addRow(0, new Label(question_bank.getSetDesctiption()));

        grid.addRow(1, new Label("Number Of Questions"));
        grid.addRow(1, new Label("" + question_bank.getQuestions().size()));

        grid.addRow(2, new Label("Active TestID"));
        grid.addRow(2, test_id_label);

        grid.addRow(3, new Label("Server Address"));
        grid.addRow(3, server_address_label);

        // grid.addRow(4, new Label("SetId"));
        // grid.addRow(4, question_set_label);

        main_body.getChildren().addLast(grid);

        // click to copy
        test_id_label.setOnMouseClicked((e) -> {
            copyToClipboard(test_id_label.getText());
        });

        server_address_label.setOnMouseClicked((e) -> {
            copyToClipboard(server_address_label.getText());
        });

        question_set_label.setOnMouseClicked((e) -> {
            copyToClipboard(question_set_label.getText());
        });

    }

    private void copyToClipboard(String message) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(message);
        clipboard.setContent(content);
    }

    private void renderTestStudentList() {
        // Header
        Label student_header = new Label("Students List");
        student_header.setCenterShape(true);
        student_header.setFont(new Font(30.0));
        main_body.getChildren().addLast(student_header);

        // Student List
        new StudentListRenderer(students_manager).render(main_body, true);
    }

    private void renderEndTestButton() {
        Button end_test_button = new Button("End Test");
        main_body.getChildren().add(end_test_button);

        end_test_button.setOnAction((ActionEvent e) -> {
            onEndTest();
        });
    }

    private void onEndTest() {
        if (serverMain != null) {
            try {
                test.deActivateTest();
                serverMain = null;
                serverMain.kill();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("TEST OVER !!!!");
    }

    //
    private void updateQuestionDom() throws Exception {

        ArrayList<Question> questions = question_bank.getQuestions();
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
            main_body.getChildren().addLast(question_box);

            // -----------
            question_number++;
        }

    }

    private void saveQuestionSet(String setDesc) throws Exception {
        QuestionSetModel question_set_model = new QuestionSetModel(setDesc);
        for (Question question : question_bank.getQuestions()) {
            String question_desc = question.getQuestion();
            QuestionModel question_model = new QuestionModel(question_set_model.getSetID(), question_desc);

            for (Option option : question.getOptions()) {
                String option_desc = option.getOption();
                boolean is_correct_option = option.isCorrect();
                // create option
                new OptionModel(question_model.getQID(), option_desc, is_correct_option);
            }

        }
    }

    private void openFile(File file) throws Exception {
        Scanner scanner = new Scanner(file);
        question_bank = QuestionParser.parseQuestion(scanner);
        // question_bank.print();
        scanner.close();
    }

}
