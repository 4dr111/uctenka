package cz.uctenka;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Path;
import java.util.Properties;

public class Podnik {
    private final String nazev;
    private final String adresa;
    private final String ico;
    private final String paticka;

    public Podnik(String obsah, String zdroj) throws IOException {
        Properties prop = new Properties();
        prop.load(new StringReader(obsah));

        this.nazev = nactiPovinnyUdaj(prop, "nazev", zdroj);
        this.adresa = nactiPovinnyUdaj(prop, "adresa", zdroj);
        this.ico = nactiPovinnyUdaj(prop, "ico", zdroj);
        this.paticka = prop.getProperty("paticka", "").trim();
    }

    public static Podnik zeSouboru(Path cesta) throws IOException {
        return new Podnik(Soubor.nactiSoubor(cesta), cesta.toString());
    }

    public static Podnik vychozi() throws IOException {
        return new Podnik(Soubor.nactiZProgramu("podnik.properties"), "podnik.properties");
    }

    private static String nactiPovinnyUdaj(Properties prop, String klic, String zdroj) throws IOException {
        String hodnota = prop.getProperty(klic);
        if (hodnota == null || hodnota.isBlank()) {
            throw new IOException("V souboru " + zdroj + " chybi udaj: " + klic);
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
