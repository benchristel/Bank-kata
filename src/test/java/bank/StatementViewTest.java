package bank;

import bank.core.StatementView;
import bank.core.Transaction;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import static bank.functions.CollectionUtils.listOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class StatementViewTest {

    @Test
    public void
    displays_an_empty_list_of_transactions() throws Exception {
        shouldOutput(
            new ArrayList<>(),
            "DATE | CREDIT | DEBIT | BALANCE\n");
    }

    @Test
    public void
    displays_one_transaction() throws Exception {
        shouldOutput(
            listOf(new Transaction(date(2020, 12, 23), 123)),
            "DATE | CREDIT | DEBIT | BALANCE\n"
                + "12/23/2020 | 123 |  | 123\n");
    }

    @Test
    public void
    displays_two_transactions() throws Exception {
        shouldOutput(
            listOf(
                new Transaction(date(2020, 12, 23), 456),
                new Transaction(date(2021, 11, 24), -123)
            ),
            "DATE | CREDIT | DEBIT | BALANCE\n"
                + "12/23/2020 | 456 |  | 456\n"
                + "11/24/2021 |  | 123 | 333\n"
        );
    }

    private void shouldOutput(Iterable<Transaction> transactions, String expected) throws IOException {
        Appendable output = new StringBuffer();
        new StatementView(transactions).print(output);
        assertThat(output.toString(), is(equalTo(expected)));
    }

    private LocalDate date(int y, int m, int d) {
        return LocalDate.of(y, m, d);
    }
}
