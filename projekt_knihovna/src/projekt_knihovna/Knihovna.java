package projekt_knihovna;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Knihovna {
private List<Book> knihy;
	public Knihovna(){
		if(!SQL_stuff.getInstance().connect())System.out.println("fail");
		knihy = new ArrayList<>(SQL_stuff.getInstance().databasePull());
		SQL_stuff.getInstance().disconnect();
	}
	
	public void pridejKnihu(Book kniha) {
		knihy.add(kniha);
	}
	
	public boolean odstranKnihu(String name) {
		return knihy.removeIf(book -> (book.getName().equalsIgnoreCase(name)));
	}
	
	public boolean changeDostupnost(String name) {
		for(Book kniha:knihy) {
			if(kniha.getName().equalsIgnoreCase(name)) {
				kniha.zmenaDostupnosti();
				return true;
			}
		}
		return false;
	}
	
	public boolean zmenaKnihy(String name, List<String> autori) {
		for(Book kniha:knihy) {
			if(kniha.getName().equalsIgnoreCase(name)) {
				kniha.setAutori(autori);
				return true;
			}
		}
		return false;
	}
	
	public boolean zmenaKnihy(String name, int rocnik) {
		for(Book kniha:knihy) {
			if(kniha.getName().equalsIgnoreCase(name)) {
				kniha.setRocnik(rocnik);
				return true;
			}
		}
		return false;
	}
	
	public boolean zmenaKnihy(String name,boolean dostupnost) {
		for(Book kniha:knihy) {
			if(kniha.getName().equalsIgnoreCase(name)) {
				kniha.setDostupnost(dostupnost);
				return true;
			}
		}
		return false;
	}
	
	public void vypisKnih() {
		Collections.sort(knihy, Comparator.comparing(b -> b.getName()));
		for(Book kniha:knihy) {
			kniha.getInfo();
		}
	}
	
	public boolean najdiDleNazvu(String name) {
		for(Book kniha:knihy) {
			if(kniha.getName().equalsIgnoreCase(name)) {
				kniha.getInfo();
				return true;
			}
		}
		return false;
	}
	
	public boolean vypisAutora(String autor) {
		boolean anythin = false;
		List<Book> autorova_dila = new ArrayList<>();
		for(Book kniha:knihy) {
			for(String autorek:kniha.getAutori()) {
				if(autorek.equalsIgnoreCase(autor)) {
					autorova_dila.add(kniha);
				}
			}
		}
		Collections.sort(autorova_dila, Comparator.comparing(b -> b.getRocnik()));
		for(Book kniha:autorova_dila) {
			kniha.getInfo();
			anythin = true;
		}
		return anythin;
	}
	
	public void vypisDleZanru(Zanr zanr) {
		for(Book kniha:knihy) {
			if(((Roman)kniha).getZanr() == zanr) kniha.getInfo();
		}
		System.out.println("Toť vše.");
	}
	
	public void vypisVypujcenych() {
		for(Book kniha:knihy) {
			if(!kniha.isDostupnost()) {
				System.out.print(kniha.getName() + "\t");
				if(kniha instanceof Roman) {
					System.out.println("Román.");
				} else {
					System.out.println("Učebnice");
				}
			}
		}
		System.out.println("Toť vše.");
	}
	
	public boolean exportBook(String name) {
		try {
			FileWriter fw = new FileWriter(name);
			BufferedWriter out = new BufferedWriter(fw);
			for(Book kniha:knihy) {
				if(kniha.getName().equalsIgnoreCase(name)) {
					if(kniha instanceof Ucebnice) {
						out.write("2;"+((Ucebnice)kniha).getDopor_rocnik());
					}
					else {
						out.write("1;"+((Roman)kniha).getZanr());
					}
					out.write(";" + kniha.getName() + ";" + kniha.getRocnik() + ";");
					if(kniha.isDostupnost())out.write("1;");
					else out.write("2;");
					List<String> autori = kniha.getAutori();
					for(String autor:autori) {
						out.write(autor + ":");
					}
					out.write("end");
					break;
				}
			}
			out.close();
			fw.close();
		}
		catch (IOException e) {
			return false;
		}
		return true;
	}
	
	public boolean importBook(String name) {
		FileReader fr=null;
		BufferedReader in=null;
		try {
			fr = new FileReader(name);
			in = new BufferedReader(fr);
			String radek=in.readLine();
			String oddelovac = "[;]+";
			String[] castiTextu = radek.split(oddelovac);
			if(castiTextu.length != 6)return false;
			String oddelovac_2 = "[:]+";
			String[] autori = castiTextu[5].split(oddelovac_2);
			if(!autori[autori.length-1].equalsIgnoreCase("end")) return false;
			List<String> autor_list = new ArrayList<String>(Arrays.asList(autori).subList(0, autori.length - 2));
			if(Integer.parseInt(castiTextu[0]) == 1) {
				knihy.add(new Roman(castiTextu[2],autor_list, Integer.parseInt(castiTextu[3]),
						Zanr.valueOf(castiTextu[1]),(Integer.parseInt(castiTextu[4]) == 1)));
			}
			else if(Integer.parseInt(castiTextu[0]) == 2) {
				knihy.add(new Ucebnice(castiTextu[2],autor_list, Integer.parseInt(castiTextu[3]),
						Integer.parseInt(castiTextu[1]),(Integer.parseInt(castiTextu[4]) == 1)));
			}
		} catch (IOException e) {
			System.out.println("Soubor  nelze otevrit");
			return false;
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			System.out.println("Chyba integrity dat v souboru");
			return false;
		} catch (IllegalArgumentException e) {
			System.out.println("Neplatný žánr.");
			return false;
		} finally {
			try {	
				if (in!=null) {
					in.close();
					fr.close();
				}
			}
			catch (IOException e) {
				System.out.println("Soubor  nelze zavrit");
				return false;
			} 
		}
		return true;
	}
	
	public void ulozKnihovnu() {
		SQL_stuff.getInstance().connect();
		SQL_stuff.getInstance().dropTables();
		SQL_stuff.getInstance().createTables();
		for(Book book:knihy) {
			SQL_stuff.getInstance().insertBook(book);
		}
		SQL_stuff.getInstance().disconnect();
	}
}
