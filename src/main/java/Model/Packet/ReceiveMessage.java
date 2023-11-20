package Model.Packet;

import Model.User.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ReceiveMessage {

    private int port;
    private User user;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private BufferedReader bufferedReader;

    public ReceiveMessage(int port, User user) throws IOException {
        this.port = port;
        this.user = user;
        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();
        bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public String receiveMessage() throws IOException {
        return bufferedReader.readLine();
    }

    public void close() throws IOException {
        bufferedReader.close();
        clientSocket.close();
        serverSocket.close();
    }
}