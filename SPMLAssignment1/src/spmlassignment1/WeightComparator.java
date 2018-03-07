package spmlassignment1;

import java.util.Comparator;

/**
 *
 * @author Pleun
 */
public class WeightComparator implements Comparator<Vertex>{
    @Override
    public int compare(Vertex o1, Vertex o2) {
        if (o1.getKey() > o2.getKey())
            return 1;
        else if (o1.getKey() < o2.getKey())
            return -1;
        else return 0;
    }
}
