package bank.core;

public class Account {

    private final AppendOnlyStore<Transaction> transactions;
    private TransactionFactory transactionFactory;

    public Account(AppendOnlyStore<Transaction> transactions, TransactionFactory transactionFactory) {
        this.transactions = transactions;
        this.transactionFactory = transactionFactory;
    }

    public void deposit(int amount) throws Exception {
        if (amount <= 0) throw new BadDepositAmount();
        transactions.add(transactionFactory.newCredit(amount));
    }

    public void withdraw(int amount) throws Exception {
        if (amount <= 0) throw new BadWithdrawalAmount();
        if (balance() < amount) throw new Overdraft();
        transactions.add(transactionFactory.newDebit(amount));
    }

    private int balance() {
        int total = 0;
        for (Transaction t : transactions) total += t.netCredit();
        return total;
    }

    public Iterable<Transaction> transactions() {
        return this.transactions;
    }

    public static class BadDepositAmount extends Exception {
        public String getMessage() {
            return "deposit amount must be positive";
        }
    }

    public static class BadWithdrawalAmount extends Exception {
        public String getMessage() {
            return "withdrawal amount must be positive";
        }
    }

    public static class Overdraft extends Exception {
        public String getMessage() {
            return "overdraft";
        }
    }
}
