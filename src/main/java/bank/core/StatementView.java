package bank.core;

import javax.annotation.Nonnull;
import java.io.IOException;

public class StatementView {

    private final Iterable<Transaction> transactions;

    public StatementView(Iterable<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void print(Appendable statement) throws IOException {
        statement.append("DATE | CREDIT | DEBIT | BALANCE\n");
        int runningBalance = 0;
        for (Transaction t : transactions) {
            runningBalance += t.netCredit();
            statement.append(TransactionView.of(t, runningBalance));
        }
    }

    private static class TransactionView implements Stringable {
        private static TransactionView of(Transaction transaction, int runningBalance) {
            AmountView amountView = transaction.isCredit()
                ? new CreditView(transaction.netCredit())
                : new DebitView(transaction.netCredit());
            return new TransactionView(transaction.date(), amountView, runningBalance);
        }

        private final String date;
        final AmountView amount;
        final int runningBalance;

        private TransactionView(String date, AmountView amount, int runningBalance) {
            this.date = date;
            this.amount = amount;
            this.runningBalance = runningBalance;
        }

        @Nonnull
        public String toString() {
            return date + " | " + amount.credit() + " | " + amount.debit() + " | " + runningBalance + "\n";
        }
    }

    private static abstract class AmountView {
        final int amount;

        AmountView(int amount) {
            this.amount = amount;
        }

        abstract String credit();

        abstract String debit();
    }

    private static class CreditView extends AmountView {
        private CreditView(int amount) {
            super(amount);
        }

        String credit() {
            return "" + amount;
        }

        String debit() {
            return "";
        }
    }

    private static class DebitView extends AmountView {
        private DebitView(int amount) {
            super(amount);
        }

        String credit() {
            return "";
        }

        String debit() {
            return "" + -amount;
        }
    }
}
