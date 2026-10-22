package cz.uctenka;

public class TiskUctenky {
    // Poskládá účtenku z Provozovny (statické) a Objednávky (dynamické) a vypíše ji.
    // Doporučení: metoda vytvorUctenku(...) vrací String a teprve Main ho vypíše
    // -> String se dá snadno otestovat.
    // Vzhled, např.:
    //   ================================
    //          U Zlatého Tygra
    //       Husova 17, Praha 1
    //          IČO: 12345678
    //   --------------------------------
    //   2x Plzeň 0,5l             118,00
    //   1x Smažený sýr            189,00
    //   --------------------------------
    //   CELKEM                307,00 Kč
    //   22.10.2026 18:30
    //      Děkujeme za návštěvu!
    //   ================================
    // Tip: String.format("%-20s%10s", ...) pro zarovnání sloupců,
    //      dlouhé názvy položek zkrátit, ať se nerozbije zarovnání.
}
