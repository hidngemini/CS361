package graph;

import util.PriorityQueue;

/**
 * Floyd Warshall and Dijkstra's implementations
 * 
 * @author maddyS
 * @version 16.12.24
 */
public class GraphAlgorithms {

    public static int[][] floydWarshall(Graph<Integer> graph){
        int numVertices = graph.numVertices();

        int[][] currentDistance = new int[numVertices][numVertices];
        int[][] previousDistance = new int[numVertices][numVertices];

        // fill adjacency matrix
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                if (graph.edgeExists(i, j)) {
                    currentDistance[i][j] = 1;
                } else if (i==j) {
                    currentDistance[i][j] = 0;
                } else {
                    currentDistance[i][j] = Integer.MAX_VALUE;
                }
            }
        }

        // iterate through possible intermediate nodes
        for (int k = 0; k < numVertices; k++) {
            // put old current into previous and put previous into current (data in current gets overwritten)
            int[][] prevPointer = previousDistance;
            previousDistance = currentDistance;
            currentDistance = prevPointer;

            // iterate through every combination of vertices
            for (int i = 0; i < numVertices; i++) {
                for (int j = 0; j < numVertices; j++) {
                    int altDistance;
                    if (previousDistance[i][k] != Integer.MAX_VALUE && previousDistance[k][j] != Integer.MAX_VALUE) {
                        altDistance = previousDistance[i][k] + previousDistance[k][j]; // compute alt distance
                    } else {
                        altDistance = Integer.MAX_VALUE; // avoid overflow if either are MAX_VALUE
                    }
                    currentDistance[i][j] = Math.min(altDistance, previousDistance[i][j]); // choose shorter distance
                }
            }
        }

    	return currentDistance;
    }
    
    // assumes all vertices are numbered 0-n consecutive (which they will be for the movielensanalyzer)
    public static int[] dijkstrasAlgorithm(Graph<Integer> graph, int source) throws Exception {
        int numVertices = graph.numVertices();

        // populate initial distance and previous arrays
        int[] dist = new int[numVertices];
        int[] prev = new int[numVertices];
        for (int i = 0; i < dist.length; i++) {
            if (graph.edgeExists(source, i)) {
                dist[i] = 1; // edge exists; cost of 1 since unweighted graph
                prev[i] = source; // previous is source
            } else {
                dist[i] = Integer.MAX_VALUE; // edge does not exist. infinite weight.
                prev[i] = -1; // no previous on this path
            }
            dist[source] = 0; // distance to itself is 0
        }

        // fill initial priority queue
    	PriorityQueue pq = new PriorityQueue();
        for (Integer vertex : graph.getVertices()) {
            pq.push(dist[vertex], vertex);
        }

        // dijkstra loop
        while (!pq.isEmpty()) {
            int u = pq.pop();
            for (int v : graph.getNeighbors(u)) {
                int altDistance = dist[u] + 1;
                if (altDistance < dist[v]) {
                    dist[v] = altDistance;
                    prev[v] = u;
                    if (pq.isPresent(v)) { // avoids breaking pq for cycles
                        pq.changePriority(altDistance, v);
                    }
                }
            }
        }

        return prev;
    }
}
