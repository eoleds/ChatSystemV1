
package Clavardage.Contoller;

import Clavardage.Network.NetworkController;
import Clavardage.Thread.ThreadController;
import Clavardage.Thread.ThreadUser;
import Clavardage.User.User;
import org.junit.Test;


import java.net.SocketException;

public class TCPtest{


    @Test
      public void testTcpCommunicationInConference() throws SocketException {
            // Création d'utilisateurs
            User user1 = new User("User1", "127.0.0.1");
            User user2 = new User("User2", "127.0.0.1");
            User user3 = new User("User3", "127.0.0.1");

            // Connexion des utilisateurs
            NetworkController nc = NetworkController.getInstance();
            nc.connect(user1);
            nc.connect(user2);
            nc.connect(user3);

            // Obtention des threads des utilisateurs
            ThreadController threadController = ThreadController.getInstance();
            ThreadUser user1Thread = threadController.getUserThread(user1);
            ThreadUser user2Thread = threadController.getUserThread(user2);
            ThreadUser user3Thread = threadController.getUserThread(user3);

            user1Thread.sendMessageTCP("Hello from User1 to User2!");
            user2Thread.sendMessageTCP("Hi from User2 to User3!");
            user3Thread.sendMessageTCP("Greetings from User3 to User1!");


        }
}



