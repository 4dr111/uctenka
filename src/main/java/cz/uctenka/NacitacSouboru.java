package cz.uctenka;

public class NacitacSouboru {
    // Načítání dat ze souborů (statická část účtenky).
    // Metody:
    //   - nactiProvozovnu(cesta) -> Provozovna   (soubor provozovna.properties, třída java.util.Properties)
    //   - nactiMenu(cesta) -> Menu               (soubor menu.csv, řádek po řádku, split podle ";")
    // Ošetřit:
    //   - soubor neexistuje
    //   - prázdný řádek / komentář (#) / řádek se špatným počtem sloupců
    //   - cena, která není číslo
    //   - kódování UTF-8 (kvůli češtině!)
    // Rozhodnout v týmu: číst soubory z resources (zabalené v .jar), nebo z cesty vedle .jar
    // (pak si je hospoda může upravit bez nové kompilace - to je asi lepší).
}
