package com.group15.bcd_real_estate;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("1. Sign Up");
        System.out.println("2. Login");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();

        if (choice == 1) {
            SignUp.signUpProcess();
        } else if (choice == 2) {
            Login.loginProcess();
        } else {
            System.out.println("Invalid choice.");
        }
    }
}