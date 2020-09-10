package edu.berkeley.harrislab.samplemanager.service;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.berkeley.harrislab.samplemanager.domain.SpecimenStorage;

/**
 * Servicio para el objeto SpecimenStorage
 * 
 * @author William Aviles
 * 
 **/

@Service("specimenStorageService")
@Transactional
public class SpecimenStorageService {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	
	/**
	 * Regresa todos los registros
	 * 
	 * @return una lista de <code>SpecimenStorage</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<SpecimenStorage> getSpecimenStorages() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM SpecimenStorage");
		// Retrieve all
		return  query.list();
	}
	
	/**
	 * Regresa todos los registros activos
	 * 
	 * @return una lista de <code>SpecimenStorage</code>(s)
	 */

	@SuppressWarnings("unchecked")
	public List<SpecimenStorage> getActiveSpecimenStorages() {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM SpecimenStorage specimenStorage where specimenStorage.pasive = '0'");
		// Retrieve all
		return  query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<SpecimenStorage> getSpecimenForBox(String boxId) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		// Create a Hibernate query (HQL)
		Query query = session.createQuery("FROM SpecimenStorage specimenStorage where specimenStorage.box.systemId =:boxId");
		query.setParameter("boxId",boxId);
		// Retrieve all
		return  query.list();
	}
	
	
	/**
	 * Regresa un registro
	 * @param systemId
	 * @return un <code>SpecimenStorage</code>
	 */

	public SpecimenStorage getSpecimenStorageBySystemId(String storageId) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM SpecimenStorage specimenStorage where " +
				"specimenStorage.storageId =:storageId");
		query.setParameter("storageId",storageId);
		SpecimenStorage specimenStorage = (SpecimenStorage) query.uniqueResult();
		return specimenStorage;
	}
	
	/**
	 * Regresa un registro
	 * @param id. 
	 * @return un <code>SpecimenStorage</code>
	 */

	public SpecimenStorage getSpecimenStorageBySpecId(String specimen) {
		// Retrieve session from Hibernate
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM SpecimenStorage specimenStorage where " +
				"specimenStorage.specimen.systemId =:specimen");
		query.setParameter("specimen",specimen);
		SpecimenStorage specimenStorage = (SpecimenStorage) query.uniqueResult();
		return specimenStorage;
	}

	
	/**
	 * Guarda un registro nuevo
	 * 
	 */
	public void saveSpecimenStorage(SpecimenStorage specimenStorage) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(specimenStorage.getSpecimen());
		session.saveOrUpdate(specimenStorage);
	}
	
	
	/**
	 * Guarda un registro editado
	 * 
	 */
	public void updateSpecimenStorage(SpecimenStorage specimenStorage) {
		Session session = sessionFactory.getCurrentSession();
		session.update(specimenStorage);
	}
	
	
	/**
	 * Eliminar un registro
	 * 
	 */
	public void deleteSpecimenStorage(SpecimenStorage specimenStorage) {
		Session session = sessionFactory.getCurrentSession();
		session.delete(specimenStorage);
	}


}
