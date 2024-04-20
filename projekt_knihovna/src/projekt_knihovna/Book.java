package projekt_knihovna;

import java.util.List;

public abstract class Book{
	private String name;
	private int rocnik;
	private boolean dostupnost;
	private List<String> autori;
	
	public Book(String name, List<String> autori, int rocnik, boolean dostupnost) {
		this.name=name;
		this.autori = autori;
		this.rocnik = rocnik;
		this.dostupnost = dostupnost;
	}
	
	public void zmenaDostupnosti() {
		dostupnost = !dostupnost;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRocnik() {
		return rocnik;
	}
	public void setRocnik(int rocnik) {
		this.rocnik = rocnik;
	}
	public boolean isDostupnost() {
		return dostupnost;
	}
	public void setDostupnost(boolean dostupnost) {
		this.dostupnost = dostupnost;
	}
	public List<String> getAutori() {
		return autori;
	}
	public void setAutori(List<String> autori) {
		this.autori = autori;
	}
	
	public abstract void getInfo();
	
}
