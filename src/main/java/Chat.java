import Controller.UserController;
import Model.User.User;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Chat {


    public static void main(String[] args) throws SocketException, UnknownHostException {


        UserController uc= UserController.getInstance();
        uc.myLogin("Sacha");
        User me = uc.getCurrentUser();
        User other = new User("Eole","192.168.123.132");
        uc.UserLogin(other);
        try {


            new Thread(() -> {
                try {
                    uc.ReceiveMessages();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // Simulation de la connexion de l'utilisateur 1
            uc.Connect(me);
            uc.Connect(other);


            /*uc.stopListening();

        new Thread(() -> {
            try {
                uc.ReceiveMessages();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();


        uc.UserLogout(other);*/

    } catch (Exception e) {
        e.printStackTrace();
    }
    }

}
