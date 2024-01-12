import Clavardage.Network.NetworkController;
import Clavardage.Thread.ThreadController;
import Clavardage.User.UserController;
import Clavardage.Interfaces.Acceuil;

import javax.swing.*;
import java.io.File;
import java.io.IOException;

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
                new Acceuil(uc,nc);
             }
        });


    }

}

