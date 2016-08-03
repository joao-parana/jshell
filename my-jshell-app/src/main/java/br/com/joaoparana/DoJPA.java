package br.com.joaoparana;

import static org.eclipse.persistence.config.PersistenceUnitProperties.JDBC_URL;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

public class DoJPA {
	EntityManagerFactory emf;

	public DoJPA(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public DBMS startPersistence() throws Exception {
		String cmd = null;
		String url = null;
		if (this.emf.getProperties().containsKey(JDBC_URL)) {
			url = (String) this.emf.getProperties().get(JDBC_URL);
			if (url.startsWith("jdbc:mysql")) {
				cmd = "select 1000;";
			} else if (url.startsWith("jdbc:oracle")) {
				cmd = "select 1000 from dual;";
			}

			EntityManager em = getEntityManager();
			Query q = em.createNativeQuery(cmd);
			Long r = (Long) q.getSingleResult();
			System.out.println("The value returned must be 1000. Please verify -> " + r);
		}

		if (url.startsWith("jdbc:mysql")) {
			return DBMS.MySQL;
		} else {
			return DBMS.Oracle;
		}

	}

	public void close() {
		this.emf.close();
	}

	private EntityManager getEntityManager() {
		return this.emf.createEntityManager();
	}

	private Long execSql(EntityManager em, String cmd) {
		Long r = 0L;
		// By the way : nothing to do for now.
		// em.getTransaction().begin();
		// Query q = em.createNativeQuery(cmd);
		// Query query = em.createNativeQuery("SELECT SYSDATE FROM DUAL");
		// Date result = (Date)query.getSingleResult();
		// Query query = em.createNativeQuery("SELECT MAX(SALARY), MIN(SALARY)
		// FROM EMPLOYEE");
		// List<Object[]> results = query.getResultList();
		// int max = results.get(0)[0];
		// int min = results.get(0)[1];
		// q.executeUpdate();
		// r = (Long) q.getSingleResult();
		// em.getTransaction().commit();
		return r;
	}

}
