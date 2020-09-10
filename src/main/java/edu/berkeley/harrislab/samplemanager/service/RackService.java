package edu.berkeley.harrislab.samplemanager.service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.berkeley.harrislab.samplemanager.domain.Rack;

/**
 * Servicio para el objeto Rack
 * 
 * @author William Aviles
 * 
 **/

@Service("rackService")
@Transactional
public class RackService {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	
	/**
	 * Regresa todos los registros
	 * 
	 * @return una lista de <code>Rack</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Rack> getRacks() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Rack");
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Regresa todos los registros activos
	 * 
	 * @return una lista de <code>Rack</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Rack> getActiveRacks() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Rack rack where rack.pasive = '0'");
		// Retrieve all
		return  query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Rack> getRacks(String equipId) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Rack r where r.equip.systemId =:equipId and r.pasive = '0'  order by r.id");
		query.setParameter("equipId",equipId);
		return query.list();
	}
	
	
	/**
	 * Regresa un registro
	 * @param systemId
	 * @return un <code>Rack</code>
	 */

	public Rack getRackBySystemId(String systemId) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Rack rack where " +
				"rack.systemId =:systemId");
		query.setParameter("systemId",systemId);
		Rack rack = (Rack) query.uniqueResult();
		return rack;
	}
	
	/**
	 * Regresa un registro
	 * @param id. 
	 * @return un <code>Rack</code>
	 */

	public Rack getRackByUserId(String id) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Rack rack where " +
				"rack.id =:id");
		query.setParameter("id",id);
		Rack rack = (Rack) query.uniqueResult();
		return rack;
	}

	
	/**
	 * Guarda un registro
	 * 
	 */
	public void saveRack(Rack rack) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(rack);
	}

}
