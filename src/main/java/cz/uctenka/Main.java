package cz.uctenka;

import java.io.Console;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    public static final String NAZEV = "Uctenka";
    public static final String VERZE = "1.1.0";

    public static void main(String[] args) {
        if (!jeTerminal()) {
            System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out), true, StandardCharsets.UTF_8));
            System.setErr(new PrintStream(new FileOutputStream(FileDescriptor.err), true, StandardCharsets.UTF_8));
        }

        System.out.println(NAZEV + " " + VERZE);
        System.out.println();

        if (args.length > 2 || (args.length == 1 && (args[0].equals("-h") || args[0].equals("--help")))) {
            System.out.println("Pouziti: java -jar uctenka.jar [podnik.properties] [menu.csv]");
            System.out.println("Bez parametru se pouziji ukazkove soubory zabalene v programu.");
            if (args.length > 2) {
                System.exit(1);
            }
            return;
        }

        Podnik podnik;
        Menu menu;
        try {
            podnik = args.length >= 1 ? Podnik.zeSouboru(Path.of(args[0])) : Podnik.vychozi();
            menu = args.length >= 2 ? Menu.zeSouboru(Path.of(args[1])) : Menu.vychozi();
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Chyba pri nacitani souboru: " + e.getMessage());
            System.exit(1);
            return;
        }

        System.out.print(menu.vytvorVypis());
        System.out.println();
        System.out.println("Zadejte cislo polozky a Enter (napr. 2), nebo cislo a pocet (napr. 2 3).");
        System.out.println("H = hotovo, vytisknout uctenku.");

        Uctenka uctenka = new Uctenka(podnik, menu);
        uctenka.nactiNakup(new Scanner(System.in));

        if (uctenka.jePrazdna()) {
            System.out.println("Nic jste nenakoupili, uctenka se netiskne.");
            return;
        }

        System.out.println();
        System.out.print(uctenka.vytvorText());
    }

    private static boolean jeTerminal() {
        Console konzole = System.console();
        if (konzole == null) {
            return false;
        }
        try {
            return (Boolean) Console.class.getMethod("isTerminal").invoke(konzole);
        } catch (ReflectiveOperationException e) {
            return true;
        }
    }
}
