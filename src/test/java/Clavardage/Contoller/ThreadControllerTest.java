package Clavardage.Contoller;

import Clavardage.Controller.ThreadController;
import Clavardage.Model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.net.SocketException;

import static org.junit.Assert.*;


    public class ThreadControllerTest {

        private ThreadController threadController;
        private User testUser;

        @Before
        public void setUp() {
            threadController = ThreadController.getInstance();
            threadController.initController();
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

        @Test
        public void testInitController() {
            assertNotNull(threadController.getDiscussion());
            assertTrue(threadController.getDiscussion().isEmpty());
        }

    }


