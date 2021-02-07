package bank.functions;

import java.util.*;

public class CollectionUtils {
    public static <T> ArrayList<T> collectArrayList(Iterable<T> i) {
        ArrayList<T> list = new ArrayList<>();
        i.forEach(list::add);
        return list;
    }

    @SafeVarargs
    public static <T> ArrayList<T> listOf(T ...elements) {
        return new ArrayList<>(Arrays.asList(elements));
    }
}
