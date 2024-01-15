package Clavardage;

import Clavardage.Network.NetworkController;
import Clavardage.User.User;
import Clavardage.User.UserController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class TCPTest {

    public static void main(String[] args) throws SocketException {
        // Créer une instance de NetworkController
        NetworkController networkController = NetworkController.getInstance();

        // Créer un utilisateur factice pour le test
        User user = new User("UtilisateurTest", "192.168.1.3");

        // Appeler la méthode de connexion
        networkController.Connect(user);

        // Envoyer un message en TCP (à un utilisateur factice)
        networkController.sendMessageTCP(user, "Bonjour, ceci est un test TCP.");

        // Fermer la connexion
        networkController.disconnect(user);
    }
}
