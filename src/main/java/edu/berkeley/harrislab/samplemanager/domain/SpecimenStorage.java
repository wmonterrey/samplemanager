package edu.berkeley.harrislab.samplemanager.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.GenericGenerator;

import edu.berkeley.harrislab.samplemanager.domain.audit.Auditable;



@Entity
@Table(name = "storage", catalog = "samplemanager", uniqueConstraints = @UniqueConstraint(columnNames = {"box","pos"}))
public class SpecimenStorage extends BaseMetaData implements Auditable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	private String storageId;
	private Specimen specimen;
	private Date storageDate;
	private Date freezeTime;
	private Box box;
	private int pos;
	
	public SpecimenStorage() {
		super();
	}
	
	public SpecimenStorage(Date recordDate, String recordUser, String recordIp, char pasive) {
		super(recordDate, recordUser, recordIp, pasive);
		// TODO Auto-generated constructor stub
	}
	
	
	
	@Id
    @Column(name = "storageId", nullable = false, length = 36)
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
	public String getStorageId() {
		return storageId;
	}
	public void setStorageId(String storageId) {
		this.storageId = storageId;
	}
	
	
	@OneToOne(optional=false)
	@JoinColumn(name="specimen")
	@ForeignKey(name = "storage_FK1")
	public Specimen getSpecimen() {
		return specimen;
	}
	public void setSpecimen(Specimen specimen) {
		this.specimen = specimen;
	}
	
	@ManyToOne(optional=false)
	@JoinColumn(name="box")
	@ForeignKey(name = "storage_FK2")
	public Box getBox() {
		return box;
	}
	public void setBox(Box box) {
		this.box = box;
	}
	
	
	@Column(name = "storageDate", nullable = false)
	public Date getStorageDate() {
		return storageDate;
	}
	public void setStorageDate(Date storageDate) {
		this.storageDate = storageDate;
	}
	@Column(name = "freezeTime", nullable = true)
	public Date getFreezeTime() {
		return freezeTime;
	}
	public void setFreezeTime(Date freezeTime) {
		this.freezeTime = freezeTime;
	}
	@Column(name = "pos", nullable = false)
	public int getPos() {
		return pos;
	}
	public void setPos(int pos) {
		this.pos = pos;
	}
	@Override
	public String toString(){
		return specimen.getSpecimenId()+" , "+ box.getId() + " , "+ pos;
	}
	@Override
	public boolean equals(Object other) {
		
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Study))
			return false;
		
		SpecimenStorage castOther = (SpecimenStorage) other;

		return (this.getStorageId().equals(castOther.getStorageId()));
	}
	@Override
	public boolean isFieldAuditable(String fieldname) {
		//Campos no auditables en la tabla
		if(fieldname.matches("recordDate")||fieldname.matches("recordUser")||fieldname.matches("specimen")){
			return false;
		}
		return true;
	}
		

}
