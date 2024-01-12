package Clavardage.Interfaces;

import Clavardage.Network.NetworkController;
import Clavardage.User.User;
import Clavardage.User.UserController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class fenetreChat extends JFrame {

    private JTextArea chatArea;
    private JTextField messageField;

    public fenetreChat(String username) {
        setTitle("Chat - " + username);
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);

        messageField = new JTextField();
        messageField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage(username + ": " + messageField.getText());
                messageField.setText("");
            }
        });

        JButton sendButton = new JButton("Envoyer");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage(username + ": " + messageField.getText());
                messageField.setText("");
            }
        });

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(inputPanel, BorderLayout.SOUTH);
    }

    private void sendMessage(String message) {
        User receiverUser = getUserFromUserClick(messageField.getText());
        if (receiverUser != null) {
            chatArea.append(receiverUser.getUsername() + ": " + message + "\n");
            NetworkController nc = NetworkController.getInstance();
            nc.sendMessageTCP(receiverUser, message);
        } else {
            System.out.println("Erreur: Utilisateur non trouvé");
        }
    }

    public static void main(String[] args) {
        String user1 = "Utilisateur 1";
        String user2 = "Utilisateur 2";

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                fenetreChat user1Chat = new fenetreChat(user1);
                user1Chat.setVisible(true);

                fenetreChat user2Chat = new fenetreChat(user2);
                user2Chat.setVisible(true);
            }
        });
    }

    private User getUserFromUserClick(String clickedUsername) {
        UserController userController = UserController.getInstance();

        for (User user : userController.getUserList()) {
            if (user.getUsername().equals(clickedUsername)) {
                return user;
            }
        }

        // Retourner null ou gérez le cas où l'utilisateur n'est pas trouvé
        return null;
    }
}