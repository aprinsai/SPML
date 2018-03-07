package spmlassignment1;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 *
 * @author Pleun
 */
public class MST_Prim {

    Graph graph;
    Graph mst;
    PriorityQueue<Vertex> frontier;

    public MST_Prim(Graph graph) {
        this.graph = graph;
        //Initialize MST as an empty graph.
        mst = new Graph(new ArrayList<Edge>(), new ArrayList());
        //Priority Queue with comparator
        WeightComparator comparator = new WeightComparator();
        frontier = new PriorityQueue(comparator);
    }

    /**
     * Initializes the frontier
     */
    public void initialize() {
        //Add every vertex into the frontier.
        ArrayList<Vertex> vertices = graph.getVertices();
        for (Vertex v : vertices)
            frontier.add(v);
        //System.out.println(frontier);      
        
        //Add root node (since we can't connect the root node to anything in the main run function.
        Vertex root = frontier.poll();
        mst.addVertex(root);
        //System.out.println(frontier);
    }

    /**
     * Runs Prim's algorithm on the graph.
     */
    public void run() {
        while (!frontier.isEmpty()) {
            Vertex u = frontier.poll();
            System.out.printf("U: %s",u);
            Vertex prev = mst.getVertices().get(mst.getVertices().size()-1);
            System.out.printf("Prev: %s",prev);
            Edge e = new Edge(prev,u,u.getKey());
            updateWeights(u);
            mst.addVertex(u);
            mst.addEdge(e);
            System.out.println(frontier);
        }
    }

    /**
     * @return the MST
     */
    public Graph getMST() {
        return mst;
    }

    /**
     * Updates the weights within the frontier.
     *
     * @param graph
     * @param u Vertex just added to the MST.
     * @param frontier (expanded nodes =/= INF.)
     */
    private void updateWeights(Vertex u) {
        ArrayList<Edge> edges = graph.getEdges();
        for (Edge e : edges) {
            Vertex v = e.isConnected(u);
            if (v != null && e.getWeight() < v.getKey()) {
                frontier.remove(v);
                v.setKey(e.getWeight());
                frontier.add(v);
            }
        }
    }

}
