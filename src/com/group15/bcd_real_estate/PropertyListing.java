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

public class PropertyListing {
    private User authenticatedUser;

    public void setAuthenticatedUser(User authenticatedUser) {
        this.authenticatedUser = authenticatedUser;
    }

    public void propertyListingProcess() {
        try {
            List<PropertyType> properties = new ArrayList<>();
            List<PropertyBlock> blockchain = new ArrayList<>();
            properties.add(new PropertyType(1, "Landed House", 800000.0, "111, TPM, 50000 Kuala Lumpur", 10000, "Garden, Yard", "Shopping Mall", "0123456789", "Available", "1 January 2023", 2020, "Adam"));
            properties.add(new PropertyType(2, "Apartment", 300000.0, "222, TPM, 50000 Kuala Lumpur", 1000, "Swimming Pool, 24/7 Security", "Train Station", "0124567890", "Available", "10 February 2023", 2021, "Chloe"));
            properties.add(new PropertyType(3, "Condo", 500000.0, "333, TPM, 50000 Kuala Lumpur", 1500, "Swimming Pool, Gym Room, Carpark, 24/7 Security", "Shopping Mall, Train Station", "0125678901", "Available", "24 April 2023", 2022, "Joey"));
            Scanner scanner = new Scanner(System.in);
            System.out.println("---PROPERTY TYPES AND DETAILS---");
            Iterator<PropertyType> iterator = properties.iterator();

            while (iterator.hasNext()) {
                PropertyType property = iterator.next();
                System.out.println("Property ID    :" + property.getPropertyID());
                System.out.println("Property Type  :" + property.getPropertyType());
                System.out.println("Price          :RM" + property.getPrice());
                System.out.println("Address        :" + property.getAddress());
                System.out.println("Squarefeet     :" + property.getSqft());
                System.out.println("Facilities     :" + property.getFacilities());
                System.out.println("Amenities       :" + property.getAmenities());
                System.out.println("Contact Number :" + property.getContact());
                System.out.println("Status         :" + property.getStatus());
                System.out.println("Listing Date   :" + property.getListingDate());
                System.out.println("Year Built     :" + property.getYearBuilt() + "\n");
            }

            String previousHash = "0";

            while (true) {
                System.out.print("Enter the Property ID you desire (1-3, 0 to exit): ");
                int choice = scanner.nextInt();
                if (choice >= 1 && choice <= 3) {
                    PropertyType selectedProperty = properties.get(choice - 1);
                    PropertyBlock newBlock = new PropertyBlock(blockchain.size() + 1, previousHash, selectedProperty);
                    blockchain.add(newBlock);
                    previousHash = newBlock.getHash();
                    System.out.println("\n---SELECTED PROPERTY DETAILS---");
                    System.out.println("Property ID    : " + selectedProperty.getPropertyID());
                    System.out.println("Property Type  : " + selectedProperty.getPropertyType());
                    System.out.println("Property Price : " + selectedProperty.getPrice());
                    System.out.println("Address        : " + selectedProperty.getAddress());
                    System.out.println("Contact Number :" + selectedProperty.getContact());
                    System.out.println("\n---BLOCKCHAIN---");
                    Iterator<PropertyBlock> blockIterator = blockchain.iterator();

                    while (blockIterator.hasNext()) {
                        PropertyBlock block = blockIterator.next();
                        System.out.println(block.toString());
                    }

                    Scanner loanScanner = new Scanner(System.in);
                    System.out.print("Apply for a housing loan for this property? (yes/no): ");
                    String loanChoice = loanScanner.next();
                    if (loanChoice.equalsIgnoreCase("yes")) {
                        // call apply loan class
                        ApplyLoan applyLoan = new ApplyLoan(selectedProperty);
                        applyLoan.applyForLoan();
                    } else if (loanChoice.equalsIgnoreCase("no")) {
                        System.out.println("\n---SELECTED PROPERTY DETAILS---");
                        System.out.println("Property ID    : " + selectedProperty.getPropertyID());
                        System.out.println("Property Type  : " + selectedProperty.getPropertyType());
                        System.out.println("Property Price : " + selectedProperty.getPrice());
                        System.out.println("Address        : " + selectedProperty.getAddress());
                        System.out.println("Contact Number :" + selectedProperty.getContact());
                        Scanner priceScanner = new Scanner(System.in);
                        System.out.print("\nEnter the exact property price : RM");
                        double enteredPrice = priceScanner.nextDouble();
                        if (validatePropertyPrice(selectedProperty, enteredPrice)) {
                            // Generate keys just before purchase
                            //Keyforagreement.generateKeyPair();

                            Date currentDate = new Date();
                            String signingDate = currentDate.toString();
                            Keyforagreement.generateKeyPair();
                            Scanner confirmationScanner = new Scanner(System.in);
                            DigitalSignature digitalSignature = new DigitalSignature();
                            String purchaseAgreement = "Purchase Agreement";
                            String digitalSignatureString = digitalSignature.signMessage(purchaseAgreement);
                            System.out.println("\nDigital Signature: " + digitalSignatureString);
                            boolean isSignatureValid = digitalSignature.verifySignature(purchaseAgreement, digitalSignatureString, digitalSignature.getPublicKey());
                            if (isSignatureValid) {
                                System.out.println("Your Digital Signature is valid. \nYour Purchase Agreement is digitally signed. \nCongratulations property is now yours.");
                                purchaseAgreement = purchaseAgreement + "\nSeller         : " + selectedProperty.getSeller() + "\n";
                                purchaseAgreement = purchaseAgreement + "Seller Contact : " + selectedProperty.getContact() + "\n" + "\n";
                                purchaseAgreement = purchaseAgreement + "Property ID    : " + selectedProperty.getPropertyID() + "\n";
                                purchaseAgreement = purchaseAgreement + "Property Type  : " + selectedProperty.getPropertyType() + "\n";
                                purchaseAgreement = purchaseAgreement + "Property Price : " + selectedProperty.getPrice() + "\n";
                                purchaseAgreement = purchaseAgreement + "Address        : " + selectedProperty.getAddress() + "\n";
                                purchaseAgreement = purchaseAgreement + "Signing Date   : " + signingDate + "\n";
                                String encryptedAgreement = encryptPurchaseAgreement(purchaseAgreement, Keyforagreement.getPublicKey());
                                System.out.println("Purchase Agreement is encrypted: " + encryptedAgreement);
                                System.out.println("\nDo you want to decrypt it? (yes/no): ");
                                String decryptConfirmation = confirmationScanner.next();
                                if (decryptConfirmation.equalsIgnoreCase("yes")) {
                                    String decryptedAgreement = decryptPurchaseAgreement(encryptedAgreement, Keyforagreement.getPrivateKey());
                                    System.out.println("\nDecrypted Agreement: \n" + decryptedAgreement);
                                    System.out.println("Thank you for your purchase!");
                                } else {
                                    System.out.println("Your agreement is encrypted. You selected not to view.");
                                    System.out.println("Thank you for your purchase!");
                                }
                                break;
                            }

                            System.out.println("Your Digital Signature is not valid.");
                        } else {
                            System.out.println("You have not completed the payment. Purchase is canceled.");
                        }
                    }
                } else {
                    if (choice == 0) {
                        System.out.println("Thank you.");
                        break;
                    }

                    System.out.println("Invalid option. Please type a valid Property ID or 0 to exit.");
                }
            }

            scanner.close();
        } catch (Exception e) {
            // Handle exceptions here
            e.printStackTrace();
        }
    }


    static boolean validatePropertyPrice(PropertyType selectedProperty, double enteredPrice) {
        return Math.abs(selectedProperty.getPrice() - enteredPrice) < 0.01;
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
