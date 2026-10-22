package cz.uctenka;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static final String NAZEV = "Uctenka";
    // držet stejnou jako <version> v pom.xml
    public static final String VERZE = "1.0.0";

    public static void main(String[] args) {
        System.out.println(NAZEV + " " + VERZE);
        System.out.println();

        Podnik podnik;
        Menu menu;
        try {
            podnik = new Podnik("podnik.properties");
            menu = new Menu("menu.csv");
        } catch (IOException e) {
            System.out.println("Chyba pri nacitani souboru: " + e.getMessage());
            return;
        }

        menu.vypis();
        System.out.println();
        System.out.println("Zadejte cislo polozky a Enter, H = hotovo.");

        Uctenka uctenka = new Uctenka(podnik, menu);
        Scanner scanner = new Scanner(System.in);
        uctenka.nactiNakup(scanner);

        if (uctenka.jePrazdna()) {
            System.out.println("Nic jste neobjednali, uctenka se netiskne.");
            return;
        }

        System.out.println();
        System.out.print(uctenka.vytvorText());
    }
}
