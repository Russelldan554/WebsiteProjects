import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

/**
 * starts a server and waits for client to join then creates thread of new client
 * @author Daniel Russell CMSC 204
 *
 */
public class ChatServerExec implements ChatServerExecInterface, Runnable{
	
	private static HashSet<String> names = new HashSet<String>();
	private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();

	/**
	 * constructor
	 */
	public ChatServerExec(){

	}
	
	@Override
	public void startServer(int port) {
		try {	
			ServerSocket server = new ServerSocket(port);
			System.out.println("Waiting for client...on port:" + server.getLocalPort());
			while(true){
				Socket sock = server.accept();
				ChatServer chat = new ChatServer(sock);
				Thread t = new Thread(chat);
				t.start();
			}
		}
		catch (IOException e) {
			System.err.println(e.getMessage());
		} 

	}

	/**
	 * adds client to list of printwriters and sends messages to all connected clients
	 * @author Dan
	 *
	 */
	private static class ChatServer implements Runnable{

		private String name;
		private Socket socket;
		
		public ChatServer(Socket socket) {
			this.socket = socket;
		}
		
		@Override
		public void run() {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter out = new PrintWriter(socket.getOutputStream());
				
				while(true){
					out.println("SUBMITNAME");
					System.out.println("SUBMITNAME");
					out.flush();
					name = in.readLine();
					System.out.println(name);
					if (hasName(name)){
						names.add(name);
						break;
					}		
				}
				
				out.println("NAMEACCEPTED");
				out.flush();
				System.out.println("NAMEACCEPTED");
				
				writers.add(out);
				
				while(true){
					String message = in.readLine();
					if (message != null){
						for (PrintWriter writer : writers) {
							writer.println("MESSAGE " + name + ": " + message);
							System.out.println(message);
							writer.flush();
						}
					}
				}				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	/**
	 * checks if username has been used in chat already
	 * @param name
	 * @return
	 */
	public static Boolean hasName(String name){
		for (String n : names){
			if (n.equalsIgnoreCase(name)){
				return false;
			}
		}
		return true;
	}

	@Override
	public void run() {
		startServer(8800);
		
	}

}
