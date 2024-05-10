package org.example.Server;

import java.net.ServerSocket;
import java.net.Socket;

// * tutorial : https://www.youtube.com/watch?v=gLfuZrrfKes

public class ServerMain {
    private ServerSocket serverSocket;

    public ServerMain(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void run() {
        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("A new Candidate Connected");

                ClientHandler clientHandler = new ClientHandler(socket);
                // spawn a new thread for each candidate
                new Thread(clientHandler).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
            closeServerSocket();
        }
    }


    public void kill() throws Exception {
        serverSocket.close();
    }

    public void closeServerSocket() {
        try {
            if (serverSocket != null) {
                for(ClientHandler clientHandler :  ClientHandler.clientHandlers){
                    clientHandler.closeEverything();
                }
                serverSocket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
