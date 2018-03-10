package spmlassignment1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * There is probably a whole bunch of shit that you could improve no doubt but we just needed to get it done quick and dirty sooo there you go. 
 * @author Pleun
 */
public class GraphMaker {

    private int nrOfVertices;
    private int nrOfEdges;
    private double minWeight;
    private double maxWeight;

    /**
     * @param nrOfVertices
     * @param nrOfEdges
     * @param minWeight
     * @param maxWeight
     * @return a graph with the nr of vertices and edges given. Throws error if
     * the graph is impossible.
     */
    public Graph makeGraph(int nrOfVertices, int nrOfEdges, double minWeight, double maxWeight) {
        //minimum is a tree, max a fully connected. Otherwise error
        if (nrOfEdges < nrOfVertices - 1 || nrOfEdges > nrOfVertices*(nrOfVertices-1)/2) 
            throw new IllegalArgumentException("Illegal nr of edges/vertices.");
        
        //For allowed trees:
        this.nrOfEdges = nrOfEdges;
        this.nrOfVertices = nrOfVertices;
        this.minWeight = minWeight;
        this.maxWeight = maxWeight;

        ArrayList<Vertex> vertices = makeVertices();
        vertices.get(0).setKey(0); // Root node. Since the vertices are shuffled it doesn't matter anyway.
        //System.out.println(vertices);
        ArrayList<Edge> edges = makeEdges(vertices);
        //System.out.println(edges);
        return new Graph(edges, vertices);
    }

    /**
     * @param nrOfVertices
     * @param minWeight edge
     * @param maxWeight

     * @return a fully connected graph with the nr of vertices given.
     */
    public Graph makeFullyConnectedGraph(int nrOfVertices,double minWeight, double maxWeight) {
        int n = nrOfVertices * (nrOfVertices - 1) / 2;
        return makeGraph(nrOfVertices, n, minWeight, maxWeight);
    }

    /**
     * @return a list of vertices with random keys and a name 0 to nrOfVertices
     */
    private ArrayList<Vertex> makeVertices() {
        ArrayList<Vertex> vertices = new ArrayList();
        for (int i = 0; i < nrOfVertices; i++)
            vertices.add(new Vertex(Double.POSITIVE_INFINITY, Integer.toString(i), null));
        return vertices;
    }

    /**
     * @return a random double between minEWeight and maxEWeight.
     */
    private double getRand() {
        Random rnd = new Random();
        return minWeight + (maxWeight - minWeight) * rnd.nextDouble();
    }

    private ArrayList<Edge> makeEdges(ArrayList<Vertex> vertices) {
        ArrayList<Edge> edges = new ArrayList();
        Collections.shuffle(vertices); //Shuffle the vertices 
        //Connect at least all vertices once, by connecting them all randomly (shuffled).
        for (int i = 0; i < vertices.size() - 1; i++)
            edges.add(new Edge(vertices.get(i), vertices.get(i + 1), getRand()));
        while (edges.size() < nrOfEdges) {
            Collections.shuffle(vertices);
            Edge rndEdge = new Edge(vertices.get(0), vertices.get(1), getRand());
            if (!hasEdge(edges, rndEdge))
                edges.add(rndEdge);
        }

        return edges;
    }

    /**
     * @param edges
     * @param rndEdge
     * @return returns true if any version of rndEdge is contained in edges.
     */
    private boolean hasEdge(ArrayList<Edge> edges, Edge rndEdge) {
        for (int i = 0; i < edges.size(); i++) {
            Vertex v11 = edges.get(i).getFirst();
            Vertex v12 = edges.get(i).getSecond();
            Vertex v21 = rndEdge.getFirst();
            Vertex v22 = rndEdge.getSecond();
            if (v11.equals(v21) && v12.equals(v22) || v11.equals(v22) && v12.equals(v21))
                return true;
        }
        return false;
    }
}
