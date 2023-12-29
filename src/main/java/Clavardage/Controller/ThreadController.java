package Clavardage.Controller;

import Clavardage.Model.ThreadUser;
import Clavardage.Model.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
        startListening();
    }

    public ThreadUser getUserThread(User user) {
        return discussion.get(user);
    }

    public boolean isUserinConversation(User user) {
        return discussion.containsKey(user);
    }

    public void startListening() {
        UserController uc = UserController.getInstance();
        NetworkController nc = NetworkController.getInstance();
        new Thread(() -> {
            try {

                ServerSocket serverSocket = new ServerSocket(8888);
                nc.ReceiveMessagesUDP(); //pourquoi ici ? bien ??

                // getting client request
                while (listening)
                // running infinite loop
                {
                    Socket mynewSocket = null;
                    try
                    {
                        // mynewSocket object to receive incoming client requests
                        mynewSocket = serverSocket.accept();
                        System.out.println("A new connection identified : " + mynewSocket);


                        // Extraire l'utilisateur émetteur en fonction de son adresse IP
                        String senderAddress = mynewSocket.getInetAddress().getHostAddress();
                        User senderUser = uc.getUserByIP(senderAddress);


                        // creation ThreadUser avec input et output sur ce thread
                        ThreadUser newThread = new ThreadUser(mynewSocket);
                        System.out.println("Thread assigned");

                        // Ajouter l'utilisateur émetteur et le nouveau thread à la map discussion
                        discussion.put(senderUser, newThread);

                        newThread.start();

                    }
                    catch (Exception e){
                        mynewSocket.close();
                        e.printStackTrace();
                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }


    //nv thread sur port aleatoire, envoie msg sur 3infos recues en precisant addresse ip et port

}