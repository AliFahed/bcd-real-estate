package com.group15.bcd_real_estate;

import java.util.Scanner;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
public class SignUp {
    public static void signUpProcess() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("---Signup Page---"); // Display a header
        System.out.println(); // Add a blank line

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        System.out.print("Enter a password: ");
        String password = scanner.nextLine();
        String hashedPassword = hashPassword(password);

        User newUser = new User(name, email, hashedPassword);
        saveUser(newUser);

        System.out.println("Sign up successful.");

        scanner.close();

        // call login process for user to login after signing up
        Login.loginProcess();

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

    public static void saveUser(User user) {
        try (FileOutputStream fileOut = new FileOutputStream("users.bin");
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {

            objectOut.writeObject(user);
            System.out.println("User saved successfully to users.bin");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
