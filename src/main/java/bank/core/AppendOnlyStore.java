package bank.core;

public interface AppendOnlyStore<T> extends Iterable<T> {
    void add(T element);
}
