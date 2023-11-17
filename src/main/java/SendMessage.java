import java.io.PrintWriter;
import java.net.Socket;
import java.io.IOException;

public class SendMessage {

    private User user;
    private Socket socket;
    private PrintWriter printWriter;

    public SendMessage(User user) throws IOException {
        this.user = user;
        socket = new Socket(user.getAddress(), 8888);
        printWriter = new PrintWriter(socket.getOutputStream(), true);
    }

    public void sendMessage(String message) {
        printWriter.println(message);
    }

    public void close() throws IOException {
        printWriter.close();
        socket.close();
    }
}