/*
package Clavardage.Contoller;

import Clavardage.Network.NetworkController;
import Clavardage.Thread.ThreadController;
import Clavardage.Thread.ThreadUser;
import Clavardage.User.User;
import Clavardage.User.UserController;
import org.junit.jupiter.api.Test;

import java.io.DataOutputStream;
import java.net.SocketException;

import static org.mockito.Mockito.*;

public class TCPtest{


        @Test
      void testTcpCommunicationInConference() throws SocketException {
            // Création d'utilisateurs
            User user1 = new User("User1", "127.0.0.1");
            User user2 = new User("User2", "127.0.0.1");
            User user3 = new User("User3", "127.0.0.1");

            // Connexion des utilisateurs
            NetworkController nc = NetworkController.getInstance();
            nc.Connect(user1);
            nc.Connect(user2);
            nc.Connect(user3);

            // Obtention des threads des utilisateurs
            ThreadController threadController = ThreadController.getInstance();
            ThreadUser user1Thread = threadController.getUserThread(user1);
            ThreadUser user2Thread = threadController.getUserThread(user2);
            ThreadUser user3Thread = threadController.getUserThread(user3);

            // Envoi de messages dans la conférence
            user1Thread.sendMessageTCP("Hello from User1 to User2!");
            user2Thread.sendMessageTCP("Hi from User2 to User3!");
            user3Thread.sendMessageTCP("Greetings from User3 to User1!");

            // Ajoutez des assertions pour vérifier que les utilisateurs reçoivent les messages corrects
            // Assurez-vous que les messages sont bien distribués entre les utilisateurs de la conférence
            // Assurez-vous que les utilisateurs non connectés reçoivent un message approprié ou ne déclenchent pas d'erreur
        }
}





*/
