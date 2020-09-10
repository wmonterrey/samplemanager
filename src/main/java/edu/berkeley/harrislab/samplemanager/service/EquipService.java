package edu.berkeley.harrislab.samplemanager.service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.berkeley.harrislab.samplemanager.domain.Equip;

/**
 * Servicio para el objeto Equip
 * 
 * @author William Aviles
 * 
 **/

@Service("equipService")
@Transactional
public class EquipService {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	
	/**
	 * Regresa todos los registros
	 * 
	 * @return una lista de <code>Equip</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Equip> getEquips() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Equip");
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Regresa todos los registros activos
	 * 
	 * @return una lista de <code>Equip</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<Equip> getActiveEquips() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM Equip equip where equip.pasive = '0' order by equip.id");
		// Retrieve all
		return  query.list();
	}
	
	
	/**
	 * Regresa un registro
	 * @param systemId
	 * @return un <code>Equip</code>
	 */

	public Equip getEquipBySystemId(String systemId) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Equip equip where " +
				"equip.systemId =:systemId");
		query.setParameter("systemId",systemId);
		Equip equip = (Equip) query.uniqueResult();
		return equip;
	}
	
	/**
	 * Regresa un registro
	 * @param id. 
	 * @return un <code>Equip</code>
	 */

	public Equip getEquipByUserId(String id) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Equip equip where " +
				"equip.id =:id");
		query.setParameter("id",id);
		Equip equip = (Equip) query.uniqueResult();
		return equip;
	}

	
	/**
	 * Guarda un registro
	 * 
	 */
	public void saveEquip(Equip equip) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(equip);
	}

}
