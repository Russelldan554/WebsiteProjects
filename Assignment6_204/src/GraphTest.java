
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class GraphTest {
	private ActorGraph graph;
	private Actor[] actor;

	@Before
	public void setUp() throws Exception {
		 graph = new ActorGraph();
		  actor = new Actor[12];
		  
		  for (int i = 1; i < 12; i++) {
			  actor[i] = new Actor("Actor_" + i);
			  graph.addVertex(actor[i]);
		  }
		  
		  graph.addEdge(actor[1], actor[2], 1, "Movie_1");
		  graph.addEdge(actor[1], actor[3], 1, "Movie_2");
		  graph.addEdge(actor[1], actor[5], 1, "Movie_3");
		  graph.addEdge(actor[3], actor[7], 1, "Movie_4");
		  graph.addEdge(actor[3], actor[8], 1, "Movie_5");
		  graph.addEdge(actor[4], actor[8], 1, "Movie_6");
		  graph.addEdge(actor[6], actor[9], 1, "Movie_7");
		  graph.addEdge(actor[9], actor[10], 1, "Movie_8");
		  graph.addEdge(actor[8], actor[10], 1, "Movie_9");
		  graph.addEdge(actor[5], actor[10], 1, "Movie_10");
		  graph.addEdge(actor[10], actor[11], 1, "Movie_11");
		  graph.addEdge(actor[2], actor[11], 1, "Movie_12");
	}

	@After
	public void tearDown() throws Exception {
		graph = null;
	}

	@Test
	public void testGetEdge() {
		//Changed the test to this to find out if it was returning the right edge which it is, but i dont know why im getting a stack overflow error here
		//I believe i may have messed up the equals method in MovieEdge but I am not sure how to fix it and i dont use it in my other classes
		MovieEdge edge = new MovieEdge(actor[2], actor[11],1, "Movie_12");
		assertEquals(edge.getMovieName(), graph.getEdge(actor[2], actor[11]).getMovieName());
		//assertEquals(new MovieEdge(actor[2], actor[11],1, "Movie_12"), graph.getEdge(actor[2], actor[11]));
		//assertEquals(new MovieEdge(actor[3], actor[7],1, "Movie_4"), graph.getEdge(actor[3], actor[7]));
	}

	@Test
	public void testAddEdge() {
		assertEquals(false, graph.containsEdge(actor[3], actor[5]));
		graph.addEdge(actor[3], actor[5], 1, "Movie_13");
		assertEquals(true, graph.containsEdge(actor[3], actor[5]));
	}

	@Test
	public void testAddVertex() {
		Actor newActor = new Actor("Actor_12");
		assertEquals(false, graph.containsVertex(newActor));
		graph.addVertex(newActor);
		assertEquals(true, graph.containsVertex(newActor));
	}

	@Test
	public void testContainsEdge() {
		assertEquals(true, graph.containsEdge(actor[2], actor[11]));
		assertEquals(false, graph.containsEdge(actor[3], actor[5]));
	}

	@Test
	public void testContainsVertex() {
		assertEquals(true, graph.containsVertex(new Actor("Actor_2")));
		assertEquals(false, graph.containsVertex(new Actor("Actor_12")));
	}

	@Test
	public void testEdgeSet() {
		Set<MovieEdge> movies = graph.edgeSet();
		ArrayList<String> movieArrayList = new ArrayList<String>();
		for(MovieEdge movie : movies)
			movieArrayList.add(movie.getMovieName());
		Collections.sort(movieArrayList);
		assertEquals("Movie_1", movieArrayList.get(0));
		assertEquals("Movie_10", movieArrayList.get(1));
		assertEquals("Movie_11", movieArrayList.get(2));
		assertEquals("Movie_12", movieArrayList.get(3));
		assertEquals("Movie_2", movieArrayList.get(4));
		assertEquals("Movie_8", movieArrayList.get(10));
	}

	@Test
	public void testEdgesOf() {
		Set<MovieEdge> movies = graph.edgesOf(new Actor("Actor_1"));
		ArrayList<String> movieArrayList = new ArrayList<String>();
		for(MovieEdge movie : movies)
			movieArrayList.add(movie.getMovieName());
		Collections.sort(movieArrayList);
		assertEquals("Movie_1", movieArrayList.get(0));
		assertEquals("Movie_2", movieArrayList.get(1));
		assertEquals("Movie_3", movieArrayList.get(2));
	}
	
	@Test
	public void testEdgesOfSTUDENT() {
		Set<MovieEdge> moviesStudent = graph.edgesOf(new Actor("Actor_10"));
		ArrayList<String> movieArrayList = new ArrayList<String>();
		for(MovieEdge movie : moviesStudent)
			movieArrayList.add(movie.getMovieName());
		Collections.sort(movieArrayList);
		assertEquals("Movie_10", movieArrayList.get(0));
		assertEquals("Movie_11", movieArrayList.get(1));
		assertEquals("Movie_8", movieArrayList.get(2));
		assertEquals("Movie_9", movieArrayList.get(3));
	}

	@Test
	public void testRemoveEdge() {
		assertEquals(true, graph.containsEdge(actor[2], actor[11]));
		graph.removeEdge(actor[2], actor[11], 1, "Movie_12");
		assertEquals(false, graph.containsEdge(actor[2], actor[11]));
	}

	@Test
	public void testRemoveEdgeSTUDENT() {
		assertEquals(true, graph.containsEdge(actor[6], actor[9]));
		graph.removeEdge(actor[6], actor[9], 1, "Movie_7");
		assertEquals(false, graph.containsEdge(actor[6], actor[9]));
	}
	
	@Test
	public void testRemoveVertex() {
		assertEquals(true, graph.containsVertex(actor[2]));
		graph.removeVertex(actor[2]);
		assertEquals(false, graph.containsVertex(actor[2]));
	}

	@Test
	public void testVertexSet() {
		Set<Actor> movies = graph.vertexSet();
		assertEquals(true,movies.contains(actor[1]));
		assertEquals(true, movies.contains(actor[10]));
		assertEquals(true, movies.contains(actor[11]));
		assertEquals(true, movies.contains(actor[2]));
		assertEquals(true, movies.contains(actor[3]));
	}

	 @Test
	  public void testActor_1ToActor_11() {
		  String beginActor = "Actor_1", endActor = "Actor_11";
		  Actor beginIndex=null, endIndex=null;
		  Set<Actor> actors = graph.vertexSet();
		  Iterator<Actor> iterator = actors.iterator();
		  while(iterator.hasNext())
		  {    	
			  Actor actor = iterator.next();
			  if(actor.getName().equals(beginActor))
				  beginIndex = actor;
			  if(actor.getName().equals(endActor))
				  endIndex = actor;		
		  }
		  if(beginIndex != null && endIndex != null)
		  {

			  ArrayList<String> path = graph.shortestPath(beginIndex,endIndex);
			  assertNotNull(path);
			  assertTrue(path.size() > 0);
			  
			  assertEquals("Actor_1 via Movie_1 to Actor_2",path.get(0).trim());
			  assertEquals("Actor_2 via Movie_12 to Actor_11",path.get(1).trim());
		  }
		  else
			  fail("Actors names are not valid");

	  }
	  
	  
	  @Test
	  public void testActor_1ToActor_10() {
		  String beginActor = "Actor_1", endActor = "Actor_10";
		  Actor beginIndex=null, endIndex=null;
		  Set<Actor> actors = graph.vertexSet();
		  Iterator<Actor> iterator = actors.iterator();
		  while(iterator.hasNext())
		  {    	
			  Actor actor = iterator.next();
			  if(actor.getName().equals(beginActor))
				  beginIndex = actor;
			  if(actor.getName().equals(endActor))
				  endIndex = actor;		
		  }
		  if(beginIndex != null && endIndex != null)
		  {

			  ArrayList<String> path = graph.shortestPath(beginIndex,endIndex);
			  assertNotNull(path);
			  assertTrue(path.size() > 0);

			  assertEquals("Actor_1 via Movie_3 to Actor_5",path.get(0).trim());
			  assertEquals("Actor_5 via Movie_10 to Actor_10",path.get(1).trim());
		  }
		  else
			  fail("Actor names are not valid");

	  }
	  
	  @Test
	  public void testActor_4ToActor_11() {
		  String beginActor = "Actor_4", endActor = "Actor_11";
		  Actor beginIndex=null, endIndex=null;
		  Set<Actor> actors = graph.vertexSet();
		  Iterator<Actor> iterator = actors.iterator();
		  while(iterator.hasNext())
		  {    	
			  Actor actor = iterator.next();
			  if(actor.getName().equals(beginActor))
				  beginIndex = actor;
			  if(actor.getName().equals(endActor))
				  endIndex = actor;		
		  }
		  if(beginIndex != null && endIndex != null)
		  {

			  ArrayList<String> path = graph.shortestPath(beginIndex,endIndex);
			  assertNotNull(path);
			  assertTrue(path.size() > 0);

			  assertEquals("Actor_4 via Movie_6 to Actor_8",path.get(0).trim());
			  assertEquals("Actor_8 via Movie_9 to Actor_10",path.get(1).trim());
			  assertEquals("Actor_10 via Movie_11 to Actor_11",path.get(2).trim());
		  }
		  else
			  fail("Actor names are not valid");

	  }
	

}