package Controller;

import Model.Packet.ContactDiscovery;
import Model.User.User;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class UserController implements Controller {

    private static final UserController instance = new UserController();

    //private List<User> userList;

    public List<User> getUserList() {
       return userList;
    }

    private List<User> userList = new ArrayList<>();

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

    //ContactDiscovery ContactList = new ContactDiscovery();

  public void myLogin(String name) {
        try {
            this.currentUser = new User(name, InetAddress.getLocalHost().toString());
            if (!getUserList().contains(this.currentUser))
                addUser(this.currentUser);

            System.out.println(getUsernames());
        } catch (SocketException e) {
            throw new RuntimeException(e);
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public void UserLogin(User user) {

        if (!getUsernames().contains(user.getUsername())) {
            System.out.println("[UserController]: New connexion detected " + user.getUsername());

            if (!getUserList().contains(user))
                addUser(user);

            System.out.println(getUsernames());
        }else{
            System.out.println("Username deja utilise");
        }
    }

    // Dans UserController
    public void UserLogout(User user) {
        // Mettez à jour localement la liste des utilisateurs en supprimant l'utilisateur déconnecté
        userList.remove(user);
        System.out.println("Local logout: " + user.getUsername());
    }



    public void CloseSocket()
    {
        this.currentUser.getSocket().close();
    }

    public User getUserByUuid(UUID uuid) {
        for (User user : getUserList()) {
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
                    if (!getUsernames().contains(message.substring(9))) {
                        User user = new User(message.substring(9), senderAddress);
                        addUser(user);
                    }else {
                        System.out.println("[apres New_User] : Username "+message.substring(9)+" deja utilise");
                    }
                    System.out.println(getUsernames());
                    NetworkController nc =  NetworkController.getInstance();
                    nc.SendMessage(8888, receivePacket.getAddress(),currentUser);

                }//
                else if(message.startsWith("New_User_Response:")) {
                    User user= new User(message.substring(18), senderAddress);
                    addUser(user);
                    System.out.println(getUsernames());
                }
                /*else if (message.startsWith("Logout:")) {
                    String usernameToLogout = message.substring(7);

                    Iterator<User> iterator = getUserList().iterator();
                    while (iterator.hasNext()) {
                        User user = iterator.next();
                        System.out.println("Current user in the list: " + user.getUsername());
                        if (user.getUsername().equals(usernameToLogout)) {
                            iterator.remove();
                            System.out.println("Utilisateur retiré : " + usernameToLogout);
                            break;  // Exit the loop once the user is removed
                        }
                    }

                    System.out.println("User List after logout: " + getUsernames());
                }*/



                // String uuidString = message.substring(7);
                   // UUID uuid = UUID.fromString(uuidString);
                    /*System.out.println("Message logout reçu");
                    String username = message.substring(7);
                    Iterator<User> iterator = userList.iterator();
                    while (iterator.hasNext()) {
                        User user = iterator.next();
                        if (user.getUsername().equals(username)) {
                            iterator.remove(); // Remove the user from the list
                            System.out.println("User with username " + username + " has logged out.");
                            break; // Exit the loop once the user is removed
                        }
                        else {
                            System.out.println("utilisateur "+username+" non trouvé");
                        }*/


                    System.out.println(getUsernames());
                }
            //
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//

}
