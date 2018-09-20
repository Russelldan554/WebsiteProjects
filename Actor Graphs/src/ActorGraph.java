import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;
/**
 * Graph Data Structure for connecting actors to movies and finding connections 
 * @author Dan
 *
 */
public class ActorGraph implements GraphInterface<Actor,MovieEdge>{
	
	ArrayList<Actor> vertex;
	ArrayList<MovieEdge> edge;
	
	/**
	 * constructor for graph
	 */
	public ActorGraph(){
		edge = new ArrayList<MovieEdge>();		
		vertex = new ArrayList<Actor>();
	}
	
	@Override
	public MovieEdge getEdge(Actor sourceVertex, Actor destinationVertex) {
		int edgeNumber = vertex.get(getVertex(sourceVertex)).getEdgeNumber(destinationVertex);
		if (edgeNumber >= 0 && edgeNumber < vertex.size()){	
			return edge.get(edgeNumber);
		}
		return null;
	}

	@Override
	public MovieEdge addEdge(Actor sourceVertex, Actor destinationVertex, int weight, String description) {
		MovieEdge add = new MovieEdge(sourceVertex, destinationVertex, weight, description);
		edge.add(add);	
		if (getVertex(sourceVertex) == -1){
			vertex.add(sourceVertex);
		}
		vertex.get(getVertex(sourceVertex)).addAdjacency(destinationVertex, (edge.size()-1));
		if (getVertex(destinationVertex) == -1){
			vertex.add(destinationVertex);
		}
		vertex.get(getVertex(destinationVertex)).addAdjacency(sourceVertex, (edge.size()-1));
		return add;
	}

	@Override
	public boolean addVertex(Actor v) {
		for (int i = 0; i < vertex.size(); i++){
			if (v.compareTo(vertex.get(i)) == 0){
				return false;
			}
		}
		vertex.add(v);
		return true;
	}

	@Override
	public boolean containsEdge(Actor sourceVertex, Actor destinationVertex) {
		return vertex.get(getVertex(sourceVertex)).contains(destinationVertex);
		
	}

	@Override
	public boolean containsVertex(Actor v) {
		for (int i = 0; i < vertex.size(); i++){
			if (v.compareTo(vertex.get(i)) == 0) {
				return true;
			} 
		}
		return false;
	}

	@Override
	public Set<MovieEdge> edgeSet() {
		Set<MovieEdge> adj = new HashSet<MovieEdge>();
		for (int i = 0; i < edge.size(); i++){
			adj.add(edge.get(i));
		}
		return adj;
	}

	@Override
	public Set<MovieEdge> edgesOf(Actor vertex) {
		Set<MovieEdge> adj = new HashSet<MovieEdge>();
		ArrayList<Actor> adjacency1 = this.vertex.get(getVertex(vertex)).getAdjacencyList();
		for (Actor act : adjacency1){
			adj.add(getEdge(vertex, act));
		}
		return adj;
	}

	@Override
	public MovieEdge removeEdge(Actor sourceVertex, Actor destinationVertex, int weight, String description) {
		int one, two;
		one = vertex.get(getVertex(sourceVertex)).removeActor(destinationVertex);
		two = vertex.get(getVertex(destinationVertex)).removeActor(sourceVertex);
		try {
			if (one > -1 && two > -1) {
				return edge.remove(one);
			}
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	@Override
	public boolean removeVertex(Actor v) {
		int remove = getVertex(v);
		if (remove > 0){
			vertex.remove(getVertex(v));
			return true;
		} else
			return false;
	}

	@Override
	public Set<Actor> vertexSet() {
		Set<Actor> actors = new HashSet<Actor>();
		for (Actor act : vertex){
			actors.add(act);
		}
		return actors;
	}
	
	@Override
	public ArrayList<String> shortestPath(Actor sourceVertex, Actor destinationVertex) {
		for (Actor act : vertex){
			act.setPrevious(null);
		}
		dijkstraShortestPath(vertex.get(getVertex(sourceVertex)));
		ArrayList<Actor> path = new ArrayList<Actor>();
		ArrayList<String> complete = new ArrayList<String>();
		Actor start = vertex.get(getVertex(destinationVertex));
		int count = 0;
		while (start != null && !start.getName().equals(vertex.get(getVertex(sourceVertex))) && count  != vertex.size()){
			path.add(start);
			start = start.getPrevious();
		}	
		Collections.reverse(path);
		for (int i = 0; i < path.size()-1; i++){
			complete.add(getEdge(path.get(i), path.get(i+1)).toString());
		}
		return complete;
	}

	@Override
	public void dijkstraShortestPath(Actor sourceVertex) {
		int[] dist = new int[vertex.size()];

		PriorityQueue<Actor> priorityQueue = new PriorityQueue<Actor>();
		
		for (int i = 1; i < dist.length; i++)
        {
            dist[i] = Integer.MAX_VALUE;
        }
		
		priorityQueue.add(vertex.get(getVertex(sourceVertex)));
		dist[getVertex(sourceVertex)] = 0;
		while (!priorityQueue.isEmpty()) {
			Actor current = vertex.get(getVertex(priorityQueue.poll()));

			for (Actor adj : current.getAdjacencyList())
			{
				int weight = 1; //getEdge(current, adj).weight;
				int distanceThroughU = dist[getVertex(current)] + weight;
				if (distanceThroughU < dist[getVertex(adj)]) {
					priorityQueue.remove(adj);

					dist[getVertex(adj)] = distanceThroughU ;
					vertex.get(getVertex(adj)).setPrevious(current);
					priorityQueue.add(adj);
				}
			}
		}	
	}
	
	/**
	 * gets the index for the actor in the vertex arraylist
	 * @param v actor to get index of
	 * @return index of actor
	 */
	public int getVertex(Actor v){
		for (int i = 0; i < vertex.size(); i++){
			if (v.compareTo(vertex.get(i)) == 0) {
				return i;
			} 
		}
		return -1;
	}
}
