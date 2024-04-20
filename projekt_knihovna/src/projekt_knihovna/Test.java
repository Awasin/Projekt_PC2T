package projekt_knihovna;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test {

	public static int pouzeCelaCisla(Scanner sc) 
	{
		int cislo = 0;
		try
		{
			cislo = sc.nextInt();
		}
		catch(Exception e)
		{
			System.out.println("Nastala vyjimka typu "+e.toString());
			System.out.println("zadejte prosim cele cislo ");
			sc.nextLine();
			cislo = pouzeCelaCisla(sc);
		}
		return cislo;
	}
	public static Zanr zanrRomanu(Scanner sc) {
		System.out.println("Zadejte žánr románu.");
		System.out.println("1...Autobiografický");
		System.out.println("2...Klíčkový");
		System.out.println("3...Detektivní");
		System.out.println("4...Scifi");
		System.out.println("5...Utopický");
		switch (pouzeCelaCisla(sc)) {
		case 1:
			return Zanr.AUTOBIOGRAFICKY;
		case 2:
			return Zanr.KLICOVY;
		case 3:
			return Zanr.DETEKTIVNI;
		case 4:
			return Zanr.SCIFI;
		case 5:
			return Zanr.UTOPICKY;
		default:
			System.out.println("Neplatná volba.");
			return zanrRomanu(sc);
		}
	}
	public static boolean pouzeAnoNe(Scanner sc) {
		String odpoved = sc.next();
		if(odpoved.equalsIgnoreCase("Ano")) return true;
		else if(odpoved.equalsIgnoreCase("Ne")) return false;
		else {
			System.out.println("Neplatná odpověď, očekáváno Ano nebo Ne");
			return pouzeAnoNe(sc);
		}
	}
	public static List<String> scanAutors(Scanner sc){
		System.out.println("Kolik autorů má kniha? Po-té zadejte autory.");
		List<String> autori = new ArrayList<>();
		for(int i = pouzeCelaCisla(sc); i > 0; i--) {
			autori.add(sc.next() + sc.nextLine());
		}
		return autori;
	}
	
	public static void main(String[] args) {
		int choice,choice_2;
		boolean run = true;
		String name;
		
		Scanner sc = new Scanner(System.in);
		
		//SQL inicializace
		Knihovna knihovna = new Knihovna();
		while(run) {
			System.out.println("Vitejte u knihovni databaze. Zadejte celé číslo pro:");
			System.out.println("1...Přidání knihy");
			System.out.println("2...Úprava existující knihy");
			System.out.println("3...Smazání knihy");
			System.out.println("4...Vypůjčení/vrácení knihy");
			System.out.println("5...Výpis všech knih v databázi");
			System.out.println("6...Vyhledání knihy dle názvu");
			System.out.println("7...Výpis autorových děl");
			System.out.println("8...Výpis knih daného žánru");
			System.out.println("9...Výpis vypůjčených knih");
			System.out.println("10..Export knihy do souboru");
			System.out.println("11..Import knihy ze souboru");
			System.out.println("12..Exit");
			choice = pouzeCelaCisla(sc);
			switch(choice) {
			case 1:
				System.out.println("Pro přidání románu stiskněte 1, pro přidání učebnice stiskněte 2.");
				choice_2 = pouzeCelaCisla(sc);
				if((choice_2 != 1) && (choice_2 != 2)) {
					System.out.println("Neplatná volba.");
					break;
				}
				System.out.println("Zadejte celý název knihy.");
				name = sc.next() + sc.nextLine();
				System.out.println("Zadejte rok vydání.");
				int rocnik = pouzeCelaCisla(sc);
				List<String> autori = new ArrayList<>();
				autori = scanAutors(sc);
				Book kniha;
				if(choice_2 == 1) {
					kniha = new Roman(name, autori, rocnik, zanrRomanu(sc), true);
				} else {
					System.out.println("Zadejte doporučený ročník učebnice.");
					int doporucen = pouzeCelaCisla(sc);
					kniha = new Ucebnice(name, autori, rocnik, doporucen, true);
				}
				knihovna.pridejKnihu(kniha);
				break;
			case 2:
				System.out.println("Úprava knihy, zadejte jméno knihy");
				name = sc.next() + sc.nextLine();
				System.out.println("1...Změna autorů");
				System.out.println("2...Změna ročníku");
				System.out.println("3...Změna dostupnosti");
				choice_2 = pouzeCelaCisla(sc);
				boolean found;
				switch(choice_2) {
				case 1:
					found = knihovna.zmenaKnihy(name, scanAutors(sc));
					break;
				case 2:
					System.out.println("Zadejte nový ročník knihy");
					int rocnik_2 = pouzeCelaCisla(sc);
					found = knihovna.zmenaKnihy(name, rocnik_2);
					break;
				case 3:
					System.out.println("Je kniha dostupná? (Ano/Ne)");
					found = knihovna.zmenaKnihy(name, pouzeAnoNe(sc));
					break;
				default:
					found = false;
					break;
				}
				if(found) System.out.println("Kniha nalezena a změněna.");
				else System.out.println("Kniha neexistuje, nebo neplatná volba.");
				break;
			case 3:
				System.out.println("Mazání knihy. Zadejte jméno knihy");
				name = sc.next() + sc.nextLine();
				if(knihovna.odstranKnihu(name))System.out.println("Odstranění úspěšné.");
				else System.out.println("Kniha nenalezena.");
				break;
			case 4:
				System.out.println("Změna dostupnosti. Kniha byla vyúpůjčena/vrácena. Zadejte jméno:");
				name = sc.next() + sc.nextLine();
				if(knihovna.changeDostupnost(name))System.out.println("Vypůjčení/vrácení úspěšné.");
				else System.out.println("Kniha nenalezena.");
				break;
			case 5:
				knihovna.vypisKnih();
				break;
			case 6:
				System.out.println("Vyhledání knihy dle jména. Zadejte jméno:");
				name = sc.next() + sc.nextLine();
				if(!knihovna.najdiDleNazvu(name))System.out.println("Kniha nenalezena.");
				break;
			case 7:
				System.out.println("Zadejte jméno autora, jehož díla mají být vypsána.");
				name = sc.next() + sc.nextLine();
				if(!knihovna.vypisAutora(name))System.out.println("Díla autora nenalezena.");
				break;
			case 8:
				knihovna.vypisDleZanru(zanrRomanu(sc));
				break;
			case 9:
				knihovna.vypisVypujcenych();
				break;
			case 10:
				System.out.println("Zadejte jméno knihy pro export.");
				name = sc.next() + sc.nextLine();
				if(!knihovna.exportBook(name))System.out.println("Export knihy selhal.");
				else System.out.println("Export úspěšný.");
				break;
			case 11:
				System.out.println("Zadejte jméno souboru pro import.");
				name = sc.next() + sc.nextLine();
				if(knihovna.importBook(name))System.out.println("Import proběhl úspěšně.");
				else System.out.println("Import selhal.");
				break;
			case 69:
				knihovna.pridejKnihu(new Roman("Zaklínač", new ArrayList<String>() {{
				    add("Andrzej Szapovski");
				}}, 2002, Zanr.SCIFI, true));
				knihovna.pridejKnihu(new Roman("Petrova dobrodružství", new ArrayList<String>() {{
				    add("Petr Sadílek");
				    add("Petr");
				    add("Náhodný Petr");
				}}, 2015, Zanr.AUTOBIOGRAFICKY, true));
				knihovna.pridejKnihu(new Ucebnice("Fyzika pro děti", new ArrayList<String>() {{
				    add("Andrzej Szapovski");
				    add("Náhodný Petr");
				}}, 2002, 22, true));
				break;
			default:
				System.out.println("Neplatná volba.");
				break;
			}
		}
	}
}
