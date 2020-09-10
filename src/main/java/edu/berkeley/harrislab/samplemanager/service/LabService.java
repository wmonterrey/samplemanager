package edu.berkeley.harrislab.samplemanager.service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.berkeley.harrislab.samplemanager.domain.Lab;

/**
 * Servicio para el objeto Lab
 * 
 * @author William Aviles
 * 
 **/

@Service("labService")
@Transactional
public class LabService {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	
	/**
	 * Regresa todos los laboratorios
	 * 
	 * @return una lista de <code>Lab</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Lab> getLabs() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Lab");
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Regresa todos los laboratorios activos
	 * 
	 * @return una lista de <code>Lab</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Lab> getActiveLabs() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Lab lab where lab.pasive = '0'");
		// Retrieve all
		return  query.list();
	}
	
	
	/**
	 * Regresa un Laboratorio
	 * @param labSystemId Id del laboratorio. 
	 * @return un <code>Lab</code>
	 */

	public Lab getLabBySystemId(String labSystemId) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Lab lab where " +
				"lab.labSystemId =:labSystemId");
		query.setParameter("labSystemId",labSystemId);
		Lab lab = (Lab) query.uniqueResult();
		return lab;
	}
	
	/**
	 * Regresa un Laboratorio
	 * @param labId Id del laboratorio. 
	 * @return un <code>Lab</code>
	 */

	public Lab getLabByUserId(String labId) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Lab lab where " +
				"lab.labId =:labId");
		query.setParameter("labId",labId);
		Lab lab = (Lab) query.uniqueResult();
		return lab;
	}

	
	/**
	 * Guarda un laboratorio
	 * @param lab El laboratorio. 
	 * 
	 */
	public void saveLab(Lab lab) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(lab);
	}

}
