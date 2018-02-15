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
    private double weight;
    
    /**
     * Constructor function for Vertex.
     * @param weight 
     */
    public Vertex(double weight){
        this.weight = weight;
    }
    
    /**
     * Setter function for weight.
     * @param weight 
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }
    
    /**
     * Getter function for weight
     * @return weight
     */
    public double getWeight() {
        return this.weight;
    }
    
}
