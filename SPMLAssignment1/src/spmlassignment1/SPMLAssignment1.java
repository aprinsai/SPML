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
        MST_Prim prim = new MST_Prim(graph);
        prim.initialize();
        prim.run();
        System.out.printf("Final MST: \n%s",prim.getMST());
        System.out.println(prim.count);
    }

    /**
     * @return a graph as drawn in the slides.
     */
    private static Graph createGraph() {
        ArrayList<Vertex> vertices = new ArrayList();

        vertices.add(new Vertex(0, "a", null));  //a 0
        vertices.add(new Vertex(INF, "b", null));//b 1
        vertices.add(new Vertex(INF, "c", null));//c 2
        vertices.add(new Vertex(INF, "d", null));//d 3
        vertices.add(new Vertex(INF, "e", null));//e 4
        vertices.add(new Vertex(INF, "f", null));//f 5
        vertices.add(new Vertex(INF, "g", null));//g 6
        vertices.add(new Vertex(INF, "h", null));//h 7
        vertices.add(new Vertex(INF, "i", null));//i 8*/
        
        ArrayList<Edge> edges = new ArrayList();
        /**edges.add(new Edge(vertices.get(0),vertices.get(1),4)); //ab
        edges.add(new Edge(vertices.get(1),vertices.get(2),6));
        edges.add(new Edge(vertices.get(2),vertices.get(3),2));
        edges.add(new Edge(vertices.get(4),vertices.get(3),3));
        edges.add(new Edge(vertices.get(0),vertices.get(4),2));
        edges.add(new Edge(vertices.get(0),vertices.get(3),2));
        edges.add(new Edge(vertices.get(0),vertices.get(2),100));*/
        edges.add(new Edge(vertices.get(0),vertices.get(7),80)); //ah
        edges.add(new Edge(vertices.get(1),vertices.get(7),100)); //bh
        edges.add(new Edge(vertices.get(1),vertices.get(2),8000)); //bc
        edges.add(new Edge(vertices.get(2),vertices.get(8),2000)); //ci
        edges.add(new Edge(vertices.get(2),vertices.get(5),30)); //cf
        edges.add(new Edge(vertices.get(2),vertices.get(3),7000)); //cd
        edges.add(new Edge(vertices.get(3),vertices.get(4),9000)); //de
        edges.add(new Edge(vertices.get(5),vertices.get(3),400000000)); //fd
        edges.add(new Edge(vertices.get(5),vertices.get(4),1)); //fe
        edges.add(new Edge(vertices.get(6),vertices.get(8),6000)); //gi
        edges.add(new Edge(vertices.get(6),vertices.get(5),200)); //gf
        edges.add(new Edge(vertices.get(7),vertices.get(6),100)); //hg
        edges.add(new Edge(vertices.get(8),vertices.get(7),7000)); //ih
        
        return new Graph(edges, vertices);
    }

    /**
     * Updates the weights within the frontier.
     * @param graph
     * @param u Vertex just added to the MST.
     * @param frontier (expanded nodes =/= INF.)
     */
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
