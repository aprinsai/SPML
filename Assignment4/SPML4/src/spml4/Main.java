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

        Qlearner qLearner = new Qlearner(mdp, 9000, 1, 0.05, 0.3);
        Action[][] policy = qLearner.run();
        Quple[][] qFunction = qLearner.getQFunction();
        for (int y = mdp.getHeight() - 1; y >= 0; y--) {
            for (int x = 0; x < mdp.getWidth(); x++)
                System.out.printf("%s ", qFunction[x][y].getAction());
                //System.out.printf("%s: \t %.4s \t", qFunction[x][y].getAction(), qFunction[x][y].getValue());
            System.out.println("");
        }

        double finalReward = 0.0;
        for (int i = 0; i < 100; i++) {
            mdp.performAction(policy[mdp.getStateXPosition()][mdp.getStateYPostion()]);
            if (mdp.isTerminated()){
                finalReward += mdp.getFinalReward();
                mdp.restart();
            }
        }
        System.out.println("");
        
        System.out.println("Final reward: "+finalReward);

    }

    private static void runValueIterator() {
        MarkovDecisionProblem mdp = new MarkovDecisionProblem();
        mdp.setInitialState(0, 0);

        ValueIterator vi = new ValueIterator(mdp, 0.2, 1);
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

    }

}
