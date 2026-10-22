package cz.uctenka;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UctenkaTest {

    private Uctenka uctenka;

    @BeforeEach
    void pripravUctenku() throws Exception {
        uctenka = new Uctenka(
                Podnik.zeSouboru(MenuTest.cesta("test-podnik.properties")),
                Menu.zeSouboru(MenuTest.cesta("test-menu.csv")));
    }

    private static BigDecimal kc(String castka) {
        return new BigDecimal(castka);
    }

    @Test
    void novaUctenkaJePrazdna() {
        assertTrue(uctenka.jePrazdna());
        assertEquals(0, uctenka.spocitejCelkem().compareTo(BigDecimal.ZERO));
    }

    @Test
    void spocitejCelkemSecteVsechnyPolozky() {
        uctenka.pridejPolozku("Cola", 2);
        uctenka.pridejPolozku("Burger", 1);

        assertEquals(kc("130.00"), uctenka.spocitejCelkem());
    }

    @Test
    void desetinneCenySeSectouPresne() throws IOException {
        Uctenka u = new Uctenka(new Podnik("nazev=A\nadresa=B\nico=1\n", "test"), new Menu("Rohlik;0,10\nMleko;0,20\n", "test"));
        u.pridejPolozku("Rohlik", 1);
        u.pridejPolozku("Mleko", 1);

        assertEquals(kc("0.30"), u.spocitejCelkem());
    }

    @Test
    void stejnaPolozkaSeSecte() {
        uctenka.pridejPolozku("Pivo", 1);
        uctenka.pridejPolozku("Pivo", 2);

        assertEquals(3, uctenka.getPocet("Pivo"));
        assertEquals(kc("135.00"), uctenka.spocitejCelkem());
        assertTrue(uctenka.vytvorText().contains("3 x 45,00"));
    }

    @Test
    void polozkaMimoMenuVyhodiChybu() {
        assertThrows(IllegalArgumentException.class, () -> uctenka.pridejPolozku("Pizza", 1));
    }

    @Test
    void nulovyNeboZapornyPocetVyhodiChybu() {
        assertThrows(IllegalArgumentException.class, () -> uctenka.pridejPolozku("Cola", 0));
        assertThrows(IllegalArgumentException.class, () -> uctenka.pridejPolozku("Cola", -1));
    }

    @Test
    void prilisVelkyPocetVyhodiChybu() {
        uctenka.pridejPolozku("Cola", Uctenka.MAX_POCET);

        assertThrows(IllegalArgumentException.class, () -> uctenka.pridejPolozku("Cola", 1));
        assertThrows(IllegalArgumentException.class, () -> uctenka.pridejPolozku("Pivo", Integer.MAX_VALUE));
        assertEquals(Uctenka.MAX_POCET, uctenka.getPocet("Cola"));
    }

    @Test
    void nactiNakupPodleCiselAzDoH() {
        uctenka.nactiNakup(new Scanner("1\n1\n3\nH\n2\n"));

        assertEquals(kc("130.00"), uctenka.spocitejCelkem());
    }

    @Test
    void nactiNakupSPoctem() {
        uctenka.nactiNakup(new Scanner("2 3\n1   2\nH\n"));

        assertEquals(3, uctenka.getPocet("Pivo"));
        assertEquals(2, uctenka.getPocet("Cola"));
    }

    @Test
    void nactiNakupPreskociNeplatnyVstup() {
        uctenka.nactiNakup(new Scanner("abc\n99\n0\n-1\n\n1 0\n1 -2\n1 x\n1 2 3\n1 99999\n2\nh\n"));

        assertEquals(kc("45.00"), uctenka.spocitejCelkem());
    }

    @Test
    void nactiNakupBezHSkonciNaKonciVstupu() {
        uctenka.nactiNakup(new Scanner("1\n"));

        assertEquals(kc("30.00"), uctenka.spocitejCelkem());
    }

    @Test
    void nactiNakupIgnorujeBomNaZacatku() {
        uctenka.nactiNakup(new Scanner("﻿1\nH\n"));

        assertEquals(kc("30.00"), uctenka.spocitejCelkem());
    }

    @Test
    void textUctenkyObsahujeUdajePolozkyACelkem() {
        uctenka.pridejPolozku("Cola", 2);
        uctenka.pridejPolozku("Burger", 1);

        String text = uctenka.vytvorText(LocalDateTime.of(2026, 10, 22, 9, 5));

        assertTrue(text.contains("Test Bistro"));
        assertTrue(text.contains("Testovaci 1, Praha"));
        assertTrue(text.contains("ICO: 11111111"));
        assertTrue(text.contains("Cola"));
        assertTrue(text.contains("60,00"));
        assertTrue(text.contains("2 x 30,00"));
        assertTrue(text.contains("Burger"));
        assertTrue(text.contains("130,00 Kc"));
        assertTrue(text.contains("22.10.2026 09:05"));
        assertTrue(text.contains("Na shledanou"));
    }

    @Test
    void vsechnyRadkyMajiMaximalneSirkuUctenky() throws IOException {
        Podnik podnik = new Podnik("nazev=Velmi dlouhy nazev obchodu ktery se nevejde na jeden radek\n"
                + "adresa=Ulice\nico=1\npaticka=Dekujeme\n", "test");
        Menu menu = new Menu("Extremne dlouhy nazev polozky ktery se urcite nevejde;12345,50\n"
                + "Slovobezmezerkterejeprilisdlouhenajedenradekuctenky;1\n", "test");
        Uctenka u = new Uctenka(podnik, menu);
        u.pridejPolozku("Extremne dlouhy nazev polozky ktery se urcite nevejde", 999);
        u.pridejPolozku("Slovobezmezerkterejeprilisdlouhenajedenradekuctenky", 1);

        String text = u.vytvorText(LocalDateTime.of(2026, 1, 1, 12, 0));

        for (String radek : text.split("\n")) {
            assertTrue(radek.length() <= Uctenka.SIRKA, "Prilis dlouhy radek: " + radek);
        }
        assertTrue(text.contains("12333155,50 Kc"));
    }

    @Test
    void cenaJeZarovnanaVpravo() {
        uctenka.pridejPolozku("Pivo", 1);

        String text = uctenka.vytvorText(LocalDateTime.of(2026, 1, 1, 12, 0));

        assertTrue(text.contains("Pivo" + " ".repeat(Uctenka.SIRKA - 4 - 5) + "45,00\n"));
    }

    @Test
    void zalomRozdeliTextNaRadky() {
        assertEquals(List.of("aaa bbb", "ccc"), Uctenka.zalom("aaa bbb ccc", 7));
        assertEquals(List.of("abcde", "fg"), Uctenka.zalom("abcdefg", 5));
        assertEquals(List.of(""), Uctenka.zalom("", 5));
    }
}
