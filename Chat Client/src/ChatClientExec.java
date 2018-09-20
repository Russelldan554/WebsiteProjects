import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
/**
 * Connects to the chat server then starts a chatClient in a new thread
 * @author Dan
 *
 */
public class ChatClientExec implements ChatClientExecInterface{
	
	int portNumber;
	
	public ChatClientExec(int portNumber){
		this.portNumber = portNumber;
	}
	
	@Override
	public void startClient() throws Exception {
		try{
			Socket client = new Socket("127.0.0.1", 8800);
			if (client.isConnected()){
				System.out.println("connected in chat");
			}
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream());

			Runnable chat = new ChatClient(in, out);
			Thread t = new Thread(chat);
			t.start();		
		} catch (UnknownHostException e){
			System.err.println("No such host");
		}
		
		
	}

}
