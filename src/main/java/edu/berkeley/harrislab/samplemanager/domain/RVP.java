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



@Entity
@Table(name = "rvps", catalog = "samplemanager", uniqueConstraints = @UniqueConstraint(columnNames = "rvpId"))
public class RVP extends BaseMetaData implements Auditable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	private String systemId;
	private String rvpId;
	private String batchName;
	private String virusSerotype;
	private String source;
	private Date date;
	private Float amount;
	private String amountUnits;
	private Integer noAliquots;
	private String diltuionRvp;
	private String rateInfection;
	private String cellType;
	private String timePostInf;
	private Float amountRetit;
	private String amountRetitUnits;
	private String obs;
	
	public RVP() {
		super();
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
	
	@Column(name = "rvpId", nullable = false, length = 50)
	public String getRvpId() {
		return rvpId;
	}
	public void setRvpId(String rvpId) {
		this.rvpId = rvpId;
	}
	@Column(name = "batchName", nullable = false, length = 50)
	public String getBatchName() {
		return batchName;
	}
	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}
	@Column(name = "virusSerotype", nullable = false, length = 50)
	public String getVirusSerotype() {
		return virusSerotype;
	}
	public void setVirusSerotype(String virusSerotype) {
		this.virusSerotype = virusSerotype;
	}
	@Column(name = "source", nullable = false, length = 50)
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	@Column(name = "date", nullable = false)
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Column(name = "amount", nullable = true)
	public Float getAmount() {
		return amount;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	@Column(name = "amountUnits", nullable = true, length = 50)
	public String getAmountUnits() {
		return amountUnits;
	}
	public void setAmountUnits(String amountUnits) {
		this.amountUnits = amountUnits;
	}
	@Column(name = "noAliquots", nullable = true)
	public Integer getNoAliquots() {
		return noAliquots;
	}
	public void setNoAliquots(Integer noAliquots) {
		this.noAliquots = noAliquots;
	}
	@Column(name = "diltuionRvp", nullable = true, length = 50)
	public String getDiltuionRvp() {
		return diltuionRvp;
	}
	public void setDiltuionRvp(String diltuionRvp) {
		this.diltuionRvp = diltuionRvp;
	}
	@Column(name = "rateInfection", nullable = true, length = 50)
	public String getRateInfection() {
		return rateInfection;
	}
	public void setRateInfection(String rateInfection) {
		this.rateInfection = rateInfection;
	}
	@Column(name = "cellType", nullable = true, length = 50)
	public String getCellType() {
		return cellType;
	}
	public void setCellType(String cellType) {
		this.cellType = cellType;
	}
	@Column(name = "timePostInf", nullable = true, length = 50)
	public String getTimePostInf() {
		return timePostInf;
	}
	public void setTimePostInf(String timePostInf) {
		this.timePostInf = timePostInf;
	}
	@Column(name = "amountRetit", nullable = true)
	public Float getAmountRetit() {
		return amountRetit;
	}
	public void setAmountRetit(Float amountRetit) {
		this.amountRetit = amountRetit;
	}
	@Column(name = "amountRetitUnits", nullable = true, length = 50)
	public String getAmountRetitUnits() {
		return amountRetitUnits;
	}
	public void setAmountRetitUnits(String amountRetitUnits) {
		this.amountRetitUnits = amountRetitUnits;
	}
	@Column(name = "obs", nullable = true, length = 500)
	public String getObs() {
		return obs;
	}
	public void setObs(String obs) {
		this.obs = obs;
	}
	

	@Override
	public String toString(){
		return rvpId;
	}
	
	
	
	@Override
	public boolean equals(Object other) {
		
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof RVP))
			return false;
		
		RVP castOther = (RVP) other;

		return (this.getRvpId().equals(castOther.getRvpId()));
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
