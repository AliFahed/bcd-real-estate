package com.group15.bcd_real_estate;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import javax.crypto.Cipher;

public class ApplyLoan {
    private static final String latestBlockHash = null;
    private List<Bank> banks = new ArrayList();
    private Bank selectedBank;
    private double monthlyPayment;
    private boolean loanEligible;
    private double propertyPrice;
    private PropertyType selectedProperty;
    private List<BankBlock> bankBlocks = new ArrayList();
    private String previousHash;
    private String latestBankBlockHash = "";
    private List<PropertyBlock> blockchain;

    public ApplyLoan(PropertyType selectedProperty) {
        this.banks.add(new Bank(1, "Public Bank", "KL Main Office", 601243019, "111, Jalan Ampang, 50000 Kuala Lumpur", "5.0% - 5.5%"));
        this.banks.add(new Bank(2, "CIMB Bank", "KL Main Office", 601910242, "222, Jalan Ampang, 50000 Kuala Lumpur", "4.5% - 5.0%"));
        this.banks.add(new Bank(3, "Maybank", "KL Main Office", 601148301, "333, Jalan Ampang, 50000 Kuala Lumpur", "4.0% - 4.5%"));
        this.selectedProperty = selectedProperty;
        this.previousHash = "";
        this.bankBlocks = new ArrayList();
        this.blockchain = new ArrayList();
    }

    public void applyForLoan() {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("\n-----BANK DETAILS-----");
            Iterator var3 = this.banks.iterator();

            while(var3.hasNext()) {
                Bank bank = (Bank)var3.next();
                System.out.println("Bank ID                          : " + bank.getBankID());
                System.out.println("Bank Name                        : " + bank.getBankName());
                System.out.println("Bank Branch                      : " + bank.getBankBranch());
                System.out.println("Bank Contact                     : " + bank.getBankContact());
                System.out.println("Bank Address                     : " + bank.getBankAddress());
                System.out.println("Range of Interest Rate Per Annum : " + bank.getInterestRate());
                System.out.println();
            }

            System.out.print("Enter the Bank ID you desire (1-" + this.banks.size() + ", 0 to exit): ");
            int choice = scanner.nextInt();
            if (choice == 0) {
                System.out.println("Thank you.");
                break;
            }

            if (choice >= 1 && choice <= this.banks.size()) {
                this.selectedBank = (Bank)this.banks.get(choice - 1);
                System.out.println("---You have chosen---");
                System.out.println("Bank Name               : " + this.selectedBank.getBankName());
                System.out.println("Bank Branch             : " + this.selectedBank.getBankBranch());
                System.out.println("Bank Contact            : " + this.selectedBank.getBankContact());
                System.out.println("Bank Address            : " + this.selectedBank.getBankAddress());
                System.out.println("Interest Rate Per Annum : " + this.selectedBank.getInterestRate());
                System.out.println("---The interest rate is based on the amount of loan you are applying.---");
                BankBlock bankBlock = new BankBlock(this.bankBlocks.size() + 1, latestBlockHash, this.selectedBank);
                this.bankBlocks.add(bankBlock);
                this.previousHash = bankBlock.getHash();
                System.out.println("\n---BLOCKCHAIN FOR BANK DETAILS---");
                Iterator var5 = this.bankBlocks.iterator();

                while(var5.hasNext()) {
                    BankBlock bankblock = (BankBlock)var5.next();
                    System.out.println(bankblock.toString());
                }

                System.out.print("\nEnter Loan Amount         : RM");
                double loanAmount = scanner.nextDouble();
                System.out.print("Enter Loan Period (months): ");
                int loanPeriodInMonths = scanner.nextInt();
                double interestRate;
                if (loanAmount >= 0.75 * this.selectedProperty.getPrice()) {
                    interestRate = 0.05;
                } else if (loanAmount >= 0.5 * this.selectedProperty.getPrice()) {
                    interestRate = 0.0525;
                } else {
                    interestRate = 0.055;
                }

                double monthlyInterestRate = interestRate / 12.0;
                double monthlyPayment = loanAmount * monthlyInterestRate / (1.0 - Math.pow(1.0 + monthlyInterestRate, (double)(-loanPeriodInMonths)));
                System.out.println("Monthly Payment           : RM" + monthlyPayment);
                System.out.print("\nYour Monthly Income       : RM");
                double monthlyIncome = scanner.nextDouble();
                if (monthlyIncome >= 2.0 * monthlyPayment) {
                    System.out.println("Loan Eligibility          : Yes \n");
                    double propertyPrice = this.selectedProperty.getPrice();
                    System.out.println("You have to settle the remaining amount.");
                    double remainingAmount = propertyPrice - loanAmount;
                    System.out.println("Remaining amount: RM" + remainingAmount + "\n");
                    Scanner remainingAmountScanner = new Scanner(System.in);
                    System.out.print("Enter the remaining amount to pay (RM): ");
                    double enteredRemainingAmount = remainingAmountScanner.nextDouble();
                    if (!(enteredRemainingAmount >= remainingAmount)) {
                        System.out.println("Insufficient remaining amount. Purchase canceled.");
                        break;
                    }

                    Date currentDate = new Date();
                    String signingDate = currentDate.toString();
                    DigitalSignature digitalSignature = new DigitalSignature();
                    String purchaseAgreement = "Purchase Agreement";
                    purchaseAgreement = purchaseAgreement + "\nSeller: " + this.selectedProperty.getSeller() + "\n";
                    purchaseAgreement = purchaseAgreement + "Seller Contact: " + this.selectedProperty.getContact() + "\n";
                    purchaseAgreement = purchaseAgreement + "Bank: " + this.selectedBank.getBankName() + "\n";
                    purchaseAgreement = purchaseAgreement + "Bank Contact: " + this.selectedBank.getBankContact() + "\n";
                    purchaseAgreement = purchaseAgreement + "Loan Amount: " + loanAmount + "\n";
                    purchaseAgreement = purchaseAgreement + "Monthly Payment: " + monthlyPayment + "\n";
                    purchaseAgreement = purchaseAgreement + "Property ID: " + this.selectedProperty.getPropertyID() + "\n";
                    purchaseAgreement = purchaseAgreement + "Property Type: " + this.selectedProperty.getPropertyType() + "\n";
                    purchaseAgreement = purchaseAgreement + "Signing Date: " + signingDate + "\n";
                    String digitalSignatureString = digitalSignature.signMessage(purchaseAgreement);
                    System.out.println("Digital Signature: " + digitalSignatureString);
                    boolean isSignatureValid = digitalSignature.verifySignature(purchaseAgreement, digitalSignatureString, digitalSignature.getPublicKey());
                    if (isSignatureValid) {
                        System.out.println("Your Digital Signature is valid. \nYour Purchase Agreement is secured. \nCongratulations! Property is now yours.");
                        Keyforagreement.generateKeyPair();
                        Scanner confirmationScanner = new Scanner(System.in);
                        String encryptedAgreement = null;

                        try {
                            encryptedAgreement = encryptPurchaseAgreement(purchaseAgreement, Keyforagreement.getPublicKey());
                        } catch (Exception var34) {
                            var34.printStackTrace();
                        }

                        System.out.println("Purchase Agreement is encrypted: " + encryptedAgreement);
                        System.out.println("\nDo you want to decrypt it? (yes/no): ");
                        String decryptConfirmation = confirmationScanner.next();
                        if (decryptConfirmation.equalsIgnoreCase("yes")) {
                            String decryptedAgreement = null;

                            try {
                                decryptedAgreement = decryptPurchaseAgreement(encryptedAgreement, Keyforagreement.getPrivateKey());
                            } catch (Exception var33) {
                                var33.printStackTrace();
                            }

                            System.out.println("\nDecrypted Agreement: \n" + decryptedAgreement);
                            System.out.println("Thank you for your purchase!");
                            System.exit(0);
                        } else {
                            System.out.println("Your agreement is encrypted. You selected not to view.");
                            System.out.println("Thank you for your purchase!");
                        }
                    } else {
                        System.out.println("Your Digital Signature is not valid.");
                    }
                } else {
                    System.out.println("Loan Eligibility          : No");
                    System.out.println("Sorry you are not eligible to apply a loan. Try other banks.");
                    this.loanEligible = false;
                }
            } else {
                System.out.println("Invalid bank choice. Please enter a valid Bank ID or 0 to exit.");
            }
        }

        scanner.close();
    }

    public double getMonthlyPayment() {
        return this.getMonthlyPayment();
    }

    /**public static void main(String[] args) {
        String previousHash = "";
        ApplyLoan applyLoan = new ApplyLoan((PropertyType)null);
        applyLoan.applyForLoan();
    }**/

    public Bank getSelectedBank() {
        return this.selectedBank;
    }

    public boolean isLoanEligible() {
        return this.loanEligible;
    }

    public void applyForLoan(double propertyPrice2) {
    }

    static String createPurchaseAgreement(PropertyType property, String signingDate) {
        StringBuilder agreement = new StringBuilder();
        agreement.append("-----PURCHASE AGREEMENT-----\n");
        agreement.append("Seller         : " + property.getSeller() + "\n");
        agreement.append("Seller Contact : " + property.getContact() + "\n");
        agreement.append("\nProperty ID  : " + property.getPropertyID() + "\n");
        agreement.append("Property Type  : " + property.getPropertyType() + "\n");
        agreement.append("Property Price : " + property.getPrice() + "\n");
        agreement.append("Address        : " + property.getAddress() + "\n");
        agreement.append("\nPayment        : Settled");
        agreement.append("Signing Date   : " + signingDate + "\n");
        return agreement.toString();
    }

    public static String encryptPurchaseAgreement(String message, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(1, publicKey);
        byte[] encryptedBytes = cipher.doFinal(message.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decryptPurchaseAgreement(String encryptedMessage, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(2, privateKey);
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedMessage);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes);
    }
}
