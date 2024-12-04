import static org.junit.Assert.*;

import java.util.Collection;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for homework assignment which is to create a graph data structure.
 * 
 * The graph data structure can be used to encode a directed or undirected graph. For
 * an undirected graph, the addEdge() method can be called twice: once for addEdge(u,v)
 * and once for addEdge(v,u)
 * 
 * @author alchambers
 *
 */

public class GraphTester {
	private Graph<Integer> g;

	@Before
	public void setUp() {
		g = new Graph<Integer>();
	}

	/*===============================
	 * 			Adding Vertices		
	 * ==============================
	 */

	@Test
	public void testAddVertices() {
		addVertices();

		assertEquals(5, g.numVertices());
		assertEquals(0, g.numEdges());

		Collection<Integer> vertices = g.getVertices();
		for(int nodeId = 1; nodeId <= 5; nodeId++) {
			assertTrue(g.containsVertex(nodeId));
			assertTrue(vertices.contains(nodeId));
		}
	}

	/*===============================
	 * 			Adding Edges		
	 * ==============================
	 */

	@Test
	public void testAddEdges() {
		buildGraph();
		System.out.println(g.toString());

		assertEquals(8, g.numEdges());

		Collection<Integer> vertices = g.getNeighbors(1);
		assertEquals(2, vertices.size());
		assertTrue(vertices.contains(2));
		assertTrue(vertices.contains(4));

		vertices = g.getNeighbors(2);
		assertEquals(2, vertices.size());
		assertTrue(vertices.contains(3));
		assertTrue(vertices.contains(5));

		vertices = g.getNeighbors(3);
		assertEquals(1, vertices.size());
		assertTrue(vertices.contains(5));

		vertices = g.getNeighbors(4);
		assertEquals(3, vertices.size());
		assertTrue(vertices.contains(2));
		assertTrue(vertices.contains(3));
		assertTrue(vertices.contains(5));

		vertices = g.getNeighbors(5);
		assertEquals(0, vertices.size());
	}

	/*===============================
	 * 		Getting Neighbors		
	 * ==============================
	 */


	@Test
	public void testGetNeighbors() {
		addVertices();

		// note this is adding a self-loop between node 1 and itself
		for(int nodeId = 1; nodeId <= 5; nodeId++) {
			g.addEdge(1,  nodeId);
		}

		Collection<Integer> neighbors = g.getNeighbors(1);
		System.out.println("Neighbors of node 1: " + neighbors);		
		
		// check that each node's list of neighbors is empty
		for(int nodeId = 2; nodeId <= 5; nodeId++) {
			assertEquals(0, g.getNeighbors(nodeId).size());
		}

		// the only node with neigbors is node 1
		assertEquals(5, g.getNeighbors(1).size());

		// make sure that student's are saving the self-loop as well
		assertTrue(g.getNeighbors(1).contains(1));
	}


	/*===============================
	 * 		Getting Vertices		
	 * ==============================
	 */

	@Test
	public void testGetVertices() {
		Collection<Integer> vertices = g.getVertices();
		assertEquals(0, vertices.size());

		g.addVertex(-1); // note I'm calling this node "-1". Nothing should break
		vertices = g.getVertices();
		assertEquals(1, vertices.size());
		assertTrue(vertices.contains(-1));

		g.clear();
		vertices = g.getVertices();
		assertEquals(0, vertices.size());		
	}

	/*===============================
	 * 			Contains Vertex		
	 * ==============================
	 */

	@Test
	public void testcontainsVertex() {
		assertFalse(g.containsVertex(1));
		assertFalse(g.containsVertex(2));
		assertFalse(g.containsVertex(3));

		addVertices();
		for(int nodeId = 1; nodeId <= 5; nodeId++) {
			assertTrue(g.containsVertex(nodeId));
		}

		g.clear();
		for(int nodeId = 1; nodeId <= 5; nodeId++) {
			assertFalse(g.containsVertex(nodeId));
		}
	}

	/*===============================
	 * 		Testing Edge Exists		
	 * ==============================
	 */

	@Test
	public void testEdgeExists() {
		addVertices();
		assertFalse(g.edgeExists(1, 2));
		assertFalse(g.edgeExists(2, 5));
		assertFalse(g.edgeExists(4, 3));
		addEdges();
		assertTrue(g.edgeExists(1, 2));
		assertTrue(g.edgeExists(2, 5));
		assertTrue(g.edgeExists(4, 3));		
	}

	@Test
	public void testNumVerticesAndNumEdges() {
		assertEquals(0, g.numEdges());
		assertEquals(0, g.numVertices());
		buildGraph();
		assertEquals(8, g.numEdges());
		assertEquals(5, g.numVertices());	
		g.clear();
		assertEquals(0, g.numEdges());
		assertEquals(0, g.numVertices());		
	}


	/*===============================
	 * 		All Other Methods		
	 * ==============================
	 */

	@Test
	public void testDegree() {
		addVertices();
		assertEquals(0, g.degree(1));
		assertEquals(0, g.degree(2));
		assertEquals(0, g.degree(3));
		assertEquals(0, g.degree(4));
		assertEquals(0, g.degree(5));		
		buildGraph();
		assertEquals(2, g.degree(1));
		assertEquals(2, g.degree(2));
		assertEquals(1, g.degree(3));
		assertEquals(3, g.degree(4));
		assertEquals(0, g.degree(5));
	}

	@Test
	public void testClear() {
		g.clear(); // should not throw an error
		assertEquals(0, g.numVertices());
		assertEquals(0, g.numEdges());

		buildGraph();
		g.clear();
		assertEquals(0, g.numVertices());
		assertEquals(0, g.numEdges());		
	}

	/*------------------------------------------------
	 * 			TEST FOR CORRECT THROWING OF ERRORS
	 *------------------------------------------------*/

	// Tries to add an edge between two vertices that don't exist
	@Test(expected=IllegalArgumentException.class)
	public void ERRORtestAddEdgeMissingEndpoint1(){
		g.addVertex(1);
		g.addEdge(1, 2);
	}

	// Tries to add an edge between two vertices that don't exist
	@Test(expected=IllegalArgumentException.class)
	public void ERRORtestAddEdgeMissingEndpoint2(){
		g.addVertex(1);
		g.addEdge(2,1);
	}

	// Tries to get neighbors of a node that does not exist
	@Test(expected=IllegalArgumentException.class)
	public void ERRORtestGetNeighborsNonExistentNode(){
		g.getNeighbors(1);		
	}


	// Checks if an edge exists between two vertices NOT in the graph
	@Test(expected=IllegalArgumentException.class)
	public void ERRORtestEdgeExistsMissingBothEndpoints() {
		g.edgeExists(1, 2);
	}

	// Checks if an edge exists between one vertex in the graph
	// and the other NOT in the graph
	@Test(expected=IllegalArgumentException.class)
	public void ERRORtestEdgeExistsMissingOneEndpoint() {
		g.addVertex(1);
		g.edgeExists(1, 2);
	}

	// Tries to add an edge between two vertices NOT in the graph
	@Test(expected=IllegalArgumentException.class)
	public void ERRORtestAddEdgeMissingBothEndpoints() {
		g.addEdge(1, 2);
	}

	// Tries to add an edge between 1 vertex in the graph
	// and the other NOT in the graph
	@Test(expected=IllegalArgumentException.class)
	public void ERRORtestAddEdgeMissingOneEndpoint() {
		g.addVertex(1);
		g.addEdge(1, 2);
	}

	// Tries to get the degree of a node not in the graph
	@Test(expected=IllegalArgumentException.class)
	public void ERRORtestDegreeMissingVertex() {
		g.degree(1);
	}



	/*-----------------------------------------
	 * 			PRIVATE HELPER METHODS
	 *-----------------------------------------*/

	private void buildGraph() {
		addVertices();
		addEdges();		
	}

	private void addVertices() {
		g.addVertex(1);
		g.addVertex(2);
		g.addVertex(3);
		g.addVertex(4);
		g.addVertex(5);
	}

	private void addEdges() {		
		g.addEdge(1,2);
		g.addEdge(1,4);
		g.addEdge(4,2);
		g.addEdge(2,3);
		g.addEdge(2,5);
		g.addEdge(4,3);
		g.addEdge(3,5);
		g.addEdge(4,5);
	}

}
