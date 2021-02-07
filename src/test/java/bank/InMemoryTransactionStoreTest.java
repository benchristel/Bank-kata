package bank;

import bank.core.AppendOnlyStore;
import bank.core.Transaction;
import bank.edges.AppendOnlyList;

public class InMemoryTransactionStoreTest extends TransactionStoreTest {

    AppendOnlyStore<Transaction> createSubject() {
        return new AppendOnlyList<>();
    }
}
