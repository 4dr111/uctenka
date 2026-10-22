package cz.uctenka;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Uctenka {
    public void pridejPolozku(String chleba, double v, int i) {
    }
    // Co si zákazník koupil + výpis účtenky.
    //   - Podnik a Menu (dostane v konstruktoru)
    //   - nakup: Map<String, Integer> (název položky -> počet kusů)
    //
    // Metody (rozdělit, ať se dají testovat!):
    //   - nactiNakup(Scanner) - ptá se uživatele, co si koupil (např. "Pivo 2"), dokud nezadá "konec"
    //       Scanner dostat jako parametr, ne new Scanner(System.in) uvnitř -> v testu jde podstrčit text.
    //       Špatný vstup (neexistující položka, písmena místo počtu, záporný počet)
    //       -> vypsat hlášku a nechat zadat znovu, NESMÍ to spadnout.
    //   - pridejPolozku(nazev, pocet) - přidá do nakupu (stejná položka podruhé = navýšit počet)
    //   - spocitejCelkem() - součet cena * počet za všechny položky
    //   - vytvorText() - vrátí celou účtenku jako String (Main ji pak vypíše), např.:
    //       ================================
    //              U Zlatého Tygra
    //           Husova 17, Praha 1
    //              IČO: 12345678
    //       --------------------------------
    //       2x Pivo 0,5l              118,00
    //       1x Smažený sýr            189,00
    //       --------------------------------
    //       CELKEM                307,00 Kč
    //       22.10.2026 18:30
    //          Děkujeme za návštěvu!
    //       ================================
    //     Zarovnání přes String.format("%-20s%10s", ...), datum přes LocalDateTime.now().import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

    public class Uctenka {
        private Podnik podnik;
        private Menu menu;
        private Map<String, Integer> nakup;

        // Konstruktor
        public Uctenka(Podnik podnik, Menu menu) {
            this.podnik = podnik;
            this.menu = menu;
            this.nakup = new HashMap<>();
        }

        // Přidání položky - jednoduchý if / else
        public void pridejPolozku(String nazev, int pocet) {
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
            text = text + "         IČO: " + podnik.getIco() + "\n";
            text = text + "--------------------------------\n";

            for (String nazev : nakup.keySet()) {
                int pocet = nakup.get(nazev);
                double cenaSpolu = menu.getCena(nazev) * pocet;

                String radek = pocet + "x " + nazev;

                // Tady použijeme String.format, jak bylo v původním zadání
                text = text + String.format("%-22s%10.2f\n", radek, cenaSpolu);
            }

            text = text + "--------------------------------\n";
            text = text + String.format("%-20s%9.2f Kč\n", "CELKEM", spocitejCelkem());

            // Aktuální čas
            LocalDateTime ted = LocalDateTime.now();
            DateTimeFormatter formatCasu = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            text = text + ted.format(formatCasu) + "\n";

            text = text + "     Děkujeme za návštěvu!\n";
            text = text + "================================\n";

            return text;
        }
    }
}
