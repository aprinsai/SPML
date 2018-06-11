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

        ValueIterator vi = new ValueIterator(mdp, 0.000005, 0.8);
        Action[][] policy = vi.run();
        
        for (int y = 0; y < mdp.getHeight(); y++) {
            for (int x = 0; x < mdp.getWidth(); x++) {
                System.out.printf("%s ",policy[x][y]);
            }
            System.out.println("");
            
        }
        
        for (int i = 0; i < 100; i++)
            mdp.performAction(policy[mdp.getStateXPosition()][mdp.getStateYPostion()]);
//
//        MarkovDecisionProblem mdp2 = new MarkovDecisionProblem(10, 10);
//        ValueIterator vi2 = new ValueIterator(mdp2, 0.00000005, 0.8);
//        Action[][] policy2 = vi2.run();
//
//        mdp2.setField(5, 5, Field.REWARD);
//        for (int i = 0; i < 100; i++)
//            mdp2.performAction(policy2[mdp2.getStateXPosition()][mdp2.getStateYPostion()]);
    }

}
