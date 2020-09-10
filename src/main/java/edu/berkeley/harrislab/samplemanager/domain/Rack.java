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
 * Simple objeto de dominio que representa un rack
 * 
 * @author William Aviles
 **/

@Entity
@Table(name = "racks", catalog = "samplemanager", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Rack extends BaseMetaData implements Auditable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String systemId;
	private String id;
	private String name;
	private Equip equip;
	private String obs;
	
	

	public Rack() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Rack(Date recordDate, String recordUser, String recordIp, char pasive) {
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
	
	@ManyToOne(optional=false)
	@JoinColumn(name="equip")
	@ForeignKey(name = "rack_FK1")
	public Equip getEquip() {
		return equip;
	}
	public void setEquip(Equip equip) {
		this.equip = equip;
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
		if (!(other instanceof Rack))
			return false;
		
		Rack castOther = (Rack) other;

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

