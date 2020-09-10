package edu.berkeley.harrislab.samplemanager.service;

import java.util.Date;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.berkeley.harrislab.samplemanager.users.model.UserPages;
import edu.berkeley.harrislab.samplemanager.users.model.UserSistema;




/**
 * 
 * @author William Aviles
 * 
 **/

@Service("visitsService")
@Transactional
public class VisitsService {
	
	@Resource(name="sessionFactory")
	private SessionFactory sessionFactory;
	
	
	/**
	 * Guarda una pagina visitada
	 * 
	 * 
	 */
	public void saveUserPages(UserSistema usuario, Date visitDate, String visitKey) {
		Session session = sessionFactory.getCurrentSession();
		UserPages up = new UserPages(usuario,visitDate,visitKey);
		session.saveOrUpdate(up);
	}

}
