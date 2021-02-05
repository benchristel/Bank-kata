package bank.edges;

import bank.core.AppendOnlyStore;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AppendOnlyList<T> implements AppendOnlyStore<T> {
    private List<T> elements = new ArrayList<>();

    @Override
    public void add(T element) {
        elements.add(element);
    }

    @Override
    public Iterator<T> iterator() {
        return elements.iterator();
    }
}
