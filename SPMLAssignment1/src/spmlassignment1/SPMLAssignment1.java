package spmlassignment1;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * Main class of the assignment. Runs a bunch of tests in the main.
 *
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
        System.out.printf("Final mst: %s",prim.getMST());
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
        edges.add(new Edge(vertices.get(0), vertices.get(1), 4)); //ab
        edges.add(new Edge(vertices.get(0), vertices.get(7), 80)); //ah
        edges.add(new Edge(vertices.get(1), vertices.get(7), 100)); //bh
        edges.add(new Edge(vertices.get(1), vertices.get(2), 8000)); //bc
        edges.add(new Edge(vertices.get(2), vertices.get(8), 2000)); //ci
        edges.add(new Edge(vertices.get(2), vertices.get(5), 30)); //cf
        edges.add(new Edge(vertices.get(2), vertices.get(3), 7000)); //cd
        edges.add(new Edge(vertices.get(3), vertices.get(4), 9000)); //de
        edges.add(new Edge(vertices.get(5), vertices.get(3), 400000000)); //fd
        edges.add(new Edge(vertices.get(5), vertices.get(4), 1)); //fe
        edges.add(new Edge(vertices.get(6), vertices.get(8), 6000)); //gi
        edges.add(new Edge(vertices.get(6), vertices.get(5), 200)); //gf
        edges.add(new Edge(vertices.get(7), vertices.get(6), 100)); //hg
        edges.add(new Edge(vertices.get(8), vertices.get(7), 7000)); //ih

        return new Graph(edges, vertices);
    }
}
