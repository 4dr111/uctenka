package cz.uctenka;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

public class PodnikTest {

    @Test
    void nacteVsechnyUdaje() throws Exception {
        Podnik podnik = Podnik.zeSouboru(MenuTest.cesta("test-podnik.properties"));

        assertEquals("Testovaci 1, Praha", podnik.getAdresa());
        assertEquals("11111111", podnik.getIco());
        assertEquals("Na shledanou", podnik.getPaticka());
    }

    @Test
    void oriznouSeMezeryNaKonci() throws Exception {
        Podnik podnik = Podnik.zeSouboru(MenuTest.cesta("test-podnik.properties"));

        assertEquals("Test Bistro", podnik.getNazev());
    }

    @Test
    void diakritikaABomNaZacatku() throws IOException {
        byte[] data = "﻿nazev=Pekárna U Čápa\nadresa=Dlouhá 1\nico=123\n".getBytes(StandardCharsets.UTF_8);

        Podnik podnik = new Podnik(Soubor.dekoduj(data), "test");

        assertEquals("Pekárna U Čápa", podnik.getNazev());
    }

    @Test
    void patickaJeNepovinna() throws IOException {
        Podnik podnik = new Podnik("nazev=A\nadresa=B\nico=1\n", "test");

        assertEquals("", podnik.getPaticka());
    }

    @Test
    void vychoziPodnikJdeNacist() throws IOException {
        assertFalse(Podnik.vychozi().getNazev().isEmpty());
    }

    @Test
    void chybejiciSouborVyhodiChybu() {
        assertThrows(IOException.class, () -> Podnik.zeSouboru(Path.of("neexistuje.properties")));
    }

    @Test
    void chybejiciUdajVyhodiChybu() {
        assertThrows(IOException.class, () -> Podnik.zeSouboru(MenuTest.cesta("podnik-bez-ico.properties")));
    }

    @Test
    void prazdnyUdajVyhodiChybu() {
        assertThrows(IOException.class, () -> new Podnik("nazev=\nadresa=B\nico=1\n", "test"));
    }
}
