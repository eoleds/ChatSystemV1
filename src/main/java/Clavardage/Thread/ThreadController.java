package Clavardage.Thread;

import Clavardage.Controller.Controller;
import Clavardage.User.User;
import Clavardage.Network.NetworkController;
import Clavardage.User.UserController;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadController implements Controller {

    private Map<User, ThreadUser> discussion;
    private ExecutorService executorService;
    private static final ThreadController instance = new ThreadController();

    public static ThreadController getInstance() {
        return instance;
    }

    public boolean listening = true;

    private ThreadController() {
        initController();
        executorService = Executors.newCachedThreadPool();
    }

    @Override
    public void initController() {
        this.discussion = new HashMap<>();
        startListeningUDP();
        startListeningTCP();
    }

    public ThreadUser getUserThread(User user) {
        return discussion.get(user);
    }

    public boolean isUserinConversation(User user) {
        return discussion.containsKey(user);
    }



    public void sendMessage(User receiver, String message) {
        ThreadUser receiverThread = discussion.get(receiver);

        if (receiverThread != null) {
            receiverThread.sendMessageTCP(message);
        } else {
            // Gérer le cas où l'utilisateur n'est pas connecté
            System.out.println("[ThreadController]: Utilisateur non connecté");
        }
    }
    //serveur UDP
    public void startListeningUDP() {
        UserController uc = UserController.getInstance();
        NetworkController nc = NetworkController.getInstance();
        new Thread(() -> {
            try {
                //Thread initial UDP qui reçoit les nouvelles connexions
                ServerSocket serverSocket = new ServerSocket(8888);
                nc.ReceiveMessagesUDP();

                while (listening) {
                    Socket mynewSocket = null;
                    try {
                        mynewSocket = serverSocket.accept();
                        System.out.println("A new connection identified : " + mynewSocket);

                    } catch (Exception e) {
                        mynewSocket.close();
                        e.printStackTrace();
                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    //Serveur TCP
    public void startListeningTCP() {
        UserController uc = UserController.getInstance();
        NetworkController nc = NetworkController.getInstance();
        new Thread(() -> {
            try {
                //Thread initial TCP qui reçoit les nouvelles connexions
                ServerSocket serverSocket = new ServerSocket(9999);

                while (listening) {
                    Socket mynewSocket = null;
                    try {

                        mynewSocket = serverSocket.accept();
                        String senderAddress = mynewSocket.getInetAddress().getHostAddress();
                        User senderUser = uc.getUserByIP(senderAddress);
                        System.out.println("A new TCP connection identified : " + mynewSocket);
                        ThreadUser newThread = new ThreadUser(mynewSocket,uc.getCurrentUser(),senderUser);
                        System.out.println("Thread assigned");
                        discussion.put(senderUser, newThread);

                    } catch (Exception e) {
                        mynewSocket.close();
                        e.printStackTrace();
                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }


}

