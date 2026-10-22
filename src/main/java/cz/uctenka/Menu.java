package cz.uctenka;

import java.util.LinkedHashMap;
import java.util.Map;

public class Menu {

    // název položky -> cena v Kč (LinkedHashMap drží pořadí, v jakém jsou položky přidané)
    private final Map<String, Integer> polozky = new LinkedHashMap<>();

    public Menu() {
        polozky.put("Cola", 30);
        polozky.put("Pivo", 45);
        polozky.put("Kofola", 35);
        polozky.put("Burger", 70);
        polozky.put("Hranolky", 40);
        polozky.put("Smazeny syr", 120);
    }

    // je položka v menu?
    public boolean obsahuje(String nazev) {
        return polozky.containsKey(nazev);
    }

    // cena položky (před voláním ověřit přes obsahuje(), jinak vrátí null)
    public Integer getCena(String nazev) {
        return polozky.get(nazev);
    }

    // vypíše menu, aby uživatel věděl, co může zadat
    public void vypis() {
        for (Map.Entry<String, Integer> polozka : polozky.entrySet()) {
            System.out.println(polozka.getKey() + " - " + polozka.getValue() + " Kč");
        }
    }
}
