package projekt_knihovna;

import java.util.List;

public class Ucebnice extends Book{
private int dopor_rocnik;

	public Ucebnice(String name, List<String> autori, int rocnik, int dopor_rocnik, boolean dostupnost) {
		super(name, autori, rocnik, dostupnost);
		this.setDopor_rocnik(dopor_rocnik);
	}

	@Override
	public void getInfo() {
		System.out.print("Titul: " + getName() + "\t" + "Autoři: " + String.join(", ", getAutori()) +
				"\t" + "Rok vydání: " + getRocnik() + "\t" + "Doporučený ročník: " + dopor_rocnik);
		if(isDostupnost()) {System.out.println(" Dostupná.");}
		else {System.out.println(" Vypůjčena.");}
	}

	public int getDopor_rocnik() {
		return dopor_rocnik;
	}

	public void setDopor_rocnik(int dopor_rocnik) {
		this.dopor_rocnik = dopor_rocnik;
	}
	
}
