package edu.berkeley.harrislab.samplemanager.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import edu.berkeley.harrislab.samplemanager.domain.audit.Auditable;

/**
 * Simple objeto de dominio que representa un laboratorio
 * 
 * 
 * @author William Aviles
 **/

@Entity
@Table(name = "labs", catalog = "samplemanager", uniqueConstraints = @UniqueConstraint(columnNames = "labId"))
public class Lab extends BaseMetaData implements Auditable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String labSystemId;
	private String labId;
	private String labName;
	private String labContact;
	private String labAddress;
	private String labPhoneNumber;
	private String labEmail;
	private String labObs;
	
	public Lab() {
		super();
	}
	
	


	public Lab(Date recordDate, String recordUser, String recordIp, char pasive) {
		super(recordDate, recordUser, recordIp, pasive);
		// TODO Auto-generated constructor stub
	}




	@Id
    @Column(name = "labSystemId", nullable = false, length = 36)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
	public String getLabSystemId() {
		return labSystemId;
	}
	public void setLabSystemId(String labSystemId) {
		this.labSystemId = labSystemId;
	}
	
	@Column(name = "labId", nullable = false, length =50)
	public String getLabId() {
		return labId;
	}
	public void setLabId(String labId) {
		this.labId = labId;
	}
	
	@Column(name = "labName", nullable = true, length =150)
	public String getLabName() {
		return labName;
	}
	public void setLabName(String labName) {
		this.labName = labName;
	}
	
	@Column(name = "labContact", nullable = true, length =150)
	public String getLabContact() {
		return labContact;
	}
	public void setLabContact(String labContact) {
		this.labContact = labContact;
	}
	
	@Column(name = "labAddress", nullable = true, length =255)
	public String getLabAddress() {
		return labAddress;
	}
	public void setLabAddress(String labAddress) {
		this.labAddress = labAddress;
	}
	
	@Column(name = "labPhoneNumber", nullable = true, length =25)	
	public String getLabPhoneNumber() {
		return labPhoneNumber;
	}
	public void setLabPhoneNumber(String labPhoneNumber) {
		this.labPhoneNumber = labPhoneNumber;
	}
	
	@Column(name = "labEmail", nullable = true, length =50)
	public String getLabEmail() {
		return labEmail;
	}
	public void setLabEmail(String labEmail) {
		this.labEmail = labEmail;
	}
	
	@Column(name = "labObs", nullable = true, length =500)
	public String getLabObs() {
		return labObs;
	}
	
	public void setLabObs(String labObs) {
		this.labObs = labObs;
	}
	@Override
	public String toString(){
		return labName;
	}
	@Override
	public boolean equals(Object other) {
		
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Lab))
			return false;
		
		Lab castOther = (Lab) other;

		return (this.getLabSystemId().equals(castOther.getLabSystemId()));
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

