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

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.spi.PersistenceUnitTransactionType;

import org.eclipse.persistence.config.TargetServer;

import com.google.inject.Guice;
import com.google.inject.Injector;

import br.com.joaoparana.guice.BillingModule;
import br.com.joaoparana.guice.BillingService;
import br.com.joaoparana.guice.LogTo;

/**
 * Hello JSHELL!
 * 
 * Some Examples from http://www.mkyong.com/tutorials/
 */
public class App {
	public static void main(String[] args) throws Exception {
		String msg;
		if (args == null) {
			msg = "•••••• HELP";
			System.out.println("\n" + msg);
			new Help().doIt(null);
			System.out.println("\n\nYou can test this Class using JShell\n\n    jshell\n"
					+ "    /classpath target/myJshellAppLib.jar\n" + "    import br.com.joaoparana.*\n"
					+ "    App app = new App()\n" + "    String[] args = { \"JPA\"}\n" + "    App.main(args)\n");
			return;
		}
		if (args.length == 0) {
			msg = "•••••• HELP";
			System.out.println("\n" + msg);
			new Help().doIt(null);
			System.out.println("\n\nYou can test this Class using JShell\n\n    jshell\n"
					+ "    /classpath target/myJshellAppLib.jar\n" + "    import br.com.joaoparana.*\n"
					+ "    App app = new App()\n" + "    String[] args = { \"JPA\"}\n" + "    App.main(args)\n");
			return;
		}
		Options key = null;
		try {
			key = Options.valueOf(args[0]);
		} catch (IllegalArgumentException e) {
			msg = "•••••• ERROR - Illegal Argument : " + args[0];
			System.out.println("\n" + msg);
			new Help().doIt(null);
			return;
		}
		switch (key) {
		case HELP:
			msg = "•••••• HELP";
			System.out.println("\n" + msg);
			new Help().doIt(null);
			break;
		case CREATE_FILE:
			msg = "•••••• Criando arquivo";
			System.out.println("\n" + msg);
			new RunJPA().doIt(null);
			break;
		case READ_FILE:
			msg = "•••••• Lendo arquivo";
			System.out.println("\n" + msg);
			new BufferedInputStreamReader().doIt(null);
			break;
		case WRITE_FILE:
			msg = "•••••• Gravando arquivo";
			System.out.println("\n" + msg);
			new WriteFileOutputStream().doIt(null);
			break;
		case OPEN:
			msg = "•••••• Ainda não foi implementado o OPEN";
			System.out.println("\n" + msg);
			break;
		case JDBC:
			msg = "•••••• Ainda não foi implementado o JDBC";
			System.out.println("\n" + msg);
			break;

		case JPA:
			msg = "•••••• Starting JPA Test with MySql Database";
			System.out.println("\n" + msg);
			new RunJPA().doIt(null);
			break;
		default:
			System.out.println("\n\nYou can test this Class using JShell\n\n    jshell\n"
					+ "    /classpath target/myJshellAppLib.jar\n" + "    import br.com.joaoparana.*\n"
					+ "    App app = new App()\n" + "    String[] args = { \"JPA\"}\n" + "    App.main(args)\n");
			return;
		}

	}

}

enum Options {
	HELP, CREATE_FILE, WRITE_FILE, READ_FILE, JDBC, OPEN, JPA
}

class RunJPA {
	public void doIt(String[] args) {
		// DuckType duckType = new DuckType();
		// duckType.example();
		// System.out.println("•••••• Starting JPA Test with MySql Database");
		EntityManagerFactory emf = null;
		DoJPA jpa = null;
		try {
			emf = getEMF(DBMS.MySQL);
			jpa = new DoJPA(emf);
			jpa.startPersistence();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jpa.close();
		}

		System.out.println("\n\nYou can test this Class using JShell\n\n    jshell\n"
				+ "    /classpath target/myJshellAppLib.jar\n" + "    import br.com.joaoparana.*\n"
				+ "    App app = new App()\n" + "    String[] args = {\"JPA\"}\n" + "    App.main(args)\n");

		/*
		 * Guice.createInjector() takes your Modules, and returns a new Injector
		 * instance. Most applications will call this method exactly once, in
		 * their main() method.
		 */
		Injector injector = Guice.createInjector(new BillingModule(LogTo.Console));

		/*
		 * Now that we've got the injector, we can build objects.
		 */
		BillingService billingService = injector.getInstance(BillingService.class);
		// ...
	}

	public EntityManagerFactory getEMF(DBMS dbms) throws UnknownHostException {
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

class CreateFile {
	public static void doIt(String[] args) {
		try {
			File file = new File("newfile.txt");
			if (file.createNewFile()) {
				System.out.println("File is created!");
			} else {
				System.out.println("File already exists.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class WriteFileOutputStream {
	public void doIt(String[] args) {
		File file = new File("newfile.txt");
		String content = "This is the text content";
		try (FileOutputStream fop = new FileOutputStream(file)) {
			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			// get the content in bytes
			byte[] contentInBytes = content.getBytes();
			fop.write(contentInBytes);
			fop.flush();
			fop.close();
			System.out.println("Done");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class BufferedInputStreamReader {
	public void doIt(String[] args) {
		File file = new File("newfile.txt");
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		DataInputStream dis = null;
		try {
			fis = new FileInputStream(file);
			bis = new BufferedInputStream(fis);
			dis = new DataInputStream(bis);
			while (dis.available() != 0) {
				System.out.println(dis.readLine());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
				bis.close();
				dis.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}

class Help {
	public void doIt(String[] args) {
		String msg = "Comandos Suportados:" + "\nHELP\n" + "CREATE_FILE\n" + "WRITE_FILE\n" + "READ_FILE\n" + "JDBC\n"
				+ "OPEN\n" + "JPA";
		System.out.println("\n" + msg);
	}
}
