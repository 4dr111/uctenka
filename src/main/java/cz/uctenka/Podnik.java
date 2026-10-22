package cz.uctenka;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class Podnik {
    private String nazev;
    private String adresa;
    private String ico;
    private String paticka;

    // nazevSouboru = soubor v src/main/resources (např. "podnik.properties"), je zabalený i v .jar
    public Podnik(String nazevSouboru) throws IOException {
        Properties prop = new Properties();

        try (InputStream input = Podnik.class.getResourceAsStream("/" + nazevSouboru)) {
            if (input == null) {
                throw new IOException("Soubor " + nazevSouboru + " nebyl nalezen");
            }
            // čtení v UTF-8, jinak se rozbije diakritika
            prop.load(new InputStreamReader(input, StandardCharsets.UTF_8));
        }

        this.nazev = nactiUdaj(prop, "nazev");
        this.adresa = nactiUdaj(prop, "adresa");
        this.ico = nactiUdaj(prop, "ico");
        this.paticka = nactiUdaj(prop, "paticka");
    }

    // vrátí údaj bez mezer na konci, nebo vyhodí chybu, když v souboru chybí
    private String nactiUdaj(Properties prop, String klic) throws IOException {
        String hodnota = prop.getProperty(klic);
        if (hodnota == null) {
            throw new IOException("V souboru chybi udaj: " + klic);
        }
        return hodnota.trim();
    }

    public String getNazev() {
        return nazev;
    }

    public String getAdresa() {
        return adresa;
    }

    public String getIco() {
        return ico;
    }

    public String getPaticka() {
        return paticka;
    }
}
