package spml4;

/**
 *
 * @author Pleun
 */
public class ValueIterator {

    private MarkovDecisionProblem mdp;
    private double[][] valueFunction;
    private final double threshold;
    private final double discount;
    
    public ValueIterator(MarkovDecisionProblem mdp, double threshold, double discount) {
        this.mdp = mdp;
        this.valueFunction = intialize();
        this.threshold = threshold;
        this.discount = discount;
    }

    private double[][] intialize() {
        valueFunction = new double[mdp.getHeight()][mdp.getWidth()];

        for (double[] valueFunctionRow : valueFunction)
            for (double value : valueFunctionRow)
                value = 0;
        return valueFunction;
    }
    
    /*
    
    Initialize:
    valueFunction is double array (same dim as mdp) with all 0
    
    Q: what is k and what is S?
    
     */

    
    public void run() {

        while(statement()) {
            // For each state
            for(int row = 0; row < valueFunction.length; row++) {
                for(int col = 0; col < valueFunction[row].length; col++) {
                    //For each action in each state
                    for(Action a: Action.values()) {
                        
                    }
                }
            }
            // Update score
        }
    }
    
    /**
     * 
     * @param x of the state
     * @param y of the state
     * @param action performed in that state
     * @return the probability of performing action in state(x,y)
     */
    private double transition(int x, int y, Action action) {
        
        return 0.0;
    }
    
    
    /**
     * Think this can be public?
     */
    public void printArray() {
        for (double[] valueFunction1 : valueFunction) {
            for (double value : valueFunction1)
                System.out.print(value + ", ");
            System.out.println("");
        }
    }

    public double[][] getValueIterations() {
        return valueFunction;
    }

    private boolean statement() {
        return true;
    }
}
