package com.group15.bcd_real_estate;

public class Property {
    private String propertyType;
    private double price;
    private String address;

    public Property(String propertyType, double price, String address) {
        this.propertyType = propertyType;
        this.price = price;
        this.address = address;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public double getPrice() {
        return price;
    }

    public String getAddress() {
        return address;
    }
}
