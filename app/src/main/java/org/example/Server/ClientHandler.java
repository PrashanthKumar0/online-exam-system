package org.example.Server;

import java.net.Socket;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.example.Model.TestModel;
import org.example.Model.QuestionModel;
import org.example.Model.TestCandidatesModel;

public class ClientHandler implements Runnable {
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();

    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    private String testID;
    private String studentID;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

            // String res = this.bufferedReader.readLine();
            // sendMessage(res);

            String[] res = this.bufferedReader.readLine().split(":");
            // System.out.println("RES LEN : "+res.length);

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
            sendMessage(QuestionModel.getAllQuestionsRaw(test.getQuestionSetId()));
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
        String messageFromClient;

        while (socket.isConnected()) {
            try {
                // messageFromClient = bufferedReader.readLine();
                // TODO : parse this message from client
                // * i need to make a parser for this too that interprets client message

            } catch (Exception e) {
                e.printStackTrace();
                closeEverything();
                break;
            }
        }
    }
}
