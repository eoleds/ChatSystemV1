package Model;

import Clavardage.Model.ThreadUser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.Assert.*;


public class ThreadUserTest {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ThreadUser threadUser;

    @Before
    public void setUp() throws IOException {
        serverSocket = new ServerSocket(0); // Use an available port
        clientSocket = new Socket("localhost", serverSocket.getLocalPort());
        threadUser = new ThreadUser(clientSocket);
    }

    @After
    public void tearDown() throws IOException {
        serverSocket.close();
        clientSocket.close();
    }

    @Test
    public void testGetOutputStream() {
        assertNotNull(threadUser.getOutputStream());
    }

    @Test
    public void testGetInputStream() {
        assertNotNull(threadUser.getInputStream());
    }

    @Test
    public void testClose() throws IOException {
        threadUser.close();
        assertTrue(clientSocket.isClosed());

    }
}
