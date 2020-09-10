package edu.berkeley.harrislab.samplemanager.users.dao;

import org.springframework.security.core.Authentication;

import edu.berkeley.harrislab.samplemanager.users.model.UserAttempts;

/**
 * Auditable es la interface para implementar los datos de usuario de spring.
 *  
 * @author      William Aviles
 * @version     1.0
 * @since       1.0
 */
public interface UserDetailsDao {

	void updateFailAttempts(String username);

	void resetFailAttempts(String username);
	
	void insertNewAccess(Authentication auth);
	
	void updateAccessDateLogout(String username, String idSesion, String direccionIp);
	
	void updateAccessUrl(String username, String idSesion, String direccionIp, String url);
	
	void checkCredentialsDate(String username);
	
	UserAttempts getUserAttempts(String username);

}