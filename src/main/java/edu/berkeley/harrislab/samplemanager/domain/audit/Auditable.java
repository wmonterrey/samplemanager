package edu.berkeley.harrislab.samplemanager.domain.audit;

/**
 * Auditable es la interface que todas las clases deben implementar para ser parte de la auditoria en el sistema.
 *  
 * @author      William Aviles
 * @version     1.0
 * @since       1.0
 */

public interface Auditable {
	
	public boolean isFieldAuditable(String fieldname);

}
