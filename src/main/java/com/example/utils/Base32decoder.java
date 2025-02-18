package com.example.utils;

public class Base32decoder {
    private static final String BASE32_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ234567";

    public static byte[] decode(String base32) {
        int[] lookupTable = new int[128];
        for (int i = 0; i < BASE32_ALPHABET.length(); i++) {
            lookupTable[BASE32_ALPHABET.charAt(i)] = i;
        }

        int outputLength = base32.length() * 5 / 8;
        byte[] decoded = new byte[outputLength];

        int buffer = 0;
        int bitsLeft = 0;
        int byteIndex = 0;

        for (char c : base32.toCharArray()) {
            if (c == '=') break; // Padding character
            buffer <<= 5;
            buffer |= lookupTable[c];
            bitsLeft += 5;
            if (bitsLeft >= 8) {
                decoded[byteIndex++] = (byte) (buffer >> (bitsLeft - 8));
                bitsLeft -= 8;
            }
        }
        return decoded;
    }
}
