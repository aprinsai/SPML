/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spmlassignment1;

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
        Vertex a = new Vertex(0);
        Vertex b = new Vertex(INF);
        Vertex c = new Vertex(INF);
        Vertex d = new Vertex(INF);
        Vertex e = new Vertex(INF);
        Vertex f = new Vertex(INF);
        Vertex g = new Vertex(INF);
        Vertex h = new Vertex(INF);
        Vertex i = new Vertex(INF);
        Vertex[] vertices = {a,b,c,d,e,f,g,h,i};
        
        Edge ab = new Edge(a,b,4);
        Edge ah = new Edge(a,h,8);
        Edge bh = new Edge(b,h,11);
        Edge bc = new Edge(b,c,8);
        Edge ci = new Edge(c,i,2);
        Edge cf = new Edge(c,f,4);
        Edge cd = new Edge(c,d,7);
        Edge de = new Edge(d,e,9);
        Edge fd = new Edge(f,d,14);
        Edge fe = new Edge(f,e,10);        
        Edge gi = new Edge(g,i,6);
        Edge gf = new Edge(g,f,2);
        Edge hg = new Edge(h,g,1);
        Edge ih = new Edge(i,h,7);
        Edge[] edges = {ab,ah,bh,bc,ci,cf,cd,de,fd,fe,gi,gf,hg,ih};
        
        Graph graph = new Graph(edges, vertices);

    }
    
}
