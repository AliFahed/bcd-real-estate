package com.group15.bcd_real_estate;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

public class DigitalSignature {
    private KeyPair keyPair;

    public DigitalSignature() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            this.keyPair = keyPairGenerator.generateKeyPair();
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }

    public String signMessage(String message) {
        try {
            PrivateKey privateKey = this.keyPair.getPrivate();
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKey);
            signature.update(message.getBytes());
            byte[] digitalSignature = signature.sign();
            return Base64.getEncoder().encodeToString(digitalSignature);
        } catch (Exception var5) {
            var5.printStackTrace();
            return null;
        }
    }

    public boolean verifySignature(String message, String signatureString, PublicKey publicKey) {
        try {
            byte[] signatureBytes = Base64.getDecoder().decode(signatureString);
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(publicKey);
            signature.update(message.getBytes());
            return signature.verify(signatureBytes);
        } catch (Exception var6) {
            var6.printStackTrace();
            return false;
        }
    }

    public PublicKey getPublicKey() {
        return this.keyPair.getPublic();
    }
}
