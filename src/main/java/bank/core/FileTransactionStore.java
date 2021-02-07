package bank.core;

import com.google.common.io.LineReader;
import org.apache.commons.collections.iterators.TransformIterator;
import org.apache.commons.io.*;

import javax.annotation.Nonnull;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;

public class FileTransactionStore implements AppendOnlyStore<Transaction> {

    @Override
    public void add(Transaction t) {
        try {
            new PrintStream(new FileOutputStream("/tmp/txns", true))
                .printf("%s|%d\n", t.date.format(DateTimeFormatter.ISO_DATE), t.netCredit());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Nonnull
    public Iterator<Transaction> iterator() {
        try {
            Stream<Transaction> stream = Files.lines(Paths.get("/tmp/txns")).map(line -> {
                String parts[] = line.split("\\|");
                return new Transaction(LocalDate.parse(parts[0]), Integer.parseInt(parts[1]));
            });
            return stream.iterator();
        } catch (NoSuchFileException e) {
            return new ArrayList<Transaction>().iterator();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
