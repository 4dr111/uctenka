package cz.uctenka;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Menu {

    private final Map<String, BigDecimal> polozky = new LinkedHashMap<>();

    public Menu(String obsah, String zdroj) throws IOException {
        String[] radky = obsah.split("\\R");
        for (int i = 0; i < radky.length; i++) {
            int cisloRadku = i + 1;
            String radek = radky[i].trim();
            if (radek.isEmpty() || radek.startsWith("#")) {
                continue;
            }

            String[] casti = radek.split(";", -1);
            if (casti.length != 2) {
                throw new IOException("Spatny format na radku " + cisloRadku + " v " + zdroj
                        + " (ma byt nazev;cena)");
            }

            String nazev = casti[0].trim();
            if (nazev.isEmpty()) {
                throw new IOException("Chybi nazev polozky na radku " + cisloRadku + " v " + zdroj);
            }
            if (polozky.containsKey(nazev)) {
                throw new IOException("Polozka " + nazev + " je v " + zdroj + " dvakrat (radek " + cisloRadku + ")");
            }

            polozky.put(nazev, nactiCenu(casti[1].trim(), cisloRadku, zdroj));
        }

        if (polozky.isEmpty()) {
            throw new IOException("Menu v " + zdroj + " je prazdne");
        }
    }

    public static Menu zeSouboru(Path cesta) throws IOException {
        return new Menu(Soubor.nactiSoubor(cesta), cesta.toString());
    }

    public static Menu vychozi() throws IOException {
        return new Menu(Soubor.nactiZProgramu("menu.csv"), "menu.csv");
    }

    private static BigDecimal nactiCenu(String text, int cisloRadku, String zdroj) throws IOException {
        BigDecimal cena;
        try {
            cena = new BigDecimal(text.replace(',', '.'));
        } catch (NumberFormatException e) {
            throw new IOException("Spatna cena na radku " + cisloRadku + " v " + zdroj);
        }
        if (cena.signum() < 0) {
            throw new IOException("Zaporna cena na radku " + cisloRadku + " v " + zdroj);
        }
        if (cena.stripTrailingZeros().scale() > 2) {
            throw new IOException("Cena na radku " + cisloRadku + " v " + zdroj + " ma vic nez 2 desetinna mista");
        }
        return cena.setScale(2, RoundingMode.UNNECESSARY);
    }

    public static String formatuj(BigDecimal castka) {
        return castka.setScale(2, RoundingMode.HALF_UP).toPlainString().replace('.', ',');
    }

    public boolean obsahuje(String nazev) {
        return polozky.containsKey(nazev);
    }

    public BigDecimal getCena(String nazev) {
        return polozky.get(nazev);
    }

    public int getPocet() {
        return polozky.size();
    }

    public String getNazev(int cislo) {
        List<String> nazvy = new ArrayList<>(polozky.keySet());
        if (cislo < 1 || cislo > nazvy.size()) {
            return null;
        }
        return nazvy.get(cislo - 1);
    }

    public String vytvorVypis() {
        StringBuilder text = new StringBuilder();
        int cislo = 1;
        for (Map.Entry<String, BigDecimal> polozka : polozky.entrySet()) {
            text.append(cislo).append("  ").append(polozka.getKey())
                    .append(" - ").append(formatuj(polozka.getValue())).append(" Kc\n");
            cislo++;
        }
        return text.toString();
    }
}
