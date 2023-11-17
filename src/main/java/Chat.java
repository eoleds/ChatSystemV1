import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Chat {
    private static class ReceiveMessages implements Runnable {


        User user;
        public ReceiveMessages(User user) {
            this.user = user;
        }


        @Override
        public void run() {
            try {
                user.ReceiveMessages();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static void main(String[] args) throws SocketException, UnknownHostException {
        // Création de deux utilisateurs
        User me = new User("luz", InetAddress.getLocalHost().toString());
        User user2 = new User("User2", "192.168.1.2");


        try {
            // Instanciation des objets SendMessage et ReceiveMessage pour chaque utilisateur
            SendMessage sendMessage1 = new SendMessage(me);
            ReceiveMessage receiveMessage1 = new ReceiveMessage(8888, me);


           SendMessage sendMessage2 = new SendMessage(user2);
           ReceiveMessage receiveMessage2 = new ReceiveMessage(8888, user2);  //Je suis pas certaine qu'il faille mettre user2 mais je crois que c'est ça


            // Démarrage des threads de ReceiveMessage pour chaque utilisateur
            Thread t = new Thread(new ReceiveMessages(me));
            t.start();


            new Thread(() -> {
                try {
                    me.ReceiveMessages();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();


            // Simulation de la connexion de l'utilisateur 1
            me.Connect();


            // Attente pour permettre à ReceiveMessage d'afficher les résultats
            t.sleep(2000);
            me.CloseSocket();


            // Fermeture des sockets après le test
            sendMessage1.close();
            sendMessage2.close();
            receiveMessage1.close();
            receiveMessage2.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}
