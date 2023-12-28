package Clavardage.Controller;

import Clavardage.Model.ThreadUser;
import Clavardage.Model.User;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class UserController implements Controller {


    public static final UserController instance = new UserController();
    private List<User> userList = new ArrayList<>();
    private User currentUser;

    public User getCurrentUser() {
        return currentUser;
    }
    public void setCurrentUser(User user) {
        this.currentUser=user;
    }


    public List<User> getUserList() {
        return userList;
    }

    public void addUser(User user) {
    String name=  user.getUsername();
        if (getUsernames().contains(name)){
            System.out.println("[adduser]: username deja utilisé");
        }else{
        userList.add(user);
        }
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

    public User getUserByUsername(String username) {
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    private boolean listening = true;

    public UserController() {
    }

    @Override
    public void initController() {
        userList = new ArrayList<>();
    }

    public static UserController getInstance() {
        return instance;
    }

    public void printUserList() {
        userList.forEach(this::print);
    }

    private void print(User user) {
        StringJoiner stringJoiner = new StringJoiner(", ");
        stringJoiner.add(user.getUsername());
        String ip = user.getIp();
        if (ip != null) {
            stringJoiner.add(ip);
        }
        System.out.println(stringJoiner.toString());
    }

    public boolean UserLogin(User user) {
        if (!getUsernames().contains(user.getUsername())) {
            System.out.println("[UserController]: New connexion detected " + user.getUsername());
            addUser(user);
            NetworkController.getInstance().Connect(user);
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



}


