package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A priority queue class implemented using a min heap.
 * Priorities cannot be negative.
 * 
 * @author maddys
 * @version 16.10.24
 *
 */
public class PriorityQueue {

	protected Map<Integer, Integer> location;
	protected List<Pair<Integer, Integer>> heap;

	/**
	 *  Constructs an empty priority queue
	 */
	public PriorityQueue() {
		this.heap = new ArrayList<Pair<Integer,Integer>>();
		this.location = new HashMap<Integer, Integer>();
	}

	/**
	 *  Insert a new element into the queue with the
	 *  given priority.
	 *
	 *	@param priority priority of element to be inserted
	 *	@param element element to be inserted
	 *	<br><br>
	 *	<b>Preconditions:</b>
	 *	<ul>
	 *	<li> The element does not already appear in the priority queue.</li>
	 *	<li> The priority is non-negative.</li>
	 *	</ul>
		 * @throws Exception 
		 *  
		 */
		public void push(int priority, int element) throws Exception {
		if (this.location.get(element) != null || priority < 0) {
			throw new Exception("push() failed its preconditions.");
		}
		Pair<Integer,Integer> newElement = new Pair<Integer, Integer>(priority, element);
		this.heap.add(newElement);
		this.location.put(element, this.heap.size()-1);
		this.percolateUpLeaf();
	}

	/**
	 *  Remove the highest priority element
	 *  @return the element with the highest priority
	 *  <br><br>
	 *	<b>Preconditions:</b>
	 *	<ul>
	 *	<li> The priority queue is non-empty.</li>
	 *	</ul>
		 * @throws Exception 
		 *  
		 */
		public int pop() throws Exception{
		if (this.isEmpty()) {
			throw new Exception("pop() failed its preconditions.");
		}
		int toReturn = this.heap.get(0).element;
		this.swap(0, this.heap.size()-1);
		this.location.remove(this.heap.get(this.heap.size()-1).element);
		this.heap.remove(this.heap.size()-1);
		this.pushDownRoot();
		return toReturn;
	}


	/**
	 *  Returns the highest priority in the queue
	 *  @return highest priority value
	 *  <br><br>
	 *	<b>Preconditions:</b>
	 *	<ul>
	 *	<li> The priority queue is non-empty.</li>
	 *	</ul>
		 * @throws Exception 
		 */
		public int topPriority() throws Exception {		
		if (this.isEmpty()) {
			throw new Exception("topPriority() failed its preconditions.");
		}
		return this.heap.get(0).priority;
	}


	/**
	 *  Returns the element with the highest priority
	 *  @return element with highest priority
	 *  <br><br>
	 *	<b>Preconditions:</b>
	 *	<ul>
	 *	<li> The priority queue is non-empty.</li>
	 *	</ul>
		 * @throws Exception 
		 */
		public int topElement() throws Exception {
		if (this.isEmpty()) {
			throw new Exception("topElement() failed its preconditions.");
		}
		return this.heap.get(0).element;
	}


	/**
	 *  Change the priority of an element already in the
	 *  priority queue.
	 *  
	 *  @param newPriority the new priority	  
	 *  @param element element whose priority is to be changed
	 *  <br><br>
	 *	<b>Preconditions:</b>
	 *	<ul>
	 *	<li> The element exists in the priority queue</li>
	 *	<li> The new priority is non-negative </li>
	 *	</ul>
		 * @throws Exception 
		 */
		public void changePriority(int newPriority, int element) throws Exception {
		if (this.location.get(element) == null) {
			throw new Exception("changePriority() failed its preconditions. (null elt)");
		}
		if (newPriority < 0) {
			throw new Exception("changePriority() failed its preconditions. (neg prio)");
		}
		int eltLocation = this.location.get(element);
		if (newPriority > this.heap.get(eltLocation).priority) {
			this.heap.get(eltLocation).priority = newPriority;
			this.pushDown(eltLocation);
		} else {
			this.heap.get(eltLocation).priority = newPriority;
			this.percolateUp(eltLocation);
		}
	}


	/**
	 *  Gets the priority of the element
	 *  
	 *  @param element the element whose priority is returned
	 *  @return the priority value
	 *  <br><br>
	 *	<b>Preconditions:</b>
	 *	<ul>
	 *	<li> The element exists in the priority queue</li>
	 *	</ul>
		 * @throws Throwable 
		 */
		public int getPriority(int element) throws Throwable {
		if (!this.isPresent(element)) {
			throw new Exception("getPriority() failed its preconditions.");
		}
		return this.heap.get(this.location.get(element)).priority;
	}

	/**
	 *  Returns true if the priority queue contains no elements
	 *  @return true if the queue contains no elements, false otherwise
	 */
	public boolean isEmpty() {
		return (this.heap.size() == 0);
	}

	/**
	 *  Returns true if the element exists in the priority queue.
	 *  @param element to be checked
	 *  @return true if the element exists, false otherwise
	 */
	public boolean isPresent(int element) {
		return (this.location.get(element) != null);
	}

	/**
	 *  Removes all elements from the priority queue
	 */
	public void clear() {
		this.heap = new ArrayList<Pair<Integer, Integer>>();
		this.location = new HashMap<Integer, Integer>();
	}

	/**
	 *  Returns the number of elements in the priority queue
	 *  @return number of elements in the priority queue
	 */
	public int size() {
		return this.heap.size();
	}


	
	/*********************************************************
	 * 				Private helper methods
	 *********************************************************/
	

	/**
	 * Push down the element at the given position in the heap 
	 * @param startIndex the index of the element to be pushed down
	 * @return the index in the list where the element is finally stored
	 */
	private int pushDown(int startIndex) {
		if (this.isLeaf(startIndex)) {
			return startIndex;
		}
		int currentPriority = this.heap.get(startIndex).priority;
		int leftIndex = left(startIndex);
		int leftPriority = this.heap.get(leftIndex).priority;
		int rightIndex = right(startIndex);
		if (rightIndex < this.heap.size()) { // check if there is a right child
			int rightPriority = this.heap.get(rightIndex).priority;
			if (currentPriority > leftPriority || currentPriority > rightPriority) {
				if (leftPriority < rightPriority) {
					this.swap(startIndex, leftIndex);
					return this.pushDown(leftIndex);
				} else {
					this.swap(startIndex, rightIndex);
					return this.pushDown(rightIndex);
				}
			}
		} else { // only child is left
			if (currentPriority > leftPriority) {
				this.swap(startIndex, leftIndex);
				return this.pushDown(leftIndex);
			}
		}
		return startIndex;
	}

	/**
	 * Percolate up the element at the given position in the heap 
	 * @param start_index the index of the element to be percolated up
	 * @return the index in the list where the element is finally stored
	 */
	private int percolateUp(int startIndex) {
		int currentPriority = this.heap.get(startIndex).priority;
		int parentIndex = parent(startIndex);
		int parentPriority = this.heap.get(parentIndex).priority;
		if (currentPriority < parentPriority) {
			this.swap(startIndex, parentIndex);
			return this.percolateUp(parentIndex);
		}
		return startIndex;
	}


	/**
	 * Swaps two elements in the priorix ty queue by updating BOTH
	 * the list representing the heap AND the map
	 * @param i The index of the element to be swapped
	 * @param j The index of the element to be swapped
	 */
	private void swap(int i, int j) {
		int oldI = this.heap.get(i).element;
		int oldIPriority = this.heap.get(i).priority;
		int oldILoc = this.location.get(oldI);

		// i know these two aren't necessary but it makes it so much easier to reason about
		int oldJ = this.heap.get(j).element; 
		int oldJLoc = this.location.get(oldJ);

		this.location.remove(oldI);
		this.location.remove(oldJ);

		this.heap.get(i).element = oldJ;
		this.heap.get(i).priority = this.heap.get(j).priority;

		this.heap.get(j).element = oldI;
		this.heap.get(j).priority = oldIPriority;

		this.location.put(oldI, oldJLoc);
		this.location.put(oldJ, oldILoc);
	}

	/**
	 * Computes the index of the element's left child
	 * @param parent index of element in list
	 * @return index of element's left child in list
	 */
	private int left(int parent) {
		return 2*parent+1;
	}

	/**
	 * Computes the index of the element's right child
	 * @param parent index of element in list
	 * @return index of element's right child in list
	 */
	private int right(int parent) {
		return 2*parent+2;
	}

	/**
	 * Computes the index of the element's parent
	 * @param child index of element in list
	 * @return index of element's parent in list
	 */

	private int parent(int child) {
		return (child-1) / 2;
	}
	

	/*********************************************************
	 * 	These are optional private methods that may be useful
	 *  If you don't use them, please delete them! 
	 *********************************************************/
	
	
	/**
	 * Push down the root element
	 * @return the index in the list where the element is finally stored
	 */
	private int pushDownRoot() {
		return this.pushDown(0);
	}

	/**
	 * Percolate up the last leaf in the heap, i.e. the most recently 
	 * added element which is stored in the last slot in the list
	 * 
	 * @return the index in the list where the element is finally stored
	 */
	private int percolateUpLeaf(){
		return this.percolateUp(this.heap.size()-1);
	}
	
	/**
	 * Returns true if element is a leaf in the heap
	 * @param i index of element in heap
	 * @return true if element is a leaf
	 */
	private boolean isLeaf(int i){
		if (left(i) < this.heap.size() || right(i) < this.heap.size()) {
			return false;
		}
		return true;
	}

	/**
	 * Returns true if element has two children in the heap
	 * @param i index of element in the heap
	 * @return true if element in heap has two children
	 */

	private boolean hasTwoChildren(int i) {
		try {
			this.heap.get(left(i));
			this.heap.get(right(i));
		} catch (IndexOutOfBoundsException e) {
			// if index is out of bounds, it obviously doesn't have leaves there
			return false;
		}
		// if both leftLeaf and rightLeaf exist, we have two leaves
		return true;
	}
	
	/**
	 * Print the underlying list representation
	 */
	private void printHeap() {
		for (Pair<Integer, Integer> pair : this.heap) {
			System.out.print("p: " + pair.priority + " e: " + pair.element + " | ");
		}
		System.out.println();
	}

	/**
	 * Print the entries in the location map
	 */
	private void printMap() {
		System.out.println(this.location);
	}

	public static void main(String[] args) throws Exception {
		PriorityQueue pq = new PriorityQueue();
		pq.push(10,15);
		pq.push(11, 40);
		pq.push(33,20);
		pq.push(1, 100);
		pq.printHeap();
		pq.printMap();
	}
}
