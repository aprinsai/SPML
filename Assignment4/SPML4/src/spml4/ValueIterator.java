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
        valueFunction = new Quple[mdp.getWidth()][mdp.getHeight()];
        nextValueFunction = new Quple[mdp.getWidth()][mdp.getHeight()];
        for (int x = 0; x < mdp.getWidth(); x++)
            for (int y = 0; y < mdp.getHeight(); y++) {
                valueFunction[x][y] = new Quple(0.0, Action.UP); //Otherwise null, but nullpointers
                nextValueFunction[x][y] = new Quple(0.0, Action.UP);
            }

    }

    /*
    
    Initialize:
    valueFunction is double array (same dim as mdp) with all 0
    
    Q: what is k and what is S?
    
     */
    public Action[][] run() {

        /*
        Copy-paste de performAction functies en 
         */
        do {
            System.out.println("iter");
            updateV();
            //printArray();
            // For each state
            for (int x = 0; x < mdp.getWidth(); x++)
                for (int y = 0; y < mdp.getHeight(); y++) {
                    Quple[] Q = new Quple[Action.values().length]; // One for each movement.
                    //For each action in each state
                    for (int i = 0; i < Action.values().length; i++)
                        Q[i] = calculateQ(Action.values()[i], x, y);
                    nextValueFunction[x][y] = getMax(Q);
                }
        } while (statement());
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
        for (int x = 0; x < mdp.getWidth(); x++)
            for (int y = 0; y < mdp.getHeight(); y++) {
                double value = valueFunction[x][y].getValue();
                double nextValue = nextValueFunction[x][y].getValue();
//                System.out.printf("V: %f, T: %f\n",Math.abs(nextValue - value), threshold);
//                System.out.println(Math.abs(nextValue - value) > threshold);
                if (Math.abs(nextValue - value) > threshold && mdp.getField(x, y) != Field.REWARD)
                    return true;
            }
        return false;
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
    private Quple calculateQ(Action action, int x, int y) {
        double upProb = 0.0;
        if (y < (mdp.getHeight() - 1) && mdp.getField(x, y + 1) != Field.OBSTACLE)
            upProb = getTransitionProb(x, y, x, y + 1, action) * (mdp.getReward(x, y + 1) + discount * valueFunction[x][y + 1].getValue());
        double downProb = 0.0;
        if (y > 0 && mdp.getField(x, y - 1) != Field.OBSTACLE)
            downProb = getTransitionProb(x, y, x, y - 1, action) * (mdp.getReward(x, y - 1) + discount * valueFunction[x][y - 1].getValue());
        double leftProb = 0.0;
        if (x > 0 && mdp.getField(x - 1, y) != Field.OBSTACLE)
            leftProb = getTransitionProb(x, y, x - 1, y, action) * (mdp.getReward(x - 1, y) + discount * valueFunction[x - 1][y].getValue());
        double rightProb = 0.0;
        if (x < (mdp.getWidth() - 1) && mdp.getField(x + 1, y) != Field.OBSTACLE)
            rightProb = getTransitionProb(x, y, x + 1, y, action) * (mdp.getReward(x + 1, y) + discount * valueFunction[x + 1][y].getValue());
        return new Quple(upProb + downProb + leftProb + rightProb, action);
    }

    /**
     * Updates the valueFunction to the nextValueFunction.
     */
    private void updateV() {
        for (int x = 0; x < mdp.getWidth(); x++)
            for (int y = 0; y < mdp.getHeight(); y++)
                valueFunction[x][y] = nextValueFunction[x][y];
    }

    private Action[][] getPolicy() {
        Action[][] policy = new Action[mdp.getWidth()][mdp.getHeight()];
        for (int x = 0; x < mdp.getWidth(); x++)
            for (int y = 0; y < mdp.getHeight(); y++)
                policy[x][y] = valueFunction[x][y].getAction();
        return policy;
    }
}
