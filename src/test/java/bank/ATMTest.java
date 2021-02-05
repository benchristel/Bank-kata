package bank;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertThat;

import bank.core.Account;
import bank.core.Clock;
import bank.core.TransactionFactory;
import bank.edges.ATM;
import bank.edges.AppendOnlyList;
import com.sun.tools.javac.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

@RunWith(MockitoJUnitRunner.class)
public class ATMTest {

    private static final String BLANK_STATEMENT = "DATE | CREDIT | DEBIT | BALANCE\n";
    private final Appendable statement = new StringBuffer();
    private final ArrayList<Exception> errors = new ArrayList<>();
    private Account account = new Account(
        new AppendOnlyList<>(),
        new TransactionFactory(new Clock())
    );

    @Test
    public void
    prints_a_blank_statement() {
        ATM atm = new ATM(statement, errors::add, account);
        atm.printStatement();
        assertThat(statement.toString(), is(equalTo(BLANK_STATEMENT)));
        assertThat(messages(errors), is(empty()));
    }

    @Test
    public void
    deposits_and_withdraws() {
        ATM atm = new ATM(statement, errors::add, account);
        atm.deposit(1);
        atm.withdraw(1);
        assertThat(messages(errors), is(empty()));
    }

    @Test
    public void
    logs_errors_from_deposit() {
        ATM atm = new ATM(statement, errors::add, account);
        atm.deposit(0);
        assertThat(messages(errors), is(equalTo(List.of("deposit amount must be positive"))));
        atm.printStatement();
        assertThat(statement.toString(), is(equalTo(BLANK_STATEMENT)));
    }

    @Test
    public void
    logs_errors_from_withdrawal() {
        ATM atm = new ATM(statement, errors::add, account);
        atm.withdraw(1);
        assertThat(messages(errors), is(equalTo(List.of("overdraft"))));
        atm.printStatement();
        assertThat(statement.toString(), is(equalTo(BLANK_STATEMENT)));
    }

    private java.util.List<String> messages(Collection<Exception> errors) {
        return errors.stream()
            .map(Exception::getMessage)
            .collect(Collectors.toList());
    }
}
