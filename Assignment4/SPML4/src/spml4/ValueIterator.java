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
        previousValueFunction = new double[mdp.getHeight()][mdp.getWidth()];
        for (int row = 0; row < mdp.getHeight(); row++)
            for (int col = 0; col < mdp.getWidth(); col++) {
                valueFunction[row][col] = 0;
                previousValueFunction[row][col] = 0;
            }

    }

    /*
    
    Initialize:
    valueFunction is double array (same dim as mdp) with all 0
    
    Q: what is k and what is S?
    
     */
    public void run() {

        while (statement())
            // For each state
            for (int row = 0; row < valueFunction.length; row++)
                for (int col = 0; col < valueFunction[row].length; col++) {
                    double[] scores = new double[Action.values().length]; // One for each movement.
                    //For each action in each state
                    for (int i = 0; i < Action.values().length; i++)
                        scores[i] = 0.0;
                    valueFunction[row][col] = mdp.getReward() + getMax(scores);
                    // utility = mdp.getReward() + Collections.max(scores)
                } // Update score
    }

    /**
     *
     * @param x of the state
     * @param y of the state
     * @param action performed in that state
     * @return the probability of performing action in state(x,y)
     * @throws IlleglArgumentException
     */
    private double getTransitionProb(int fromX, int fromY, int toX, int toY, Action action) {
        //Check if from/to is valid.
        if (Math.abs(fromX - toX) > 1 || Math.abs(fromY - toY) > 1 || (Math.abs(fromX - toX) == 1 && Math.abs(fromY - toY) == 1))
            throw new IllegalArgumentException("Invalid from/to position.");
        //For moving up. Can't really be done pretty with switch can it?
        if (action == Action.UP) {
            if (fromX == toX && fromY == toY)
                return mdp.getpNoStep();
            if (toY < fromY) // Going up 
                return mdp.getpPerform();
            if (fromY < toY) // Going down
                return mdp.getpBackstep();
            if (toX < fromX) // Going left
                return mdp.getpSidestep()/2;
            if (fromX < toX) // Going right
                return mdp.getpSidestep()/2;
        }
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
     * @return true if there exists a state where the difference between the
     * current state and the previous state is bigger than the threshold.
     */
    private boolean statement() {
        for (int row = 0; row < valueFunction.length; row++)
            for (int col = 0; col < valueFunction[row].length; col++) {
                double value = valueFunction[row][col];
                double prevValue = previousValueFunction[row][col];
                // Vooruit kijken (k+1) of achteruit kijken??? -- Think about this later.
                // Iets doen met de terminator state, want value = prevValue aka 0. -- Check if done correct?
                if (value - prevValue > threshold && mdp.getField(row, col) != Field.REWARD)
                    return true;
            }
        return true;
    }

    /**
     * @param scores array of doubles
     * @return the maximum value in the scores array.
     */
    private double getMax(double[] scores) {
        Double max = null;
        for (double score : scores)
            if (score > max)
                max = score;
        return max;
    }
}
