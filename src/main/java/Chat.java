import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Chat {

    public static void main(String[] args) throws SocketException, UnknownHostException {

        User me = new User("luz", InetAddress.getLocalHost().toString());
        try {


            new Thread(() -> {
                try {
                    me.ReceiveMessages();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // Simulation de la connexion de l'utilisateur 1
            me.Connect();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}
