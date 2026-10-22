package cz.uctenka;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import org.junit.jupiter.api.Test;

public class MenuTest {

    @Test
    void nacteVsechnyPolozkySeSpravnouCenou() throws IOException {
        Menu menu = new Menu("test-menu.csv");

        assertEquals(30, menu.getCena("Cola"));
        assertEquals(45, menu.getCena("Pivo"));
        assertEquals(70, menu.getCena("Burger"));
    }

    @Test
    void obsahujePoznaPolozkuVMenu() throws IOException {
        Menu menu = new Menu("test-menu.csv");

        assertTrue(menu.obsahuje("Pivo"));
        assertFalse(menu.obsahuje("Pizza"));
    }

    @Test
    void getNazevVraciPolozkyVPoradiZeSouboru() throws IOException {
        Menu menu = new Menu("test-menu.csv");

        assertEquals("Cola", menu.getNazev(1));
        assertEquals("Pivo", menu.getNazev(2));
        assertEquals("Burger", menu.getNazev(3));
    }

    @Test
    void getNazevMimoRozsahVraciNull() throws IOException {
        Menu menu = new Menu("test-menu.csv");

        assertNull(menu.getNazev(0));
        assertNull(menu.getNazev(4));
        assertNull(menu.getNazev(-1));
    }

    @Test
    void chybejiciSouborVyhodiChybu() {
        assertThrows(IOException.class, () -> new Menu("neexistuje.csv"));
    }

    @Test
    void cenaKteraNeniCisloVyhodiChybu() {
        assertThrows(IOException.class, () -> new Menu("menu-spatna-cena.csv"));
    }

    @Test
    void spatnyPocetSloupcuVyhodiChybu() {
        assertThrows(IOException.class, () -> new Menu("menu-spatny-format.csv"));
    }

    @Test
    void zapornaCenaVyhodiChybu() {
        assertThrows(IOException.class, () -> new Menu("menu-zaporna-cena.csv"));
    }

    @Test
    void prazdneMenuVyhodiChybu() {
        assertThrows(IOException.class, () -> new Menu("menu-prazdne.csv"));
    }
}
