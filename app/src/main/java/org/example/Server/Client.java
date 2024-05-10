package org.example.Server;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;

public class Client {
    Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    // Will be passed from gui app
    public Client(String hostString) throws Exception {
        InetSocketAddress address = new InetSocketAddress(hostString, 1000);
        this.socket = new Socket(address.getAddress(), address.getPort());

        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
        this.bufferedReader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));

    }

    public String getQuestionsRaw(String studentID, String testID) throws Exception{
        // connect with server
        String connectString = testID + ":" + studentID;
        sendMessage(connectString);
        String readHead = bufferedReader.readLine();
        System.out.println("Head : "+readHead);
        
        if(readHead.equals(IServerResponses.connected)) {
            String rawQuestion = "";
            String line = bufferedReader.readLine();
            System.out.println("BUFFER : " + line);

            //? flaw client will be stuck in infinite loop in case of something wrong?
            while(!line.equals(IServerResponses.endResponseSignature)) {
                rawQuestion += line + "\n";
                line = bufferedReader.readLine();
            }
            return rawQuestion;
        }
        return null;
    }

    public void sendMessage(String message) throws Exception{
        this.bufferedWriter.write(message, 0, message.length());
        this.bufferedWriter.newLine();
        this.bufferedWriter.flush();
    }
}
