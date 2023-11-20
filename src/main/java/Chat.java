import Controller.UserController;
import Model.User.User;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Chat {

    public static void main(String[] args) throws SocketException, UnknownHostException {

        //User me = new User("luz", InetAddress.getLocalHost().toString());
        UserController uc= UserController.getInstance();
        uc.myLogin("Sacha");
        User me = uc.getCurrentUser();
        try {


            new Thread(() -> {
                try {
                    uc.ReceiveMessages();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // Simulation de la connexion de l'utilisateur 1
            uc.Connect();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}
