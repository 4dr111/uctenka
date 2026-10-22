package cz.uctenka;

public class VstupUzivatele {
    // Čtení objednávky od uživatele z příkazové řádky (java.util.Scanner).
    // Návrh průběhu:
    //   - vypíše se menu
    //   - uživatel zadá kód položky a počet (např. "3 2" = položka 3, dva kusy)
    //   - opakuje se, dokud nezadá prázdný řádek / "konec"
    // Ošetřit:
    //   - nesmyslný vstup (písmena místo čísla), neexistující kód, záporný/nulový počet
    //     -> vypsat hlášku a nechat zadat znovu, program NESMÍ spadnout
    // Tip pro testy: Scanner předávejte v konstruktoru (ne new Scanner(System.in) uvnitř),
    // pak jde v testu podstrčit vstup jako String.
}
