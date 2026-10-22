# Účtenka

Jednoduchý program pro tisk účtenky v hospodě (Java, Maven).

## Autoři
- TODO jména členů týmu

## Struktura

| Třída | K čemu je |
|---|---|
| `Main` | spouští program, propojuje ostatní třídy |
| `InfoOProgramu` | název a verze |
| `Provozovna` | statické údaje o hospodě (název, adresa, IČO, patička) |
| `Polozka` | jedna položka menu (kód, název, cena) |
| `Menu` | všechny položky menu |
| `PolozkaObjednavky` | položka + počet kusů |
| `Objednavka` | co si zákazník objednal, celkový součet |
| `NacitacSouboru` | čte `provozovna.properties` a `menu.csv` |
| `VstupUzivatele` | čte objednávku z příkazové řádky |
| `TiskUctenky` | poskládá a naformátuje účtenku |

## Doporučený postup práce
1. **Git + GitHub**: jeden založí repozitář a nahraje tuhle kostru, ostatní ho naklonují.
   Každý pracuje na své větvi a mergují se přes Pull Request – tak je vidět, že přispěli všichni.
2. **pom.xml**: doplnit JUnit 5 a plugin pro spustitelný jar, ověřit, že `mvn package` projde.
3. **Rozdělení práce** (třídy jsou na sobě málo závislé, dá se dělat paralelně):
   - člověk A: `Polozka`, `Menu`, `NacitacSouboru` + data v souborech
   - člověk B: `PolozkaObjednavky`, `Objednavka`, `VstupUzivatele`
   - člověk C: `TiskUctenky`, `Main`, `InfoOProgramu`, README
   - každý píše testy ke svým třídám
4. **Pořadí**: nejdřív datové třídy (`Polozka`, `Provozovna`, `PolozkaObjednavky`, `Objednavka`),
   pak načítání a vstup, nakonec tisk a `Main`.
5. Na konci společně zkusit program „rozbít“ nesmyslnými vstupy (prázdný vstup, písmena,
   chybějící soubor, diakritika v konzoli…).

## Sestavení a spuštění
Potřeba: Java 21 a Maven.

```
mvn package
java -jar target/uctenka-<verze>.jar
```

## Jak vydat novou verzi
TODO – např.: změnit verzi v `pom.xml` (a v `InfoOProgramu`), `mvn package`,
commit, git tag `v1.1.0`, nahrát .jar do GitHub Releases.
