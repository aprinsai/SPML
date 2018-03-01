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
        return this.vertices;
    }
    
    public ArrayList<Edge> getEdges() {
        return this.edges;
    }
    
    @Override
    public String toString() {
        return null;
    }
}
