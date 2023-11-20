package Model.Packet;

import java.util.*;

public class ContactDiscovery {

    private HashMap<String, String> contacts = new HashMap<>(); //La HashMap contient la liste de contacts

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
}