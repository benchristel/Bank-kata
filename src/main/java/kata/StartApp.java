package kata;

import static kata.builders.DateCreator.date;
import static kata.domain.Amount.amountOf;

import kata.domain.Account;
import kata.domain.Statement;

public class StartApp {
	
	public static void main(String[] args) {
		Account account = new Account(new Statement());
		
		account.deposit(amountOf(1000), date("10/01/2012"));
		account.deposit(amountOf(2000), date("13/01/2012"));
		account.withdrawal(amountOf(500), date("14/01/2012"));
		
		account.printStatement(System.out);
	}

}
