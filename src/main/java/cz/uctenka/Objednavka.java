package cz.uctenka;

public class Objednavka {
    // DYNAMICKÉ údaje - co si zákazník konkrétně objednal.
    //   - List<PolozkaObjednavky>
    //   - datum a čas objednávky (LocalDateTime.now())
    //   - případně číslo účtenky / číslo stolu
    // Metody:
    //   - pridej(polozka, pocet) - když už tam položka je, ideálně jen navýšit počet
    //   - celkovaCena() - součet všech řádků
    //   - jePrazdna() - nemá smysl tisknout prázdnou účtenku
}
