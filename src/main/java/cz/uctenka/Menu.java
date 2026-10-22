package cz.uctenka;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Menu {

    // název položky -> cena v Kč (LinkedHashMap drží pořadí, v jakém jsou položky přidané)
    private final Map<String, Integer> polozky = new LinkedHashMap<>();

    // nazevSouboru = soubor v src/main/resources (např. "menu.csv"), řádek = nazev;cena
    public Menu(String nazevSouboru) throws IOException {
        InputStream input = Menu.class.getResourceAsStream("/" + nazevSouboru);
        if (input == null) {
            throw new IOException("Soubor " + nazevSouboru + " nebyl nalezen");
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
            String radek;
            int cisloRadku = 0;
            while ((radek = reader.readLine()) != null) {
                cisloRadku++;
                radek = radek.trim();
                // prázdné řádky a komentáře přeskočit
                if (radek.isEmpty() || radek.startsWith("#")) {
                    continue;
                }

                String[] casti = radek.split(";");
                if (casti.length != 2) {
                    throw new IOException("Spatny format na radku " + cisloRadku + " v " + nazevSouboru);
                }

                int cena;
                try {
                    cena = Integer.parseInt(casti[1].trim());
                } catch (NumberFormatException e) {
                    throw new IOException("Spatna cena na radku " + cisloRadku + " v " + nazevSouboru);
                }
                if (cena < 0) {
                    throw new IOException("Zaporna cena na radku " + cisloRadku + " v " + nazevSouboru);
                }

                polozky.put(casti[0].trim(), cena);
            }
        }

        if (polozky.isEmpty()) {
            throw new IOException("Menu v " + nazevSouboru + " je prazdne");
        }
    }

    // je položka v menu?
    public boolean obsahuje(String nazev) {
        return polozky.containsKey(nazev);
    }

    // cena položky (před voláním ověřit přes obsahuje(), jinak vrátí null)
    public Integer getCena(String nazev) {
        return polozky.get(nazev);
    }

    // název položky podle čísla z menu (číslováno od 1), nebo null, když takové číslo není
    public String getNazev(int cislo) {
        List<String> nazvy = new ArrayList<>(polozky.keySet());
        if (cislo < 1 || cislo > nazvy.size()) {
            return null;
        }
        return nazvy.get(cislo - 1);
    }

    // vypíše menu s čísly, aby uživatel věděl, co může zadat
    public void vypis() {
        int cislo = 1;
        for (Map.Entry<String, Integer> polozka : polozky.entrySet()) {
            System.out.println(cislo + " " + polozka.getKey() + " - " + polozka.getValue() + " Kc");
            cislo++;
        }
    }
}
