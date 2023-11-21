package Controller;

import Model.Packet.ContactDiscovery;
import Model.Packet.SendMessage;
import Model.User.User;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class UserController implements Controller {

    private static final UserController instance = new UserController();

    private List<User> userList;

    public List<User> getUserList() {
        return userList;
    }

    private User currentUser;
    public User getCurrentUser() {
        return currentUser;
    }

    private boolean listening = true; // Variable de contrôle

    // ...

    public void stopListening() {
        listening = false;
    }
    private UserController() {
    }

    @Override
    public void initController() {
        userList = new ArrayList<>();

    }

    public static UserController getInstance() {
        return instance;
    }

    ContactDiscovery ContactList = new ContactDiscovery();

    public void myLogin(String name) {
        try {
            this.currentUser = new User(name, InetAddress.getLocalHost().toString());
        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public void UserLogin(User user) {
        System.out.println("[UserController]: New connexion detected " + user);

        if (!ContactList.getUserList().contains(user))
            ContactList.addUser(user);
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
            System.out.println("Broadcast sent successfully");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void UserLogout(User user)
    {
        try{
        InetAddress broadcastAddress = InetAddress.getByName("255.255.255.255");
        int port = 8888;
        String message = "Logout" + user.getUuid();
        byte[] sendData = message.getBytes();
        DatagramPacket packet = new DatagramPacket(sendData,sendData.length, broadcastAddress,port);
        user.getSocket().send(packet);
        System.out.println("Broadcast sent successfully");
    } catch (IOException e) {
        throw new RuntimeException(e);
    }

    }

    private void SendMessage(int port, InetAddress IPadresse) throws IOException {
        int port1 = port;
        String message1 = "New_User_Response:" + this.currentUser.getUsername();
        byte[] sendData1 = message1.getBytes();
        DatagramPacket packet1 = new DatagramPacket(sendData1, sendData1.length, IPadresse, port1);
        this.currentUser.getSocket().send(packet1);
    }

    public void CloseSocket()
    {
        this.currentUser.getSocket().close();
    }

    public User getUserByUuid(UUID uuid) {
        for (User user : ContactList.getUserList()) {
            if (user.getUuid().equals(uuid))
                return user;
        }
        return null;
    }
    public void ReceiveMessages() throws IOException {

        int port = 1234; // Specify the port to listen on

        try {
            DatagramSocket socket = new DatagramSocket(port);
            DatagramPacket receivePacket = new DatagramPacket(new byte[1024], 1024);

            while (listening) {
                socket.receive(receivePacket);
                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                String senderAddress = receivePacket.getAddress().getHostAddress();

                int senderPort = receivePacket.getPort();
                System.out.println("Greetings, " + senderAddress + " : " + senderPort + " : " + message);

                if (message.startsWith("New_User:")) {
                    if (!ContactList.getUsernames().contains(message.substring(9))) {
                        User user = new User(message.substring(9), senderAddress);
                        ContactList.addUser(user);
                    }
                    SendMessage(8888, receivePacket.getAddress());

                }//
                else if(message.startsWith("New_User_Response:")) {
                    User user= new User(message.substring(18), senderAddress);
                    ContactList.addUser(user);
                }
                else if (message.startsWith("Logout")) {
                    String uuidString = message.substring(6);
                    UUID uuid = UUID.fromString(uuidString);
                    ContactList.getUserList().remove(getUserByUuid(uuid));
                    System.out.println(ContactList.getUserList());
                }
            }//
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //testcommit

}
