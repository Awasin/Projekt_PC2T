package projekt_knihovna;

import java.util.List;

public class Roman extends Book {
private Zanr zanr;

	public Roman(String name, List<String> autori, int rocnik, Zanr zanr, boolean dostupnost) {
		super(name, autori, rocnik, dostupnost);
		this.setZanr(zanr);
	}

	@Override
	public void getInfo() {
		System.out.print("Titul: " + getName() + "\t" + "Autoři: " + String.join(", ", getAutori()) +
				"\t" + "Rok vydání: " + getRocnik() + "\t" + "Žánr: " + zanr);
		if(isDostupnost()) {System.out.println(" Dostupná.");}
		else {System.out.println(" Vypůjčena.");}
	}
	
	public Zanr getZanr() {
		return zanr;
	}

	public void setZanr(Zanr zanr) {
		this.zanr = zanr;
	}
	

}
