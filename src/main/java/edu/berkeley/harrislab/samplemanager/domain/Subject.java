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
@Table(name = "subjects", catalog = "samplemanager", uniqueConstraints = @UniqueConstraint(columnNames = {"subjectId","studyId"}))
public class Subject extends BaseMetaData implements Auditable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	private String systemId;
	private String subjectId;
	private Study studyId;
	private Date enrollmentDate;
	
	public Subject() {
		super();
	}
	public Subject(Date recordDate, String recordUser, String recordIp, char pasive) {
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
	
	@Column(name = "subjectId", nullable = false, length = 50)
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	
	@ManyToOne(optional=false)
	@JoinColumn(name="studyId")
	@ForeignKey(name = "subject_FK1")
	public Study getStudyId() {
		return studyId;
	}
	public void setStudyId(Study studyId) {
		this.studyId = studyId;
	}
	
	@Column(name = "enrollmentDate", nullable = true)
	public Date getEnrollmentDate() {
		return enrollmentDate;
	}
	public void setEnrollmentDate(Date enrollmentDate) {
		this.enrollmentDate = enrollmentDate;
	}
	@Override
	public String toString(){
		return subjectId;
	}
	@Override
	public boolean equals(Object other) {
		
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof Study))
			return false;
		
		Subject castOther = (Subject) other;

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
