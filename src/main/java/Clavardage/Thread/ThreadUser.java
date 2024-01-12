package Clavardage.Thread;

import Clavardage.User.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ThreadUser extends Thread {
    public User meUser;

    public User otherUser;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public DataOutputStream getOutputStream() {
        return out;
    }
    public DataInputStream getInputStream() {
        return in;
    }
    public ThreadUser(Socket socket,User meUser,User otherUser) {
        this.socket = socket;
        this.meUser = meUser;
        this.otherUser = otherUser;
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            this.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessageTCP(String message) {
        try {
            // Utilisez le flux de sortie pour envoyer le message au client
            out.writeUTF(message);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void close() throws IOException {
        socket.close();
        in.close();
        out.close();
    }


}
