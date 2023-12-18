package Clavardage.Controller;

import Clavardage.Model.ThreadUser;
import Clavardage.Model.User;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ThreadController implements Controller {

    private Map<User, ThreadUser> discussion;
    private static final ThreadController instance = new ThreadController();

    public static ThreadController getInstance() {
        return instance;
    }
    private ThreadController(){};

    @Override
    public void initController() {
        this.discussion = new HashMap<User, ThreadUser>();

        //if (!Main.OUTPUT.exists())
          //  Main.OUTPUT.mkdir();
    }

    public ThreadUser getUserThread(User user) {
        return discussion.get(user);
    }

    public boolean isUserinConversation(User user) {
        return discussion.containsKey(user);
    }


    public void OuvrirDiscussion(User user, int localPort) {
        try {
            if (!discussion.containsKey(user)) {
                //PacketToEmit packet = new PacketEmtOpenConversation(agent, localPort);
              //  PacketManager.getInstance().sendPacket(agent.getIp(), packet);

               // if (Chat.DEBUG)
                    System.out.println("[ThreadManager] Waiting for connexion at port " + localPort + "...");

                ServerSocket serverSocket = new ServerSocket(localPort);
                Socket socket = serverSocket.accept();
                serverSocket.close();

                //if (Chat.DEBUG)
                    System.out.println("[ThreadManager] Connexion accepted");

                ThreadUser thread = new ThreadUser(socket);
                discussion.put(user, thread);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void FermerDiscussion(User user) {
        try {
            if (discussion.containsKey(user)) {
               discussion.get(user).close();
                discussion.remove(user);

               // if (Chat.DEBUG)
                    System.out.println("[ThreadManager] Conversation closed with user " + user.getUsername());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void DiscussionOuverte(User user, int port) {
        try {
            //if (Chat.DEBUG)
                System.out.println("[ThreadManager] Connection to distant port " + port + "...");

            Socket socket = new Socket(user.getIp(), port);
            ThreadUser thread = new ThreadUser(socket);

            //if (Chat.DEBUG)
                System.out.println("[ThreadManager] Thread created");

            discussion.put(user, thread);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void DiscussionFermee(User user) {
        try {
            if (discussion.containsKey(user)) {
                discussion.get(user).close();
                discussion.remove(user);

               // if (Chat.DEBUG)
                    System.out.println("[ThreadManager] Conversation closed with agent " + user.getUsername());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Map<User, ThreadUser> getDiscussion() {
        return this.discussion;
    }
}
