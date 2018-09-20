/**
 * Data Element to store edges of graph in ActorGraph class
 * @author Dan
 *
 */
public class MovieEdge implements Comparable<MovieEdge> {

	Actor source;
	Actor destination;
	int weight;
	String movieName;
	
	/**
	 * constructor for movie Edge
	 * @param source
	 * @param destination
	 * @param weight
	 * @param movieName
	 */
	public MovieEdge(Actor source, Actor destination, int weight, String movieName){
		this.source= source;
		this.destination = destination;
		this.weight = weight;
		this.movieName = movieName;
	}
	
	/**
	 * returns the source Actor
	 * @return returns source Actor
	 */
	public Actor getSource(){
		return source;
	}
	
	/**
	 * returns the destination Actor
	 * @return destination actor
	 */
	public Actor getDestination(){
		return destination;
	}

	/**
	 * returns the weight of the edge
	 * @return weight of edge
	 */
	public int getWeight(){
		return weight;
	}
	
	/**
	 * returns the movie name
	 * @return movie name
	 */
	public String getMovieName(){
		return movieName;
	}
	
	/**
	 * returns string with Source actor via Movie Name to Destination Actor
	 */
	public String toString(){
		return (source + " via " + movieName + " to " + destination);
	}
	
	/**
	 * doesnt work I dont use it
	 */
	public boolean equals(Object o){
		return true;
	}
	
	/**
	 * compares the weights of edges
	 */
	@Override
	public int compareTo(MovieEdge o) {
		if (weight < o.getWeight()) {
			return -1;
		} else if (weight == o.getWeight()) {
			return 0;
		} else
			return 1;
	}
	
	
	
}
