package Clavardage.Model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ThreadUser {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    public DataOutputStream getOutputStream() {
        return out;
    }
    public DataInputStream getInputStream() {
        return in;
    }
    public ThreadUser(Socket socket) {
        this.socket = socket;
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
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
