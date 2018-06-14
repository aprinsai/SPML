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

        // Q: Y IT NO WORK PLS
        MarkovDecisionProblem mdp = new MarkovDecisionProblem();
        mdp.setInitialState(0, 0);

        ValueIterator vi = new ValueIterator(mdp, 0.005, 0.9);
        Action[][] policy = vi.run();

        Quple[][] lastValueFunc = vi.getValueIterations();
        for (int y = 0; y < mdp.getHeight(); y++) {
            for (int x = 0; x < mdp.getWidth(); x++)
                System.out.printf("%s ", lastValueFunc[x][y].getValue());
            System.out.println("");
        }
        System.out.println("");
        for (int y = 0; y < mdp.getHeight(); y++) {
            for (int x = 0; x < mdp.getWidth(); x++)
                System.out.printf("%s ", policy[x][y]);
            System.out.println("");
        }

        for (int i = 0; i < 100; i++) {
            mdp.performAction(policy[mdp.getStateXPosition()][mdp.getStateYPostion()]);
            if (mdp.isTerminated())
                mdp.restart();
        }

//        MarkovDecisionProblem mdp2 = new MarkovDecisionProblem(10, 10);
//        mdp2.setField(5, 5, Field.REWARD);
//        ValueIterator vi2 = new ValueIterator(mdp2, 0.0005, 0.8);
//        Action[][] policy2 = vi2.run();
//
//        System.out.println("");
//        for (int y = 0; y < mdp2.getHeight(); y++) {
//            for (int x = 0; x < mdp2.getWidth(); x++)
//                System.out.printf("%s ", policy2[x][y]);
//            System.out.println("");
//        }
//
//        for (int i = 0; i < 100; i++) {
//            mdp2.performAction(policy2[mdp2.getStateXPosition()][mdp2.getStateYPostion()]);
//            if (mdp2.isTerminated())
//                mdp2.restart();
//        }
    }

}
