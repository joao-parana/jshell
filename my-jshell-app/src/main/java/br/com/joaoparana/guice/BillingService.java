package br.com.joaoparana.guice;

import com.google.inject.Inject;

public class BillingService {
	private final CreditCardProcessor processor;
	private final TransactionLog transactionLog;

	@Inject
	BillingService(CreditCardProcessor processor, TransactionLog transactionLog) {
		this.processor = processor;
		this.transactionLog = transactionLog;
	}

	public Receipt chargeOrder(PizzaOrder order, CreditCard creditCard) {
		return null;
		// ...
	}
}