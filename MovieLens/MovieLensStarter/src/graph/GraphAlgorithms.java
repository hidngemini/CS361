package graph;

import util.PriorityQueue;

public class GraphAlgorithms {

    public static int[][] floydWarshall(Graph<Integer> graph){
    	return null;
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
                if (dist[u] == Integer.MAX_VALUE) {
                    System.out.println(u);
                }
                int altDistance = dist[u] + 1;
                if (altDistance < dist[v]) {
                    dist[v] = altDistance;
                    prev[v] = u;
                    System.out.println(v);
                    System.out.println(dist[v]);
                    System.out.println(altDistance);
                    if (pq.isPresent(v)) { // avoids breaking pq for cycles
                        pq.changePriority(v, altDistance);
                    }
                }
            }
        }

        return prev;
    }

    public static void main(String[] args) throws Exception {
        // test dijkstra
        Graph<Integer> graph = new Graph<Integer>();
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);
        graph.addVertex(3);
        graph.addEdge(0,1);
        graph.addEdge(0,2);
        graph.addEdge(2,3);
        int[] prev = dijkstrasAlgorithm(graph, 0);
        for (int i : prev) {
            System.out.print(i);
            System.out.print(" ");
        }
        System.out.println();

        // probably test floyd warshall
    }
}
