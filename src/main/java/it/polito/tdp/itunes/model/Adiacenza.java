package it.polito.tdp.itunes.model;

public class Adiacenza implements Comparable<Adiacenza>{
	
	Album a;
	int bilancio;
	
	public Adiacenza(Album a, int bilancio) {
		this.a = a;
		this.bilancio = bilancio;
	}

	@Override
	public int compareTo(Adiacenza o) {
		return -(this.bilancio-o.bilancio);
	}

	public Album getA() {
		return a;
	}

	public int getBilancio() {
		return bilancio;
	}

	@Override
	public String toString() {
		return a.toString() + "   " + this.getBilancio();
	}
	
	
	
	

}
