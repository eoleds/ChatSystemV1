package Model.Packet;

import Model.User.User;

import java.util.*;

public class ContactDiscovery {

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

    private List<UserInfo> userList = new ArrayList<>();

    public void addUser(UserInfo userInfo) {
        userList.add(userInfo);
    }

    public void removeUser(UserInfo userInfo) {
        userList.remove(userInfo);
    }

    public List<UserInfo> getUserList() {
        return userList;
    }

    public List<String> getUsernames() {
        List<String> usernames = new ArrayList<>();
        for (UserInfo userInfo : userList) {
            usernames.add(userInfo.getUsername());
        }
        return usernames;
    }

    public List<UUID> getUuids() {
        List<UUID> uuids = new ArrayList<>();
        for (UserInfo userInfo : userList) {
            uuids.add(userInfo.getUuid());
        }
        return uuids;
    }
}

/*private HashMap<String, String> contacts = new HashMap<>(); //La HashMap contient la liste de contacts

    public void adduser(String username, String IP) {
        contacts.put(username, IP);
    } //Ajoute un nouvel utilisateur à la map

    public ArrayList<String> getContacts() {
        ArrayList<String> listenames = new ArrayList<>();
        for (Map.Entry<String, String> entry : contacts.entrySet()) {
            String username = entry.getKey();
            listenames.add(username);
        }
        return listenames;
    } //Renvoie tous les contacts comme une list
}*/

    /*private List<User> userList = new ArrayList<>();

    public void addUser(User user) {
        userList.add(user);
    }

    public void removeUser (User user) { userList.remove(user);}

    public List<User> getUserList() {
        return userList;
    }

    public List<String> getUsernames() {
        List<String> usernames = new ArrayList<>();
        for (User user : userList) {
            usernames.add(user.getUsername());
        }
        return usernames;
    }*/