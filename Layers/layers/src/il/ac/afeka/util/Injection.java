package il.ac.afeka.util;

import java.util.HashMap;
import java.util.Map;

// A data structure that represents a finite injection

public class Injection<First, Second> {

    private Map<First, Second> first2second;
    private Map<Second, First> second2first;

    public Injection() {
        first2second = new HashMap<>();
        second2first = new HashMap<>();
    }

    public First getBySecond(Second key) {
        return second2first.get(key);
    }

    public Second getByFirst(First key) {
        return first2second.get(key);
    }

    public void put(First first, Second second) {
        first2second.put(first, second);
        second2first.put(second, first);
    }

    public void remove(First first, Second second) {
        first2second.remove(first, second);
        second2first.remove(second, first);
    }
}
