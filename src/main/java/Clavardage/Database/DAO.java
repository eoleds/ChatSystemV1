package Clavardage.Database;

import Clavardage.User.User;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class DAO {

    protected Connection conn = null;

    public DAO(Connection conn) {
        this.conn = conn;
    }



    //------------------------Utilisateur----------------------------------//
    // Vérification en BD si l'utilisateur existe, création sinon
    public User createUtilisateur(User user) throws Exception {
        User utilisateur = user;
        ResultSet rr;

        try {
            PreparedStatement ps = conn.prepareStatement("select username from utilisateur where username=?");
            try {
                ps.setString(1, user.getUsername());
                rr = ps.executeQuery();

                if (rr.next()) {

                } else {
                    PreparedStatement pss = conn.prepareStatement("insert into utilisateur(username, IP) values(?,?)");
                    pss.setString(1, utilisateur.getUsername());
                    pss.setString(2, utilisateur.getIp());
                }
            } finally {
                ps.close();
            }
        } finally {
        }

        return utilisateur;
    }

    public void deleteUtilisateur(User user) throws SQLException {
        try {
            PreparedStatement ps = conn.prepareStatement("delete from utilisateur where username=?");
            try {
                ps.setString(1, user.getUsername());
                ps.executeUpdate();
            } finally {
                ps.close();
            }
        } finally {
        }
    }


    public User findUtilisateur(String Username) throws SQLException {
        User utilisateur = new User();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("select * from utilisateur where username=?");
            try {
                ps.setString(1, utilisateur.getUsername());
                rs = ps.executeQuery();

                if (rs.next()) {
                    utilisateur.setUsername(rs.getString(1));
                    utilisateur.setIp(rs.getString(2));
                }
            } finally {
                rs.close();
            }
        } finally {
            ps.close();
        }

        return utilisateur;
    }


/*

    public HashMap<String, User> listerMapUtilisateurs() throws SQLException {
        HashMap<String, User> utilisateurs = new HashMap<>();
        Statement s = null;
        ResultSet rss = null;

        try {
            s = conn.createStatement();
            try {
                String req = "select idUtilisateur,nom,prenom,mdp,typeUtil from utilisateur";
                rss = s.executeQuery(req);

                while (rss.next()) {
                    User u = new User(null, null, null, null, null);
                    u.setIdUtilisateur(rss.getString(1));
                    u.setNom(rss.getString(2));
                    u.setPrenom(rss.getString(3));
                    u.setMdp(rss.getString(4));
                    u.setTypeUtil(rss.getString(5));
                    utilisateurs.put(u.getIdUtilisateur(), u);
                }
            } finally {
                rss.close();
            }
        } finally {
            s.close();
        }

        return utilisateurs;
    }
*/


}
