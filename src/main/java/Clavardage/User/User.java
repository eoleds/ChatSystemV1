package Clavardage.User;

//import Model.Packet.ContactDiscovery;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.UUID;

public class User {

    String username;
    String IP;

    private final UUID uuid;
    //ContactDiscovery ContactList = new ContactDiscovery();
    private DatagramSocket socket;

    public User(String username, String IP) throws SocketException {
        this.username = username;
        this.IP = IP;
        this.socket = new DatagramSocket();
        this.uuid = UUID.randomUUID();
    }


    public UUID getUuid() {
        return uuid;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getIp() {
        return IP;
    }

    public DatagramSocket getSocket() {
        return socket;
    }

}
