
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class ActorGraphManagerTest {
	private ActorGraphManager graph;
	private String[] actor;
	  
	@Before
	public void setUp() throws Exception {
		  graph = new ActorGraphManager();
		  actor = new String[12];
		  
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

		  
	}

	@After
	public void tearDown() throws Exception {
		graph = null;
	}

	@Test
	public void testAddMovie() {
		ArrayList<String> movies = graph.allMovies();
		assertEquals("Movie_1", movies.get(0));
		assertEquals("Movie_10", movies.get(1));
		assertEquals("Movie_11", movies.get(2));
		assertEquals("Movie_12", movies.get(3));
		assertEquals("Movie_13", movies.get(4));
		graph.addMovie(actor[4], actor[11], "Movie_14");
		movies = graph.allMovies();
		assertEquals("Movie_1", movies.get(0));
		assertEquals("Movie_10", movies.get(1));
		assertEquals("Movie_11", movies.get(2));
		assertEquals("Movie_12", movies.get(3));
		assertEquals("Movie_13", movies.get(4));
		assertEquals("Movie_14", movies.get(5));
		
	}

	@Test
	public void testGetMovie() {
		assertEquals("Movie_12", graph.getMovie(actor[2], actor[11]));
		assertEquals("Movie_4", graph.getMovie(actor[3], actor[7]));
	}

	@Test
	public void testAddActor() {
		assertEquals(false, graph.containsActor("Actor_12"));
		graph.addActor("Actor_12");
		assertEquals(true, graph.containsActor("Actor_12"));
	}

	@Test
	public void testContainsActor() {
		assertEquals(true, graph.containsActor("Actor_2"));
		assertEquals(false, graph.containsActor("Actor_12"));
	}

	@Test
	public void testContainsMovieConnection() {
		assertEquals(true, graph.containsMovieConnection(actor[2], actor[11]));
		assertEquals(false, graph.containsMovieConnection(actor[3], actor[5]));
	}

	@Test
	public void testAllMovies() {
		ArrayList<String> movies = graph.allMovies();
		assertEquals("Movie_1", movies.get(0));
		assertEquals("Movie_10", movies.get(1));
		assertEquals("Movie_11", movies.get(2));
		assertEquals("Movie_7", movies.get(10));
		assertEquals("Movie_8", movies.get(11));
	}

	@Test
	public void testDeleteMovieConnection() {
		assertEquals(true, graph.containsMovieConnection(actor[2], actor[11]));
		graph.deleteMovieConnection(actor[2], actor[11], "Movie_12");
		assertEquals(false, graph.containsMovieConnection(actor[2], actor[11]));
	}

	@Test
	public void testDeleteActor() {
		assertEquals(true, graph.containsActor("Actor_2"));
		graph.deleteActor(actor[2]);
		assertEquals(false, graph.containsActor("Actor_2"));
	}

	@Test
	public void testDeleteActorSTUDENT() {
		assertEquals(true, graph.containsActor("Actor_10"));
		graph.deleteActor(actor[10]);
		assertEquals(false, graph.containsActor("Actor_10"));
	}
	
	@Test
	public void testAllActors() {
		ArrayList<String> movies = graph.allActors();
		assertEquals("Actor_1", movies.get(0));
		assertEquals("Actor_10", movies.get(1));
		assertEquals("Actor_11", movies.get(2));
		assertEquals("Actor_2", movies.get(3));
		assertEquals("Actor_8", movies.get(9));
	}

	@Test
	public void testGetPath() {
		ArrayList<String> path = graph.getPath(actor[1],actor[11]);
		  assertNotNull(path);
		  assertTrue(path.size() > 0);
		  
		  assertEquals("Actor_1 via Movie_1 to Actor_2",path.get(0).trim());
		  assertEquals("Actor_2 via Movie_12 to Actor_11",path.get(1).trim());
	}
	
	@Test
	public void testGetPathA() {
		ArrayList<String> path = graph.getPath(actor[1],actor[10]);
		  assertNotNull(path);
		  assertTrue(path.size() > 0);
		  
		  assertEquals("Actor_1 via Movie_3 to Actor_5",path.get(0).trim());
		  assertEquals("Actor_5 via Movie_10 to Actor_10",path.get(1).trim());
	}
	
	@Test
	public void testGetPathB() {
		ArrayList<String> path = graph.getPath(actor[1],actor[6]);
		  assertNotNull(path);
		  assertTrue(path.size() > 0);
		  assertEquals("Actor_1 via Movie_2 to Actor_3",path.get(0).trim());
		  assertEquals("Actor_3 via Movie_4 to Actor_7",path.get(1).trim());
		  assertEquals("Actor_7 via Movie_13 to Actor_6",path.get(2).trim());
	}
	
	@Test
	public void testGetPathSTUDENT() {
		ArrayList<String> path = graph.getPath(actor[1],actor[4]);
		  assertNotNull(path);
		  assertTrue(path.size() > 0);
		  assertEquals("Actor_1 via Movie_2 to Actor_3",path.get(0).trim());
		  assertEquals("Actor_3 via Movie_5 to Actor_8",path.get(1).trim());
		  assertEquals("Actor_4 via Movie_6 to Actor_8",path.get(2).trim());
	}

}