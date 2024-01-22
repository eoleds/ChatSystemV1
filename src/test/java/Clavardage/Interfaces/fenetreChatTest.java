package Clavardage.Interfaces;
import org.junit.Before;
import org.junit.Test;
import Clavardage.User.UserController;
import Clavardage.User.User;

import static org.junit.Assert.*;

import java.net.SocketException;

public class fenetreChatTest {

    //test de récupération de l'user d'apres l'interface

    public void testGetUserFromUserClick() throws SocketException {


        UserController userController = UserController.getInstance();
        // Créez une instance de la classe fenetreChat
        fenetreChat fenetre = new fenetreChat("Utilisateur Test");


        // Créez quelques utilisateurs fictifs pour le test
        User user1 = new User("Utilisateur1", "192.168.0.1");
        User user2 = new User("Utilisateur2", "192.168.0.2");
        User user3 = new User("Utilisateur3", "192.168.0.3");


        // Ajoutez les utilisateurs fictifs à la liste des utilisateurs dans UserController
        userController.addUser(user1);
        userController.addUser(user2);
        userController.addUser(user3);

        // Testez le cas où l'utilisateur est trouvé
        User resultUser = fenetre.getUserFromUserClick("Utilisateur1");
        assertEquals(user1, resultUser);

        // Testez le cas où l'utilisateur n'est pas trouvé
        resultUser = fenetre.getUserFromUserClick("UtilisateurInconnu");
        assertNull(resultUser);
    }
}