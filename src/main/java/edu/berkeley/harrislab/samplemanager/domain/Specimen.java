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



@Entity
@Table(name = "specimens", catalog = "samplemanager", uniqueConstraints = @UniqueConstraint(columnNames = "specimenId"))
public class Specimen extends BaseMetaData implements Auditable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	private String systemId;
	private String specimenId;
	private Date labReceiptDate;
	private String specimenType;
	private String specimenCondition;
	private Float volume;
	private String volUnits;
	private String parentSpecimenId;
	
	private Subject subjectId;
	
	private String inStorage;
	private String orthocode;
	private Integer varA;
	private Integer varB;
	private String obs;
	
	public Specimen() {
		super();
	}
	public Specimen(Date recordDate, String recordUser, String recordIp, char pasive) {
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
	
	@Column(name = "specimenId", nullable = false, length = 50)
	public String getSpecimenId() {
		return specimenId;
	}
	
	public void setSpecimenId(String specimenId) {
		this.specimenId = specimenId;
	}
	
	@ManyToOne(optional=true)
	@JoinColumn(name="subjectId")
	@ForeignKey(name = "specimen_FK1")
	public Subject getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Subject subjectId) {
		this.subjectId = subjectId;
	}
	@Column(name = "labReceiptDate", nullable = false)
	public Date getLabReceiptDate() {
		return labReceiptDate;
	}
	public void setLabReceiptDate(Date labReceiptDate) {
		this.labReceiptDate = labReceiptDate;
	}
	@Column(name = "specimenType", nullable = false, length = 50)
	public String getSpecimenType() {
		return specimenType;
	}
	public void setSpecimenType(String specimenType) {
		this.specimenType = specimenType;
	}
	@Column(name = "specimenCondition", nullable = true, length = 50)
	public String getSpecimenCondition() {
		return specimenCondition;
	}
	public void setSpecimenCondition(String specimenCondition) {
		this.specimenCondition = specimenCondition;
	}
	@Column(name = "volume", nullable = true)
	public Float getVolume() {
		return volume;
	}
	public void setVolume(Float volume) {
		this.volume = volume;
	}
	@Column(name = "volUnits", nullable = true, length = 50)
	public String getVolUnits() {
		return volUnits;
	}
	public void setVolUnits(String volUnits) {
		this.volUnits = volUnits;
	}
	@Column(name = "parentSpecimenId", nullable = true, length = 50)
	public String getParentSpecimenId() {
		return parentSpecimenId;
	}
	public void setParentSpecimenId(String parentSpecimenId) {
		this.parentSpecimenId = parentSpecimenId;
	}
	
	
	@Column(name = "inStorage", nullable = false, length = 5)
	public String getInStorage() {
		return inStorage;
	}
	public void setInStorage(String inStorage) {
		this.inStorage = inStorage;
	}
	
	
	@Column(name = "orthocode", nullable = true, length = 50)
	public String getOrthocode() {
		return orthocode;
	}
	public void setOrthocode(String orthocode) {
		this.orthocode = orthocode;
	}
	
	@Column(name = "obs", nullable = true, length = 500)
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}
	
	
	
	@Column(name = "varA", nullable = true)
	public Integer getVarA() {
		return varA;
	}
	public void setVarA(Integer varA) {
		this.varA = varA;
	}
	@Column(name = "varB", nullable = true)
	public Integer getVarB() {
		return varB;
	}
	public void setVarB(Integer varB) {
		this.varB = varB;
	}
	@Override
	public String toString(){
		return specimenId;
	}
	
	
	
	@Override
	public boolean equals(Object other) {
		
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Study))
			return false;
		
		Specimen castOther = (Specimen) other;

		return (this.getSpecimenId().equals(castOther.getSpecimenId()));
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
