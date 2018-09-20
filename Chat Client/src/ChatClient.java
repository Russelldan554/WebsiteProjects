import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
/**
 * Opens GUI for chat for user to enter messages and select username
 * @author Daniel Russell CMSC 204
 *
 */
public class ChatClient implements ChatClientInterface {
	
	TextField chatField;
	TextArea chatLog;
	String name;
	TextInputDialog nameIn;
	String screeName;
	
	private BufferedReader in;
	private PrintWriter out;
	
	public ChatClient(BufferedReader in, PrintWriter out){
		this.in = in;
		this.out = out;
		name = " ";

		nameIn = new TextInputDialog();
		nameIn.setTitle("Enter ScreenName");
		nameIn.setContentText("Enter ScreenName: ");
		nameIn.showAndWait();
		name = nameIn.getResult();
		
		Stage stage1 = new Stage();
		GridPane main = new GridPane();
		VBox panels = new VBox();
		chatField = new TextField();
		chatField.setEditable(false);
		chatLog = new TextArea();
		chatLog.setEditable(false);
		panels.getChildren().addAll(chatField, chatLog);
		main.getChildren().add(panels);
		
		chatField.setOnAction(event -> {
			out.println(chatField.getText());
			out.flush();
			chatField.setText("");
		});
		
		Scene scene = new Scene(main);
		stage1.setScene(scene);
		stage1.show();
		stage1.setTitle("chat client");
	}

	@Override
	public void run() {
		int count = 0;
		while (true) {
			try {
				String input = in.readLine();
				if (input.contains("SUBMITNAME")){
					if (count > 0) {
						name = "name" + Math.random();
					}
					count++;
					out.println(name);
					out.flush();					
				} else if (input.startsWith("NAMEACCEPTED")) {
					chatField.setEditable(true);					
				} else if (input.startsWith("MESSAGE")){
					chatLog.appendText(input.substring(8) + "\n");			
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getServerPort() {
		// TODO Auto-generated method stub
		return 0;
	}
}
