package cz.uctenka;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class Uctenka {
    private Podnik podnik;
    private Menu menu;
    private Map<String, Integer> nakup;

    // Konstruktor
    public Uctenka(Podnik podnik, Menu menu) {
        this.podnik = podnik;
        this.menu = menu;
        // LinkedHashMap - položky zůstanou v pořadí, v jakém je zákazník zadal
        this.nakup = new LinkedHashMap<>();
    }

    // Čte čísla položek, dokud uživatel nezadá H (hotovo)
    public void nactiNakup(Scanner scanner) {
        while (scanner.hasNextLine()) {
            // odstraní neviditelný znak BOM, který Windows někdy pošle na začátek vstupu
            String vstup = scanner.nextLine().replace(String.valueOf((char) 0xFEFF), "").trim();

            if (vstup.equalsIgnoreCase("H")) {
                return;
            }
            if (vstup.isEmpty()) {
                continue;
            }

            int cislo;
            try {
                cislo = Integer.parseInt(vstup);
            } catch (NumberFormatException e) {
                System.out.println("Neplatny vstup, zadejte cislo polozky nebo H.");
                continue;
            }

            String nazev = menu.getNazev(cislo);
            if (nazev == null) {
                System.out.println("Polozka s cislem " + cislo + " v menu neni.");
                continue;
            }

            pridejPolozku(nazev, 1);
            System.out.println("Pridano: " + nazev);
        }
    }

    // Nemá smysl tisknout prázdnou účtenku
    public boolean jePrazdna() {
        return nakup.isEmpty();
    }

    // Přidání položky - jednoduchý if / else
    public void pridejPolozku(String nazev, int pocet) {
        if (!menu.obsahuje(nazev)) {
            throw new IllegalArgumentException("Polozka neni v menu: " + nazev);
        }
        if (pocet <= 0) {
            throw new IllegalArgumentException("Pocet musi byt kladny: " + pocet);
        }

        if (nakup.containsKey(nazev)) {
            int staryPocet = nakup.get(nazev);
            nakup.put(nazev, staryPocet + pocet);
        } else {
            nakup.put(nazev, pocet);
        }
    }

    // Výpočet celkové ceny - obyčejný for cyklus
    public double spocitejCelkem() {
        double celkem = 0;

        for (String nazev : nakup.keySet()) {
            int pocet = nakup.get(nazev);
            double cenaZaKus = menu.getCena(nazev);
            celkem = celkem + (cenaZaKus * pocet);
        }

        return celkem;
    }

    // Tvorba účtenky spojováním textu přes "+"
    public String vytvorText() {
        String text = "";

        text = text + "================================\n";
        // Mezery natvrdo pro zarovnání na střed
        text = text + "        " + podnik.getNazev() + "\n";
        text = text + "       " + podnik.getAdresa() + "\n";
        text = text + "         ICO: " + podnik.getIco() + "\n";
        text = text + "--------------------------------\n";

        for (String nazev : nakup.keySet()) {
            int pocet = nakup.get(nazev);
            double cenaSpolu = menu.getCena(nazev) * pocet;

            String radek = pocet + "x " + nazev;

            // Tady použijeme String.format, jak bylo v původním zadání
            text = text + String.format(Locale.ROOT, "%-22s%10.2f\n", radek, cenaSpolu);
        }

        text = text + "--------------------------------\n";
        text = text + String.format(Locale.ROOT, "%-20s%9.2f Kc\n", "CELKEM", spocitejCelkem());

        // Aktuální čas
        LocalDateTime ted = LocalDateTime.now();
        DateTimeFormatter formatCasu = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        text = text + ted.format(formatCasu) + "\n";

        text = text + "     " + podnik.getPaticka() + "\n";
        text = text + "================================\n";

        return text;
    }
}
