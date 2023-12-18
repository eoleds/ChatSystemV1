package Clavardage.Controller;

import Clavardage.Model.User;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;

public class NetworkController implements Controller{
    @Override
    public void initController() {

    }

    private static final NetworkController instance = new NetworkController();
    public static NetworkController getInstance() {
        return instance;
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
            System.out.println("Broadcast sent successfully by " +user.getUsername());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void SendMessage(int port, InetAddress IPadresse, User meUser) throws IOException {
        int port1 = port;
        String message1 = "New_User_Response:" + meUser.getUsername();
        byte[] sendData1 = message1.getBytes();
        DatagramPacket packet1 = new DatagramPacket(sendData1, sendData1.length, IPadresse, port1);
        meUser.getSocket().send(packet1);
    }


}
