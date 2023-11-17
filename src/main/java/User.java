import java.io.IOException;
import java.net.*;


public class User {


    String username;
    String IP;
    ContactDiscovery ContactList = new ContactDiscovery();
    private DatagramSocket socket;


    public User(String username, String IP) throws SocketException {
        this.username = username;
        this.IP = IP;
        this.socket = new DatagramSocket();
    }


    public void Connect()
    {
        try {
            InetAddress broadcastAddress = InetAddress.getByName("255.255.255.255");
            int port = 8888;
            String message = "New_User:" + username;
            byte[] sendData = message.getBytes();
            DatagramPacket packet = new DatagramPacket(sendData,sendData.length, broadcastAddress,port);
            socket.send(packet);
            System.out.println("Broadcast sent successfully");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void SendMessage(int port, InetAddress IPadresse) throws IOException {
        int port1 = port;
        String message1 = "New_User_Response:" + username;
        byte[] sendData1 = message1.getBytes();
        DatagramPacket packet1 = new DatagramPacket(sendData1, sendData1.length, IPadresse, port1);
        socket.send(packet1);
    }


    public void CloseSocket()
    {
        socket.close();
    }


    public void ReceiveMessages() throws IOException {


        int port = 8888; // Specify the port to listen on


        try {
            DatagramSocket socket = new DatagramSocket(port);
            DatagramPacket receivePacket = new DatagramPacket(new byte[1024], 1024);


            while (true) {
                socket.receive(receivePacket);
                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                String senderAddress = receivePacket.getAddress().getHostAddress();


                int senderPort = receivePacket.getPort();
                System.out.println("Greetings, " + senderAddress + " : " + senderPort + " : " + message);


                if (message.startsWith("New_User:")) {
                    if (!ContactList.getContacts().contains(message.substring(9))) {
                        ContactList.adduser(message.substring(9), senderAddress);
                    }
                    SendMessage(8888, receivePacket.getAddress());


                }
                else if(message.startsWith("New_User_Response:")) {


                    ContactList.adduser(message.substring(18), senderAddress);
                }
                System.out.println(ContactList.getContacts());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
