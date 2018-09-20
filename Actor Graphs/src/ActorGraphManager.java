import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 * Manager for class Actor Graph takes input from gui to start graph
 * @author Daniel Russell
 *
 */
public class ActorGraphManager implements ActorGraphManagerInterface {

	ActorGraph graph;
	/**
	 * constructor for graph manager
	 */
	public ActorGraphManager(){
		graph = new ActorGraph();
	}
	
	@Override
	public boolean addMovie(String actor1, String actor2, String movieName) {
		Actor actorOne = new Actor(actor1);
		Actor actorTwo = new Actor(actor2);
		if (graph.addEdge(actorOne, actorTwo, 1, movieName) == null){
			return false;
		} else
		 return true;
	}

	@Override
	public String getMovie(String actor1, String actor2) {
		String movie = " ";
		MovieEdge moEdge;
		Actor actorOne = new Actor(actor1);
		Actor actorTwo = new Actor(actor2);
		if (graph.containsEdge(actorOne, actorTwo)){
			moEdge = graph.getEdge(actorOne, actorTwo);
			movie = moEdge.getMovieName();
		}
		return movie;
	}

	@Override
	public boolean addActor(String v) {
		Actor actor = new Actor(v);
		return graph.addVertex(actor);
	}

	@Override
	public boolean containsActor(String v) {
		return graph.containsVertex(new Actor(v));
	}

	@Override
	public boolean containsMovieConnection(String actor1, String actor2) {
		Actor actorOne = new Actor(actor1);
		Actor actorTwo = new Actor(actor2);
		return graph.containsEdge(actorOne, actorTwo);
	}

	@Override
	public ArrayList<String> allMovies() {
		ArrayList<MovieEdge> allMovies = new ArrayList<MovieEdge>();
		Set<MovieEdge> edges = graph.edgeSet();
		ArrayList<String> moviesString = new ArrayList<String>();
		allMovies.addAll(edges);
		for (int i = 0; i < allMovies.size(); i++){
			moviesString.add(allMovies.get(i).getMovieName());
		}
		Collections.sort(moviesString);
		return moviesString;
	}
	

	@Override
	public boolean deleteMovieConnection(String actor1, String actor2, String movieName) {
		MovieEdge remove = graph.removeEdge(new Actor(actor1), new Actor(actor2), 1, movieName);
		if (remove == null){
			return false;
		} else
			return true;
	}

	@Override
	public boolean deleteActor(String v) {
		return graph.removeVertex(new Actor(v));
	}

	@Override
	public ArrayList<String> allActors() {
		ArrayList<Actor> allActors = new ArrayList<Actor>();
		Set<Actor> actors = graph.vertexSet();
		ArrayList<String> actorsString = new ArrayList<String>();
		allActors.addAll(actors);
		for (int i = 0; i < allActors.size(); i++){
			actorsString.add(allActors.get(i).getName());
		}
		Collections.sort(actorsString);
		return actorsString;
	}

	@Override
	public ArrayList<String> getPath(String actor1, String actor2) {
		return graph.shortestPath(new Actor(actor1), new Actor(actor2));
	}
}
