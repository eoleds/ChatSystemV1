package Clavardage.Database;

import java.sql.*;

public class ConnexionBD {

    private static Connection conn;

    public static Connection getInstance() throws ClassNotFoundException, InstantiationException, IllegalAccessException {

        if(conn == null){
            try {
                // connexion � la base de donn�e par : jdbc:mysql://nomhote:port/numbed
                Class.forName("org.sqlite.JDBC");

                // db parameters
                String url = "jdbc:sqlite:C:/sqlite/DB.db";
                // create a connection to the database
                conn = DriverManager.getConnection(url);

                System.out.println("Driver O.K.");

                conn = DriverManager.getConnection(url);


            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }



    public static void deconnexion() throws Exception {

        if(conn != null)
            try {
                conn.close();
            } catch ( SQLException ignore ) {
                //ignore
            }
    }

}
