package cz.uctenka;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Podnik {
    public String nazev;
    public String adresa;
    public String ico;
    public String paticka;

    public Podnik(String nazevSouboru) {
        Properties prop = new Properties()

        FileInputStream input = new FileInputStream(nazevSouboru);
        prop.load(input);
        input.close();

        this.nazev = prop.getProperty("nazev");
        this.adresa = prop.getProperty("adresa");
        this.ico = prop.getProperty("ico");
        this.paticka = prop.getProperty("paticka");

    }
}
