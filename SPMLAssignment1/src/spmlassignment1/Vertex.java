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
    private String name;
    private Vertex parent;
    
    /**
     * Constructor function for Vertex.
     * @param key 
     */
    public Vertex(double key, String name, Vertex parent){
        this.key = key;
        this.name = name;
        this.parent = parent;
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
        return key;
    }
    
    /**
     * 
     * @param parent 
     */
    public void setParent(Vertex parent) {
        this.parent = parent;
    }
    
    public Vertex getParent() {
        return parent;
    }
    
    @Override
    public String toString() {
        return name;
        //return String.format("%s : %f",name,key);
    }
}
