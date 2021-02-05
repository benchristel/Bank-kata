package bank;

import bank.core.*;
import bank.edges.ATM;
import bank.edges.AppendOnlyList;

import static java.lang.System.exit;

public class App {
	
	public static void main(String[] args) {
		AppendOnlyStore<Transaction> transactions = new AppendOnlyList<>();
		ATM account = new ATM(System.out, App::printAndExit, new Account(transactions, new TransactionFactory(new Clock())));

		account.deposit(10);
		account.deposit(20);
		account.withdraw(15);

		account.printStatement();
	}

	private static void printAndExit(Exception e) {
		System.out.print(e.getMessage());
		exit(1);
	}
}
