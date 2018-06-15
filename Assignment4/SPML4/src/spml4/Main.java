package spml4;

import java.util.Arrays;

/**
 * This main is for testing purposes (and to show you how to use the MDP class).
 *
 * @author Jered Vroon
 *
 */
public class Main {

    /**
     * @param args, not used
     */
    public static void main(String[] args) {
        //runValueIterator();
        runQlearner();
    }

    private static void runQlearner() {
        MarkovDecisionProblem mdp = new MarkovDecisionProblem();
        mdp.setInitialState(0, 0);

        Qlearner qLearner = new Qlearner(mdp, 10000, 0.8, 0.2, 0.3);
        Action[][] policy = qLearner.run();
        Quple[][] qFunction = qLearner.getQFunction();

        for (int y = mdp.getHeight() - 1; y >= 0; y--) {
            for (int x = 0; x < mdp.getWidth(); x++)
                System.out.printf("%s: \t %.4s \t", qFunction[x][y].getAction(), qFunction[x][y].getValue());
            System.out.println("");
        }

        for (int i = 0; i < 100; i++) {
            mdp.performAction(policy[mdp.getStateXPosition()][mdp.getStateYPostion()]);
            if (mdp.isTerminated())
                mdp.restart();
        }
        System.out.println("");
        
        // Second one does not work very well
        MarkovDecisionProblem mdp2 = new MarkovDecisionProblem(10, 10);
        mdp2.setField(5, 5, Field.REWARD);
        Qlearner qLearner2 = new Qlearner(mdp2, 100000, 0.8, 0.2, 0.2);
        Action[][] policy2 = qLearner2.run();
        Quple[][] qFunction2 = qLearner2.getQFunction();

        for (int y = mdp2.getHeight() - 1; y >= 0; y--) {
            for (int x = 0; x < mdp2.getWidth(); x++)
                System.out.printf("%s: \t %.4s \t", qFunction2[x][y].getAction(), qFunction2[x][y].getValue());
            System.out.println("");
        }

        for (int i = 0; i < 100; i++) {
            mdp2.performAction(policy2[mdp2.getStateXPosition()][mdp2.getStateYPostion()]);
            if (mdp2.isTerminated())
                mdp2.restart();
        }
        
    }

    private static void runValueIterator() {
        MarkovDecisionProblem mdp = new MarkovDecisionProblem();
        mdp.setInitialState(0, 0);

        ValueIterator vi = new ValueIterator(mdp, 0.005, 0.8);
        Action[][] policy = vi.run();

        for (int y = mdp.getHeight() - 1; y >= 0; y--) {
            for (int x = 0; x < mdp.getWidth(); x++)
                System.out.printf("%s ", policy[x][y]);
            System.out.println("");
        }

        for (int i = 0; i < 100; i++) {
            mdp.performAction(policy[mdp.getStateXPosition()][mdp.getStateYPostion()]);
            if (mdp.isTerminated())
                mdp.restart();
        }

        MarkovDecisionProblem mdp2 = new MarkovDecisionProblem(10, 10);
        mdp2.setField(5, 5, Field.REWARD);
        ValueIterator vi2 = new ValueIterator(mdp2, 0.0005, 0.8);
        Action[][] policy2 = vi2.run();

        System.out.println("");
        for (int y = mdp2.getHeight() - 1; y >= 0; y--) {
            for (int x = 0; x < mdp2.getWidth(); x++)
                System.out.printf("%s ", policy2[x][y]);
            System.out.println("");
        }

        for (int i = 0; i < 100; i++) {
            mdp2.performAction(policy2[mdp2.getStateXPosition()][mdp2.getStateYPostion()]);
            if (mdp2.isTerminated())
                mdp2.restart();
        }
    }

}
