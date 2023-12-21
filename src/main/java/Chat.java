import Clavardage.Controller.NetworkController;
import Clavardage.Controller.ThreadController;
import Clavardage.Controller.UserController;
import Clavardage.Model.User;

import java.io.File;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Chat {
    public static final boolean DEBUG = true;
    public static final File OUTPUT = new File("./output");

    public static void main(String[] args) throws SocketException, UnknownHostException {


        UserController uc = UserController.getInstance();
        NetworkController nc = NetworkController.getInstance();
        ThreadController tc = ThreadController.getInstance();


        User me = new User("SachaInsa", InetAddress.getLocalHost().toString());
        uc.setCurrentUser(me);
        uc.UserLogin(me);
        nc.Connect(me);



    }
}


