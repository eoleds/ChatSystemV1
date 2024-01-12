package Clavardage.Network;

import Clavardage.Controller.Controller;
import Clavardage.Thread.ThreadController;
import Clavardage.Thread.ThreadUser;
import Clavardage.User.UserController;
import Clavardage.User.User;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class NetworkController implements Controller {
    @Override
    public void initController() {
    }

    private static final NetworkController instance = new NetworkController();
    public static NetworkController getInstance() {
        return instance;
    }

    //********* UDP Client **************************************************************************************//
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

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Methode pour ajouter les utilisateurs déjà connectés quand on se connecte grace leur réponse
    public void SendMessageConnexion(int port, InetAddress IPadresse, User meUser) throws IOException {
        int port1 = port;
        String message1 = "New_User_Response:" + meUser.getUsername();
        byte[] sendData1 = message1.getBytes();
        DatagramPacket packet1 = new DatagramPacket(sendData1, sendData1.length, IPadresse, port1);
        meUser.getSocket().send(packet1);
    }


    public void sendMessageUDP( String message) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        InetAddress addr= InetAddress.getByName("255.255.255.255");
        int port = 8888;
        byte[] buff = message.getBytes();
        DatagramPacket packet = new DatagramPacket(buff, buff.length, addr, port);
        socket.send(packet);
        socket.close();
    }



    public void sendMessageTCP(User receiver, String message) {
        ThreadController threadController = ThreadController.getInstance();
        ThreadUser receiverThread = threadController.getUserThread(receiver);

        if (receiverThread != null) {
            receiverThread.sendMessageTCP(message);
        } else {
            // Gérer le cas où le thread de l'utilisateur n'est pas trouvé
            System.out.println("[NetworkController]: Thread de l'utilisateur non trouvé");
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

    //*********** SERVEUR UDP**************//
    public void ReceiveMessagesTCP() throws IOException {
        UserController uc = UserController.getInstance();
        int port = 9999; // Specify the port to listen on
        boolean listening = true;
        try {
            DatagramSocket socket = new DatagramSocket(port);
            DatagramPacket receivePacket = new DatagramPacket(new byte[1024], 1024);

            while (listening) {
                socket.receive(receivePacket);




            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ReceiveMessagesUDP() throws IOException {
        UserController uc = UserController.getInstance();
        int port = 8888; // Specify the port to listen on
        boolean listening = true;
        try {
            DatagramSocket socket = new DatagramSocket(port);
            DatagramPacket receivePacket = new DatagramPacket(new byte[1024], 1024);

            while (listening) {
                socket.receive(receivePacket);
                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                String senderAddress = receivePacket.getAddress().getHostAddress();
                int senderPort = receivePacket.getPort();
                System.out.println("Greetings UDP, " + senderAddress + " : " + senderPort + " : " + message);

                if (message.startsWith("New_User:")) {
                    String username = message.substring(9);
                    if (!uc.getUsernames().contains(username)) {
                        User user = new User(username, senderAddress);
                        uc.addUser(user);
                    } else {
                        System.out.println("[apres New_User] : Username " + username + " deja utilise");
                    }
                    System.out.println(uc.getUsernames());
                    NetworkController nc = NetworkController.getInstance();
                    nc.SendMessageConnexion(8888, receivePacket.getAddress(), uc.getCurrentUser());
                } else if (message.startsWith("New_User_Response:")) {
                    String username = message.substring(18);
                    User user = new User(username, senderAddress);
                    uc.addUser(user);
                    System.out.println("utilisateurs connectés:"+uc.getUsernames());
                } else if (message.startsWith("User_Disconnected:")) {
                    String username = message.substring(18);
                    User disconnectedUser = uc.getUserByUsername(username);
                    if (disconnectedUser != null) {
                        uc.getUserList().remove(disconnectedUser);
                        System.out.println("User " + username + " disconnected.");
                    } else {
                        System.out.println("User already disconnected: " + username);
                    }
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
