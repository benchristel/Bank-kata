package bank.core;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import static java.util.Objects.hash;

public class Transaction {
    private final int amount;
    final LocalDate date;

    public Transaction(LocalDate date, int amount) {
        this.date = date;
        this.amount = amount;
    }

    public boolean isCredit() {
        return netCredit() > 0;
    }

    public int netCredit() {
        return amount;
    }

    public String toString() {
        return "Transaction(" + date.format(DateTimeFormatter.ISO_DATE) + ", " + amount + ")";
    }

    public String date() {
        return date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return amount == that.amount &&
            Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, date);
    }
}
