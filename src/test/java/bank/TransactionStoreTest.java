package bank;

import bank.core.AppendOnlyStore;
import bank.core.Transaction;
import org.apache.commons.io.FileUtils;
import org.junit.*;

import java.io.File;
import java.time.LocalDate;

import static bank.functions.CollectionUtils.collectArrayList;
import static bank.functions.CollectionUtils.listOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public abstract class TransactionStoreTest {
    private AppendOnlyStore<Transaction> cachedSubject;

    abstract AppendOnlyStore<Transaction> createSubject();

    private AppendOnlyStore<Transaction> store() {
        if (this.cachedSubject == null)
            this.cachedSubject = createSubject();
        return this.cachedSubject;
    }

    @Before
    public void
    cleanUpFile() {
        FileUtils.deleteQuietly(new File("/tmp/txns"));
    }

    @Test
    public void
    is_initially_empty() {
        assertThat(store().iterator().hasNext(), is(false));
    }

    @Test
    public void
    stores_and_retrieves_a_transaction() {
        LocalDate givenDate = LocalDate.parse("2021-02-06");
        store().add(new Transaction(givenDate, 123));
        assertThat(
            collectArrayList(store()),
            is(equalTo(listOf(new Transaction(givenDate, 123))))
        );
    }

    @Test
    public void
    multiple_transactions() {
        LocalDate givenDate = LocalDate.parse("2021-02-06");
        store().add(new Transaction(givenDate, 123));
        store().add(new Transaction(givenDate, -456));
        assertThat(
            collectArrayList(store()),
            is(equalTo(listOf(
                new Transaction(givenDate, 123),
                new Transaction(givenDate, -456)
            )))
        );
    }


}
