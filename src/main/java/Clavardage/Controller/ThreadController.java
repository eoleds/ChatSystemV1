package Clavardage.Controller;

import Clavardage.Model.ThreadUser;
import Clavardage.Model.User;

import java.io.IOException;
import java.net.ServerSocket;
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
        new Thread(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(8888);
                NetworkController.getInstance().ReceiveMessagesUDP();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }


    //nv thread sur port aleatoire, envoie msg sur 3infos recues en precisant addresse ip et port

}