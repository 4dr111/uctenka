package cz.uctenka;

import java.util.Scanner;

public class Main {

    // název a verze programu (verzi držet stejnou jako <version> v pom.xml)
    public static final String NAZEV = "Uctenka";
    public static final String VERZE = "0.1.0";

    public static void main(String[] args) {
        System.out.println(NAZEV + " " + VERZE);
        System.out.println();

        try {
            Podnik podnik = new Podnik("podnik.properties");
            Menu menu = new Menu();

            System.out.println("Menu:");
            menu.vypis();
            System.out.println();

            Uctenka uctenka = new Uctenka(podnik, menu);
            Scanner scanner = new Scanner(System.in);
            uctenka.nactiNakup(scanner);

            System.out.println();
            System.out.println(uctenka.vytvorText());
        } catch (Exception e) {
            // uživateli srozumitelná hláška místo stacktrace
            System.out.println("Chyba: " + e.getMessage());
        }
    }
}
