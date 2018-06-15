package spml4;

import java.awt.Point;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Pleun
 * @author Nouknouk
 */
public class Qlearner {

    private MarkovDecisionProblem mdp;
    private Quple[][] qFunction;
//    private Quple[][] nextQFunction;
    private final int nrOfIterations;
    private final double learningRate;
    private final double discount;

    private int currX;
    private int currY;
    
    public Qlearner(MarkovDecisionProblem mdp, int nrOfIterations, double discount, double alpha) {
        this.mdp = mdp;
        intialize();
        this.nrOfIterations = nrOfIterations;
        this.discount = discount;
        this.learningRate = alpha;
    }

    private void intialize() {
        qFunction = new Quple[mdp.getWidth()][mdp.getHeight()];
//        nextQFunction = new Quple[mdp.getWidth()][mdp.getHeight()];
        for (int x = 0; x < mdp.getWidth(); x++)
            for (int y = 0; y < mdp.getHeight(); y++) {
                qFunction[x][y] = new Quple(0.0, null);
//                nextQFunction[x][y] = new Quple(0.0, null);
            }
        currX = mdp.getStateXPosition();
        currY = mdp.getStateYPostion();

    }

    /*
    
    Initialize:
    valueFunction is double array (same dim as mdp) with all 0
    
    Q: what is k and what is S?
    
     */
    public Action[][] run() {
        //Start in random initial state?
        for (int k = 0; k < nrOfIterations; k++)
            while (!isFinalState()) {
                Action action = getRandomAction();
                mdp.setDeterministic();
                Point p = mdp.tryPerformAction(action, currX, currY);
                mdp.setStochastic();
                int newX = p.x;
                int newY = p.y;
                double reward = mdp.getReward(newX, newY);
                double q = qFunction[currX][currY].getValue();
                double maxQ = getMaxQ(newX, newY);
                qFunction[currX][currY] = new Quple(q + learningRate * (reward + discount * maxQ - q), action);
                // Q(s,a) = q + learningRate * (reward + discount * maxQ() - q)
                //Update position
                currX = newX;
                currY = newY;
            }
        return getPolicy();
    }

    private double getMaxQ(int newX, int newY) {
        Quple[] qValues = new Quple[Action.values().length];
        for (int i = 0; i < qValues.length; i++) {
            Action a = Action.values()[i];
            mdp.setDeterministic();
            Point p =  mdp.tryPerformAction(a, newX, newY);
            mdp.setStochastic();
            qValues[i] = new Quple(qFunction[p.x][p.y].getValue(),a);
        }
        return getMax(qValues).getValue();
    }
    
    public void printArray() {
        for (Quple[] valueFunction1 : qFunction) {
            for (Quple value : valueFunction1)
                System.out.print(value.getValue() + ", ");
            System.out.println("");
        }
        System.out.println("");
    }

    public Quple[][] getValueIterations() {
        return qFunction;
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

    private Action[][] getPolicy() {
        Action[][] policy = new Action[mdp.getWidth()][mdp.getHeight()];
        for (int x = 0; x < mdp.getWidth(); x++)
            for (int y = 0; y < mdp.getHeight(); y++)
                policy[x][y] = qFunction[x][y].getAction();
        return policy;
    }

    private boolean isFinalState() {
        return mdp.getField(currX, currY) == Field.NEGREWARD || mdp.getField(currX, currY) == Field.REWARD;
    }

    private Action getRandomAction() {
        Random rnd = new Random();
        int index = rnd.nextInt(Action.values().length);
        return Action.values()[index];
    }
}
