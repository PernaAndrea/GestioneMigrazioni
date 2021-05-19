package it.polito.tdp.borders.model;

public class Evento implements Comparable<Evento>{

	private int t;//riferimento al tempo T
	private Country country; //riferimento ad uno stato 
	private int n;//presone che arrivano al tempo t
	
	public Evento(int t, Country country, int n) {
		super();
		this.t = t;
		this.country = country;
		this.n = n;
	}

	public int getT() {
		return t;
	}

	public void setT(int t) {
		this.t = t;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	@Override
	public int compareTo(Evento o) {
		// TODO Auto-generated method stub
		//faccio l'ordinamento sul tempo t 
		return (this.t - o.t) ;
	}
	
	
}
