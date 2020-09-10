package edu.berkeley.harrislab.samplemanager.domain;

import java.util.List;


public class BoxAliquots {
	
	private Box box;
	private int disponibles ;
	private int primeraDisponible ;
	private List<SpecimenStorage> aliquots;
	private String mensaje;
	
	public BoxAliquots() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BoxAliquots(Box box, int disponibles) {
		super();
		this.box = box;
		this.disponibles = disponibles;
	}
	public Box getBox() {
		return box;
	}
	public void setBox(Box box) {
		this.box = box;
	}
	
	public int getDisponibles() {
		return disponibles;
	}
	public void setDisponibles(int disponibles) {
		this.disponibles = disponibles;
	}
	
	public int getPrimeraDisponible() {
		return primeraDisponible;
	}
	public void setPrimeraDisponible(int primeraDisponible) {
		this.primeraDisponible = primeraDisponible;
	}
	public List<SpecimenStorage> getAliquots() {
		return aliquots;
	}
	public void setAliquots(List<SpecimenStorage> aliquots) {
		this.aliquots = aliquots;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	

}
