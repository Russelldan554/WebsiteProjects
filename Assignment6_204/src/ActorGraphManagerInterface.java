
import java.util.*;

public interface ActorGraphManagerInterface {
	
	/**
	 * Adds a movie with 2 actors and a movie name
	 * @param actor1 name of actor 1 (lastname, firstname)
	 * @param actor2 name of actor 2 (lastname, firstname)
	 * @param movieName name of movie
	 * @return true if the movie was added successfully
	 */
	public boolean addMovie(String actor1, String actor2, String movieName);
	
	/**
	 * Returns the name of the movie that both actors are connected through
	 * @param actor1 name of actor 1 (lastname, firstname)
	 * @param actor2 name of actor 2 (lastname, firstname)
	 * @return name of movie if actor 1 and actor2 are in the same movie, returns null if not
	 */
	public String getMovie(String actor1, String actor2);
	
	/**
	 * Adds an actor to the graph
	 * @param v the actor's name  (lastname, firstname)
	 * @return true if the actor was successfully added, false if not
	 */
	public boolean addActor(String v);
	
	/**
	 * Determines if an actor is already in the graph
	 * @param v the actor's name  (lastname, firstname)
	 * @return true if the actor is in the graph, false if not
	 */
	public boolean containsActor(String v);
	
	/**
	 * Determines if a movie is in the graph
	 * @param actor1 name of actor 1 (lastname, firstname)
	 * @param actor2 name of actor 2 (lastname, firstname)
	 * @return true if the movie is in the graph, false if not
	 */
	public boolean containsMovieConnection(String actor1, String actor2);
	
	/**
	 * Creates an arraylist of all movie titles in sorted order
	 * @return an arraylist of all movie titles in sorted order
	 */
	public ArrayList<String> allMovies();
	
	/**
	 * Deletes a movie from the graph
	 * @param actor1 name of actor 1 (lastname, firstname)
	 * @param actor2 name of actor 2 (lastname, firstname)
	 * @param movieName the movie name
	 * @return true if the movie was successfully deleted, false if not
	 */
	public boolean deleteMovieConnection(String actor1, String actor2, String movieName);
	
	/**
	 * Deletes an actor from the graph
	 * @param v name of actor (lastname, firstname)
	 * @return true if the actor was successfully deleted, false if not
	 */
	public boolean deleteActor(String v);

	/**
	 * Creates an arraylist of all actors in alphabetical order (last name, first name)
	 * @return an arraylist of all actors in alphabetical order (last name, first name)
	 */
	public ArrayList<String> allActors();
	
	/**
	 * Returns the shortest path from actor 1 to actor
	 * @param actor1 name of actor 1 (lastname, firstname)
	 * @param actor2 name of actor 2 (lastname, firstname)
	 * @return an Arraylist of Movies connecting the two actors together, null if the
	 * actors have no path to connect them.
	 */
	public ArrayList<String> getPath(String actor1, String actor2);
	
}