package br.org.venturus.tambor.utils.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtils {	
	
	private static final EntityManagerFactory FACTORY = Persistence.createEntityManagerFactory("tambor");
	
	public static EntityManager getEntityManager() {
		return FACTORY.createEntityManager();
	}

}
