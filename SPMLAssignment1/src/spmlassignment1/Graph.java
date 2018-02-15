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
    private Edge[] edges;
    private Vertex[] vertices; 
    
    public Graph(Edge[] edges, Vertex[] vertices) {
        this.edges = edges;
        this.vertices = vertices;
    }
}
