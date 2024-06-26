package org.example;

import java.io.File;
import java.util.Scanner;
import org.example.Helpers.QuestionParser;
import org.example.Structs.QuestionBank;

import org.example.Model.TestModel;
import org.example.Model.OptionModel;
import org.example.Model.StudentModel;
import org.example.Model.QuestionModel;
import org.example.Model.QuestionSetModel;
import org.example.Model.TestResponseModel;
import org.example.Server.ServerMain;
import org.example.Model.ModelsInitializer;
import org.example.Model.TestCandidatesModel;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import org.example.Database.SQLDatabaseWrapper;
import org.example.Helpers.ServerHelper;

public class Main {

    public static void main(String[] args) throws Exception {
        // test();

        initDatabase();
        // initializeServer();

        App app = new App();
        app.run();
    }

    // private static void initializeServer() {
    //     // spawn a new server thread
    //     new Thread(new Runnable() {
    //         @Override
    //         public void run() {
    //             try {
    //                 ServerSocket serverSocket = new ServerSocket(1000);
    //                 ServerMain server = new ServerMain(serverSocket);
    //                 System.out.println("Server Listening on : " + ServerHelper.getLocalIpAddress()+":1000");
    //                 server.run();
    //             } catch (Exception e) {
    //                 e.printStackTrace();
    //             }
    //         }
    //     }).start();
    // }

    private static void initDatabase() {
        try {

            SQLDatabaseWrapper.connect();

            ModelsInitializer.init();

            // test_db();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // SQLDatabaseWrapper.close();
        }

    }

    static void test_db() throws Exception {
        QuestionSetModel qsm = new QuestionSetModel("Hello World");
        QuestionModel qm = new QuestionModel(qsm.getSetID(), "Hello World");
        OptionModel om = new OptionModel(qm.getQID(), "Hello World", true);
        OptionModel om2 = new OptionModel(qm.getQID(), "Hello World Incorrect", false);
        StudentModel sm = new StudentModel("22/11/EC/040", "prashanth kumar", "anonymous0@jnu.ac.in");

        TestModel test = new TestModel(qsm.getSetID());

        String TestID = test.getTestID();
        String StudentID = sm.getRoll();

        TestCandidatesModel tcm = new TestCandidatesModel(TestID, StudentID);
        TestResponseModel tr = new TestResponseModel(StudentID, om.getOptID(), test.getTestID());

        //// om.setDescription("Hello Bhai");
    }

    // static void test()  throws Exception {
    //     System.err.println("__________BEGIN TEST_________________");
    //     File file = new File("C:\\Users\\Prashanth Kumar\\Desktop\\sample_questions.txt");
    //     Scanner sc = new Scanner(file);
    //     QuestionBank qb = QuestionParser.parseQuestion(sc);
    //     qb.print();
    //     sc.close();
    // }
}
