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
        mdp.setInitialState(0, 0);
        for (int i = 0; i < 15; i++) {
            mdp.performAction(Action.UP);
            mdp.performAction(Action.UP);
            mdp.performAction(Action.RIGHT);
            mdp.performAction(Action.RIGHT);
            mdp.performAction(Action.RIGHT);
            mdp.restart();
        }

        MarkovDecisionProblem mdp2 = new MarkovDecisionProblem(10, 10);
        mdp2.setField(5, 5, Field.REWARD);
        for (int i = 0; i < 100; i++) {
            mdp2.performAction(Action.UP);
            mdp2.performAction(Action.RIGHT);
            mdp2.performAction(Action.DOWN);
            mdp2.performAction(Action.LEFT);
        }
    }

    /*
    
    Initialize:
    valueFunction is double array (same dim as mdp) with all 0
    
    Q: what is k and what is S?
    
     */
    private double[][] ValueIterations(MarkovDecisionProblem mdp) {
        double[][] valueFunction = new double[mdp.getWidth()][mdp.getHeight()];
        intialize(valueFunction);

        return null;
    }

    private void intialize(double[][] valueFunction) {
        for (double[] valueFunction1 : valueFunction)
            for (double value : valueFunction1)
                value = 0;
    }

    private void printArray(double[][] valueFunction) {
        for (double[] valueFunction1 : valueFunction) {
            for (double value : valueFunction1)
                System.out.print(value + " ");
            System.out.println("");
        }
    }

}
