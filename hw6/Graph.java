import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

/**
 * An interface describing a Graph object. The graph can be either directed or undirected.
 * @author alchambers
 */
public class Graph<T> implements GraphIfc<T> {
    private int numVertices;
    private int numEdges;
    private HashMap<T,HashSet<T>> adjLists;

    public Graph() {
        this.numEdges = 0;
        this.numVertices = 0;
        this.adjLists = new HashMap<T,HashSet<T>>();
    }

    public int numVertices() {
        return this.numVertices;
    }

    public int numEdges() {
        return this.numEdges;
    }

    public void clear() {
        this.adjLists.clear();
        this.numEdges = 0;
        this.numVertices = 0;
    }

    public void addVertex(T vertex) {
        if (this.containsVertex(vertex)) {
            System.out.println("Not an error, but " + vertex + " already exists.");
        } else {
            this.numVertices++;
            this.adjLists.put(vertex, new HashSet<T>());
        }
    }

    public void addEdge(T to, T from) {
        if (!this.containsVertex(to)) {
            throw new IllegalArgumentException(to + " is not in the graph!");
        } else if (!this.containsVertex(from)) {
            throw new IllegalArgumentException(from + " is not in the graph!"); 
        } else {
            this.numEdges++;
            this.adjLists.get(to).add(from);
        }
    }

    public Collection<T> getVertices() {
        return this.adjLists.keySet();
    }
    
    public Collection<T> getNeighbors(T vertex) {
        if (!this.containsVertex(vertex)) {
            throw new IllegalArgumentException(vertex + "does not occur in this graph.");
        } else {
            return this.adjLists.get(vertex);
        }
    }

    public boolean containsVertex(T vertex) {
        return this.adjLists.containsKey(vertex);
    }

    public boolean edgeExists(T from, T to) {
        if (!this.containsVertex(from)) {
            throw new IllegalArgumentException(from + " is not in the graph!");
        } else if (!this.containsVertex(to)) {
            throw new IllegalArgumentException(to + " is not in the graph!"); 
        } else {
            return this.adjLists.get(from).contains(to);
        }
    }

    public int degree(T vertex) {
        if (!this.containsVertex(vertex)) {
            throw new IllegalArgumentException(vertex + "does not occur in this graph.");
        } else {
            return this.adjLists.get(vertex).size();
        }
    } 

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (T vertex : this.adjLists.keySet()) {
            sb.append(vertex);
            sb.append(": {");
            for (T linkedTo : this.adjLists.get(vertex)) {
                sb.append(linkedTo);
                sb.append(" ");
            }
            sb.append("}\n");
        }
        return sb.toString();
    }
}
