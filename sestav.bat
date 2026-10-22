@echo off
rem Sestavi program bez Mavenu: prelozi kod, spusti testy a vytvori out\uctenka.jar
rem Spusteni: sestav.bat (v PowerShellu .\sestav.bat)

set JUNIT=lib\junit-platform-console-standalone-1.11.4.jar

if exist out rmdir /s /q out

echo === Preklad programu
javac -encoding UTF-8 -d out\main src\main\java\cz\uctenka\*.java || goto chyba
copy /y src\main\resources\* out\main\ >nul || goto chyba

echo === Preklad testu
javac -encoding UTF-8 -d out\test -cp "out\main;%JUNIT%" src\test\java\cz\uctenka\*.java || goto chyba
copy /y src\test\resources\* out\test\ >nul || goto chyba

echo === Spusteni testu
java -jar %JUNIT% execute --class-path "out\main;out\test" --scan-class-path --disable-banner --details=summary || goto chyba

echo === Vytvoreni out\uctenka.jar
jar --create --file out\uctenka.jar --main-class cz.uctenka.Main -C out\main . || goto chyba

echo.
echo Hotovo. Spusteni programu: java -jar out\uctenka.jar
exit /b 0

:chyba
echo.
echo SESTAVENI SELHALO
exit /b 1
