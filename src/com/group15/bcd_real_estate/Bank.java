package com.group15.bcd_real_estate;

public class Bank {
    private int bankID;
    private String bankName;
    private String bankBranch;
    private int bankContact;
    private String bankAddress;
    private String interestRate;

    public Bank(int bankID, String bankName, String bankBranch, int bankContact, String bankAddress, String interestRate) {
        this.bankID = bankID;
        this.bankName = bankName;
        this.bankBranch = bankBranch;
        this.bankContact = bankContact;
        this.bankAddress = bankAddress;
        this.interestRate = interestRate;
    }

    public int getBankID() {
        return this.bankID;
    }

    public String getBankName() {
        return this.bankName;
    }

    public String getBankBranch() {
        return this.bankBranch;
    }

    public int getBankContact() {
        return this.bankContact;
    }

    public String getBankAddress() {
        return this.bankAddress;
    }

    public String getInterestRate() {
        return this.interestRate;
    }

    public String toString() {
        return "Bank [bankName=" + this.bankName + ", bankBranch=" + this.bankBranch + ", bankContact=" + this.bankContact + ", bankAddress=" + this.bankAddress + "]";
    }
}
