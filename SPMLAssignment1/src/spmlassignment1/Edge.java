package spmlassignment1;

/**
 *
 * @author Anouk and also Plean plz
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
    
    public Vertex isConnected(Vertex v) {
        if(v.equals(firstNode))
            return secondNode;
        else if (v.equals(secondNode))
            return firstNode;
        else return null;
    }
    
    /**
     * Getter function for weight.
     * @return weight
     */
    public double getWeight() {
        return this.weight;
    }
    
    public Vertex getFirst() {
        return this.firstNode;
    }
    
    public Vertex getSecond() {
        return this.secondNode;
    }
    
    @Override
    public String toString() {
        return String.format("%s <--%f--> %s",firstNode,weight,secondNode);
    }
}