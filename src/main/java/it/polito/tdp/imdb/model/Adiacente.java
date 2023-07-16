package it.polito.tdp.imdb.model;

public class Adiacente implements Comparable<Adiacente>{

	Director d;
	double nAttori;
	
	public Adiacente(Director d, double nAttori) {
		this.d = d;
		this.nAttori = nAttori;
	}

	public Director getD() {
		return d;
	}

	public void setD(Director d) {
		this.d = d;
	}

	public double getnAttori() {
		return nAttori;
	}

	public void setnAttori(double nAttori) {
		this.nAttori = nAttori;
	}

	@Override
	public int compareTo(Adiacente o) {
		return (int) -(nAttori - o.getnAttori());
	}
	
}