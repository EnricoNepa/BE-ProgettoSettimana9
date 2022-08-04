package com.filmonline.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.filmonline.dao.FilmDao;
import com.filmonline.model.Film;
import com.filmonline.util.JpaUtil;

import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * @author Enrico Nepa
 * **/

public class FilmDaoImpl implements FilmDao{
	
	private EntityManager em = null;
	private EntityTransaction entityTransaction = null;

	public void em() {
		em = JpaUtil.getEntityManagerFactory().createEntityManager();
		entityTransaction = em.getTransaction();
	}
	
	/**
	 * @param Film è un attributo della classe {@link Film} 
	 * 
	 * utilizzato nel seguente metodo per salvare l'oggetto
	 * 
	 * nel DataBase
	 * */

	public void save(Film f) {
		em();
		try {

			/* Salviamo la entity Film */
			entityTransaction.begin();
			String incasso = BCrypt.hashpw(f.getIncasso(), BCrypt.gensalt());
			f.setIncasso(incasso);
			em.persist(f);
			entityTransaction.commit();

		} catch (Exception ex) {
			ex.printStackTrace();
			entityTransaction.rollback();
		} finally {
			em.close();
		}
		
	}
	
	/**
	 * @param regista è un attributo della classe {@link Film} 
	 * 
	 * utilizzato nel seguente metodo per recuperare la correlata
	 * 
	 * lista di Film riportando come ritorno
	 * 
	 * @return {@link List<Film>}
	 * */
	
	@SuppressWarnings("unchecked")
	public List<Film> findByRegista(String regista) {
		em();
		List<Film> f = new ArrayList<Film>();
		try {

			/* Cerchiamo la entity Film */
			entityTransaction.begin();
			Query q = em.createQuery("SELECT f FROM Film f WHERE f.regista LIKE :regista");
			q.setParameter("regista", regista);
			f = (List<Film>)q.getResultList();
			entityTransaction.commit();

		} catch (Exception ex) {
			ex.printStackTrace();
			entityTransaction.rollback();
		} finally {
			em.close();
		}
		
		return f;
	}

	
	/**
	 * @param Film è un attributo della classe {@link Film} 
	 * 
	 * utilizzato nel seguente metodo per aggiornare l'oggetto
	 * 
	 * nel DataBase
	 * */
	
	public void update(Film f) {
		em();
		try {

			/* Aggiorniamo la entity Film */
			String incassoCriptato= BCrypt.hashpw(f.getIncasso(),BCrypt.gensalt());
            f.setIncasso(incassoCriptato);
            em.getTransaction().begin();
            em.merge(f);
            em.getTransaction().commit();
			}			

		catch (Exception ex) {
			ex.printStackTrace();
			entityTransaction.rollback();
		} 
		
		finally {
			em.close();
		}
		
	}

	
	/**
	 * @param Film è un attributo della classe {@link Film} 
	 * 
	 * utilizzato nel seguente metodo per eliminare l'oggetto
	 * 
	 * nel DataBase
	 * */
	
	public void delete(Long id) {
		em();
		Film f = new Film();
		try {

			/* Eliminiamo la entity Film */
			entityTransaction.begin();
			f = em.find(Film.class,id);
			em.remove(f);
			entityTransaction.commit();

		} catch (Exception ex) {
			ex.printStackTrace();
			entityTransaction.rollback();
		} finally {
			em.close();
		}
		
	}

	
	/**
	 * @param id è un attributo della classe {@link Film} 
	 * 
	 * utilizzato nel seguente metodo per recuperare il correlato
	 * 
	 * Film riportando come ritorno
	 * 
	 * @return {@link Film}
	 * */
	
	public Film findById(Long id) {
		em();
		Film f = new Film();
		try {

			/* Cerchiamo la entity Cittadino */
			entityTransaction.begin();
			f = em.find(Film.class,id);
			entityTransaction.commit();

		} catch (Exception ex) {
			ex.printStackTrace();
			entityTransaction.rollback();
		} finally {
			em.close();
		}
		
		return f;
		
	}

	/**
	 * Recupera la lista completa di 
	 * 
	 * tutti i Film del DataBase
	 * 
	 * riportando come ritorno
	 * 
	 * @return {@link List<Film>}
	 * */
	
	@SuppressWarnings("unchecked")
	public List<Film> getAllFilms() {
		em();
		List<Film> f = new ArrayList<Film>();
		try {
			f = em.createQuery("SELECT f FROM Film f").getResultList();
			}
		catch(Exception ex) {
			ex.printStackTrace();
			entityTransaction.rollback();
		}
		finally {
			em.close();
		}
		
		return f;
	}
}
