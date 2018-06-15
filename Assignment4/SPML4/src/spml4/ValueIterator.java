package spml4;

import java.awt.Point;
import java.util.Arrays;

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
                if (mdp.getField(x, y) == Field.REWARD) {
                    valueFunction[x][y] = new Quple(1.0, null);
                    nextValueFunction[x][y] = new Quple(1.0, null);
                }
                else if (mdp.getField(x, y) == Field.NEGREWARD) {
                    valueFunction[x][y] = new Quple(-1.0, null);
                    nextValueFunction[x][y] = new Quple(-1.0, null);
                }
                else {
                    valueFunction[x][y] = new Quple(0.0, null); 
                    nextValueFunction[x][y] = new Quple(0.0, null);
                }
            }

    }

    /*
    
    Initialize:
    valueFunction is double array (same dim as mdp) with all 0
    
    Q: what is k and what is S?
    
     */
    public Action[][] run() {

        do {
            System.out.print(".");
            updateV();
            //printArray();
            // For each state
            for (int x = 0; x < mdp.getWidth(); x++)
                for (int y = 0; y < mdp.getHeight(); y++) {
                    Quple[] Q = new Quple[Action.values().length]; // One for each movement.
                    //For each action in each state
                    for (int i = 0; i < Action.values().length; i++)
                        Q[i] = calculateBellman(Action.values()[i], x, y);
                    // Don't update the reward field
                    if(mdp.getField(x,y) != Field.REWARD && mdp.getField(x,y) != Field.NEGREWARD && mdp.getField(x,y) != Field.OBSTACLE)
                        nextValueFunction[x][y] = getMax(Q);
                }
        } while (checkForThreshold());
        System.out.println("");
        return getPolicy();
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
    private boolean checkForThreshold() {
        for (int x = 0; x < mdp.getWidth(); x++)
            for (int y = 0; y < mdp.getHeight(); y++) {
                double value = valueFunction[x][y].getValue();
                double nextValue = nextValueFunction[x][y].getValue();
//                System.out.printf("V: %f, T: %f\n", Math.abs(nextValue - value), threshold);
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

    /*
    Ipv upProb uitrekenen: zeg tegen MDP: doe de stap (zonder te verplaatsen), returnt de x,y van waar je terecht komt. Prop die in de valueFunction. 
     */
    /**
     *
     * @param action
     * @param y
     * @param x
     * @return the Qvalue
     */
    private Quple calculateBellman(Action action, int x, int y) {

        mdp.setDeterministic();
        double [] probPerAction = new double[Action.values().length];
        for (int i = 0; i < probPerAction.length; i++) {
            Point p = mdp.tryPerformAction(Action.values()[i], x, y);
            int newX = p.x;
            int newY = p.y;
            probPerAction[i] = mdp.getTransitionProb(action, Action.values()[i]) * (mdp.getReward(newX, newY) + discount * valueFunction[newX][newY].getValue());
        }
        mdp.setStochastic();
        double prob = Arrays.stream(probPerAction).sum();
        return new Quple(prob, action);
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
