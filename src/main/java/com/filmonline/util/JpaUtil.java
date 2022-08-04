package com.filmonline.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JpaUtil {

	private static final EntityManagerFactory entityManagerFactory;

	static {
		entityManagerFactory = Persistence.createEntityManagerFactory("filmonline"); 
	}

	public static EntityManagerFactory getEntityManagerFactory() {
		return entityManagerFactory;

	}
}
