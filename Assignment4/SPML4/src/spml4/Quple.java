package spml4;

/**
 * This class is a simple data structure used to store an action with its value.
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