package org.example;

// import java.io.File;
// import java.util.Scanner;
// import org.example.Helpers.QuestionParser;
// import org.example.Structs.QuestionBank;
import org.example.Model.OptionModel;
import org.example.Model.QuestionModel;
import org.example.Model.QuestionSetModel;
import org.example.Model.StudentModel;
import org.example.Database.SQLDatabaseWrapper;

public class Main {
    public static void main(String[] args) throws Exception {
        // test();
        initDatabase();
        App app = new App();
        app.run();
    }

    private static void initDatabase() throws Exception {
        try {

            SQLDatabaseWrapper.connect();

            // init all model
            QuestionSetModel.init();
            QuestionModel.init();
            StudentModel.init();
            OptionModel.init();

            // test_db();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // SQLDatabaseWrapper.close();
        }

    }

    static void test_db() throws Exception{
        QuestionSetModel qsm = new QuestionSetModel("Hello World");
        QuestionModel qm = new QuestionModel(qsm.getSetID(), "Hello World");
        OptionModel om = new OptionModel(qm.getQID(), "Hello World", true);
        StudentModel sm = new StudentModel("22/11/EC/040", "prashanth kumar", "anonymous0@jnu.ac.in");
        //// om.setDescription("Hello Bhai");
    }

    static void test() {
        // System.err.println("__________BEGIN TEST_________________");
        // File file = new File("C:\\Users\\Prashanth
        // Kumar\\Desktop\\sample_questions.txt");
        // Scanner sc = new Scanner(file);
        // QuestionBank qb = QuestionParser.parseQuestion(sc);
        // qb.print();
        // sc.close();
    }
}
