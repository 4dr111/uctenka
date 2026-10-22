package cz.uctenka;

public class Uctenka {
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
    //     Zarovnání přes String.format("%-20s%10s", ...), datum přes LocalDateTime.now().
}
