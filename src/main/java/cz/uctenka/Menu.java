package cz.uctenka;

public class Menu {
    // Seznam jídel a pití s cenami, načtený ze souboru menu.csv (formát: nazev;cena).
    //   - uložení třeba jako Map<String, BigDecimal> (název -> cena)
    //     (LinkedHashMap zachová pořadí ze souboru)
    //
    // Metody:
    //   - konstruktor / nactiZeSouboru(cesta) - přečte soubor řádek po řádku, split podle ";"
    //   - getCena(nazev) - cena položky (co když položka neexistuje?)
    //   - obsahuje(nazev) - je položka v menu?
    //   - vypisMenu() - aby uživatel věděl, z čeho vybírat (klidně s čísly 1, 2, 3...)
    //
    // Ošetřit: soubor neexistuje, prázdné řádky a komentáře (#), cena není číslo, kódování UTF-8.
    // Ceny ne v double (počítá nepřesně), ale BigDecimal nebo v haléřích jako int.
}
