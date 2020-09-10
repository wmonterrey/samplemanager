package edu.berkeley.harrislab.samplemanager.service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.berkeley.harrislab.samplemanager.domain.Study;

/**
 * Servicio para el objeto Study
 * 
 * @author William Aviles
 * 
 **/

@Service("studyService")
@Transactional
public class StudyService {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	
	/**
	 * Regresa todos los registros
	 * 
	 * @return una lista de <code>Study</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Study> getStudys() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Study");
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Regresa todos los registros activos
	 * 
	 * @return una lista de <code>Study</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Study> getActiveStudys() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Study study where study.pasive = '0'");
		// Retrieve all
		return  query.list();
	}
	
	
	/**
	 * Regresa un registro
	 * @param systemId
	 * @return un <code>Study</code>
	 */

	public Study getStudyBySystemId(String systemId) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Study study where " +
				"study.systemId =:systemId");
		query.setParameter("systemId",systemId);
		Study study = (Study) query.uniqueResult();
		return study;
	}
	
	/**
	 * Regresa un registro
	 * @param id. 
	 * @return un <code>Study</code>
	 */

	public Study getStudyByUserId(String id) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Study study where " +
				"study.id =:id");
		query.setParameter("id",id);
		Study study = (Study) query.uniqueResult();
		return study;
	}

	
	/**
	 * Guarda un registro
	 * 
	 */
	public void saveStudy(Study study) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(study);
	}

}
