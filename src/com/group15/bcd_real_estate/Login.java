package com.group15.bcd_real_estate;

import java.util.*;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Login {
    public static void loginProcess() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("---Login Page---"); // Display a header
            System.out.println(); // Add a blank line

            System.out.print("Enter your email: ");
            String enteredEmail = scanner.nextLine(); // Use nextLine() to read the entire line

            System.out.print("Enter your password: ");
            String enteredPassword = scanner.nextLine(); // Use nextLine() to read the entire line

            User authenticatedUser = authenticate(enteredEmail, enteredPassword);

            if (authenticatedUser != null) {
                System.out.println("Login successful.");
                // Create an instance of PropertyListing and pass the authenticated user
                PropertyListing propertyListing = new PropertyListing();
                propertyListing.setAuthenticatedUser(authenticatedUser); // Set the authenticated user
                propertyListing.propertyListingProcess();
            } else {
                System.out.println("Login failed.");
            }
        } catch (NoSuchElementException e) {
            // Handle NoSuchElementException here, if necessary.
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private static User authenticate(String email, String password) {
        try (FileInputStream fileIn = new FileInputStream("users.bin");
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {

            User storedUser;
            while (true) {
                try {
                    storedUser = (User) objectIn.readObject();

                    if (storedUser.getEmail().equals(email)) {
                        String enteredPasswordHash = hashPassword(password);

                        if (storedUser.getPassword().equals(enteredPasswordHash)) {
                            return storedUser; // Authentication successful
                        }
                    }
                } catch (ClassNotFoundException e) {
                    // End of file, no more users to check
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null; // Authentication failed
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes());

            StringBuilder hashString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                hashString.append(Integer.toString((hashByte & 0xff) + 0x100, 16).substring(1));
            }

            return hashString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
