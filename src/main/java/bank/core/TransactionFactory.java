package bank.core;

public class TransactionFactory {
    private final Clock clock;

    public TransactionFactory(Clock clock) {
        this.clock = clock;
    }

    public Transaction newCredit(int magnitude) {
        return newTransaction(magnitude);
    }

    public Transaction newDebit(int magnitude) {
        return newTransaction(-magnitude);
    }

    private Transaction newTransaction(int amount) {
        return new Transaction(clock.today(), amount);
    }
}
