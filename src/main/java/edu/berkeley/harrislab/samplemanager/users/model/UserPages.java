package edu.berkeley.harrislab.samplemanager.users.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "pagesvisited", catalog = "samplemanager")
public class UserPages {

	private int id;
	private UserSistema usuario;
	private Date visitDate;
	private String visitKey;
	
	
	public UserPages() {
		super();
	}
	
	
	public UserPages(UserSistema usuario, Date visitDate, String visitKey) {
		super();
		this.usuario = usuario;
		this.visitDate = visitDate;
		this.visitKey = visitKey;
	}

	@Id
	@GenericGenerator(name="idautoinc2" , strategy="increment")
	@GeneratedValue(generator="idautoinc2")
	@Column(name="id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	@ManyToOne(optional=false)
	@JoinColumn(name="username")
	@ForeignKey(name = "userPages_FK")
	public UserSistema getUsuario() {
		return usuario;
	}

	public void setUsuario(UserSistema usuario) {
		this.usuario = usuario;
	}

	@Column(name = "visitDate")
	public Date getVisitDate() {
		return visitDate;
	}
	public void setVisitDate(Date loginDate) {
		this.visitDate = loginDate;
	}
	@Column(name = "visitKey", nullable = true, length =50)
	public String getVisitKey() {
		return visitKey;
	}


	public void setVisitKey(String visitKey) {
		this.visitKey = visitKey;
	}
	@Override
	public String toString(){
		return usuario.toString();
	}
}