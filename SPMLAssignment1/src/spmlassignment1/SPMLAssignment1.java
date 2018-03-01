/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spmlassignment1;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 *
 * Plan: eerst graph maken, daarna priority queue voor 'intermediate step' om te kiezen welke vertex je connect
 * Die remove je dan en voeg je toe aan je MST (wss array ofzo)
 * @author Anouk & Pleun
 */
public class SPMLAssignment1 {

    private static final double INF = Double.POSITIVE_INFINITY;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Graph graph = createGraph();
        WeightComparator comparator = new WeightComparator();
        PriorityQueue<Vertex> frontier = new PriorityQueue(comparator); 
        ArrayList<Vertex> vertices = graph.getVertices();

        for(Vertex v : vertices)
            frontier.add(v);
        
        System.out.println(frontier);

        Vertex u = extract_min(frontier);
        
        System.out.println(u);
        
        updateWeights(graph, u, frontier);

        System.out.println(frontier);
    }

    private static Graph createGraph() {
        ArrayList<Vertex> vertices = new ArrayList();

        vertices.add(new Vertex(0));  //a 0
        vertices.add(new Vertex(INF));//b 1
        vertices.add(new Vertex(INF));//c 2
        vertices.add(new Vertex(INF));//d 3
        vertices.add(new Vertex(INF));//e 4
        vertices.add(new Vertex(INF));//f 5
        vertices.add(new Vertex(INF));//g 6
        vertices.add(new Vertex(INF));//h 7
        vertices.add(new Vertex(INF));//i 8
        
        ArrayList<Edge> edges = new ArrayList();
        edges.add(new Edge(vertices.get(0),vertices.get(1),4));
        edges.add(new Edge(vertices.get(0),vertices.get(7),8));
        edges.add(new Edge(vertices.get(1),vertices.get(7),11));
        edges.add(new Edge(vertices.get(1),vertices.get(2),8));
        edges.add(new Edge(vertices.get(2),vertices.get(8),2));
        edges.add(new Edge(vertices.get(2),vertices.get(5),4));
        edges.add(new Edge(vertices.get(2),vertices.get(3),7));
        edges.add(new Edge(vertices.get(3),vertices.get(4),9));
        edges.add(new Edge(vertices.get(5),vertices.get(3),14));
        edges.add(new Edge(vertices.get(5),vertices.get(4),10));        
        edges.add(new Edge(vertices.get(6),vertices.get(8),6));
        edges.add(new Edge(vertices.get(6),vertices.get(5),2));
        edges.add(new Edge(vertices.get(7),vertices.get(6),1));
        edges.add(new Edge(vertices.get(8),vertices.get(7),7));
        
        return new Graph(edges, vertices);
    }

    private static Vertex extract_min(PriorityQueue<Vertex> frontier) {
        return frontier.poll(); //retrieves and removes first element. 
    }

    private static void updateWeights(Graph graph, Vertex u, PriorityQueue<Vertex> frontier) {
        ArrayList<Edge> edges = graph.getEdges();
        for(Edge e : edges) {
            Vertex v = e.isConnected(u);
            if(v != null && e.getWeight() < v.getKey()) {
                frontier.remove(v);
                v.setKey(e.getWeight());
                frontier.add(v);
            }
        }
    }
    
    
    
}
