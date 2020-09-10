package edu.berkeley.harrislab.samplemanager.service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.berkeley.harrislab.samplemanager.domain.Specimen;

/**
 * Servicio para el objeto Specimen
 * 
 * @author William Aviles
 * 
 **/

@Service("specimenService")
@Transactional
public class SpecimenService {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	
	/**
	 * Regresa todos los registros
	 * 
	 * @return una lista de <code>Specimen</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Specimen> getSpecimens() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Specimen");
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Regresa todos los registros activos
	 * 
	 * @return una lista de <code>Specimen</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Specimen> getActiveSpecimens() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Specimen specimen where specimen.pasive = '0'");
		// Retrieve all
		return  query.list();
	}
	
	
	/**
	 * Regresa un registro
	 * @param systemId
	 * @return un <code>Specimen</code>
	 */

	public Specimen getSpecimenBySystemId(String systemId) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Specimen specimen where " +
				"specimen.systemId =:systemId");
		query.setParameter("systemId",systemId);
		Specimen specimen = (Specimen) query.uniqueResult();
		return specimen;
	}
	
	/**
	 * Regresa un registro
	 * @param id. 
	 * @return un <code>Specimen</code>
	 */

	public Specimen getSpecimenByUserId(String specimenId) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Specimen specimen where " +
				"specimen.specimenId =:specimenId");
		query.setParameter("specimenId",specimenId);
		Specimen specimen = (Specimen) query.uniqueResult();
		return specimen;
	}

	
	/**
	 * Guarda un registro
	 * 
	 */
	public void saveSpecimen(Specimen specimen) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(specimen);
	}

}
