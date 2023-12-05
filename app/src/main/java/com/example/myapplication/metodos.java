package com.example.myapplication;

import android.os.Build;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class metodos {
    public static String hashPassword(String password) {
        try {
            int iterations = 1000;
            char[] characters = password.toCharArray();
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            byte[] salt = new byte[16];
            sr.nextBytes(salt);

            PBEKeySpec spec = new PBEKeySpec(characters, salt, iterations, 64 * 8);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = skf.generateSecret(spec).getEncoded();

            BigInteger bi = new BigInteger(1, hash);
            String hex = bi.toString(16);

            int paddingLength = (hash.length * 2) - hex.length();
            if (paddingLength > 0) {
                hex = String.format("%0" + paddingLength + "d", 0) + hex;
            }

            bi = new BigInteger(1, salt);
            String saltHex = bi.toString(16);

            paddingLength = (salt.length * 2) - saltHex.length();
            if (paddingLength > 0) {
                saltHex = String.format("%0" + paddingLength + "d", 0) + saltHex;
            }

            return iterations + ":" + saltHex + ":" + hex;
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }}
