package cz.uctenka;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UctenkaTest {

    private Uctenka uctenka;

    @BeforeEach
    void pripravUctenku() throws IOException {
        // test-menu.csv: 1 Cola 30, 2 Pivo 45, 3 Burger 70
        uctenka = new Uctenka(new Podnik("test-podnik.properties"), new Menu("test-menu.csv"));
    }

    @Test
    void novaUctenkaJePrazdna() {
        assertTrue(uctenka.jePrazdna());
        assertEquals(0, uctenka.spocitejCelkem());
    }

    @Test
    void spocitejCelkemSecteVsechnyPolozky() {
        uctenka.pridejPolozku("Cola", 2);
        uctenka.pridejPolozku("Burger", 1);

        assertEquals(130, uctenka.spocitejCelkem());
    }

    @Test
    void stejnaPolozkaSeSecte() {
        uctenka.pridejPolozku("Pivo", 1);
        uctenka.pridejPolozku("Pivo", 2);

        assertEquals(135, uctenka.spocitejCelkem());
        assertTrue(uctenka.vytvorText().contains("3x Pivo"));
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
    void nactiNakupPodleCiselAzDoH() {
        uctenka.nactiNakup(new Scanner("1\n1\n3\nH\n2\n"));

        // 2x Cola + 1x Burger, Pivo za H se uz nenacte
        assertEquals(130, uctenka.spocitejCelkem());
    }

    @Test
    void nactiNakupPreskociNeplatnyVstup() {
        uctenka.nactiNakup(new Scanner("abc\n99\n0\n-1\n\n2\nh\n"));

        assertEquals(45, uctenka.spocitejCelkem());
    }

    @Test
    void nactiNakupBezHSkonciNaKonciVstupu() {
        uctenka.nactiNakup(new Scanner("1\n"));

        assertEquals(30, uctenka.spocitejCelkem());
    }

    @Test
    void nactiNakupIgnorujeBomNaZacatku() {
        uctenka.nactiNakup(new Scanner("﻿1\nH\n"));

        assertEquals(30, uctenka.spocitejCelkem());
    }

    @Test
    void textUctenkyObsahujeUdajePolozkyACelkem() {
        uctenka.pridejPolozku("Cola", 2);
        uctenka.pridejPolozku("Burger", 1);

        String text = uctenka.vytvorText();

        assertTrue(text.contains("Test Bistro"));
        assertTrue(text.contains("Testovaci 1, Praha"));
        assertTrue(text.contains("ICO: 11111111"));
        assertTrue(text.contains("2x Cola"));
        assertTrue(text.contains("60.00"));
        assertTrue(text.contains("1x Burger"));
        assertTrue(text.contains("CELKEM"));
        assertTrue(text.contains("130.00 Kc"));
        assertTrue(text.contains("Na shledanou"));
    }
}
