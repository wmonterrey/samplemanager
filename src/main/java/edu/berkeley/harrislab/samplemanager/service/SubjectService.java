package edu.berkeley.harrislab.samplemanager.service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.berkeley.harrislab.samplemanager.domain.Subject;

/**
 * Servicio para el objeto Subject
 * 
 * @author William Aviles
 * 
 **/

@Service("subjectService")
@Transactional
public class SubjectService {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	
	/**
	 * Regresa todos los registros
	 * 
	 * @return una lista de <code>Subject</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Subject> getSubjects() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Subject");
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Regresa todos los registros activos
	 * 
	 * @return una lista de <code>Subject</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Subject> getActiveSubjects() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Subject subject where subject.pasive = '0' order by subject.subjectId");
		// Retrieve all
		return  query.list();
	}
	
	
	/**
	 * Regresa un registro
	 * @param systemId
	 * @return un <code>Subject</code>
	 */

	public Subject getSubjectBySystemId(String systemId) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Subject subject where " +
				"subject.systemId =:systemId");
		query.setParameter("systemId",systemId);
		Subject subject = (Subject) query.uniqueResult();
		return subject;
	}
	
	/**
	 * Regresa un registro
	 * @param id. 
	 * @return un <code>Subject</code>
	 */

	public Subject getSubjectByUserId(String subjectId, String studyId) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Subject subject where " +
				"subject.subjectId =:subjectId and subject.studyId.id =:studyId");
		query.setParameter("subjectId",subjectId);
		query.setParameter("studyId",studyId);
		Subject subject = (Subject) query.uniqueResult();
		return subject;
	}

	
	/**
	 * Guarda un registro
	 * 
	 */
	public void saveSubject(Subject subject) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(subject);
	}

}
