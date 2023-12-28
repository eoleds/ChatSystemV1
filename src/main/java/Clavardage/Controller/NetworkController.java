package Clavardage.Controller;

import Clavardage.Model.ThreadUser;
import Clavardage.Model.User;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class NetworkController implements Controller{
    @Override
    public void initController() {

    }

    private static final NetworkController instance = new NetworkController();
    public static NetworkController getInstance() {
        return instance;
    }

    public void Connect(User user)
    {
        try {
            InetAddress broadcastAddress = InetAddress.getByName("255.255.255.255");
            int port = 8888;
            String message = "New_User:" + user.getUsername();
            byte[] sendData = message.getBytes();
            DatagramPacket packet = new DatagramPacket(sendData,sendData.length, broadcastAddress,port);
            user.getSocket().send(packet);
            System.out.println("Broadcast sent successfully by " +user.getUsername());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void disconnect(User user) {
        try {
            InetAddress broadcastAddress = InetAddress.getByName("255.255.255.255");
            int port = 8888;
            String message = "User_Disconnected:" + user.getUsername();
            byte[] sendData = message.getBytes();
            DatagramPacket packet = new DatagramPacket(sendData, sendData.length, broadcastAddress, port);
            user.getSocket().send(packet);
            System.out.println("Disconnect message sent successfully by " + user.getUsername());

            // Vous pouvez également ajouter des actions de déconnexion locales ici,
            // comme fermer les threads ou les connexions associés à cet utilisateur.

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void SendMessageConnexion(int port, InetAddress IPadresse, User meUser) throws IOException {
        int port1 = port;
        String message1 = "New_User_Response:" + meUser.getUsername();
        byte[] sendData1 = message1.getBytes();
        DatagramPacket packet1 = new DatagramPacket(sendData1, sendData1.length, IPadresse, port1);
        meUser.getSocket().send(packet1);
    }

    //Methode pour recevoir les messages en UDP
    public void ReceiveMessagesUDP() throws IOException {

        int port = 8888; // Specify the port to listen on
        UserController uc = UserController.getInstance();
        try {
            DatagramSocket socket = new DatagramSocket(port);
            DatagramPacket receivePacket = new DatagramPacket(new byte[1024], 1024);

            while (true) {
                socket.receive(receivePacket);
                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                String senderAddress = receivePacket.getAddress().getHostAddress();
                int senderPort = receivePacket.getPort();
                System.out.println("Greetings, " + senderAddress + " : " + senderPort + " : " + message);

                if (message.startsWith("New_User:")) {
                    String username = message.substring(9);
                    if (!uc.getUsernames().contains(username)) {
                        User user = new User(username, senderAddress);
                        uc.addUser(user);
                    } else {
                        System.out.println("[apres New_User] : Username " + username + " deja utilise");
                    }
                    System.out.println(uc.getUsernames());
                    User me = uc.getCurrentUser();
                    SendMessageConnexion(8888, receivePacket.getAddress(), me);
                } else if (message.startsWith("New_User_Response:")) {
                    String username = message.substring(18);
                    User user = new User(username, senderAddress);
                    System.out.println(uc.getUsernames());

                }  else if (message.startsWith("User_Disconnected:")) {
                String username = message.substring(18);
                User disconnectedUser = uc.getUserByUsername(username);
                    if (disconnectedUser != null) {
                        uc.getUserList().remove(disconnectedUser);
                        System.out.println("User " + username + " disconnected.");
                    } else {
                        System.out.println("Unknown user disconnected: " + username);
                    }
                }else if (message.startsWith("Requete_Ouverture:")) {
                        String[] parties = message.split(":");
                        if (parties.length == 3 && parties[0].equals("Requete_Ouverture_Thread")) {
                            String nomUtilisateur = parties[1];
                            int portCible = Integer.parseInt(parties[2]);
                            ThreadController tc = ThreadController.getInstance();
                            // Vérifier si le thread existe déjà pour cet utilisateur
                            User utilisateurCible = uc.getUserByUsername(nomUtilisateur);
                            ThreadUser threadExistant = tc.getUserThread(utilisateurCible);

                            if (threadExistant == null) {
                                // Le thread n'existe pas encore, ouvrez-le
                                // tc.OuvrirDiscussion(utilisateurCible, portCible);

                                // Vous pouvez également ajouter le thread à votre liste de threads actifs si nécessaire
                            } else {
                                // Le thread existe déjà, vous pouvez gérer cela en conséquence
                                System.out.println("Le thread existe déjà pour l'utilisateur " + nomUtilisateur);
                            }
                        }
                    }
                }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void envoyerRequeteOuvertureThread(User utilisateurCible, int portCible, User utilisateurEmetteur) {
        try {
            String adresseCibleStr = utilisateurCible.getIp();
            InetAddress adresseCible = InetAddress.getByName(adresseCibleStr);

            String message = "Requete_Ouverture_Thread:" + utilisateurEmetteur.getUsername() + ":" + portCible;
            int port = 8888;

            byte[] sendData = message.getBytes();
            DatagramPacket paquet = new DatagramPacket(sendData, sendData.length, adresseCible, port);
            utilisateurEmetteur.getSocket().send(paquet);

            System.out.println("Requête de thread envoyée avec succès par " + utilisateurEmetteur.getUsername());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    }

