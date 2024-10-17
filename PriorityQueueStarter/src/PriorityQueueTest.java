import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

/**
 * These unit tests use JUnit 5
 * @author achambers
 *
 */
public class PriorityQueueTest {
	PriorityQueue pq;

	@Before
	public void setUp() {
		pq = new PriorityQueue();			
	}


	/*
	 * UNIT TESTS FOR PUSH
	 */

	@Test
	public void testPush(){
		pq.push(10, 15);
		assertEquals(1, pq.size());
		assertEquals(10, (int)pq.heap.get(0).priority);
		assertEquals(15, (int)pq.heap.get(0).element);
		assertEquals(0, (int)pq.location.get(15));

		pq.push(11, 40);
		assertEquals(2, pq.size());
		assertEquals(10, (int)pq.heap.get(0).priority);
		assertEquals(15, (int)pq.heap.get(0).element);
		assertEquals(0, (int)pq.location.get(15));

		assertEquals(11, (int)pq.heap.get(1).priority);
		assertEquals(40, (int)pq.heap.get(1).element);
		assertEquals(1, (int)pq.location.get(40));
				
		
		pq.push(33, 20);
		assertEquals(3, pq.size());
		assertEquals(10, (int)pq.heap.get(0).priority);
		assertEquals(15, (int)pq.heap.get(0).element);
		assertEquals(0, (int)pq.location.get(15));

		assertEquals(11, (int)pq.heap.get(1).priority);
		assertEquals(40, (int)pq.heap.get(1).element);
		assertEquals(1, (int)pq.location.get(40));		
		
		assertEquals(33, (int)pq.heap.get(2).priority);
		assertEquals(20, (int)pq.heap.get(2).element);
		assertEquals(2, (int)pq.location.get(20));	
		
		
		
		pq.push(1, 100);
		assertEquals(4, pq.size());
		assertEquals(1, (int)pq.heap.get(0).priority);
		assertEquals(100, (int)pq.heap.get(0).element);
		assertEquals(0, (int)pq.location.get(100));
		
		
		assertEquals(10, (int)pq.heap.get(1).priority);
		assertEquals(15, (int)pq.heap.get(1).element);
		assertEquals(1, (int)pq.location.get(15));

		assertEquals(33, (int)pq.heap.get(2).priority);
		assertEquals(20, (int)pq.heap.get(2).element);
		assertEquals(2, (int)pq.location.get(20));			
		
		assertEquals(11, (int)pq.heap.get(3).priority);
		assertEquals(40, (int)pq.heap.get(3).element);
		assertEquals(3, (int)pq.location.get(40));		
		
		
		
		pq.push(2, 103);		
		assertEquals(5, pq.size());

		assertEquals(1, (int)pq.heap.get(0).priority);
		assertEquals(2, (int)pq.heap.get(1).priority);
		assertEquals(33, (int)pq.heap.get(2).priority);
		assertEquals(11, (int)pq.heap.get(3).priority);
		assertEquals(10, (int)pq.heap.get(4).priority);

		assertEquals(0, (int)pq.location.get(100));
		assertEquals(1, (int)pq.location.get(103));
		assertEquals(2, (int)pq.location.get(20));
		assertEquals(3, (int)pq.location.get(40));
		assertEquals(4, (int)pq.location.get(15));		
	}


	
	/*
	 * UNIT TESTS FOR POP
	 */
	@Test
	public void testPop() {
		fullyPopulatePQ();
		assertEquals(5, pq.size());	

		pq.pop();
		assertEquals(2, (int)pq.heap.get(0).priority);
		assertEquals(10, (int)pq.heap.get(1).priority);
		assertEquals(33, (int)pq.heap.get(2).priority);
		assertEquals(11, (int)pq.heap.get(3).priority);
		assertEquals(0, (int)pq.location.get(103));
		assertEquals(1, (int)pq.location.get(15));
		assertEquals(2, (int)pq.location.get(20));
		assertEquals(3, (int)pq.location.get(40));

		pq.pop();
		assertEquals(10, (int)pq.heap.get(0).priority);
		assertEquals(11, (int)pq.heap.get(1).priority);
		assertEquals(33, (int)pq.heap.get(2).priority);
		assertEquals(0, (int)pq.location.get(15));
		assertEquals(1, (int)pq.location.get(40));
		assertEquals(2, (int)pq.location.get(20));

		pq.pop();
		assertEquals(11, (int)pq.heap.get(0).priority);
		assertEquals(33, (int)pq.heap.get(1).priority);
		assertEquals(0, (int)pq.location.get(40));
		assertEquals(1, (int)pq.location.get(20));

		pq.pop();		
		assertEquals(33, (int)pq.heap.get(0).priority);
		assertEquals(0, (int)pq.location.get(20));		
		
		pq.pop();
		assertEquals(0, pq.heap.size());
		assertEquals(0, pq.location.keySet().size());
	}


	
	/*
	 * UNIT TESTS FOR CHANGE PRIORITY
	 */
	@Test
	public void testIncreasePriorityOfRoot() {
		partiallyPopulatePQ();	

		// Change root node from priority 10 to priority 1 (should not affect ordering in heap)
		// Element 15 goes from priority 10 to priority 1		
		// BEFORE: [ (10,15), (11,40), (33,20)]
		// AFTER: [ (1, 15), (11,40), (33,20)] 
		pq.changePriority(1, 15); 

		assertEquals(1, pq.topPriority());
		assertEquals(15, pq.topElement());

		pq.pop();
		assertEquals(11, pq.topPriority());
		assertEquals(40, pq.topElement());

		pq.pop();
		assertEquals(33, pq.topPriority());
		assertEquals(20, pq.topElement());			
	}

	@Test
	public void testIncreasePriorityOfLeaf() {
		partiallyPopulatePQ();
		
		// Change leaf node to highest priority
		// Element 20 goes from priority 33 to priority 1
		// BEFORE: [ (10,15), (11,40), (33,20)]
		// AFTER:  [ (1, 20), (10,15), (11,40)] 

		pq.changePriority(1, 20);

		assertEquals(1, pq.topPriority());
		assertEquals(20, pq.topElement());

		pq.pop();
		assertEquals(10, pq.topPriority());
		assertEquals(15, pq.topElement());

		pq.pop();
		assertEquals(11, pq.topPriority());
		assertEquals(40, pq.topElement());			
	}

	@Test
	public void testDecreasePriorityOfRoot() {
		partiallyPopulatePQ();

		// Change root node to lowest priority
		// Element 15 goes from priority 10 to priority 50
		// BEFORE: [ (10,15), (11,40), (33,20)]
		// AFTER:  [ (11,40), (33,20), (50,15)] 

		pq.changePriority(50, 15);

		assertEquals(11, pq.topPriority());
		assertEquals(40, pq.topElement());

		pq.pop();
		assertEquals(33, pq.topPriority());
		assertEquals(20, pq.topElement());

		pq.pop();
		assertEquals(50, pq.topPriority());
		assertEquals(15, pq.topElement());			
	}

	

	/*
	 * UNIT TESTS FOR ALL OTHER METHODS
	 */
	@Test
	public void OTHERtestGetPriority(){
		partiallyPopulatePQ();
		assertEquals(10, pq.getPriority(15));
		assertEquals(11, pq.getPriority(40));
		assertEquals(33, pq.getPriority(20));
	}

	@Test
	public void OTHERtestIsEmptyInitially() {
		assertTrue(pq.isEmpty());	
	}

	@Test
	public void OTHERtestIsEmptyPopping(){
		partiallyPopulatePQ();
		pq.pop();
		pq.pop();
		pq.pop();
		assertTrue(pq.isEmpty());
	}

	@Test
	public void OTHERtestIsEmptyClear(){
		partiallyPopulatePQ();
		pq.clear();
		assertTrue(pq.isEmpty());
	}

	@Test
	public void OTHERtestIsPresent() {
		partiallyPopulatePQ();
		assertTrue(pq.isPresent(15)); // these are the elements
		assertTrue(pq.isPresent(40));
		assertTrue(pq.isPresent(20));
		assertFalse(pq.isPresent(10)); // these are the priorities
		assertFalse(pq.isPresent(11));
		assertFalse(pq.isPresent(33));
	}

	@Test
	public void OTHERtestClear() {
		partiallyPopulatePQ();
		pq.clear();		
		assertEquals(0, pq.size());
		assertTrue(pq.isEmpty());
	}

	@Test
	public void OTHERtestTopAndPop(){
		partiallyPopulatePQ();		
		
		assertEquals(10, pq.topPriority());
		assertEquals(15, pq.topElement());

		pq.pop();
		assertEquals(2, pq.size());

		assertEquals(11, pq.topPriority());
		assertEquals(40, pq.topElement());

		pq.pop();
		assertEquals(1, pq.size());

		assertEquals(33, pq.topPriority());
		assertEquals(20, pq.topElement());	

		pq.pop();
		assertEquals(0, pq.size());
	}

	@Test
	public void OTHERsize() {
		assertEquals(0, pq.size()); // should initially be 0		
		partiallyPopulatePQ();			
		assertEquals(3, pq.size());			
	}

	@Test
	public void OTHERtopPriority() {
		partiallyPopulatePQ();
		assertEquals(10, pq.topPriority());
	}
	
	@Test
	public void OTHERtopElement() {
		partiallyPopulatePQ();
		assertEquals(15, pq.topElement());
	}
	
	
		
	/*
	 * UNIT TESTS FOR PRECONDITIONS
	 */

	// Pushing a negative priority
	@Test(expected=AssertionError.class)
	public void ERRORtestPushNegative() {
		pq.push(-10,  0);
	}

	// Pushing the same element twice
	@Test(expected=AssertionError.class)
	public void ERRORtestDoublePush(){
		pq.push(10, 5);
		pq.push(11, 5);
	}	


	// Calling pop on empty queue
	@Test(expected=AssertionError.class)
	public void ERRORtestPopEmptyQueue() {
		pq.pop();
	}


	// Calling top priority on empty queue
	@Test(expected=AssertionError.class)
	public void ERRORtestTopPriorityEmptyQueue() {
		pq.topPriority();
	}

	// Calling top element on empty queue
	@Test(expected=AssertionError.class)
	public void ERRORtestTopElementEmptyQueue() {
		pq.topElement();
	}

	// Calling change priority on an empty queue
	@Test(expected=AssertionError.class)
	public void ERRORtestChangePriorityElementNotPresent() {
		pq.changePriority(3, 5);
	}

	// Calling get priority on an element that doesn't exist
	@Test(expected=AssertionError.class)
	public void ERRORtestGetPriorityElementNotPresent() {
		pq.getPriority(100);
	}

	
	
	
	
	/*
	 * PRIVATE METHODS FOR POPULATING THE PRIORITY QUEUE 
	 */
	
	
	/*
	 * Populate the priority queue instance variables (heap and location):
	 * 
	 * 			1
	 * 		  /	  \
	 * 		 2	  33
	 * 		/ \
	 * 	   11 10
	 * 
	 * The array looks like:
	 * 	[1, 2, 33, 11, 10]
	 */
	private void fullyPopulatePQ() {
		ArrayList<Pair<Integer, Integer>> heap = new ArrayList<>();
		HashMap<Integer, Integer> location = new HashMap<>();

		heap.add(new Pair<>(1, 100));
		heap.add(new Pair<>(2, 103));
		heap.add(new Pair<>(33, 20));
		heap.add(new Pair<>(11, 40));
		heap.add(new Pair<>(10, 15));

		location.put(100, 0);
		location.put(103, 1);
		location.put(20, 2);
		location.put(40, 3);
		location.put(15, 4);

		pq.heap = heap;
		pq.location = location;
	}
	
	
	/*
	 * Populate the priority queue instance variables (heap and location):
	 * 
	 * 		    10
	 * 		   / \
	 * 		 11	  33
	 * 
	 * The array looks like:
	 * 	[10, 11, 33]
	 */
	private void partiallyPopulatePQ() {
		ArrayList<Pair<Integer, Integer>> heap = new ArrayList<>();
		HashMap<Integer, Integer> location = new HashMap<>();

		
		heap.add(new Pair<>(10, 15));
		heap.add(new Pair<>(11, 40));
		heap.add(new Pair<>(33, 20));
		
		location.put(15, 0);
		location.put(40, 1);
		location.put(20, 2);
		pq.heap = heap;
		pq.location = location;
		
		
	}
	
	
}
