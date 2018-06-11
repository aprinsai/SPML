package spml4;

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

        MarkovDecisionProblem mdp = new MarkovDecisionProblem();

        ValueIterator vi = new ValueIterator(mdp, 0.00000005, 0.8);
        Action[][] policy = vi.run();

        mdp.setInitialState(0, 0);
        for (int i = 0; i < 100; i++)
            mdp.performAction(policy[mdp.getStateXPosition()][mdp.getStateYPostion()]);

        MarkovDecisionProblem mdp2 = new MarkovDecisionProblem(10, 10);
        mdp2.setField(5, 5, Field.REWARD);
        for (int i = 0; i < 100; i++)
            mdp2.performAction(policy[mdp2.getStateXPosition()][mdp2.getStateYPostion()]);
    }

}
