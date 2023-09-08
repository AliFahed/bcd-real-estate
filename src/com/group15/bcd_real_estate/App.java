package com.group15.bcd_real_estate;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        System.out.println("---Welcome to Golden Properties---");

        while (true) {
            System.out.println();
            System.out.println("Choose a method to get started");
            System.out.println("1. Sign Up");
            System.out.println("2. Login");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            if (choice == 1) {
                SignUp.signUpProcess();
                break;
            } else if (choice == 2) {
                Login.loginProcess();
                break;
            } else {
                System.out.println("Invalid choice, Please try again.");
            }
        }
    }
}