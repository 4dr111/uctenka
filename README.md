# Účtenka 1.0.0

Jednoduchý program pro tisk účtenky v kavárně / hospodě. Spouští se z příkazové řádky:
vypíše menu, uživatel zadává čísla položek a po zadání `H` (hotovo) se vytiskne účtenka.

## Autoři
- Adriano Lo Scalzo, Juraj Šula, Adriana Mećava, Simona Kundrátová

## Ukázka

```
1 Cola - 30 Kc
2 Pivo - 45 Kc
...
Zadejte cislo polozky a Enter, H = hotovo.
2
Pridano: Pivo
5
Pridano: Hranolky
H

================================
        Laskava Kavaren
       Hlavni 123, 110 00 Praha 1
         ICO: 12345678
--------------------------------
1x Pivo                    45.00
1x Hranolky                40.00
--------------------------------
CELKEM                  85.00 Kc
22.10.2026 19:14
     Dekujeme za navstevu!
================================
```

## Struktura

| Soubor | K čemu je |
|---|---|
| `src/main/java/cz/uctenka/Main.java` | spouští program, název a verze |
| `src/main/java/cz/uctenka/Menu.java` | jídla a pití s cenami, načtené z `menu.csv` |
| `src/main/java/cz/uctenka/Podnik.java` | název, adresa, IČO a patička, načtené z `podnik.properties` |
| `src/main/java/cz/uctenka/Uctenka.java` | čte, co si zákazník koupil, sečte to a vytvoří text účtenky |
| `src/main/resources/menu.csv` | menu, jeden řádek = `nazev;cena` (cena v celých Kč) |
| `src/main/resources/podnik.properties` | údaje o podniku (`nazev`, `adresa`, `ico`, `paticka`) |
| `src/test/java/...` | unit testy (JUnit 5) |
| `src/test/resources/...` | testovací soubory pro unit testy |
| `lib/` | knihovna JUnit pro spouštění testů bez Mavenu |
| `sestav.bat` | sestavení bez Mavenu |
| `pom.xml` | sestavení přes Maven (volitelné) |

Menu a údaje o podniku se mění jen úpravou souborů v `src/main/resources`, kód se měnit nemusí
(soubory jsou zabalené v `.jar`, takže je potřeba program znovu sestavit).

## Sestavení a spuštění (bez Mavenu)
Potřeba: **Java 21** (JDK).

Ve složce projektu spustit:

```
sestav.bat
```

(v PowerShellu `.\sestav.bat`). Skript přeloží kód, spustí všechny unit testy a vytvoří
`out\uctenka.jar`. Když některý test selže, sestavení se zastaví.

Spuštění programu:

```
java -jar out\uctenka.jar
```

## Sestavení přes Maven (volitelné)
Projekt má připravený `pom.xml`, takže stačí nainstalovat Maven
(`winget install Apache.Maven` nebo z https://maven.apache.org) a pak:

```
mvn package
java -jar target\uctenka-1.0.0.jar
```

Maven si JUnit stáhne sám, složku `lib/` nepotřebuje. Testy samotné jdou spustit přes `mvn test`.

## Jak vydat novou verzi
Verze se číslují sémanticky `MAJOR.MINOR.PATCH`:
- **PATCH** (1.0.0 → 1.0.1): oprava chyby
- **MINOR** (1.0.0 → 1.1.0): nová funkce, která nic nerozbije
- **MAJOR** (1.0.0 → 2.0.0): velká změna, např. jiný formát souborů

Postup:
1. Změnit verzi na dvou místech: konstanta `VERZE` v `Main.java` a `<version>` v `pom.xml`
   (a nadpis tohoto README).
2. Sestavit a ověřit, že projdou testy: `sestav.bat` (nebo `mvn package`).
3. Commitnout a nahrát na GitHub:
   ```
   git add .
   git commit -m "Verze 1.1.0"
   git tag v1.1.0
   git push
   git push --tags
   ```
4. Na GitHubu v **Releases → Draft a new release** vybrat tag `v1.1.0`
   a přiložit vytvořený `out\uctenka.jar`.
