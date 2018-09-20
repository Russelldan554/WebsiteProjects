import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
/**
 * Gui controller for chat room creates stage and starts server and clients
 * @author Daniel Russell CMSC 204
 *
 */
public class ChatGUI extends Application{

	Button startServer, startClient, exit; 
	Label rules, header;
	ChatServerExec server;
	Boolean serverStarted;
	final int PORT_NUMBER = 8800;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		server = new ChatServerExec();
		
		
		VBox main = new VBox();
		String headline = "Chat Room Controller\n";
		String mainText =  "1.Start the server\n"
				+ "2.Start a client\n"
				+ "3.Enter a screen name in the client's GUI.\n"
				+ "4.Start more clients\n"
				+ "5.Enter a messag in a client's GUI.";		
		
		VBox mainRules = new VBox();
		mainRules.setPadding(new Insets(0,0,0,20));
		rules = new Label();
		rules.setText(mainText);
		header = new Label(headline);
		header.setText(headline);
		header.setStyle("-fx-font-size: 30;");
		rules.setStyle("-fx-font-size: 18;");
		mainRules.getChildren().addAll(header, rules);
		
		HBox buttons = new HBox(20);
		buttons.setPadding(new Insets(20,0,0,30));
		startServer = new Button("Start the Server");
		startClient = new Button("Start each Client");
		exit = new Button("Exit");
		buttons.getChildren().addAll(startServer, startClient, exit);
		
		exit.setOnAction(event -> {
			System.exit(0);
		});
		
		serverStarted = false;//sentinel value for starting server
		startServer.setOnAction(event -> {
			//server.startServer(PORT_NUMBER);
			Runnable r1 = new ChatServerExec();
			Thread t = new Thread(r1);
			t.start();
			serverStarted = true;
		});
		
		startClient.setOnAction(event -> {
			if (serverStarted) {
				try {
					new ChatClientExec(PORT_NUMBER).startClient();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				};
			}
		});
		
		main.getChildren().addAll(mainRules, buttons);
		Scene scene = new Scene(main, 420,250);
		stage.setScene(scene);
		stage.setTitle("Chat Room Controller");	
		stage.show();		
	}

}
