package com.group15.bcd_real_estate;

import java.util.Scanner;
public class PropertyListing {
    public static void main(String[] args) {
        // Create an array of Property objects
        Property[] properties = {
                new Property("Apartment", 150000.0, "111, TPM, 50000 Kuala Lumpur"),
                new Property("Landed House", 300000.0, "222, TPM, 50000 Kuala Lumpur"),
                new Property("Condo", 200000.0, "333, TPM, 50000 Kuala Lumpur")
        };

        // Display property details
        System.out.println("Property Units Available:");
        for (int i = 0; i < properties.length; i++) {
            Property property = properties[i];
            System.out.println((i + 1) + ". Property Type: " + property.getPropertyType());
            System.out.println("   Price: RM" + property.getPrice());
            System.out.println("   Address: " + property.getAddress());
            System.out.println();
        }

        // Ask the user to choose a property
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of the property you want to choose: ");
        int choice = scanner.nextInt();

        if (choice >= 1 && choice <= properties.length) {
            Property selectedProperty = properties[choice - 1];
            System.out.println("You have chosen the following property: " + choice);
        } else {
            System.out.println("Invalid option. Please type a valid property number.");
        }

        Scanner loanscanner = new Scanner(System.in);
        System.out.print("Applying for a loan? (yes/no): ");
        String loanChoice = loanscanner.next();

        if (loanChoice.equalsIgnoreCase("yes")) {
            System.out.println("You have chosen to apply for a loan.");
            // Implement loan application logic here, loan amount after interest rate, calculate monthly payment

            loanscanner.close();
        } else {
            System.out.println("Thank you.");
        }

        scanner.close();
    } /*else {
        System.out.println("Invalid choice. Please select a valid property number.");
    } */
}
