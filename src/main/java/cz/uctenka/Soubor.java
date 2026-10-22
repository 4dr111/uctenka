package cz.uctenka;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

final class Soubor {

    private Soubor() {
    }

    static String nactiSoubor(Path cesta) throws IOException {
        if (!Files.isRegularFile(cesta)) {
            throw new IOException("Soubor " + cesta + " nebyl nalezen");
        }
        return dekoduj(Files.readAllBytes(cesta));
    }

    static String nactiZProgramu(String nazev) throws IOException {
        try (InputStream input = Soubor.class.getResourceAsStream("/" + nazev)) {
            if (input == null) {
                throw new IOException("Soubor " + nazev + " nebyl nalezen");
            }
            return dekoduj(input.readAllBytes());
        }
    }

    static String dekoduj(byte[] data) {
        String text;
        try {
            text = StandardCharsets.UTF_8.newDecoder().decode(ByteBuffer.wrap(data)).toString();
        } catch (CharacterCodingException e) {
            Charset zaloha = Charset.isSupported("windows-1250")
                    ? Charset.forName("windows-1250")
                    : StandardCharsets.ISO_8859_1;
            text = new String(data, zaloha);
        }
        if (text.startsWith("﻿")) {
            text = text.substring(1);
        }
        return text;
    }
}
