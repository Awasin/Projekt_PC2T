# Správa knihovny - Java Aplikace
## Obsah

+ [Zadání](#zadání)
+ [Instalace](#instalace)
+ [Použití](#použití)
  
Projekt do předmětu BPC-PC2T 2024.
Autor: Vojtěch Šolc (247181)
  
## Zadání
Tato aplikace umožňuje uživatelům spravovat knihovnu, která obsahuje romány a učebnice. Každá kniha má název, autora/autory, rok vydání a stav dostupnosti. Existují dva typy knih:

+ Romány – s parametrem žánru (existuje celkem 5 různých žánrů).
+ Učebnice – s informací pro jaký ročník jsou vhodné.

1. Přidání nové knihy: Uživatel vybere typ knihy a zadá všechny dostupné parametry.
2. Úprava knihy: Uživatel vybere knihu podle názvu a může upravit autora/autorů, rok vydání nebo stav dostupnosti.
3. Smazání knihy: Uživatel vybere knihu podle názvu a smaže ji ze seznamu.
4. Označení knihy jako vypůjčené/vrácené: Uživatel označí knihu jako vypůjčenou nebo vrácenou, což ovlivní stav dostupnosti.
5. Výpis knih: Uživatel může nechat vypsat všechny knihy v abecedním pořadí s informacemi jako název, autoři, žánr/ročník dle typu knihy, rok vydání a stav dostupnosti.
6. Vyhledání knihy: Uživatel zadá název knihy a zobrazí se mu veškeré informace o ní.
7. Výpis všech knih daného autora v chronologickém pořadí.
8. Výpis všech knih daného žánru: Uživatel zadá žánr a zobrazí se mu seznam knih patřících do tohoto žánru.
9. Uložení informace o vybrané knize do souboru: Podle názvu knihy.
10. Načtení všech informací o dané knize ze souboru (soubor obsahuje vždy jednu knihu).
11. Uložení informací do SQL databáze při ukončení programu.
12. Načtení informací z SQL databáze při spuštění programu.
Poznámka: SQL databáze se použije pouze při spuštění a ukončení programu.


## Instalace

1. Naklonujte tento repozitář na váš počítač.
2. Otevřete projekt ve vývojovém prostředí.
3. Spusťte aplikaci.

## Použití
1. Aplikace komunikuje pomocí konzole. Po jejím spuštění budete vyzváni k výběru akce z nabízených možností.
2. Postupujte podle pokynů na obrazovce pro provedení požadované operace. Konzole vás bude navigovat a ptát se na informace.
