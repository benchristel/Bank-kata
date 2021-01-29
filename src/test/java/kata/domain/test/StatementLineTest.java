package kata.domain.test;

import static kata.builders.DateCreator.date;
import static kata.builders.TransactionBuilder.aTransaction;
import static kata.domain.Amount.amountOf;
import static org.mockito.Mockito.verify;

import java.io.PrintStream;

import kata.domain.StatementLine;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class StatementLineTest {
	
	@Mock PrintStream printer;
	
	@Test public void
	should_print_itself() {
		StatementLine statementLine = new StatementLine(
											aTransaction()
												.with(amountOf(1000))
												.with(date("10/01/2012")).build(),
											amountOf(1000));
		
		statementLine.printTo(printer);
		
		verify(printer).println("10/01/2012 | 1000.00  |          | 1000.00");
	}
	
	@Test public void
	should_print_withdrawal() {
		StatementLine statementLine = new StatementLine(
											aTransaction()
												.with(amountOf(-1000))
												.with(date("10/01/2012")).build(), 
											amountOf(-1000));
		
		statementLine.printTo(printer);
		
		verify(printer).println("10/01/2012 |          | 1000.00  | -1000.00");
	}
	

}
