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
 * Simple objeto de dominio que representa una caja
 * 
 * 
 * @author William Aviles
 **/

@Entity
@Table(name = "boxes", catalog = "samplemanager", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Box extends BaseMetaData implements Auditable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String systemId;
	private String id;
	private String name;
	private Rack rack;
	private Integer rows;
	private Integer columns;
	private Integer capacity;
	private String obs;
	
	

	public Box() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Box(Date recordDate, String recordUser, String recordIp, char pasive) {
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
	@JoinColumn(name="rack")
	@ForeignKey(name = "box_FK1")
	public Rack getRack() {
		return rack;
	}
	public void setRack(Rack rack) {
		this.rack = rack;
	}
	
	
	@Column(name = "rows", nullable = false)
	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	@Column(name = "columns", nullable = false)
	public Integer getColumns() {
		return columns;
	}

	public void setColumns(Integer columns) {
		this.columns = columns;
	}
	
	@Column(name = "capacity", nullable = false)
	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
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
		if (!(other instanceof Box))
			return false;
		
		Box castOther = (Box) other;

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

