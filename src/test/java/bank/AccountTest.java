package bank;

import bank.core.*;
import bank.edges.AppendOnlyList;
import com.google.common.collect.Lists;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static bank.functions.CollectionUtils.listOf;

public class AccountTest {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private final AppendOnlyStore<Transaction> transactions = new AppendOnlyList<>();
    private final LocalDate today = LocalDate.of(2020, 12, 23);
    private Clock stoppedClock = new StoppedClock(today);

    @Test
    public void
    rejects_zero_deposit() throws Exception {
        thrown.expect(Account.BadDepositAmount.class);
        account().deposit(0);
    }

    @Test
    public void
    rejects_negative_deposit() throws Exception {
        thrown.expect(Account.BadDepositAmount.class);
        account().deposit(-1);
    }

    @Test
    public void
    does_not_record_invalid_deposit() {
        try {
            account().deposit(0);
        } catch (Exception e) {
        }

        assertThat(listOf(account().transactions()), is(empty()));
    }

    @Test
    public void
    accepts_positive_deposit() throws Exception {
        account().deposit(1);
    }

    @Test
    public void
    rejects_zero_withdrawal() throws Exception {
        thrown.expect(Account.BadWithdrawalAmount.class);
        account().withdraw(0);
    }

    @Test
    public void
    rejects_negative_withdrawal() throws Exception {
        thrown.expect(Account.BadWithdrawalAmount.class);
        account().withdraw(-1);
    }

    @Test
    public void
    rejects_withdrawal_from_empty_account() throws Exception {
        thrown.expect(Account.Overdraft.class);
        account().withdraw(1);
    }

    @Test
    public void
    rejects_overdraft() throws Exception {
        thrown.expect(Account.Overdraft.class);
        account().deposit(1);
        account().withdraw(1);
        account().withdraw(1);
    }

    @Test
    public void
    accepts_withdrawal() throws Exception {
        account().deposit(1);
        account().deposit(1);
        account().withdraw(2);
    }

    @Test
    public void
    records_one_deposit() throws Exception {
        account().deposit(1);
        ArrayList<Transaction> expected = Lists.newArrayList(
            new Transaction(today, 1)
        );
        assertThat(listOf(account().transactions()), is(equalTo(expected)));
    }

    @Test
    public void
    records_multiple_deposits() throws Exception {
        account().deposit(500);
        account().deposit(300);
        ArrayList<Transaction> expected = Lists.newArrayList(
            new Transaction(today, 500),
            new Transaction(today, 300)
        );
        assertThat(listOf(account().transactions()), is(equalTo(expected)));
    }

    @Test
    public void
    records_withdrawals() throws Exception {
        account().deposit(800);
        account().withdraw(500);
        account().withdraw(300);
        ArrayList<Transaction> expected = Lists.newArrayList(
            new Transaction(today, 800),
            new Transaction(today, -500),
            new Transaction(today, -300)
        );
        assertThat(listOf(account().transactions()), is(equalTo(expected)));
    }

    private Account accountCache;
    private Account account() {
        if (accountCache == null)
            accountCache = new Account(transactions, new TransactionFactory(stoppedClock));
        return accountCache;
    }

}