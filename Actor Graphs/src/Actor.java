import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/**
 * Data Element the represents vertexs in Actor Graph class
 * @author Dan
 *
 */
public class Actor implements Comparable<Actor>{

	private String name;
	private ArrayList<Actor> adjacency;
	private Map<String, Integer> adjList;
	private Actor previous;
	
	/**
	 * constructor for Actor
	 * @param name
	 */
	public Actor(String name){
		this.name = name;	
		adjacency = new ArrayList<Actor>();
		adjList = new HashMap<String, Integer>();
	}
	
	/**
	 * returns the name of the Actor
	 * @return name of Actor
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * doesnt work 
	 */
	public boolean equals(Object obj){
		if (this == obj){
			return true;
		} else
			return false;		
	}
	
	/**
	 * returns string with name of actor
	 */
	public String toString(){
		return name;
	}
	
	/**
	 * sets the previous actor in min tree
	 * @param v actor previous
	 */
	public void setPrevious(Actor v){
		previous = v;
	}
	
	/**
	 * returns the previous actor in min tree
	 * @return previous Actor
	 */
	public Actor getPrevious(){
		return previous;
	}
	
	/**
	 *  returns 0 if actors are equal -1 if smaller and 1 if larger
	 */
	@Override
	public int compareTo(Actor arg0) {
		if (this.name.equals(arg0.getName())){
			return 0;
		}
		if (name.compareTo(arg0.getName()) < 0) {
			return -1;
		} else
			return 1;
	}

	/**
	 * adds actor at index to adjacency list
	 * @param actor to add
	 * @param index where it is on graph
	 * @return true if added
	 */
	public boolean addAdjacency(Actor actor, int index){
		adjacency.add(actor);
		adjList.put(actor.getName(), index);
		return true;
	}
	
	/**
	 * returns true if the actor is in the adjacency list
	 * @param actor
	 * @return true if actor is in adj list
	 */
	public Boolean contains(Actor actor){
		for (int i = 0; i < adjacency.size(); i++){
			if (adjacency.get(i).getName().equals(actor.getName())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * returns an array list of actors that are adjacent to this Actor
	 * @return an array list of actors that are adjacent to this Actor
	 */
	public ArrayList<Actor> getAdjacencyList(){
		return adjacency;
	}
	
	/**
	 * returns the edge number of the adjacent actor
	 * @param v actor to get edge number
	 * @return edge number
	 */
	public int getEdgeNumber(Actor v){
		return adjList.getOrDefault(v.getName(), -1);
	}
	
	/**
	 * removes actor from adjacency list
	 * @param v actor to remove
	 * @return -1 if failed or edge of removed actor
	 */
	public int removeActor(Actor v){
		Boolean list = false;
		for (int i = 0; i < adjacency.size(); i++) {
			if (adjacency.get(i).compareTo(v) == 0) {
				adjacency.remove(i);
				list = true;
			}
		}
		if (list == true){
			return adjList.remove(v.getName());
		}
		return -1;
	}

}
