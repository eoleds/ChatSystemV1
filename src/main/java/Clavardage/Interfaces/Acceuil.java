package Clavardage.Interfaces;

import Clavardage.Controller.UserController;
import Clavardage.Model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Acceuil extends JFrame {

    private JTextField nameField;
    private UserController userController;
    private JFrame chatFrame;  // Ajout de la référence à la fenêtre de chat

    public Acceuil(UserController userController) {
        super("Page d'accueil du Chat");
        this.userController = userController;

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
                JList<String> connectedUsersList = new JList<>(new DefaultListModel<>());
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
                        //sendMessage(messageField.getText());
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
                        // updateConnectedUsersList(connectedUsersList);
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
}
//yes
