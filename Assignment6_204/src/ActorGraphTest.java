
import static org.junit.Assert.*;

import org.junit.*;
import java.util.*;

public class ActorGraphTest {

  private ActorGraph graph;
  private ActorGraph studentGraph;
  Actor[] actor;
  Actor[] actor2;
  
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
	  
	  
	  // MY TEST GRAPH
	  
	  studentGraph = new ActorGraph();
	  actor2 = new Actor[12];
	  
	  for (int i = 1; i < 12; i++) {
		  actor2[i] = new Actor("Actor_" + i);
		  studentGraph.addVertex(actor[i]);
	  }
	  
	  // There is exactly 1 path from 10 to 5
	  // There are four non-cyclic paths from 2 to 9, 
	  // one much smaller than the others
	  studentGraph.addEdge(actor[1], actor[2], 1, "Movie_1");
	  studentGraph.addEdge(actor[1], actor[3], 1, "Movie_2");
	  studentGraph.addEdge(actor[1], actor[4], 1, "Movie_3");
	  studentGraph.addEdge(actor[1], actor[10], 1, "Movie_4");
	  studentGraph.addEdge(actor[2], actor[3], 1, "Movie_5");
	  studentGraph.addEdge(actor[2], actor[4], 1, "Movie_6");
	  studentGraph.addEdge(actor[4], actor[9], 1, "Movie_7");
	  studentGraph.addEdge(actor[4], actor[10], 1, "Movie_8");
	  studentGraph.addEdge(actor[5], actor[8], 1, "Movie_9");
	  studentGraph.addEdge(actor[6], actor[8], 1, "Movie_10");
	  studentGraph.addEdge(actor[6], actor[11], 1, "Movie_11");
	  studentGraph.addEdge(actor[7], actor[9], 1, "Movie_12");
	  studentGraph.addEdge(actor[7], actor[11], 1, "Movie_13");
	  studentGraph.addEdge(actor[9], actor[10], 1, "Movie_14");
}

	@After
	public void tearDown() throws Exception {
		graph = null;
		actor = null;
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
		  fail("Actors names are not valid");

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
		  fail("Actors names are not valid");

  }
  public void testStudentActor_2ToActor_9() {
	  String beginActor = "Actor_2", endActor = "Actor_9";
	  Actor beginIndex=null, endIndex=null;
	  Set<Actor> actors = studentGraph.vertexSet();
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

		  ArrayList<String> path = studentGraph.shortestPath(beginIndex,endIndex);
		  assertNotNull(path);
		  assertTrue(path.size() > 0);

		  String result="";
		  for(String edge : path)
			  result += edge;
		  Scanner scan = new Scanner(result);
		  
		  assertEquals("Actor_2 via Movie_6 to Actor_4",scan.nextLine());
		  assertEquals("Actor_4 via Movie_7 to Actor_10",scan.nextLine());
	  }
	  else
		  fail("Actors names are not valid");

  }

public void testStudentActor_10ToActor_5() {
	  String beginActor = "Actor_10", endActor = "Actor_5";
	  Actor beginIndex=null, endIndex=null;
	  Set<Actor> actors = studentGraph.vertexSet();
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

		  ArrayList<String> path = studentGraph.shortestPath(beginIndex,endIndex);
		  assertNotNull(path);
		  assertTrue(path.size() > 0);

		  String result="";
		  for(String edge : path)
			  result += edge;
		  Scanner scan = new Scanner(result);
		  
		  assertEquals("Actor_10 via Movie_14 to Actor_9",scan.nextLine());
		  assertEquals("Actor_9 via Movie_12 to Actor_7",scan.nextLine());
		  assertEquals("Actor_7 via Movie_13 to Actor_11",scan.nextLine());
		  assertEquals("Actor11 via Movie_11 to Actor_6",scan.nextLine());
		  assertEquals("Actor_6 via Movie_10 to Actor_8",scan.nextLine());
		  assertEquals("Actor_8 via Movie_9 to Actor_5",scan.nextLine());
	  }
	  else
		  fail("Actors names are not valid");

}
} 