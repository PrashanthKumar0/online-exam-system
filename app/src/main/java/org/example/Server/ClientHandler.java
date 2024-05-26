package org.example.Server;

import java.net.Socket;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.example.Model.TestModel;
import org.example.Model.TestResponseModel;
import org.example.Structs.QuestionBank;
import org.example.Structs.Student;
import org.example.Controllers.AdminMainController;
import org.example.Model.QuestionModel;
import org.example.Model.StudentModel;
import org.example.Model.TestCandidatesModel;

public class ClientHandler implements Runnable {
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    private String testID;
    private String studentID;

    private boolean isRootClient = false;
    private static boolean isRootConected = false;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

            // String res = this.bufferedReader.readLine();
            // sendMessage(res);

            String[] res = this.bufferedReader.readLine().split(":");
            // System.out.println("RES LEN : "+res.length);

            if (res[0].equals(IServerResponses.rootClientHandshake)) {
                if (!ClientHandler.isRootConected) {
                    isRootClient = true;
                    ClientHandler.isRootConected = true;
                    sendMessage(IServerResponses.connected);
                } else {
                    sendMessage(IServerResponses.failedConnecting);
                    System.out.println("Root Client already connected");
                }
                return;
            }

            this.testID = res[0];
            this.studentID = res[1];

            System.out.println("Recieved (" + testID + " , " + studentID + ")");
            // * Check if test is valid and is active
            TestModel test = TestModel.getTest(testID);
            if (test == null || !test.isActive()) {
                sendMessage(IServerResponses.failedConnecting);
                closeEverything();
                return;
            }

            // * Check if this student can give test
            if (!TestCandidatesModel.canStudentGiveTest(testID, studentID)) {
                sendMessage(IServerResponses.ineligibleForTest);
                closeEverything();
                return;
            }

            // * on successful server sends questions
            sendMessage(IServerResponses.connected);
            sendMessage(QuestionModel.getAllQuestionsRaw(test.getQuestionSetId(), true));
            sendMessage(IServerResponses.endResponseSignature);
            System.out.println("Sent QuestionSet#" + test.getQuestionSetId() + " to Student(" + studentID + ")");
            clientHandlers.add(this);

        } catch (Exception e) {
            e.printStackTrace();
            // closeEverything(socket, bufferedReader, bufferedWriter);
            closeEverything();
        }
    }

    public void closeEverything() {
        // TODO : notify that client is closed (connection drop)
        clientHandlers.remove(this);
        try {
            // ? closing server is enough as it implicitly closed buffers
            // if (bufferedReader != null) {
            // bufferedReader.close();
            // }

            // if (bufferedWriter != null) {
            // bufferedWriter.close();
            // }
            if (socket != null) {
                socket.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        try {
            bufferedWriter.write(message, 0, message.length());
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        // ! listen for client actions
        while (socket.isConnected()) {
            try {
                if (isRootClient) {
                    handleRootClient();
                } else {
                    handleNormalClient();
                }
            } catch (Exception e) {
                e.printStackTrace();
                closeEverything();
                break;
            }

        }
    }

    void handleRootClient() throws Exception {
        String messageFromClient = bufferedReader.readLine();
        String getStudentInfoRoute = "/get-students-info";
        if (messageFromClient.startsWith(getStudentInfoRoute)) {
            messageFromClient = messageFromClient.substring(getStudentInfoRoute.length());
            String testID = messageFromClient.trim();
            ArrayList<StudentModel> students = TestCandidatesModel.getAllStudentsFromTestID(testID);
            for (StudentModel student : students) {
                String res = student.getRoll() + "," + student.getName() + "," + student.getRoll() + "," + student.getMarks();
                sendMessage(res);
            }
            sendMessage(IServerResponses.endResponseSignature);
        }
    }

    private void handleNormalClient() throws Exception {
        String messageFromClient = bufferedReader.readLine();

        if (messageFromClient.equals(IServerResponses.endResponseSignature)) {
            // send final score
            // TODO : remove from TestCandidatesModel
            closeEverything();
            return;
        }

        String OptionID = messageFromClient.substring(1);
        String studentID = bufferedReader.readLine();
        String testID = bufferedReader.readLine();
        if (!TestCandidatesModel.canStudentGiveTest(testID, studentID)) {
            return;
        }

        if (messageFromClient.startsWith("-")) {
            System.out.println("- Client Unselected OptionID#(" + OptionID + ")  StudentID(" + studentID + ")  testID(" + testID + ")");
            TestResponseModel.remove(studentID, OptionID, testID);
        } else {
            try {
                new TestResponseModel(studentID, OptionID, testID);
            } catch (Exception e) {
                // ! not actually exception? we just ignore if record is already in response
            }
            System.out.println("+ Client Selected OptionID#(" + OptionID + ")  StudentID(" + studentID + ")  testID(" + testID + ")");
        }
    }

}
