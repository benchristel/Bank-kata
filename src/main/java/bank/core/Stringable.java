package bank.core;

import javax.annotation.Nonnull;

public interface Stringable extends CharSequence {
    @Override
    default int length() {
        return toString().length();
    }

    @Override
    default char charAt(int index) {
        return toString().charAt(index);
    }

    @Override
    default CharSequence subSequence(int start, int end) {
        return toString().subSequence(start, end);
    }

    @Nonnull
    String toString();
}
