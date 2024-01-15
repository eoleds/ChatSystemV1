package Clavardage.Contoller;

import Clavardage.Network.NetworkController;
import Clavardage.Thread.ThreadController;
import Clavardage.User.UserController;
import Clavardage.User.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;

import static org.junit.Assert.*;


public class ThreadControllerTest {

    private ThreadController threadController;

    private UserController userController;
    private NetworkController networkController;
    private User testUser;

    @Before
    public void setUp() throws IOException {
        threadController = ThreadController.getInstance();
        threadController.initController();
        userController = UserController.getInstance();
        networkController = NetworkController.getInstance();
        try {
            testUser = new User("testUser", "127.0.0.1");
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }

    }

    @After
    public void tearDown() {
        threadController = null;
        testUser = null;
    }

    @Test
    public void testGetInstance() {
        assertNotNull(ThreadController.getInstance());
    }

    // @Test
    public void testInitController() {
        //assertNotNull(threadController.getDiscussion());
        //assertTrue(threadController.getDiscussion().isEmpty());
    }

    @Test //fonctionne mais erreur adresse ip deja utilisée, à tester en lan
    public void startListeningAndReceiveMessagesUDPTest() throws Exception, InterruptedException {
        // Créer un utilisateur fictif pour tester
        User testUser = new User("TestUser", InetAddress.getLocalHost().toString());
        userController.setCurrentUser(testUser);

        // Lancer la réception des messages UDP dans un thread séparé
        Thread udpThread = new Thread(() -> {
            try {
                networkController.ReceiveMessagesUDP();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        udpThread.start();

        // Attendre un court moment pour laisser le thread UDP démarrer
        Thread.sleep(100);

        // Lancer la méthode startListening dans un autre thread
        Thread listeningThread = new Thread(() -> threadController.startListeningUDP());
        listeningThread.start();

        // Attendre un court moment pour laisser le thread de démarrage écouter
        Thread.sleep(100);

        // Effectuer des actions qui devraient générer des messages UDP (par exemple, se connecter)
        networkController.Connect(testUser);

        // Attendre un court moment pour permettre la réception des messages
        Thread.sleep(100);

        // Vérifier que l'utilisateur a bien été ajouté
        assertTrue(userController.getUsernames().contains(testUser.getUsername()));

        // Arrêter les threads
        threadController.listening =false;
        udpThread.join();
        listeningThread.join();
    }





}