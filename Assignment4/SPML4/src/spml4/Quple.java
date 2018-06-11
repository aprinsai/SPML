package spml4;

/**
 * We're so funny we're making puns combining Q-value and Tuple haha
 * @author Pleun
 * @author Noukie
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
