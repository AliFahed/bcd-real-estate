package com.group15.bcd_real_estate;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
public class PropertyBlock {
    private int index;
    private String previousHash;
    private String timestamp;
    private PropertyType property;
    private String hash;

    public PropertyBlock(int index, String previousHash, PropertyType property) {
        this.index = index;
        this.previousHash = previousHash;
        this.timestamp = (new Date()).toString();
        this.property = property;
        this.hash = this.calculateHash();
    }

    public String calculateHash() {
        String data = this.index + this.previousHash + this.timestamp + this.property.toString();

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(data.getBytes());
            StringBuilder hexString = new StringBuilder();
            byte[] var8 = hashBytes;
            int var7 = hashBytes.length;

            for(int var6 = 0; var6 < var7; ++var6) {
                byte hashByte = var8[var6];
                String hex = Integer.toHexString(255 & hashByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }

                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException var10) {
            var10.printStackTrace();
            return null;
        }
    }

    public String getHash() {
        return this.hash;
    }

    public String getPreviousHash() {
        return this.previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public String toString() {
        return "Header [index=" + this.index + " [Timestamp: " + this.timestamp + ", Current Hash: " + this.hash + ", previousHash=" + this.previousHash + "]\n" + this.property.toString();
    }
}
