package edu.berkeley.harrislab.samplemanager.language;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import edu.berkeley.harrislab.samplemanager.domain.BaseMetaData;
import edu.berkeley.harrislab.samplemanager.domain.audit.Auditable;


/**
 * MessageResource es la clase que representa el registro de catalogos y mensajes en el sistema.
 * 
 * MessageResource incluye informacion como:
 * 
 * <ul>
 * <li>Clave del mensaje
 * <li>Catalogo al que pertenece
 * <li>Clave del catalogo
 * <li>Valor en espanol
 * <li>Valor en ingles
 * </ul>
 * <p>
 * 
 * 
 *  
 * @author      William Aviles
 * @version     1.0
 * @since       1.0
 */
@Entity
@Table(name = "messages", catalog = "samplemanager", uniqueConstraints={@UniqueConstraint(columnNames = {"catRoot" , "catKey"})})
public class MessageResource extends BaseMetaData implements Auditable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private String messageKey;
    private String catRoot;
    private String catKey;
    private char isCat = '0';
    private int order=0;
    private String spanish;
    private String english;
    
    public MessageResource() {

	}

    @Id
    @Column(name = "messageKey", nullable = false, length = 100)
    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }
    
    @Column(name = "catRoot", nullable = true, length = 50)
    public String getCatRoot() {
		return catRoot;
	}

	public void setCatRoot(String catRoot) {
		this.catRoot = catRoot;
	}

	@Column(name = "catKey", nullable = true, length = 50)
    public String getCatKey() {
		return catKey;
	}

	public void setCatKey(String catKey) {
		this.catKey = catKey;
	}
	
	@Column(name="isCat", nullable = false, length = 1)
	public char getIsCat() {
		return isCat;
	}

	public void setIsCat(char isCat) {
		this.isCat = isCat;
	}
	
	@Column(name="orden", nullable = false)
	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@Column(name = "en", nullable = true, length = 255)
    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    
    @Column(name = "es", nullable = true, length = 255)
    public String getSpanish() {
		return spanish;
	}

	public void setSpanish(String spanish) {
		this.spanish = spanish;
	}
	
	@Override
	public String toString(){
		return messageKey;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageResource that = (MessageResource) o;

        if (messageKey != null ? !messageKey.equals(that.messageKey) : that.messageKey != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return messageKey != null ? messageKey.hashCode() : 0;
    }
    
	@Override
	public boolean isFieldAuditable(String fieldname) {
		//Campos no auditables en la tabla
		if(fieldname.matches("recordDate")||fieldname.matches("recordUser")){
			return false;
		}
		return true;
	}

	

}
