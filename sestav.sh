#!/bin/sh
set -e
cd "$(dirname "$0")"
JUNIT=lib/junit-platform-console-standalone-1.11.4.jar
case "$(uname -s)" in
    MINGW*|MSYS*|CYGWIN*) SEP=";" ;;
    *) SEP=":" ;;
esac

rm -rf out

echo "=== Preklad programu"
javac --release 17 -encoding UTF-8 -d out/main src/main/java/cz/uctenka/*.java
cp src/main/resources/* out/main/

echo "=== Preklad testu"
javac --release 17 -encoding UTF-8 -d out/test -cp "out/main${SEP}$JUNIT" src/test/java/cz/uctenka/*.java
cp src/test/resources/* out/test/

echo "=== Spusteni testu"
java -jar "$JUNIT" execute --class-path "out/main${SEP}out/test" --scan-class-path --disable-banner --details=summary

echo "=== Vytvoreni out/uctenka.jar"
jar --create --file out/uctenka.jar --main-class cz.uctenka.Main -C out/main .

echo
echo "Hotovo. Spusteni programu: java -jar out/uctenka.jar"
