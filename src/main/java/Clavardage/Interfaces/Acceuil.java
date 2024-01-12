package Clavardage.Interfaces;

import Clavardage.Network.NetworkController;
import Clavardage.User.UserController;
import Clavardage.User.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

public class Acceuil extends JFrame {

    private JTextField nameField;
    private UserController userController;
    private NetworkController networkController;
    private JFrame chatFrame;
    private DefaultListModel<String> connectedUsersListModel;  // Ajout du modèle de données pour la liste des utilisateurs

    public Acceuil(UserController userController, NetworkController networkController) {
        super("Page d'accueil du Chat");
        this.userController = userController;
        this.networkController=networkController;

        // Initialisation du modèle de données pour la liste des utilisateurs
        connectedUsersListModel = new DefaultListModel<>();

        JLabel nameLabel = new JLabel("Entrez votre prénom :");
        nameField = new JTextField(20);
        JButton connectButton = new JButton("Se connecter");

        JPanel panel = new JPanel();
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(connectButton);

        add(panel);

        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onConnectButtonClick();
            }
        });

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void onConnectButtonClick() {
        String userName = nameField.getText();
        if (!userName.isEmpty()) {
            try {
                String localIP = InetAddress.getLocalHost().getHostAddress();
                User user = new User(userName, localIP);

                if (userController.UserLogin(user)) {
                    userController.setCurrentUser(user);
                    JOptionPane.showMessageDialog(this, "Bienvenue, " + userName + " ! Connexion réussie.");
                    openChatInterface();
                } else {
                    JOptionPane.showMessageDialog(this, "Nom d'utilisateur déjà utilisé.");
                }
            } catch (UnknownHostException | SocketException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez entrer votre prénom.");
        }
    }

    private void openChatInterface() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                chatFrame = new JFrame("Chat de " + userController.getCurrentUser().getUsername());
                chatFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                chatFrame.setSize(800, 500);
                chatFrame.setLocationRelativeTo(null);

                chatFrame.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                        onDisconnectButtonClick();
                    }
                });

                JPanel chatPanel = new JPanel(new BorderLayout());
                JList<String> connectedUsersList = new JList<>(connectedUsersListModel);  // Utilisation du modèle de données
                JScrollPane connectedUsersScrollPane = new JScrollPane(connectedUsersList);
                chatPanel.add(connectedUsersScrollPane, BorderLayout.WEST);

                JTextArea chatTextArea = new JTextArea(15, 30);
                JScrollPane chatScrollPane = new JScrollPane(chatTextArea);
                chatPanel.add(chatScrollPane, BorderLayout.CENTER);

                JTextField messageField = new JTextField(20);
                JButton sendButton = new JButton("Envoyer");

                sendButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            networkController.sendMessageUDP(messageField.getText());
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        messageField.setText("");

                    }
                });
                JPanel messagePanel = new JPanel();
                messagePanel.add(messageField);
                messagePanel.add(sendButton);
                chatPanel.add(messagePanel, BorderLayout.NORTH);

                JButton disconnectButton = new JButton("Déconnexion");
                disconnectButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        onDisconnectButtonClick();
                    }
                });
                chatPanel.add(disconnectButton, BorderLayout.SOUTH);

                chatFrame.add(chatPanel);

                chatFrame.setVisible(true);

                Timer timer = new Timer(5000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        updateConnectedUsersList(connectedUsersList);
                    }
                });
                timer.start();
            }
        });
    }

    private void onDisconnectButtonClick() {
        User currentUser = userController.getCurrentUser();
        if (currentUser != null) {
            userController.UserLogout(currentUser);
            JOptionPane.showMessageDialog(this, "Déconnexion réussie.");
            chatFrame.dispose();  // Fermez l'interface de chat
        }
    }

    private void updateConnectedUsersList(JList<String> connectedUsersList) {
        List<String> connectedUsers = userController.getUsernames();
        connectedUsersListModel.removeAllElements();
        for (String user : connectedUsers) {
            connectedUsersListModel.addElement(user);
        }
    }
}
//yes
