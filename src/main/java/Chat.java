import Clavardage.Controller.NetworkController;
import Clavardage.Controller.ThreadController;
import Clavardage.Controller.UserController;
import Clavardage.Interfaces.Acceuil;
import Clavardage.Model.User;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Chat {
    public static final boolean DEBUG = true;
    public static final File OUTPUT = new File("./output");

    public static void main(String[] args) throws IOException {


        UserController uc = UserController.getInstance();
        NetworkController nc = NetworkController.getInstance();
        ThreadController tc = ThreadController.getInstance();

        SwingUtilities.invokeLater(new Runnable() {
             @Override
             public void run() {
                new Acceuil(uc);
             }
        });



/*
        User me = new User("SachaLozere", InetAddress.getLocalHost().toString());
        uc.setCurrentUser(me);
        uc.UserLogin(me);
        uc.UserLogout(me);*/
    }
}

