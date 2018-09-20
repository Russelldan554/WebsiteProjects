import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
/**
 * GUI for creating Actor graph and finding relations to actors
 * @author Daniel Russell CMSC 204
 *
 */
public class GUI extends Application{

	Button actorButton, actorToMovieButton, addMovieButton, doneMovieButton, findConnectionButton, readFileButton, exitButton;
	TextField actorsField, moviesField;
	TextArea connectionsField;
	Label actorLabel, movieNameLabel, fromLabel, toLabel;
	ActorGraphManager graph;
	/**
	 * launches start
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * creates stage and runs gui
	 */
	@Override
	public void start(Stage stage) throws Exception {
		VBox main = new VBox(20);
		
		graph = new ActorGraphManager();
		
		//Actors field titled pane and add buttons
		VBox actorBox = new VBox(10);
		HBox actorInput = new HBox(10);
		actorLabel = new Label("Actor's Name(Last, First)");
		actorsField = new TextField();
		actorButton = new Button("Add Actor");
		actorInput.setAlignment(Pos.BASELINE_CENTER);
		actorBox.setAlignment(Pos.BASELINE_CENTER);
		actorInput.getChildren().addAll(actorLabel, actorsField);
		actorBox.getChildren().addAll(actorInput, actorButton);
		TitledPane actorpane = new TitledPane("Actor", actorBox);
		actorpane.setCollapsible(false);
		
		//Movies field Titled Pane and add buttons
		VBox movieBox = new VBox(10);
		HBox movieInput = new HBox(10);
		movieNameLabel = new Label("Movie Name");
		moviesField = new TextField();
		addMovieButton = new Button("Add Movie");
		movieInput.setAlignment(Pos.BASELINE_CENTER);
		movieBox.setAlignment(Pos.BASELINE_CENTER);
		movieInput.getChildren().addAll(movieNameLabel, moviesField, addMovieButton);
		HBox movieSelect = new HBox(10);
		movieSelect.setVisible(false);
		ChoiceBox<String> actorAdd = new ChoiceBox<String>();
		actorToMovieButton = new Button("Add Actor to Movie");
		doneMovieButton = new Button("Done");
		movieSelect.setAlignment(Pos.BASELINE_CENTER);
		movieSelect.getChildren().addAll(actorAdd, actorToMovieButton, doneMovieButton);
		movieBox.getChildren().addAll(movieInput, movieSelect);
		TitledPane moviePane = new TitledPane("Movie", movieBox);
		moviePane.setCollapsible(false);
		
		//connection pane with fields to shows movie connections created
		VBox connectionBox = new VBox(10);
		HBox fromToSelect = new HBox(10);
		fromLabel = new Label("From");
		ChoiceBox<String> firstActor = new ChoiceBox<String>();
		ChoiceBox<String> secondActor = new ChoiceBox<String>();
		toLabel = new Label("To");
		fromToSelect.getChildren().addAll(fromLabel,firstActor, toLabel, secondActor);
		connectionsField = new TextArea();
		connectionsField.setMinSize(400, 200);
		connectionsField.setEditable(false);
		HBox connectBox = new HBox(10);
		findConnectionButton = new Button("Find Connection"); 
		fromToSelect.setAlignment(Pos.BASELINE_CENTER);
		connectBox.getChildren().add(findConnectionButton);
		connectBox.setAlignment(Pos.BASELINE_CENTER);
		connectionBox.getChildren().addAll(fromToSelect, connectionsField, connectBox);
		TitledPane connectionPane = new TitledPane("Find Connection", connectionBox);
		connectionPane.setCollapsible(false);
		
		//readfile and exit buttons created
		HBox lastButtons = new HBox();
		exitButton = new Button("Exit");
		readFileButton = new Button("Read File");
		lastButtons.setAlignment(Pos.BASELINE_CENTER);
		lastButtons.getChildren().addAll(readFileButton, exitButton);
		
		//for testing
		/*String[] actor = new String[12];

		for (int i = 1; i < 12; i++) {
			actor[i] = "Actor_" + i;
			graph.addActor(actor[i]);
		}

		graph.addMovie(actor[1], actor[2], "Movie_1");
		graph.addMovie(actor[1], actor[3], "Movie_2");
		graph.addMovie(actor[1], actor[5], "Movie_3");
		graph.addMovie(actor[3], actor[7], "Movie_4");
		graph.addMovie(actor[3], actor[8], "Movie_5");
		graph.addMovie(actor[4], actor[8], "Movie_6");
		graph.addMovie(actor[6], actor[9], "Movie_7");
		graph.addMovie(actor[9], actor[10], "Movie_8");
		graph.addMovie(actor[8], actor[10], "Movie_9");
		graph.addMovie(actor[5], actor[10], "Movie_10");
		graph.addMovie(actor[10], actor[11], "Movie_11");
		graph.addMovie(actor[2], actor[11], "Movie_12");
		graph.addMovie(actor[7], actor[6], "Movie_13");
	*/
		exitButton.setOnAction(event -> {
			System.exit(0);
		});
		
		actorButton.setOnAction(event -> {
			if (actorsField.getText() != null)
				graph.addActor(actorsField.getText());
			actorsField.clear();
			actorAdd.setItems(FXCollections.observableList(graph.allActors()));
			firstActor.setItems(FXCollections.observableList(graph.allActors()));
			secondActor.setItems(FXCollections.observableList(graph.allActors()));
		});
		
		addMovieButton.setOnAction(event -> {
			moviesField.setEditable(false);
			movieSelect.setVisible(true);
			});
		
		ArrayList<String> actorsToAdd = new ArrayList<String>();
		actorToMovieButton.setOnAction(event -> {
			actorsToAdd.add(actorAdd.getValue());
		});
		
		doneMovieButton.setOnAction(event -> {
			graph.addMovie(actorsToAdd.get(0), actorsToAdd.get(1), moviesField.getText());
			actorsToAdd.clear();
			moviesField.clear();
			moviesField.setEditable(true);
			movieSelect.setVisible(false);
		});
		
		findConnectionButton.setOnAction(event -> {
			String one = firstActor.getValue();
			String two = secondActor.getValue();
			String completeText = "";
			if(one != null && two != null) {
				ArrayList<String> path = graph.getPath(one, two);
				for (String text: path) {
					completeText += text + "\n";
				}
				connectionsField.setText(completeText);
				if (completeText.equals("")) {
					connectionsField.setText("No Connection");
				}
			} else
				connectionsField.setText("Error");
			
		});
		
		
		readFileButton.setOnAction(event -> {
			File inputFile = null;
			String line;
			FileChooser chooser = new FileChooser();
			chooser.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.txt"));
			File selectedFile = chooser.showOpenDialog(stage);
			if (selectedFile != null) {
				inputFile = selectedFile;
			}
			try {
				
			FileReader fileReader = 
					new FileReader(inputFile);

			BufferedReader bufferedReader = 
					new BufferedReader(fileReader);


			while((line = bufferedReader.readLine()) != null) {
				String split = ";";
				ArrayList<String> newedge = new ArrayList<String>();
				StringTokenizer seperate = new StringTokenizer(line, split);
				while(seperate.hasMoreTokens()) {
					newedge.add(seperate.nextToken());						
				}
				if (newedge.size() == 3)
					graph.addMovie(newedge.get(1), newedge.get(2), newedge.get(0));
			}


			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			actorAdd.setItems(FXCollections.observableList(graph.allActors()));
			firstActor.setItems(FXCollections.observableList(graph.allActors()));
			secondActor.setItems(FXCollections.observableList(graph.allActors()));

			
		});
		
		main.getChildren().addAll(actorpane, moviePane, connectionPane, lastButtons);
		Scene scene = new Scene(main);
		stage.setScene(scene);
		stage.setTitle("6 Degrees of Kevin Bacon");
		stage.show();		
	}

}
