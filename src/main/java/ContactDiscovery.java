import java.util.*;

public class ContactDiscovery {

    private HashMap<String, String> contacts = new HashMap<>(); //La HashMap contient la liste de contacts

    public void adduser(String username, String IP) {
        contacts.put(username, IP);
    } //Ajoute un nouvel utilisateur à la map

    public HashMap<String, String> getContacts() {
        return contacts;
    } //Renvoie la HashMap avec tous les contacts
}