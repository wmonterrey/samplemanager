package edu.berkeley.harrislab.samplemanager.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * BaseMetaData es la clase que almacena la informacion de referencia acerca de los objetos.
 * 
 * BaseMetaData incluye informacion como:
 * 
 * <ul>
 * <li>Fecha del registro
 * <li>Usuario que registra
 * <li>Estado del registro
 * <li>Identificador del dispositivo
 * <li>Si el registro fue borrado
 * </ul>
 * 
 * 
 *  
 * @author      William Aviles
 * @version     1.0
 * @since       1.0
 */
@MappedSuperclass 
public class BaseMetaData implements Serializable 
{  


	private static final long serialVersionUID = 1L;
	private Date recordDate;
	private String recordUser;
	private String recordIp;
	private char pasive = '0';
	
	public BaseMetaData() {

	}
	
	
	
	public BaseMetaData(Date recordDate, String recordUser) {
		super();
		this.recordDate = recordDate;
		this.recordUser = recordUser;
	}



	public BaseMetaData(Date recordDate, String recordUser, String recordIp, char pasive) {
		super();
		this.recordDate = recordDate;
		this.recordUser = recordUser;
		this.recordIp = recordIp;
		this.pasive = pasive;
	}

	@Temporal( TemporalType.TIMESTAMP)
	@Column(name="recordDate")
	public Date getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}

	@Column(name="recordUser", length = 50)
	public String getRecordUser() {
		return recordUser;
	}

	public void setRecordUser(String recordUser) {
		this.recordUser = recordUser;
	}
	
	@Column(name="pasive", nullable = false, length = 1)
	public char getPasive() {
		return pasive;
	}

	public void setPasive(char pasive) {
		this.pasive = pasive;
	}

	@Column(name="recordIp", length = 50)
	public String getRecordIp() {
		return recordIp;
	}

	public void setRecordIp(String recordIp) {
		this.recordIp = recordIp;
	}
	

}  
