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
    public int count=0;

    public MST_Prim(Graph graph) {
        this.graph = graph;
        //Initialize MST as an empty graph.
        mst = new Graph(new ArrayList<Edge>(), new ArrayList<Vertex>());
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
        
    }

    /**
     * Runs Prim's algorithm on the graph.
     */
    public void run() {
        while (!frontier.isEmpty()) {
            Vertex u = frontier.poll();
            System.out.printf("U: %s",u);
            System.out.printf(" Parent: %s",u.getParent());
            Edge e = new Edge(u.getParent(),u,u.getKey());
            mst.addVertex(u);
            mst.addEdge(e);
            updateKey(u);
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
    private void updateKey(Vertex u) {
        ArrayList<Edge> edges = graph.getEdges();
        for (Edge e : edges) {
            Vertex v = e.isConnected(u);
            count ++;
            if (v != null && e.getWeight() < v.getKey() && !mst.contains(v)) {
                frontier.remove(v);
                v.setKey(e.getWeight());
                v.setParent(u);
                frontier.add(v);
            }
        }
    }

}
