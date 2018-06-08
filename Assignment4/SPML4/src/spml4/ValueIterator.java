package spml4;

/**
 *
 * @author Pleun
 */
public class ValueIterator {

    private MarkovDecisionProblem mdp;
    private double[][] valueFunction;
    private double[][] previousValueFunction;
    private final double threshold;
    private final double discount;
    
    public ValueIterator(MarkovDecisionProblem mdp, double threshold, double discount) {
        this.mdp = mdp;
        intialize();
        this.threshold = threshold;
        this.discount = discount;
    }

    private void intialize() {
        valueFunction = new double[mdp.getHeight()][mdp.getWidth()];
        previousValueFunction = new double[mdp.getHeight()][mdp.getWidth()]  ;
        for (double[] valueFunctionRow : valueFunction)
            for (double value : valueFunctionRow) 
                value = 0;
        //Misschien copy functie? System.arraycopy();
        for (double[] prevValueFunctionRow : previousValueFunction)
            for (double value : prevValueFunctionRow) 
                value = 0;
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
                    double[] scores = new double[Action.values().length]; // One for each movement.
                    //For each action in each state
                    for(int i = 0; i < Action.values().length; i++) {
                        scores[i] = 0.0;
                    }
                    valueFunction[row][col] = mdp.getReward() + getMax(scores);
                    // utility = mdp.getReward() + Collections.max(scores)
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
    private double getTransitionProb(int fromX, int fromY, int toX, int toY, Action action) {
        
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

    
    /**
     * @return true if there exists a state where 
     * the difference between the current state and the previous state
     * is bigger than the threshold.
     */
    private boolean statement() {
        for(int row = 0; row<valueFunction.length;row++) {
            for(int col = 0; col<valueFunction[row].length; col++) {
                double value = valueFunction[row][col];
                double prevValue = previousValueFunction[row][col];
                // Vooruit kijken (k+1) of achteruit kijken???
                // Iets doen met de terminator state, want value = prevValue aka 0. 
                if (value - prevValue > threshold)
                    return true;
            }
        }
        return true;
    }

    /**
     * @param scores array of doubles
     * @return the maximum value in the scores array. 
     */
    private double getMax(double[] scores) {
        Double max = null;
        for(double score : scores) 
            if(score > max)
                max = score;
        return max;
    }
}
