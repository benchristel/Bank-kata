package bank;

import bank.core.AppendOnlyStore;
import bank.core.Transaction;
import bank.core.FileTransactionStore;

public class FileTransactionStoreTest extends TransactionStoreTest {
    @Override
    AppendOnlyStore<Transaction> createSubject() {
        return new FileTransactionStore();
    }
}
