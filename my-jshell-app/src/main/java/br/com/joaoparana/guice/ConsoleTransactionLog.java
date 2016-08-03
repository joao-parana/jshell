package br.com.joaoparana.guice;

import java.util.logging.Logger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ConsoleTransactionLog implements TransactionLog {

	private final Logger logger;

	@Inject
	public ConsoleTransactionLog(Logger logger) {
		this.logger = logger;
	}

	public void logConnectException(UnreachableException e) {
		/* the message is logged to the "ConsoleTransacitonLog" logger */
		logger.warning("Connect exception failed, " + e.getMessage());
	}
}