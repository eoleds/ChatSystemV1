package Contoller;

import Controller.UserController;
import Model.User.User;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.SocketException;
import java.util.List;

import static org.junit.Assert.*;


public class UserControllerTest {

    private static final String SACHA_USERNAME = "saxha";
    private static final String SACHA_IP = "192.168.123.136";

    private static final String SASHA_USERNAME = "sasha";
    private static final String SACHA2_IP = "192.168.123.138";
    private static final String SASHA_IP = "192.168.123.139";
    private static final User SACHA_USER;

    static {
        try {
            SACHA_USER = new User(SACHA_USERNAME,SACHA_IP);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    private static final User SACHA_USER2;

    static {
        try {
            SACHA_USER2 = new User(SACHA_USERNAME,SACHA2_IP);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }
    private static final User SASHA_USER;

    static {
        try {
            SASHA_USER = new User(SASHA_USERNAME,SASHA_IP);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    private ByteArrayOutputStream out;

    private UserController userController;

    @Before
    public void setUp() {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        userController = new UserController();
    }

    private String standardOutput() {
        return out.toString();
    }

    @Test
    public void add_user_test() throws Exception {
        userController.addUser(SACHA_USER);
        assertTrue(userController.getUserList().contains(SACHA_USER));
        userController.getUserList().remove(SACHA_USER);
    }

    @Test
    public void login_user() throws Exception {
        userController.UserLogin(SACHA_USER);
        assertTrue(userController.getUsernames().contains(SACHA_USERNAME));

    }

    @Test
    public void login_user_with_username_already_used_test() throws Exception {
        List<User> liste1 = userController.getUserList();
        userController.UserLogin(SACHA_USER2);
        List<User> liste2 = userController.getUserList();
        assertEquals(liste2,liste1);
    }


    @Test
    public void login_second_user() throws Exception {
        userController.UserLogin(SACHA_USER);
        List<User> liste1 = userController.getUserList();
        assertFalse(liste1.contains("sasha"));
        userController.UserLogin(SASHA_USER);
        List<User> liste2 = userController.getUserList();
        assertFalse(liste1.contains("saxha"));

    }

}
