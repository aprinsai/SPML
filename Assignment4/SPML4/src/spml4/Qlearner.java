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
    private final double epsilon;
    private final Random rnd = new Random();

    private int currX;
    private int currY;

    public Qlearner(MarkovDecisionProblem mdp, int nrOfIterations, double discount, double learningRate, double epsilon) {
        this.mdp = mdp;
        intialize();
        this.nrOfIterations = nrOfIterations;
        this.discount = discount;
        this.learningRate = learningRate;
        this.epsilon = epsilon;
    }

    /**
     * initializes the qFunction with Quples of 0.0, Action.UP
     * a quple of the reward, null is added to the terminal states.
     */
    private void intialize() {
        qFunction = new Quple[mdp.getWidth()][mdp.getHeight()];
//        nextQFunction = new Quple[mdp.getWidth()][mdp.getHeight()];
        for (int x = 0; x < mdp.getWidth(); x++)
            for (int y = 0; y < mdp.getHeight(); y++)
                if (mdp.getField(x, y) == Field.REWARD)
                    qFunction[x][y] = new Quple(1.0, null);
                else if (mdp.getField(x, y) == Field.NEGREWARD)
                    qFunction[x][y] = new Quple(-1.0, null);
                else
                    qFunction[x][y] = new Quple(0.0, Action.UP);
    }

    /**
     * run() is run for a certain amount of iterations, specified in the main class
     * while the agent is not in the final state,
     * an action is set and the agent performs this action in a deterministic setting
     * the new coordinates are used to give the qFunction a new quple with a Q value that 
     * is the previous Q value but updated, and the action.
     * @return 
     */
    public Action[][] run() {
        //Start in random initial state?
        for (int k = 0; k < nrOfIterations; k++) {
            currX = mdp.getStateXPosition();
            currY = mdp.getStateYPostion();

            while (!isFinalState()) {
//                System.out.printf("[%d,%d] : %f\n",currX,currY,qFunction[currX][currY].getValue());
                //Perform random action and get state
                Action action = getGreedyAction();
                mdp.setDeterministic();
                Point p = mdp.tryPerformAction(action, currX, currY);
                mdp.setStochastic();
                int newX = p.x;
                int newY = p.y;

                //Get the data from that new state
                double reward = mdp.getReward(newX, newY);
                double q = qFunction[currX][currY].getValue();
                double maxQ = getMaxQ(newX, newY).getValue();
                qFunction[currX][currY] = new Quple(q + learningRate * (reward + discount * maxQ - q), action);

                //Update position
                currX = newX;
                currY = newY;
            }
        }
        return getPolicy();
    }

    /**
     * tries to perform all actions in the state newX, newY 
     * @param newX
     * @param newY
     * @return Quple with the max value and action
     */
    private Quple getMaxQ(int newX, int newY) {
        Quple[] qValues = new Quple[Action.values().length];
        for (int i = 0; i < qValues.length; i++) {
            Action a = Action.values()[i];
            mdp.setDeterministic();
            Point p = mdp.tryPerformAction(a, newX, newY);
            mdp.setStochastic();
            qValues[i] = new Quple(qFunction[p.x][p.y].getValue(), a);
        }
        return getMax(qValues);
    }

    public Quple[][] getQFunction() {
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

    /**
     * 
     * @return policy
     */
    private Action[][] getPolicy() {
        Action[][] policy = new Action[mdp.getWidth()][mdp.getHeight()];
        for (int x = 0; x < mdp.getWidth(); x++)
            for (int y = 0; y < mdp.getHeight(); y++)
                policy[x][y] = qFunction[x][y].getAction();
        return policy;
    }

    /**
     * checks whether the current state is the final state.
     * @return 
     */
    private boolean isFinalState() {
        return mdp.getField(currX, currY) == Field.NEGREWARD || mdp.getField(currX, currY) == Field.REWARD;
    }

    /**
     * 
     * @return random action
     */
    private Action getRandomAction() {
        int index = rnd.nextInt(Action.values().length);
        return Action.values()[index];
    }
    
    /**
     * Either returns a random action or an action with maximum value.
     * @return Action
     */
    private Action getGreedyAction() {
        double prob = rnd.nextDouble();
        if (prob > epsilon) {
            return getMaxQ(currX, currY).getAction();
        }
        else
            return getRandomAction();
    }
}
