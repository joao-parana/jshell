package br.com.joaoparana.guice;

import com.google.inject.ImplementedBy;

@ImplementedBy(PaypalCreditCardProcessor.class)
public interface CreditCardProcessor {
	ChargeResult charge(String amount, CreditCard creditCard)
		      throws UnreachableException;
}
