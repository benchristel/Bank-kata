package bank.functions;

import java.util.ArrayList;
import java.util.List;

public class CollectionUtils {
    public static <T> List<T> listOf(Iterable<T> i) {
        List<T> list = new ArrayList<>();
        i.forEach(list::add);
        return list;
    }
}
