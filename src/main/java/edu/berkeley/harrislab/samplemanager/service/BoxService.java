package edu.berkeley.harrislab.samplemanager.service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.berkeley.harrislab.samplemanager.domain.Box;

/**
 * Servicio para el objeto Box
 * 
 * @author William Aviles
 * 
 **/

@Service("boxService")
@Transactional
public class BoxService {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	
	/**
	 * Regresa todos los registros
	 * 
	 * @return una lista de <code>Box</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Box> getBoxes() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Box");
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Regresa todos los registros activos
	 * 
	 * @return una lista de <code>Box</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Box> getActiveBoxes() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Box box where box.pasive = '0' order by box.id");
		// Retrieve all
		return  query.list();
	}
	
	
	/**
	 * Regresa todos los registros activos
	 * 
	 * @return una lista de <code>Box</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Box> getActiveBoxes(String rackId) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Box box where box.rack.systemId =:rackId and box.pasive = '0' order by box.id");
		query.setParameter("rackId",rackId);
		// Retrieve all
		return  query.list();
	}
	
	
	/**
	 * Regresa un registro
	 * @param systemId
	 * @return un <code>Box</code>
	 */

	public Box getBoxBySystemId(String systemId) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Box box where " +
				"box.systemId =:systemId");
		query.setParameter("systemId",systemId);
		Box box = (Box) query.uniqueResult();
		return box;
	}
	
	/**
	 * Regresa un registro
	 * @param id. 
	 * @return un <code>Box</code>
	 */

	public Box getBoxByUserId(String id) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Box box where " +
				"box.id =:id");
		query.setParameter("id",id);
		Box box = (Box) query.uniqueResult();
		return box;
	}

	
	/**
	 * Guarda un registro
	 * 
	 */
	public void saveBox(Box box) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(box);
	}

}
