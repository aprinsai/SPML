/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spmlassignment1;

import java.util.ArrayList;

/**
 *
 * @author Anouk & Pleun
 */
public class Graph {
    private ArrayList<Edge> edges;
    private ArrayList<Vertex> vertices; 
    
    public Graph(ArrayList<Edge> edges, ArrayList<Vertex> vertices) {
        this.edges = edges;
        this.vertices = vertices;
    }
    
    public ArrayList<Vertex> getVertices() {
        return vertices;
    }
    
    public ArrayList<Edge> getEdges() {
        return edges;
    }
    
    public void addVertex(Vertex v) {
        vertices.add(v);
    }
    
    public void addEdge(Edge e) {
        edges.add(e);
    }
    
    public void removeEdge(Edge e) {
        edges.remove(e);
    }
    
    @Override
    public String toString() {
        String s = "";
        for(Edge e : edges)
            s+= e+"\n";
        return s;
    }
}