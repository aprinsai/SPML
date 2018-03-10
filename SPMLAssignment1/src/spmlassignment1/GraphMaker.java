package spmlassignment1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author Pleun
 */
public class GraphMaker {

    private int nrOfVertices;
    private int nrOfEdges;
    private double minEWeight;
    private double maxEWeight;
    private double minKWeight;
    private double maxKWeight;

    /**
     * @param nrOfVertices
     * @param nrOfEdges
     * @param minEWeight
     * @param maxEWeight
     * @param minKWeight
     * @param maxKWeight
     * @return a graph with the nr of vertices and edges given. Throws error if
     * the graph can't be connected.
     */
    public Graph makeGraph(int nrOfVertices, int nrOfEdges, double minEWeight, double maxEWeight, double minKWeight, double maxKWeight) {
        if (nrOfEdges < nrOfVertices - 1)
            throw new IllegalArgumentException("Illegal nr of edges/vertices.");
        this.nrOfEdges = nrOfEdges;
        this.nrOfVertices = nrOfVertices;
        this.minEWeight = minEWeight;
        this.maxEWeight = maxEWeight;
        this.minEWeight = minKWeight;
        this.maxEWeight = maxKWeight;

        ArrayList<Vertex> vertices = makeVertices();
        //System.out.println(vertices);
        ArrayList<Edge> edges = makeEdges(vertices);
        //System.out.println(edges);
        return new Graph(edges, vertices);
    }

    /**
     * @param nrOfVertices
     * @param nrOfEdges
     * @param minEWeight edge
     * @param maxEWeight
     * @param minKWeight Key
     * @param maxKWeight
     * @return a fully connected graph with the nr of vertices given.
     */
    public Graph makeFullyConnectedGraph(int nrOfVertices,double minEWeight, double maxEWeight, double minKWeight, double maxKWeight) {
        int n = nrOfVertices * (nrOfVertices - 1) / 2;
        return makeGraph(nrOfVertices, n, minEWeight, maxEWeight, minKWeight, maxKWeight);
    }

    /**
     * @return a list of vertices with random keys and a name 0 to nrOfVertices
     */
    private ArrayList<Vertex> makeVertices() {
        ArrayList<Vertex> vertices = new ArrayList();
        for (int i = 0; i < nrOfVertices; i++)
            vertices.add(new Vertex(getRandK(), Integer.toString(i), null));
        return vertices;
    }

    /**
     * @return a random double between minEWeight and maxEWeight.
     */
    private double getRandE() {
        Random rnd = new Random();
        return minEWeight + (maxEWeight - minEWeight) * rnd.nextDouble();
    }

    /**
     * @return a random double between minWeight and maxWeight.
     */
    private double getRandK() {
        Random rnd = new Random();
        return minKWeight + (maxKWeight - minKWeight) * rnd.nextDouble();
    }

    private ArrayList<Edge> makeEdges(ArrayList<Vertex> vertices) {
        ArrayList<Edge> edges = new ArrayList();
        Collections.shuffle(vertices); //Shuffle the vertices 
        //Connect at least all vertices once, by connecting them all randomly (shuffled).
        for (int i = 0; i < vertices.size() - 1; i++)
            edges.add(new Edge(vertices.get(i), vertices.get(i + 1), getRandE()));
        while (edges.size() < nrOfEdges) {
            Collections.shuffle(vertices);
            Edge rndEdge = new Edge(vertices.get(0), vertices.get(1), getRandE());
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
