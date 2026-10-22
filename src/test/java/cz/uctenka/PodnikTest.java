package cz.uctenka;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import org.junit.jupiter.api.Test;

public class PodnikTest {

    @Test
    void nacteVsechnyUdaje() throws IOException {
        Podnik podnik = new Podnik("test-podnik.properties");

        assertEquals("Testovaci 1, Praha", podnik.getAdresa());
        assertEquals("11111111", podnik.getIco());
        assertEquals("Na shledanou", podnik.getPaticka());
    }

    @Test
    void oriznouSeMezeryNaKonci() throws IOException {
        Podnik podnik = new Podnik("test-podnik.properties");

        assertEquals("Test Bistro", podnik.getNazev());
    }

    @Test
    void chybejiciSouborVyhodiChybu() {
        assertThrows(IOException.class, () -> new Podnik("neexistuje.properties"));
    }

    @Test
    void chybejiciUdajVyhodiChybu() {
        assertThrows(IOException.class, () -> new Podnik("podnik-bez-ico.properties"));
    }
}
