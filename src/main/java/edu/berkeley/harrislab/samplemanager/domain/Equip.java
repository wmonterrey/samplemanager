package edu.berkeley.harrislab.samplemanager.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.GenericGenerator;

import edu.berkeley.harrislab.samplemanager.domain.audit.Auditable;

/**
 * Simple objeto de dominio que representa un equipo de almacenamiento
 * 
 * 
 * @author William Aviles
 **/

@Entity
@Table(name = "equips", catalog = "samplemanager", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Equip extends BaseMetaData implements Auditable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String systemId;
	private String id;
	private String name;
	private String type;
	private Lab lab;
	private String obs;
	
	

	public Equip() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Equip(Date recordDate, String recordUser, String recordIp, char pasive) {
		super(recordDate, recordUser, recordIp, pasive);
		// TODO Auto-generated constructor stub
	}
	
	@Id
    @Column(name = "systemId", nullable = false, length = 36)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
	public String getSystemId() {
		return systemId;
	}
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	
	@Column(name = "id", nullable = false, length =50)
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Column(name = "name", nullable = true, length =150)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	@Column(name = "type", nullable = false, length =50)
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@ManyToOne(optional=false)
	@JoinColumn(name="lab")
	@ForeignKey(name = "equip_FK1")
	public Lab getLab() {
		return lab;
	}
	public void setLab(Lab lab) {
		this.lab = lab;
	}
	
	@Column(name = "obs", nullable = true, length =500)
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}
	
	@Override
	public String toString(){
		return name;
	}
	
	@Override
	public boolean equals(Object other) {
		
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Equip))
			return false;
		
		Equip castOther = (Equip) other;

		return (this.getSystemId().equals(castOther.getSystemId()));
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

