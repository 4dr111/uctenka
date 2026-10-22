# Účtenka

Jednoduchý program pro tisk účtenky v hospodě (Java, Maven).

## Autoři
- TODO jména členů týmu

## Struktura

| Třída | K čemu je |
|---|---|
| `Main` | spouští program, název a verze |
| `Menu` | jídla a pití s cenami, načtené z `menu.csv` |
| `Podnik` | název, adresa, IČO, patička, načtené z `podnik.properties` |
| `Uctenka` | zeptá se uživatele, co si koupil, sečte to a vytvoří účtenku |

## Doporučený postup práce
1. **Git + GitHub**: jeden založí repozitář a nahraje tuhle kostru, ostatní ho naklonují.
   Každý pracuje na své větvi a mergují se přes Pull Request – tak je vidět, že přispěli všichni.
2. **pom.xml**: doplnit JUnit 5 a plugin pro spustitelný jar, ověřit, že `mvn package` projde.
3. **Rozdělení práce** (dá se dělat paralelně):
   - člověk A: `Menu` + `menu.csv`
   - člověk B: `Podnik` + `podnik.properties`, `Main`, README
   - člověk C: `Uctenka`
   - každý píše testy ke svým třídám
4. **Pořadí**: nejdřív `Menu` a `Podnik` (na nich `Uctenka` závisí), pak `Uctenka`, nakonec `Main`.
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
