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
            }catch (SocketException e) {
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

        @Test
        public void testOuvrirDiscussion() {
            assertFalse(threadController.isUserinConversation(testUser));
            threadController.OuvrirDiscussion(testUser, 8888);
            assertTrue(threadController.isUserinConversation(testUser));
            assertNotNull(threadController.getUserThread(testUser));
        }

        public void testDiscussionOuverte() {
            assertFalse(threadController.isUserinConversation(testUser));
            threadController.DiscussionOuverte(testUser, 8888);
            assertTrue(threadController.isUserinConversation(testUser));
            assertNotNull(threadController.getUserThread(testUser));
        }


        public void testFermerDiscussion() {
            threadController.OuvrirDiscussion(testUser, 8888); // Ouvrez une discussion d'abord
            assertTrue(threadController.isUserinConversation(testUser));
            threadController.FermerDiscussion(testUser);
            assertFalse(threadController.isUserinConversation(testUser));
            assertNull(threadController.getUserThread(testUser));
        }



        public void testDiscussionFermee() {
            threadController.DiscussionOuverte(testUser, 1234); // Ouvrez une discussion d'abord
            assertTrue(threadController.isUserinConversation(testUser));
            threadController.DiscussionFermee(testUser);
            assertFalse(threadController.isUserinConversation(testUser));
            assertNull(threadController.getUserThread(testUser));
        }
    }
