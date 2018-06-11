package spml4;

/**
 *
 * @author Pleun
 * @author noukie
 */
public class ValueIterator {

    private MarkovDecisionProblem mdp;
    private Quple[][] valueFunction;
    private Quple[][] nextValueFunction;
    private final double threshold;
    private final double discount;

    public ValueIterator(MarkovDecisionProblem mdp, double threshold, double discount) {
        this.mdp = mdp;
        intialize();
        this.threshold = threshold;
        this.discount = discount;
    }

    private void intialize() {
        valueFunction = new Quple[mdp.getHeight()][mdp.getWidth()];
        nextValueFunction = new Quple[mdp.getHeight()][mdp.getWidth()];
        for (int row = 0; row < mdp.getHeight(); row++)
            for (int col = 0; col < mdp.getWidth(); col++) {
                valueFunction[row][col] = new Quple(0.0, null);
                nextValueFunction[row][col] = new Quple(0.0, null);
            }

    }

    /*
    
    Initialize:
    valueFunction is double array (same dim as mdp) with all 0
    
    Q: what is k and what is S?
    
     */
    public Action[][] run() {

        /*
        All x/y's and row/col's are fucked up :( CHECK THATTTT!!!!!!
        */
        while (statement()) {
            updateV();
            printArray();
            // For each state
            for (int row = 0; row < valueFunction.length; row++)
                for (int col = 0; col < valueFunction[row].length; col++) {
                    Quple[] Q = new Quple[Action.values().length]; // One for each movement.
                    //For each action in each state
                    for (int i = 0; i < Action.values().length; i++)
                        Q[i] = calculateQ(Action.values()[i], row, col);
                    nextValueFunction[row][col] = getMax(Q);
                }
        }
        return getPolicy();
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
        switch (action) {
            case UP:
                if (fromX == toX && fromY == toY) // Staying
                    return mdp.getpNoStep();
                if (toY > fromY) // Going up 
                    return mdp.getpPerform();
                if (fromY > toY) // Going down
                    return mdp.getpBackstep();
                if (toX < fromX || fromX < toX) // Going left or right
                    return mdp.getpSidestep() / 2;
                break;
            case DOWN:
                if (fromX == toX && fromY == toY) // Staying
                    return mdp.getpNoStep();
                if (toY < fromY) // Going down 
                    return mdp.getpPerform();
                if (fromY < toY) // Going down
                    return mdp.getpBackstep();
                if (toX > fromX || fromX > toX) // Going left or right
                    return mdp.getpSidestep() / 2;
                break;
            case LEFT:
                if (fromX == toX && fromY == toY) // Staying
                    return mdp.getpNoStep();
                if (toX < fromX) // Going left 
                    return mdp.getpPerform();
                if (fromX < toX) // Going right
                    return mdp.getpBackstep();
                if (toY > fromY || fromY > toY) // Going up or down
                    return mdp.getpSidestep() / 2;
                break;
            case RIGHT:
                if (fromX == toX && fromY == toY) // Staying
                    return mdp.getpNoStep();
                if (toX < fromX) // Going right 
                    return mdp.getpPerform();
                if (fromX < toX) // Going left
                    return mdp.getpBackstep();
                if (toY > fromY || fromY > toY) // Going up or down
                    return mdp.getpSidestep() / 2;
                break;
        }
        return 0.0;
    }

    /**
     * Think this can be public?
     */
    public void printArray() {
        for (Quple[] valueFunction1 : valueFunction) {
            for (Quple value : valueFunction1)
                System.out.print(value.getValue() + ", ");
            System.out.println("");
        }
        System.out.println("");
    }

    public Quple[][] getValueIterations() {
        return valueFunction;
    }

    /**
     * @return true if there exists a state where the difference between the
     * current state and the previous state is bigger than the threshold.
     */
    private boolean statement() {
        for (int row = 0; row < valueFunction.length; row++)
            for (int col = 0; col < valueFunction[row].length; col++) {
                double value = valueFunction[row][col].getValue();
                double nextValue = nextValueFunction[row][col].getValue();
                // Vooruit kijken (k+1) of achteruit kijken??? -- .
                if (nextValue - value > threshold && mdp.getField(row, col) != Field.REWARD)
                    return true;
            }
        return true;
    }

    /**
     * @param Quples array of doubles
     * @return the maximum value in the scores array.
     */
    private Quple getMax(Quple[] Quples) {
        Quple max = null;
        for (Quple Q : Quples)
            if (max == null || Q.getValue() > max.getValue())
                max = Q;
        return max;
    }

    /**
     *
     * @param action
     * @param y
     * @param x
     * @return the Qvalue
     */
    private Quple calculateQ(Action action, int y, int x) {
        double upProb = 0.0;
        if (y < (mdp.getHeight() - 1) && mdp.getField(x, y + 1) != Field.OBSTACLE)
            upProb = getTransitionProb(x, y, x, y + 1, action) * (mdp.getReward(x, y + 1) + discount * valueFunction[y + 1][x].getValue());
        double downProb = 0.0;
        if (y > 0 && mdp.getField(x, y - 1) != Field.OBSTACLE)
            downProb = getTransitionProb(x, y, x, y - 1, action) * (mdp.getReward(x, y - 1) + discount * valueFunction[y - 1][x].getValue());
        double leftProb = 0.0;
        if (x > 0 && mdp.getField(x - 1, y) != Field.OBSTACLE)
            leftProb = getTransitionProb(x, y, x - 1, y, action) * (mdp.getReward(x - 1, y) + discount * valueFunction[y][x - 1].getValue());
        double rightProb = 0.0;
        if (x < (mdp.getWidth() - 1) && mdp.getField(x + 1, y) != Field.OBSTACLE)
            rightProb = getTransitionProb(x, y, x + 1, y, action) * (mdp.getReward(x + 1, y) + discount * valueFunction[y][x + 1].getValue());
        return new Quple(upProb + downProb + leftProb + rightProb, action);
    }

    /**
     * Updates the valueFunction to the nextValueFunction.
     */
    private void updateV() {
        for (int row = 0; row < valueFunction.length; row++)
            for (int col = 0; col < valueFunction.length; col++)
                valueFunction[row][col] = nextValueFunction[row][col];
    }

    private Action[][] getPolicy() {
        Action[][] policy = new Action[mdp.getHeight()][mdp.getWidth()];
        for (int row = 0; row < valueFunction.length; row++)
            for (int col = 0; col < valueFunction.length; col++)
                policy[row][col] = valueFunction[row][col].getAction();
        return policy;
    }
}
