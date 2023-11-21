package Controller;

import Model.Packet.ContactDiscovery;
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

    public void stopListening() {
        listening = false;
    }

    private boolean listening = true;

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
            if (!ContactList.getUserList().contains(this.currentUser))
                ContactList.addUser(this.currentUser);

            System.out.println(ContactList.getUsernames());
        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public void UserLogin(User user) {

        if (!ContactList.getUsernames().contains(user.getUsername())) {
            System.out.println("[UserController]: New connexion detected " + user);

            if (!ContactList.getUserList().contains(user))
                ContactList.addUser(user);

            System.out.println(ContactList.getUsernames());
        }else{
            System.out.println("Username deja utilise");
        }
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

        int port = 8888; // Specify the port to listen on

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
                    System.out.println(ContactList.getUsernames());
                    //SendMessage(8888, receivePacket.getAddress(),getCurrentUser());

                }//
                else if(message.startsWith("New_User_Response:")) {
                    User user= new User(message.substring(18), senderAddress);
                    ContactList.addUser(user);
                    System.out.println(ContactList.getUsernames());
                }
                else if (message.startsWith("Logout")) {
                    String uuidString = message.substring(6);
                    UUID uuid = UUID.fromString(uuidString);
                    ContactList.getUserList().remove(getUserByUuid(uuid));
                    System.out.println(ContactList.getUsernames());
                }
            }//
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
