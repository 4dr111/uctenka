package cz.uctenka;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

public class MenuTest {

    static Path cesta(String nazev) throws Exception {
        return Path.of(MenuTest.class.getResource("/" + nazev).toURI());
    }

    @Test
    void nacteVsechnyPolozkySeSpravnouCenou() throws Exception {
        Menu menu = Menu.zeSouboru(cesta("test-menu.csv"));

        assertEquals(new BigDecimal("30.00"), menu.getCena("Cola"));
        assertEquals(new BigDecimal("45.00"), menu.getCena("Pivo"));
        assertEquals(new BigDecimal("70.00"), menu.getCena("Burger"));
        assertEquals(3, menu.getPocet());
    }

    @Test
    void obsahujePoznaPolozkuVMenu() throws Exception {
        Menu menu = Menu.zeSouboru(cesta("test-menu.csv"));

        assertTrue(menu.obsahuje("Pivo"));
        assertFalse(menu.obsahuje("Pizza"));
    }

    @Test
    void getNazevVraciPolozkyVPoradiZeSouboru() throws Exception {
        Menu menu = Menu.zeSouboru(cesta("test-menu.csv"));

        assertEquals("Cola", menu.getNazev(1));
        assertEquals("Pivo", menu.getNazev(2));
        assertEquals("Burger", menu.getNazev(3));
    }

    @Test
    void getNazevMimoRozsahVraciNull() throws Exception {
        Menu menu = Menu.zeSouboru(cesta("test-menu.csv"));

        assertNull(menu.getNazev(0));
        assertNull(menu.getNazev(4));
        assertNull(menu.getNazev(-1));
    }

    @Test
    void desetinnaCenaSTeckouICarkou() throws IOException {
        Menu menu = new Menu("Rohlik;2,90\nMleko;24.5\n", "test");

        assertEquals(new BigDecimal("2.90"), menu.getCena("Rohlik"));
        assertEquals(new BigDecimal("24.50"), menu.getCena("Mleko"));
    }

    @Test
    void diakritikaVUtf8ISBom() throws IOException {
        Menu menu = new Menu(Soubor.dekoduj("﻿Smažený sýr;120\n".getBytes(java.nio.charset.StandardCharsets.UTF_8)), "test");

        assertTrue(menu.obsahuje("Smažený sýr"));
    }

    @Test
    void souborVKodovaniWindows1250() throws IOException {
        byte[] data = "Čočka;55\n".getBytes(Charset.forName("windows-1250"));

        Menu menu = new Menu(Soubor.dekoduj(data), "test");

        assertTrue(menu.obsahuje("Čočka"));
    }

    @Test
    void vychoziMenuJdeNacist() throws IOException {
        assertTrue(Menu.vychozi().getPocet() > 0);
    }

    @Test
    void vypisObsahujeCislaACeny() throws IOException {
        Menu menu = new Menu("Cola;30\nPivo;45,5\n", "test");

        assertEquals("1  Cola - 30,00 Kc\n2  Pivo - 45,50 Kc\n", menu.vytvorVypis());
    }

    @Test
    void chybejiciSouborVyhodiChybu() {
        assertThrows(IOException.class, () -> Menu.zeSouboru(Path.of("neexistuje.csv")));
    }

    @Test
    void cenaKteraNeniCisloVyhodiChybu() {
        assertThrows(IOException.class, () -> Menu.zeSouboru(cesta("menu-spatna-cena.csv")));
    }

    @Test
    void spatnyPocetSloupcuVyhodiChybu() {
        assertThrows(IOException.class, () -> Menu.zeSouboru(cesta("menu-spatny-format.csv")));
        assertThrows(IOException.class, () -> new Menu("Cola;30;\n", "test"));
        assertThrows(IOException.class, () -> new Menu("Cola\n", "test"));
    }

    @Test
    void zapornaCenaVyhodiChybu() {
        assertThrows(IOException.class, () -> Menu.zeSouboru(cesta("menu-zaporna-cena.csv")));
    }

    @Test
    void prazdneMenuVyhodiChybu() {
        assertThrows(IOException.class, () -> Menu.zeSouboru(cesta("menu-prazdne.csv")));
        assertThrows(IOException.class, () -> new Menu("", "test"));
    }

    @Test
    void duplicitniPolozkaVyhodiChybu() {
        assertThrows(IOException.class, () -> new Menu("Cola;30\nCola;35\n", "test"));
    }

    @Test
    void prazdnyNazevVyhodiChybu() {
        assertThrows(IOException.class, () -> new Menu(";30\n", "test"));
    }

    @Test
    void viceNezDveDesetinnaMistaVyhodiChybu() {
        assertThrows(IOException.class, () -> new Menu("Cola;30.555\n", "test"));
    }
}
