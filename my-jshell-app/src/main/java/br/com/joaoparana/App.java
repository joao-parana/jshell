package br.com.joaoparana;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Persistence;
import javax.persistence.spi.PersistenceUnitTransactionType;

import org.eclipse.persistence.config.TargetServer;

import static org.eclipse.persistence.config.PersistenceUnitProperties.*;
/**
 * Hello JPA!
 *
 */
public class App {
	public static void main(String[] args) {
		System.out.println("Hello JPA!");
		Map properties = new HashMap();
		 
	    // Ensure RESOURCE_LOCAL transactions is used.
	    properties.put(TRANSACTION_TYPE,
	        PersistenceUnitTransactionType.RESOURCE_LOCAL.name());
	 
	    // Configure the internal EclipseLink connection pool
	    properties.put(JDBC_DRIVER, "oracle.jdbc.OracleDriver");
	    properties.put(JDBC_URL, "jdbc:oracle:thin:@localhost:1521:ORCL");
	    properties.put(JDBC_USER, "scott");
	    properties.put(JDBC_PASSWORD, "tiger");
	 
	    // Configure logging. FINE ensures all SQL is shown
	    properties.put(LOGGING_LEVEL, "FINE");
	    properties.put(LOGGING_TIMESTAMP, "false");
	    properties.put(LOGGING_THREAD, "false");
	    properties.put(LOGGING_SESSION, "false");
	 
	    // Ensure that no server-platform is configured
	    properties.put(TARGET_SERVER, TargetServer.None);
	//Now the EntityManagerFactory can be instantiated for testing using:

	String unitName = "myPersistence";
	Persistence.createEntityManagerFactory(unitName , properties);
	}
}
