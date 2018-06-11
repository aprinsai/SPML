package spml4;

/**
 *
 * @author Pleun
 */
public class Quple {
    private double value;
    private Action action;
    
    public Quple(double value, Action action) {
        this.value = value;
        this.action = action;
    }

    public double getValue() {
        return value;
    }

    public Action getAction() {
        return action;
    }

}
