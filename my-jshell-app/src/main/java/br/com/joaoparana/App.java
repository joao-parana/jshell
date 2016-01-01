package br.com.joaoparana;

import static org.eclipse.persistence.config.PersistenceUnitProperties.DDL_GENERATION;
import static org.eclipse.persistence.config.PersistenceUnitProperties.DROP_AND_CREATE;
import static org.eclipse.persistence.config.PersistenceUnitProperties.JDBC_DRIVER;
import static org.eclipse.persistence.config.PersistenceUnitProperties.JDBC_PASSWORD;
import static org.eclipse.persistence.config.PersistenceUnitProperties.JDBC_URL;
import static org.eclipse.persistence.config.PersistenceUnitProperties.JDBC_USER;
import static org.eclipse.persistence.config.PersistenceUnitProperties.LOGGING_FILE;
import static org.eclipse.persistence.config.PersistenceUnitProperties.LOGGING_LEVEL;
import static org.eclipse.persistence.config.PersistenceUnitProperties.LOGGING_SESSION;
import static org.eclipse.persistence.config.PersistenceUnitProperties.LOGGING_THREAD;
import static org.eclipse.persistence.config.PersistenceUnitProperties.LOGGING_TIMESTAMP;
import static org.eclipse.persistence.config.PersistenceUnitProperties.TARGET_SERVER;
import static org.eclipse.persistence.config.PersistenceUnitProperties.TRANSACTION_TYPE;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.spi.PersistenceUnitTransactionType;

import org.eclipse.persistence.config.TargetServer;

/**
 * Hello JPA!
 *
 */
public class App {
	public static void main(String[] args) throws Exception {
		// DuckType duckType = new DuckType();
		// duckType.example();
		System.out.println("Starting JPA Test with MySql Database");
		EntityManagerFactory emf = getEMF(DBMS.MySQL);
		DoJPA jpa = new DoJPA(emf);
		jpa.startPersistence();
		jpa.close();
	}

	public static EntityManagerFactory getEMF(DBMS dbms) throws UnknownHostException {
		String url = null;
		String driver = null;
		String user = null;
		String passwd = null;
		String ddlGeneration = null;
		Map<String, String> properties = new HashMap<String, String>();

		// Ensure RESOURCE_LOCAL transactions is used.
		properties.put(TRANSACTION_TYPE, PersistenceUnitTransactionType.RESOURCE_LOCAL.name());

		// Se estiver no Contêiner Docker
		String MYSQL_TCP_ADDR = System.getenv("MYSQL_PORT_3306_TCP_ADDR");

		InetAddress ip = InetAddress.getByName("localhost");
		if (MYSQL_TCP_ADDR != null) {
			ip = InetAddress.getByName(MYSQL_TCP_ADDR);
		} else {
			ip = InetAddress.getByName("mysql");
		}

		// Configure EclipseLink connection
		if (dbms.equals(DBMS.Oracle)) {
			url = "jdbc:oracle:thin:@localhost:1521:ORCL";
			driver = "oracle.jdbc.OracleDriver";
			user = "test";
			passwd = "test";
			properties.put(JDBC_DRIVER, driver);
			properties.put(JDBC_URL, url);
			properties.put(JDBC_USER, user);
			properties.put(JDBC_PASSWORD, passwd);
		} else if (dbms.equals(DBMS.MySQL)) {
			url = "jdbc:mysql://" + ip.getHostAddress() + ":" + 3306 + "/test";
			driver = "com.mysql.jdbc.Driver";
			user = "test";
			passwd = "test";
			ddlGeneration = DROP_AND_CREATE;
			properties.put(JDBC_DRIVER, driver);
			properties.put(JDBC_URL, url);
			properties.put(JDBC_USER, user);
			properties.put(JDBC_PASSWORD, passwd);
			// DDL_GENERATION : CREATE_ONLY ou DROP_AND_CREATE
			properties.put(DDL_GENERATION, ddlGeneration);
		}

		// Configure logging. FINE ensures all SQL is shown
		properties.put(LOGGING_LEVEL, "FINE");
		properties.put(LOGGING_TIMESTAMP, "false");
		properties.put(LOGGING_THREAD, "false");
		properties.put(LOGGING_SESSION, "false");
		properties.put(LOGGING_FILE, "jpa-presistence.log");

		// Ensure that no server-platform is configured
		properties.put(TARGET_SERVER, TargetServer.None);
		// Now the EntityManagerFactory can be instantiated for testing using:

		String unitName = "myPersistence";
		EntityManagerFactory emf = Persistence.createEntityManagerFactory(unitName, properties);
		System.out.println("••• Informações de conexão ao Database\n••• url = " + url + "\n••• driver = " + driver
				+ "\n••• user = " + user + "\n••• passwd = " + passwd);
		return emf;
	}
}
