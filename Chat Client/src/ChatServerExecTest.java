import static org.junit.Assert.*;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class ChatServerExecTest {
	public int CHAT_ROOM_PORT;
	ChatServerExec chatServerExec;
    private Socket clientSocket;
    private BufferedReader clientIn;
    private PrintWriter clientOut;
	private ChatClient chatClient;

	@Before
	public void setUp() throws Exception {
		CHAT_ROOM_PORT  = 6000;
		chatServerExec = new ChatServerExec();
	}

	@After
	public void tearDown() throws Exception {
		chatServerExec = null;
	}

/*
	@Test
	public void testStartServer() {
		String serverMsg="";
		try {
			chatServerExec.startServer(CHAT_ROOM_PORT);
			clientSocket = new Socket("127.0.0.1",CHAT_ROOM_PORT);
			       	
        	clientIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            clientOut = new PrintWriter(clientSocket.getOutputStream(), true);
            
            serverMsg = clientIn.readLine();
            assertEquals(serverMsg,"SUBMITNAME");
            clientOut.println("Jones");
            serverMsg = clientIn.readLine();
            assertEquals(serverMsg,"NAMEACCEPTED");
            clientOut.println("This is my test message");
            serverMsg = clientIn.readLine();
            assertEquals(serverMsg,"MESSAGE Jones: This is my test message");
 
		} catch (IOException e) {
			e.printStackTrace();
		}
	
      
 
	}*/	
	
	@Test
	public void testSTUDENTStartServer() {
		String serverMsg="";
		try {
			
			Thread t = new Thread(chatServerExec);
			t.start();
			clientSocket = new Socket("127.0.0.1", 8800);
			       	
        	clientIn = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            clientOut = new PrintWriter(clientSocket.getOutputStream(), true);
            
            serverMsg = clientIn.readLine();
            assertEquals(serverMsg,"SUBMITNAME");
            clientOut.println("Jones");
            serverMsg = clientIn.readLine();
            assertEquals(serverMsg,"NAMEACCEPTED");
            clientOut.println("This is my test message");
            serverMsg = clientIn.readLine();
            assertEquals(serverMsg,"MESSAGE Jones: This is my test message");
 
		} catch (IOException e) {
			e.printStackTrace();
		}
		
      
 
	}
	

}