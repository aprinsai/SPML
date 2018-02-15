/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spmlassignment1;

/**
 *
 * @author Anouk
 */
public class Edge {
    private Vertex firstNode;
    private Vertex secondNode;
    private double weight; //want dan kunnen we inf gebruiken
    
    /**
     * Constructor function for Edge
     * @param firstNode
     * @param secondNode
     * @param weight 
     */
    public Edge(Vertex firstNode, Vertex secondNode, double weight){
        this.firstNode = firstNode;
        this.secondNode = secondNode;
        this.weight = weight;
    }
    
    /**
     * Getter function for weight.
     * @return weight
     */
    public double getWeight() {
        return this.weight;
    }
}

