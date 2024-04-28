package org.example;

// import java.io.File;
// import java.util.Scanner;
// import org.example.Helpers.QuestionParser;
// import org.example.Structs.QuestionBank;
import org.example.Database.SQLDatabaseWrapper;
import org.example.Model.QuestionModel;
import org.example.Model.QuestionSetModel;


public class Main {
    public static void main(String[] args) throws Exception {
        // test();
        initDatabase();
        App app = new App();
        app.run();
    }


    private static void initDatabase() throws Exception{
        SQLDatabaseWrapper.connect();

        // init all model
        QuestionSetModel.init();
        QuestionModel.init();

        QuestionSetModel qsm = new QuestionSetModel("Hello World");
        QuestionModel qm = new QuestionModel(qsm.getSetID(),"Hello World");
        qm.setDescription("Hello Bhai");
    }

    static void test(){
        // System.err.println("__________BEGIN TEST_________________");
        // File file = new File("C:\\Users\\Prashanth Kumar\\Desktop\\sample_questions.txt");
        // Scanner sc = new Scanner(file);
        // QuestionBank qb = QuestionParser.parseQuestion(sc);
        // qb.print();
        // sc.close();
    }
}
