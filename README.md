# Uctenka 1.1.0

A simple command-line tool that prints a shop receipt.

## Authors

- Adriano Lo Scalzo
- Juraj Šula
- Adriana Mećava
- Simona Kundrátová

## Requirements

Java 17 or newer.

## Build

With Maven:

```
mvn package
```

Without Maven (also runs the tests):

```
sestav.bat     (Windows)
./sestav.sh    (Linux / macOS)
```

## Run

```
java -jar target/uctenka-1.1.0.jar shop.properties menu.csv
```

(`out/uctenka.jar` if built without Maven.)

Without arguments the built-in example files are used.

The program lists the menu. Type the item number (e.g. `2`) or the number and quantity
(e.g. `2 3`) and press Enter. Type `H` to finish and print the receipt.

## Input files (UTF-8)

`shop.properties` – data printed on every receipt (`paticka` = footer, optional):

```
nazev = My Shop
adresa = Main Street 1, Prague
ico = 12345678
paticka = Thank you!
```

`menu.csv` – one item per line, `name;price`:

```
Cola;30
Bread;2,90
```

## Example

```
========================================
            Laskava Kavaren
       Hlavni 123, 110 00 Praha 1
             ICO: 12345678
----------------------------------------
Pivo                               90,00
  2 x 45,00
----------------------------------------
CELKEM                          90,00 Kc
----------------------------------------
22.10.2026 19:33

         Dekujeme za navstevu!
========================================
```

## Releasing a new version

Versions follow [Semantic Versioning](https://semver.org) (`MAJOR.MINOR.PATCH`).

1. Change the version in `pom.xml` (`<version>`) and in `Main.java` (`VERZE`).
2. Build and make sure the tests pass: `mvn package`.
3. Commit, tag and push:
   ```
   git commit -am "Version 1.2.0"
   git tag v1.2.0
   git push --tags
   ```
