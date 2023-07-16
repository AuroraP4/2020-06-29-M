package it.polito.tdp.imdb.model;

public class Arco {

	Integer regista1;
	Integer regista2;
	int nAttoriInComune;
	
	public Arco(Integer regista1, Integer regista2, int nAttoriInComune) {
		this.regista1 = regista1;
		this.regista2 = regista2;
		this.nAttoriInComune = nAttoriInComune;
	}

	public Integer getRegista1() {
		return regista1;
	}

	public void setRegista1(Integer regista1) {
		this.regista1 = regista1;
	}

	public Integer getRegista2() {
		return regista2;
	}

	public void setRegista2(Integer regista2) {
		this.regista2 = regista2;
	}

	public int getnAttoriInComune() {
		return nAttoriInComune;
	}

	public void setnAttoriInComune(int nAttoriInComune) {
		this.nAttoriInComune = nAttoriInComune;
	}
	
	
}
