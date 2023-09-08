package com.group15.bcd_real_estate;

import java.io.*;
import java.security.*;
import java.util.Base64;

public class Keyforagreement {
    private static final String ALGORITHM = "RSA";
    private static KeyPair keyPair;
    private static final String KEYSTORE_PATH = "keyforagreement.p12";
    private static final char[] KEYSTORE_PASSWORD = "123".toCharArray();
    private static final char[] KEY_PASSWORD = "keypassword".toCharArray();
    private static final String ALIAS = "mykey"; // Ensure this matches the alias used in storeKeyPair

    public static void generateKeyPair() {
        try {
            KeyPairGenerator keygen = KeyPairGenerator.getInstance(ALGORITHM);
            keygen.initialize(2048); // You can adjust the key size as needed

            keyPair = keygen.generateKeyPair();
            storeKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadKeyPair() {
        try (FileInputStream fis = new FileInputStream(KEYSTORE_PATH)) {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(fis, KEYSTORE_PASSWORD);
            PrivateKey loadedPrivateKey = (PrivateKey) keyStore.getKey(ALIAS, KEY_PASSWORD);
            PublicKey loadedPublicKey = keyPair.getPublic();
            keyPair = new KeyPair(loadedPublicKey, loadedPrivateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static PublicKey getPublicKey() {
        return keyPair.getPublic();
    }

    public static PrivateKey getPrivateKey() {
        return keyPair.getPrivate();
    }

    public static String encodePublicKey() {
        byte[] publicKeyBytes = keyPair.getPublic().getEncoded();
        return Base64.getEncoder().encodeToString(publicKeyBytes);
    }

    public static String encodePrivateKey() {
        byte[] privateKeyBytes = keyPair.getPrivate().getEncoded();
        return Base64.getEncoder().encodeToString(privateKeyBytes);
    }

    private static void storeKeyPair() {
        try (FileOutputStream fos = new FileOutputStream(KEYSTORE_PATH)) {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(null, null);
            keyStore.setKeyEntry(ALIAS, keyPair.getPrivate(), KEY_PASSWORD, null);
            keyStore.store(fos, KEYSTORE_PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

