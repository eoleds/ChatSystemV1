import Controller.NetworkController;
import Controller.UserController;
import Model.User.User;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Chat {


    public static void main(String[] args) throws SocketException, UnknownHostException {


        UserController uc= UserController.getInstance();
        NetworkController nc = NetworkController.getInstance();
        //uc.myLogin("Sacha");

        User me=new User("Michel", InetAddress.getLocalHost().toString());

        User eole = new User("Eole","192.168.123.132");
        User eole2 =  new User("Eole","192.168.123.133");
        uc.UserLogin(eole);
       uc.UserLogin(eole2);
        uc.UserLogin(me);

        // Simulation de la connexion de l'utilisateur 1
        nc.Connect(me);
        nc.Connect(eole);
        nc.Connect(eole2);
        System.out.println(uc.getUsernames());
        //
        System.out.println("test deco");
        nc.UserLogout(eole);
        nc.UserLogout(me);
        System.out.println(uc.getUsernames());
        nc.Connect(me);


        try {

            new Thread(() -> {
                try {
                    uc.ReceiveMessages();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();



    } catch (Exception e) {
        e.printStackTrace();
    }
    }

}


//network controller : broadcast, check unicité username sur le reseau
//