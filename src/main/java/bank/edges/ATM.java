package bank.edges;

import bank.core.Account;
import bank.core.StatementView;

import java.io.IOException;
import java.util.function.Consumer;

public class ATM implements thirdparty.ATM {

    private final Appendable statement;
    private final Consumer<Exception> handleError;
    private final Account account;

    public ATM(Appendable statement, Consumer<Exception> handleError, Account account) {
        this.statement = statement;
        this.handleError = handleError;
        this.account = account;
    }

    public void printStatement() {
        try {
            new StatementView(account.transactions()).print(statement);
        } catch (IOException e) {
            handleError.accept(e);
        }
    }

    public void deposit(int amount) {
        try {
            account.deposit(amount);
        } catch (Exception e) {
            handleError.accept(e);
        }
    }

    public void withdraw(int amount) {
        try {
            account.withdraw(amount);
        } catch (Exception e) {
            handleError.accept(e);
        }
    }
}
