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
public class Vertex {
    private double key;
    
    /**
     * Constructor function for Vertex.
     * @param key 
     */
    public Vertex(double key){
        this.key = key;
    }
    
    public boolean isConnected(Vertex vertex, Edge[] edges) {
        for (Edge edge : edges) {
            //If the nodes are connected. (this refers to this Vertex object.)
            if((edge.getFirst() == this && edge.getSecond() == vertex))// || (edge.getFirst() == vertex && edge.getSecond() == this)
                return true;
        }
        return false;
    }
    
    /**
     * Setter function for getKey.
     * @param key 
     */
    public void setKey(double key) {
        this.key = key;
    }
    
    /**
     * Getter function for getKey
     * @return getKey
     */
    public double getKey() {
        return this.key;
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.key);
    }
}
