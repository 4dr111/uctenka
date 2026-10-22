package cz.uctenka;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Uctenka {
    public static final int SIRKA = 40;
    public static final int MAX_POCET = 999;

    private final Podnik podnik;
    private final Menu menu;
    private final Map<String, Integer> nakup = new LinkedHashMap<>();

    public Uctenka(Podnik podnik, Menu menu) {
        this.podnik = podnik;
        this.menu = menu;
    }

    public void nactiNakup(Scanner scanner) {
        while (scanner.hasNextLine()) {
            String vstup = scanner.nextLine().replace("﻿", "").trim();

            if (vstup.equalsIgnoreCase("H")) {
                return;
            }
            if (vstup.isEmpty()) {
                continue;
            }

            String[] casti = vstup.split("\\s+");
            if (casti.length > 2) {
                System.out.println("Neplatny vstup, zadejte cislo polozky (a pripadne pocet) nebo H.");
                continue;
            }

            int cislo;
            int pocet = 1;
            try {
                cislo = Integer.parseInt(casti[0]);
                if (casti.length == 2) {
                    pocet = Integer.parseInt(casti[1]);
                }
            } catch (NumberFormatException e) {
                System.out.println("Neplatny vstup, zadejte cislo polozky (a pripadne pocet) nebo H.");
                continue;
            }

            String nazev = menu.getNazev(cislo);
            if (nazev == null) {
                System.out.println("Polozka s cislem " + cislo + " v menu neni.");
                continue;
            }

            try {
                pridejPolozku(nazev, pocet);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                continue;
            }
            System.out.println("Pridano: " + pocet + "x " + nazev);
        }
    }

    public boolean jePrazdna() {
        return nakup.isEmpty();
    }

    public void pridejPolozku(String nazev, int pocet) {
        if (!menu.obsahuje(nazev)) {
            throw new IllegalArgumentException("Polozka neni v menu: " + nazev);
        }
        if (pocet <= 0) {
            throw new IllegalArgumentException("Pocet musi byt kladny: " + pocet);
        }

        int novyPocet = nakup.getOrDefault(nazev, 0) + pocet;
        if (pocet > MAX_POCET || novyPocet > MAX_POCET) {
            throw new IllegalArgumentException("Maximalni pocet jedne polozky je " + MAX_POCET + ".");
        }
        nakup.put(nazev, novyPocet);
    }

    public int getPocet(String nazev) {
        return nakup.getOrDefault(nazev, 0);
    }

    public BigDecimal spocitejCelkem() {
        BigDecimal celkem = BigDecimal.ZERO;
        for (Map.Entry<String, Integer> polozka : nakup.entrySet()) {
            celkem = celkem.add(menu.getCena(polozka.getKey()).multiply(BigDecimal.valueOf(polozka.getValue())));
        }
        return celkem;
    }

    public String vytvorText() {
        return vytvorText(LocalDateTime.now());
    }

    public String vytvorText(LocalDateTime cas) {
        StringBuilder text = new StringBuilder();
        String dvojitaCara = "=".repeat(SIRKA) + "\n";
        String cara = "-".repeat(SIRKA) + "\n";

        text.append(dvojitaCara);
        pridejNaStred(text, podnik.getNazev());
        pridejNaStred(text, podnik.getAdresa());
        pridejNaStred(text, "ICO: " + podnik.getIco());
        text.append(cara);

        for (Map.Entry<String, Integer> polozka : nakup.entrySet()) {
            String nazev = polozka.getKey();
            int pocet = polozka.getValue();
            BigDecimal cenaZaKus = menu.getCena(nazev);
            BigDecimal cenaSpolu = cenaZaKus.multiply(BigDecimal.valueOf(pocet));

            pridejRadek(text, nazev, Menu.formatuj(cenaSpolu));
            if (pocet > 1) {
                text.append("  ").append(pocet).append(" x ").append(Menu.formatuj(cenaZaKus)).append("\n");
            }
        }

        text.append(cara);
        pridejRadek(text, "CELKEM", Menu.formatuj(spocitejCelkem()) + " Kc");
        text.append(cara);
        text.append(cas.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"))).append("\n");
        if (!podnik.getPaticka().isEmpty()) {
            text.append("\n");
            pridejNaStred(text, podnik.getPaticka());
        }
        text.append(dvojitaCara);

        return text.toString();
    }

    private static void pridejNaStred(StringBuilder text, String obsah) {
        for (String radek : zalom(obsah, SIRKA)) {
            text.append(" ".repeat((SIRKA - radek.length()) / 2)).append(radek).append("\n");
        }
    }

    private static void pridejRadek(StringBuilder text, String vlevo, String vpravo) {
        int sirkaVlevo = Math.max(1, SIRKA - vpravo.length() - 1);
        List<String> radky = zalom(vlevo, sirkaVlevo);
        for (int i = 0; i < radky.size() - 1; i++) {
            text.append(radky.get(i)).append("\n");
        }
        String posledni = radky.get(radky.size() - 1);
        int mezery = Math.max(1, SIRKA - posledni.length() - vpravo.length());
        text.append(posledni).append(" ".repeat(mezery)).append(vpravo).append("\n");
    }

    static List<String> zalom(String obsah, int sirka) {
        List<String> radky = new ArrayList<>();
        StringBuilder radek = new StringBuilder();
        for (String slovo : obsah.trim().split("\\s+")) {
            while (slovo.length() > sirka) {
                if (radek.length() > 0) {
                    radky.add(radek.toString());
                    radek.setLength(0);
                }
                radky.add(slovo.substring(0, sirka));
                slovo = slovo.substring(sirka);
            }
            if (radek.length() > 0 && radek.length() + 1 + slovo.length() > sirka) {
                radky.add(radek.toString());
                radek.setLength(0);
            }
            if (radek.length() > 0) {
                radek.append(' ');
            }
            radek.append(slovo);
        }
        if (radek.length() > 0 || radky.isEmpty()) {
            radky.add(radek.toString());
        }
        return radky;
    }
}
