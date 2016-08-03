package br.com.joaoparana.guice;

import com.google.inject.AbstractModule;

public class BillingModule extends AbstractModule {
	private LogTo logTo;

	public BillingModule(LogTo logTo) {
		super();
		this.logTo = logTo;
	}

	@Override
	protected void configure() {

		if (logTo.equals(LogTo.Console)) {
			/*
			 * This tells Guice that whenever it sees a dependency on a
			 * TransactionLog, it should satisfy the dependency using a
			 * ConsoleTransactionLog.
			 */
			bind(TransactionLog.class).to(ConsoleTransactionLog.class);
		} else if (logTo.equals(LogTo.MySql)) {
			/*
			 * This tells Guice that whenever it sees a dependency on a
			 * TransactionLog, it should satisfy the dependency using a
			 * DatabaseTransactionLog.
			 */
			bind(TransactionLog.class).to(DatabaseTransactionLog.class);
			/*
			 * Linked bindings can also be chained:
			 */
			bind(DatabaseTransactionLog.class).to(MySqlDatabaseTransactionLog.class);
		} else if (logTo.equals(LogTo.Memory)) {
			/*
			 * Eager Singletons
			 */
			bind(TransactionLog.class).to(InMemoryTransactionLog.class).asEagerSingleton();
		}

		/*
		 * Similarly, this binding tells Guice that when CreditCardProcessor is
		 * used in a dependency, that should be satisfied with a
		 * PaypalCreditCardProcessor.
		 */
		bind(CreditCardProcessor.class).to(PaypalCreditCardProcessor.class);
	}
}