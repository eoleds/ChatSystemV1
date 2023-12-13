package Controller;

import Model.User.User;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class UserController implements Controller {

    public static class UserInfo {
        private String username;
        private UUID uuid;

        public UserInfo(String username, UUID uuid) {
            this.username = username;
            this.uuid = uuid;
        }

        public String getUsername() {
            return username;
        }

        public UUID getUuid() {
            return uuid;
        }
    }

    public static final UserController instance = new UserController();
    private List<User> userList = new ArrayList<>();
    private User currentUser;

    public List<User> getUserList() {
        return userList;
    }

    public void addUser(User user) {
        userList.add(user);
    }

    public List<String> getUsernames() {
        List<String> usernames = new ArrayList<>();
        for (User user : userList) {
            usernames.add(user.getUsername());
        }
        return usernames;
    }
    public List<UUID> getUserUUIDs() {
        List<UUID> userUUIDs = new ArrayList<>();
        for (User user : userList) {
            userUUIDs.add(user.getUuid());
        }
        return userUUIDs;
    }


    private boolean listening = true;

    public UserController() {}

    @Override
    public void initController() {
        userList = new ArrayList<>();
    }

    public static UserController getInstance() {
        return instance;
    }

    public boolean UserLogin(User user) {
        if (!getUsernames().contains(user.getUsername())) {
            System.out.println("[UserController]: New connexion detected " + user.getUsername());
            addUser(user);
            System.out.println(getUsernames());
            System.out.println(getUserUUIDs());
            return true;
        } else {
            System.out.println("Username déjà utilisé");
            return false;
        }
    }

    public void UserLogout(User user) {
        // Recherchez l'UserInfo correspondant à l'utilisateur
        User userToRemove = null;
        for (User user1 : userList) {
            if (user1.getUsername().equals(user.getUsername())) {
                userToRemove = user1;
                break;
            }
        }
        System.out.println(getUsernames());
        System.out.println(getUserUUIDs());

        // Supprimez l'UserInfo de la liste
        if (userToRemove != null) {
            userList.remove(userToRemove);
            System.out.println("Local logout: " + user.getUsername());
        }
    }


    public void CloseSocket() {
        this.currentUser.getSocket().close();
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
                    String username = message.substring(9);
                    if (!getUsernames().contains(username)) {
                        User user = new User(username, senderAddress);
                        addUser(user);
                    } else {
                        System.out.println("[apres New_User] : Username " + username + " deja utilise");
                    }
                    System.out.println(getUsernames());
                    NetworkController nc = NetworkController.getInstance();
                    nc.SendMessage(8888, receivePacket.getAddress(), currentUser);
                } else if (message.startsWith("New_User_Response:")) {
                    String username = message.substring(18);
                    User user = new User(username, senderAddress);
                    addUser(user);
                    System.out.println(getUsernames());
                }
                    System.out.println(getUsernames());
                }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


