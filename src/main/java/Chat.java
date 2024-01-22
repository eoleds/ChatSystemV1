import Clavardage.Network.NetworkController;
import Clavardage.Thread.ThreadController;
import Clavardage.User.UserController;
import Clavardage.Interfaces.Acceuil;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Chat {
    public static final boolean DEBUG = true;
    public static final File OUTPUT = new File("./output");

    private static Connection conn;

    public static void main(String[] args) throws IOException {

        connect();
        UserController uc = UserController.getInstance();
        NetworkController nc = NetworkController.getInstance();
        ThreadController tc = ThreadController.getInstance();




        SwingUtilities.invokeLater(new Runnable() {
             @Override
             public void run() {
                new Acceuil(uc,nc);
             }
        });


    }

    /**
     * Connect to a sample database
     */
    public static void connect() {
        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");

            // db parameters
            String url = "jdbc:sqlite:C:/sqlite/db/chat.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);
            System.out.println(conn.getSchema());

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}

